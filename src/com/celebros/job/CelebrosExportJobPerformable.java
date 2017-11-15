/**
 *
 */
package com.celebros.job;

import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.cronjob.enums.CronJobResult;
import de.hybris.platform.cronjob.enums.CronJobStatus;
import de.hybris.platform.servicelayer.cronjob.AbstractJobPerformable;
import de.hybris.platform.servicelayer.cronjob.PerformResult;
import de.hybris.platform.solrfacetsearch.config.IndexConfig;
import de.hybris.platform.solrfacetsearch.config.exceptions.FieldValueProviderException;
import de.hybris.platform.solrfacetsearch.provider.FieldValueProvider;
import de.hybris.platform.util.Config;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.log4j.Logger;
import org.apache.solr.internal.csv.writer.CSVField;
import org.apache.solr.internal.csv.writer.CSVWriter;

import com.celebros.data.CelebrosExportData;
import com.celebros.data.CelebrosLookupLineData;
import com.celebros.gateway.CelebrosGateway;
import com.celebros.model.CelebrosExportCronjobModel;
import com.celebros.model.CelebrosIndexAttributeModel;
import com.celebros.service.CelebrosExportService;
import com.celebros.util.CelebrosUtils;
import com.celebros.visitor.CelebrosVisitor;
import com.celebros.visitor.impl.CelebrosProductVisitor;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;


/**
 * Performable class for CelebrosExportJob
 */
public class CelebrosExportJobPerformable extends AbstractJobPerformable<CelebrosExportCronjobModel>
{

	CelebrosExportService celebrosExportService;
	CelebrosGateway celebrosGateway;
	Map<String, FieldValueProvider> mandatoryValueProviders;

	@Override
	public PerformResult perform(final CelebrosExportCronjobModel cronjob)
	{
		final String productExportTableName = Config.getParameter("celebros.index.export.filename");
		final String exportDir = Config.getParameter("celebros.index.export.dir");
		final long timestamp = System.currentTimeMillis();
		final CelebrosExportData loggerExportData = celebrosExportService.getLoggerExportData(cronjob.getSite(), timestamp);
		final Logger log = celebrosExportService.createLogger(CelebrosExportJobPerformable.class, loggerExportData);
		Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler()
		{
			@Override
			public void uncaughtException(final Thread t, final Throwable e)
			{
				log.error("Uncaught exception in thread: " + t.getName(), e);
			}
		});
		final Map<CelebrosIndexAttributeModel, FieldValueProvider> attributes = celebrosExportService.getValueProviders(cronjob
				.getCustomAttributes());
		//Build dummy attributeModel for category

		attributes.put(buildCategoryAttribute(), mandatoryValueProviders.get("category"));

		final Map<CelebrosIndexAttributeModel, Set<CelebrosLookupLineData>> lookupAttributes = new HashMap<CelebrosIndexAttributeModel, Set<CelebrosLookupLineData>>();
		final IndexConfig config = getCelebrosExportService().buildIndexConfig(cronjob.getSite(), cronjob.getSessionLanguage(),
				cronjob.getSessionCurrency());
		final List<CelebrosExportData> exportFiles = Lists.newArrayList();

