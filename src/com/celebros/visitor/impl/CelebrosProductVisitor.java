/**
 *
 */
package com.celebros.visitor.impl;

import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.solrfacetsearch.config.IndexConfig;
import de.hybris.platform.solrfacetsearch.config.exceptions.FieldValueProviderException;
import de.hybris.platform.solrfacetsearch.provider.FieldValue;
import de.hybris.platform.solrfacetsearch.provider.FieldValueProvider;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.apache.solr.internal.csv.writer.CSVWriter;

import com.celebros.data.CelebrosLookupLineData;
import com.celebros.model.CelebrosIndexAttributeModel;
import com.celebros.service.CelebrosExportService;
import com.celebros.util.CelebrosUtils;
import com.celebros.valueprovider.CelebrosLookupValueProvider;
import com.celebros.visitor.CelebrosVisitor;
import com.google.common.collect.Maps;


/**
 * CelebrosVisitor for visiting all products
 *
 */
public class CelebrosProductVisitor implements CelebrosVisitor<ProductModel>
{
	private final IndexConfig config;
	private final Map<CelebrosIndexAttributeModel, Set<CelebrosLookupLineData>> lookupTables;
	private final Map<CelebrosIndexAttributeModel, FieldValueProvider> valueProviderMap;
	private final Map<String, FieldValueProvider> mandatoryValueProvidersMap;
	private final CelebrosExportService celebrosExportService;
	private final CSVWriter productWriter;
	private final String baseUrl;
	private final String imageBaseUrl;
	private final Logger log;

	final Set<CelebrosIndexAttributeModel> customAttributes;
	final List<CelebrosIndexAttributeModel> lookupAttributes;


	public CelebrosProductVisitor(final IndexConfig config,
			final Map<CelebrosIndexAttributeModel, Set<CelebrosLookupLineData>> lookupTables,
			final Map<CelebrosIndexAttributeModel, FieldValueProvider> valueProviderMap,
			final Map<String, FieldValueProvider> mandatoryValueProvidersMap, final CelebrosExportService celebrosExportService,
			final CSVWriter productWriter, final String baseUrl, final String imageBaseUrl, final Logger log)
	{
		this.config = config;
		this.lookupTables = lookupTables;
		this.valueProviderMap = valueProviderMap;
		this.mandatoryValueProvidersMap = mandatoryValueProvidersMap;
		this.celebrosExportService = celebrosExportService;
		this.productWriter = productWriter;
		this.baseUrl = baseUrl;
		this.imageBaseUrl = imageBaseUrl;
		this.log = log;

		//Get All custom Attributes
		customAttributes = valueProviderMap.keySet();
		log.info("Export contains " + customAttributes.size() + " custom attributes");

		//Get Lookup attributes
		lookupAttributes = celebrosExportService.lookupAttributes(valueProviderMap.keySet());
		log.info("Export contains " + lookupAttributes.size() + " custom attributes which are lookup tables");
	}


	@Override
	public void visit(final ProductModel product) throws FieldValueProviderException
	{


		//Add To/Create lookupTables
		for (final CelebrosIndexAttributeModel lookupAttribute : lookupAttributes)
		{
			final FieldValueProvider fieldValueProvider = valueProviderMap.get(lookupAttribute);
			if (fieldValueProvider instanceof CelebrosLookupValueProvider)
			{
				populateLookupTables(product, lookupAttribute, (CelebrosLookupValueProvider) fieldValueProvider);
			}
			else
			{
				log.error(lookupAttribute.getValueProviderBean()
						+ " is not a CelebrosLookupValueProvider, Cannot be used as a lookup attribute");
				throw new FieldValueProviderException(lookupAttribute.getValueProviderBean()
						+ " is not a CelebrosLookupValueProvider, Cannot be used as a lookup attribute");
			}
		}

		//Write the product CSV Line
		final Map<String, String> csvLine = Maps.newHashMap();
		//Put the mandatory values in
		putMandatoryValue(csvLine, "id", product);
		putMandatoryValue(csvLine, "title", product);
		putMandatoryValue(csvLine, "price", product);
		putMandatoryValue(csvLine, "link", product, baseUrl);
		putMandatoryValue(csvLine, "imageURL", product, imageBaseUrl);
		putMandatoryValue(csvLine, "description", product);
		putMandatoryValue(csvLine, "parentProduct", product);

		//Put custom values in
		for (final CelebrosIndexAttributeModel attribute : customAttributes)
		{
			final FieldValueProvider valueProvider = valueProviderMap.get(attribute);
			//No need to ensure lookup value providers are cool as this is checked above
			try
			{
				final Collection<FieldValue> values = valueProvider.getFieldValues(config,
						celebrosExportService.buildIndexedProperty(attribute), product);
				CelebrosUtils.put(csvLine, attribute.getAttributeName(), CelebrosUtils.concatFieldValues(values));
			}
			catch (final FieldValueProviderException e)
			{
				//Empty value due to FieldValueProviderException
				CelebrosUtils.put(csvLine, attribute.getAttributeName(), "");
				log.debug(attribute.getAttributeName() + " No value provided for product " + product.getCode() + ", leaving blank",
						e);
			}
		}
		productWriter.writeRecord(csvLine);
		log.debug("Adding product " + product.getCode() + " to product csv");
	}


	private void populateLookupTables(final ProductModel product, final CelebrosIndexAttributeModel lookupAttribute,
			final CelebrosLookupValueProvider valueProvider) throws FieldValueProviderException
	{
		final List<CelebrosLookupLineData> lineDatas = valueProvider.getLookupValues(config,
				celebrosExportService.buildIndexedProperty(lookupAttribute), product);
		if (savedLookupAttribute(lookupAttribute))
		{
			lookupTables.get(lookupAttribute).addAll(lineDatas);
		}
		else
		{
			final Set<CelebrosLookupLineData> lineDataSet = new HashSet<CelebrosLookupLineData>();
			lineDataSet.addAll(lineDatas);
			lookupTables.put(lookupAttribute, lineDataSet);
		}
	}

	private boolean savedLookupAttribute(final CelebrosIndexAttributeModel attribute)
	{
		for (final CelebrosIndexAttributeModel saved : lookupTables.keySet())
		{
			if (saved.getAttributeName().equals(attribute.getAttributeName()))
			{
				return true;
			}
		}
		return false;
	}

	private void putMandatoryValue(final Map line, final String attrName, final ProductModel product)
			throws FieldValueProviderException
	{
		putMandatoryValue(line, attrName, product, "");
	}

	private void putMandatoryValue(final Map line, final String attrName, final ProductModel product, final String prefix)
			throws FieldValueProviderException
	{
		//Build dummy attributeModel
		final CelebrosIndexAttributeModel attribute = new CelebrosIndexAttributeModel();
		attribute.setAttributeName(attrName);
		attribute.setLocalized(true);

		//Get value(s) and stick it in the map
		final FieldValueProvider valueProvider = mandatoryValueProvidersMap.get(attrName);
		try
		{
			final String value = CelebrosUtils.concatFieldValues(
					valueProvider.getFieldValues(config, celebrosExportService.buildIndexedProperty(attribute), product), prefix);
			CelebrosUtils.put(line, attrName, value);
		}
		catch (final FieldValueProviderException e)
		{
			CelebrosUtils.put(line, attrName, "");
			if (log.isDebugEnabled())
			{
				log.debug(attribute.getAttributeName() + " Exception for product " + product.getCode() + ", leaving blank", e);
			}
		}
	}
}