		//Visit products
		final CelebrosExportData productsData = celebrosExportService.buildCelebrosExportData(productExportTableName, "",
				cronjob.getSessionLanguage(), cronjob.getSite(), timestamp);
		log.info("Building Maps");
		try
		{
			final BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(exportDir
					+ File.separator + productsData.getFilename()), "UTF-8"));
			final CSVWriter productWriter = getCelebrosExportService().createCSVWriter(attributes.keySet(), bufferedWriter);
			log.info("Created product CSV writer with " + productWriter.getConfig().getFields().length + " columns "
					+ fieldNames(productWriter.getConfig().getFields()));
			final CelebrosVisitor<ProductModel> visitor = new CelebrosProductVisitor(config, lookupAttributes, attributes,
					mandatoryValueProviders, celebrosExportService, productWriter, cronjob.getBaseUrl(), cronjob.getImageBaseUrl(),
					log);
			celebrosExportService.visitAllProducts(log, cronjob.getSite(), cronjob.getProductType(), visitor);
			bufferedWriter.close();
		}
		catch (final FieldValueProviderException | IOException e)
		{
			e.printStackTrace();
			log.error("Error", e);
			return new PerformResult(CronJobResult.ERROR, CronJobStatus.FINISHED);
		}

		//Log lookupMap Details
		for (final Entry<CelebrosIndexAttributeModel, Set<CelebrosLookupLineData>> entry : lookupAttributes.entrySet())
		{
			log.info("Added " + entry.getValue().size() + " lines to " + entry.getKey().getAttributeName() + " lookup table");
		}

		//Write Lookup Tables
		for (final CelebrosIndexAttributeModel attribute : lookupAttributes.keySet())
		{
			final StringWriter lookupStringWriter = new StringWriter();
			final CSVWriter lookupWriter = celebrosExportService.createLookupCSVWriter(lookupStringWriter);
			log.info("Created " + attribute.getAttributeName() + " lookup table CSV writer");
			for (final CelebrosLookupLineData lineData : lookupAttributes.get(attribute))
			{
				final Map<String, String> lookupTableLine = Maps.newHashMap();
				CelebrosUtils.put(lookupTableLine, "id", lineData.getId());
				CelebrosUtils.put(lookupTableLine, "name", lineData.getName());
				CelebrosUtils.put(lookupTableLine, "parentId", lineData.getParentId());
				log.debug("adding " + lineData.getId() + " to " + attribute.getAttributeName() + " lookup Table");
				lookupWriter.writeRecord(lookupTableLine);
			}
			exportFiles.add(celebrosExportService.buildCelebrosExportData(attribute.getAttributeName(),
					lookupStringWriter.toString(), cronjob.getSessionLanguage(), cronjob.getSite(), timestamp));
		}

		//Write lookup files to FS
		for (final CelebrosExportData exportFile : exportFiles)
		{
			log.info("Saving file " + exportFile.getFilename());
			celebrosGateway.saveExport(exportFile);
		}

		//add log exportFile
		exportFiles.add(loggerExportData);
		// add products to exportFile
		exportFiles.add(productsData);


		log.info("Zipping up " + exportFiles.size() + " files and uploading to " + cronjob.getRemoteHost());
		if (celebrosGateway.zipAndUpload(celebrosExportService.buildCelebrosUploadData(exportFiles, cronjob.getRemoteHost(),
				cronjob.getRemotePort(), cronjob.getRemoteLocation(), cronjob.getUsername(), cronjob.getPassword(),
				cronjob.getSite(), timestamp)))
		{
			return new PerformResult(CronJobResult.SUCCESS, CronJobStatus.FINISHED);
		}
		else
		{
			return new PerformResult(CronJobResult.ERROR, CronJobStatus.FINISHED);
		}
	}

	private List<String> fieldNames(final CSVField[] fields)
	{
		final List<String> fieldNames = Lists.newArrayList();
		for (final CSVField field : fields)
		{
			fieldNames.add(field.getName());
		}
		return fieldNames;
	}

	private CelebrosIndexAttributeModel buildCategoryAttribute()
	{
		final CelebrosIndexAttributeModel categoryAttribute = new CelebrosIndexAttributeModel();
		categoryAttribute.setAttributeName("category");
		categoryAttribute.setLookupTable(Boolean.TRUE);
		categoryAttribute.setLocalized(true);
		return categoryAttribute;
	}

	public CelebrosExportService getCelebrosExportService()
	{
		return celebrosExportService;
	}

	public void setCelebrosExportService(final CelebrosExportService celebrosExportService)
	{
		this.celebrosExportService = celebrosExportService;
	}

	public CelebrosGateway getCelebrosGateway()
	{
		return celebrosGateway;
	}

	public void setCelebrosGateway(final CelebrosGateway celebrosGateway)
	{
		this.celebrosGateway = celebrosGateway;
	}

	public Map<String, FieldValueProvider> getMandatoryValueProviders()
	{
		return mandatoryValueProviders;
	}

	public void setMandatoryValueProviders(final Map<String, FieldValueProvider> mandatoryValueProviders)
	{
		this.mandatoryValueProviders = mandatoryValueProviders;
	}

}
