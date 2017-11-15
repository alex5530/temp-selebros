/**
 *
 */
package com.celebros.service.impl;

import de.hybris.platform.category.CategoryService;
import de.hybris.platform.category.model.CategoryModel;
import de.hybris.platform.cms2.model.site.CMSSiteModel;
import de.hybris.platform.commerceservices.url.impl.AbstractUrlResolver;
import de.hybris.platform.core.Registry;
import de.hybris.platform.core.model.c2l.CurrencyModel;
import de.hybris.platform.core.model.c2l.LanguageModel;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.core.model.type.ComposedTypeModel;
import de.hybris.platform.servicelayer.type.TypeService;
import de.hybris.platform.solrfacetsearch.config.IndexConfig;
import de.hybris.platform.solrfacetsearch.config.IndexedProperty;
import de.hybris.platform.solrfacetsearch.config.exceptions.FieldValueProviderException;
import de.hybris.platform.solrfacetsearch.provider.FieldValueProvider;
import de.hybris.platform.util.Config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Writer;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.log4j.FileAppender;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.apache.solr.internal.csv.writer.CSVConfig;
import org.apache.solr.internal.csv.writer.CSVField;
import org.apache.solr.internal.csv.writer.CSVWriter;
import org.springframework.integration.annotation.Header;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.util.StringUtils;

import com.celebros.dao.impl.CelebrosProductDao;
import com.celebros.data.CelebrosExportData;
import com.celebros.data.CelebrosUploadData;
import com.celebros.model.CelebrosIndexAttributeModel;
import com.celebros.service.CelebrosExportService;
import com.celebros.valueprovider.impl.CelebrosModelAttributeValueProvider;
import com.celebros.visitor.CelebrosVisitor;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.io.Files;


/**
 * Default implementation of CelebrosExportService
 */
public class DefaultCelebrosExportService implements CelebrosExportService
{

	private static final Logger LOG = Logger.getLogger(DefaultCelebrosExportService.class);
	private static final String ZIP_FILE_NAME = Config.getParameter("celebros.index.export.zip.filename");
	private CategoryService categoryService;
	private AbstractUrlResolver<ProductModel> productModelUrlResolver;
	private TypeService typeService;
	private CelebrosProductDao celebrosProductDao;

	@Override
	public void visitAllProducts(final Logger log, final CMSSiteModel site, final ComposedTypeModel productType,
			final CelebrosVisitor<ProductModel> visitor) throws IOException, FieldValueProviderException
	{
		for (final ProductModel product : celebrosProductDao.getApprovedProductsInCatalogVersion(site.getDefaultCatalog()
				.getActiveCatalogVersion()))
		{
			if (typeService.isInstance(productType, product))
			{
				visitor.visit(product);
			}
		}
	}

	private void visitCategory(final CategoryModel category, final CelebrosVisitor<CategoryModel> visitor)
			throws FieldValueProviderException
	{
		visitor.visit(category);

		for (final CategoryModel child : category.getCategories())
		{
			visitCategory(child, visitor);
		}
	}

	@Override
	public Map<CelebrosIndexAttributeModel, FieldValueProvider> getValueProviders(
			final List<CelebrosIndexAttributeModel> attributes)
	{
		final Map<CelebrosIndexAttributeModel, FieldValueProvider> valueProviders = Maps.newHashMap();
		for (final CelebrosIndexAttributeModel attribute : attributes)
		{
			if (BooleanUtils.isTrue(attribute.getEnabled()))
			{
				if (StringUtils.isEmpty(attribute.getValueProviderBean()))
				{
					final CelebrosModelAttributeValueProvider valueProvider = new CelebrosModelAttributeValueProvider();
					valueProvider.setAttributeName(attribute.getAttributeName());
					valueProviders.put(attribute, valueProvider);
				}
				else
				{
					final FieldValueProvider valueProvider = (FieldValueProvider) Registry.getApplicationContext().getBean(
							attribute.getValueProviderBean());
					valueProviders.put(attribute, valueProvider);
				}
			}
		}
		return valueProviders;
	}

	@Override
	public IndexConfig buildIndexConfig(final CMSSiteModel site, final LanguageModel language, final CurrencyModel currency)
	{
		final IndexConfig config = new IndexConfig();
		config.setBaseSite(site);
		config.setCatalogVersions(site.getDefaultCatalog().getCatalogVersions());
		config.setCurrencies(Collections.singleton(currency));
		config.setLanguages(Collections.singleton(language));
		return config;
	}

	@Override
	public IndexedProperty buildIndexedProperty(final CelebrosIndexAttributeModel attribute)
	{
		final IndexedProperty property = new IndexedProperty();
		property.setDisplayName(attribute.getAttributeName());
		property.setName(attribute.getAttributeName());
		property.setExportId(attribute.getAttributeName());
		property.setType("string");
		property.setValueProviderParameter(attribute.getValueProviderParameter());
		property.setLocalized(attribute.getLocalized());
		property.setClassAttributeAssignment(attribute.getClassAttributeAssignment());
		return property;
	}

	@Override
	public CSVWriter createCSVWriter(final Collection<CelebrosIndexAttributeModel> attributes, final Writer writer)
	{
		final CSVConfig config = new CSVConfig();
		final Map<String, String> header = new HashMap<String, String>();
		config.setDelimiter('\t');
		config.addField(new CSVField("id", 75));
		header.put("id", "\"id\"");
		config.addField(new CSVField("title", 255));
		header.put("title", "\"title\"");
		config.addField(new CSVField("price", 75));
		header.put("price", "\"price\"");
		config.addField(new CSVField("link", 65535));
		header.put("link", "\"link\"");
		config.addField(new CSVField("imageURL", 65535));
		header.put("imageURL", "\"imageURL\"");
		config.addField(new CSVField("description", 65535));
		header.put("description", "\"description\"");
		config.addField(new CSVField("parentProduct", 65535));
		header.put("parentProduct", "\"parentProduct\"");
		for (final CelebrosIndexAttributeModel attribute : attributes)
		{
			config.addField(new CSVField(attribute.getAttributeName(), 65535));
			header.put(attribute.getAttributeName(), "\"" + attribute.getAttributeName() + "\"");
		}
		final CSVWriter csvWriter = new CSVWriter(config);
		csvWriter.setWriter(writer);
		csvWriter.writeRecord(header);
		return csvWriter;
	}

	@Override
	public CSVWriter createLookupCSVWriter(final Writer writer)
	{
		final CSVConfig config = new CSVConfig();
		final Map<String, String> header = new HashMap<String, String>();
		config.setDelimiter('\t');
		config.addField(new CSVField("id", 75));
		header.put("id", "\"id\"");
		config.addField(new CSVField("name", 65535));
		header.put("name", "\"name\"");
		config.addField(new CSVField("parentId", 65535));
		header.put("parentId", "\"parentId\"");
		final CSVWriter output = new CSVWriter(config);
		output.setWriter(writer);
		output.writeRecord(header);
		return output;
	}

	@Override
	public String getProductUrl(final ProductModel product)
	{
		return productModelUrlResolver.resolve(product);
	}

	@Override
	public String getImageUrl(final ProductModel product)
	{
		return product.getPicture() != null ? product.getPicture().getURL() : "";
	}

	@Override
	public CelebrosExportData buildCelebrosExportData(final String table, final String data, final LanguageModel language,
			final CMSSiteModel site, final long timestamp)
	{
		final CelebrosExportData exportData = new CelebrosExportData();
		exportData.setData(data.replaceAll("\\n", "\r\n"));
		exportData.setTable(table);
		exportData.setFilename(site.getUid() + "_" + language.getIsocode() + "_" + table + "." + timestamp + ".txt");
		return exportData;
	}

	@Override
	public CelebrosUploadData buildCelebrosUploadData(final List<CelebrosExportData> exports, final String host,
			final Integer port, final String location, final String username, final String password, final CMSSiteModel site,
			final long timestamp)
	{
		final CelebrosUploadData uploadData = new CelebrosUploadData();
		uploadData.setExports(exports);
		uploadData.setRemoteHost(host);
		uploadData.setRemotePort(String.valueOf(port));
		uploadData.setRemoteLocation(location);
		uploadData.setUsername(username);
		uploadData.setPassword(password);
		uploadData.setZipFileName(ZIP_FILE_NAME + "_" + site.getUid() + "-" + timestamp + ".zip");
		return uploadData;
	}

	@Override
	public Message<String> generateFilename(final Message<CelebrosExportData> incMessage)
	{
		final Map headers = new HashMap();
		headers.put("filename", incMessage.getPayload().getFilename());
		return new GenericMessage<String>(incMessage.getPayload().getData(), headers);
	}

	@Override
	public List<CelebrosIndexAttributeModel> lookupAttributes(final Collection<CelebrosIndexAttributeModel> attributes)
	{
		final List<CelebrosIndexAttributeModel> output = Lists.newArrayList();
		for (final CelebrosIndexAttributeModel attribute : attributes)
		{
			if (BooleanUtils.isTrue(attribute.getLookupTable()))
			{
				output.add(attribute);
			}
		}
		return output;
	}

	@Override
	public Message<CelebrosUploadData> zipFiles(final Message<CelebrosUploadData> message,
			@Header("filePath") final String filePathHeader)
	{
		try
		{
			final String filePath = filePathHeader + File.separatorChar;
			final CelebrosUploadData uploadData = message.getPayload();

			final FileOutputStream fileOutputStream = new FileOutputStream(filePath + uploadData.getZipFileName());
			final ZipOutputStream zipOutputStream = new ZipOutputStream(fileOutputStream);
			for (final CelebrosExportData exportData : uploadData.getExports())
			{
				final File file = new File(filePath + exportData.getFilename());
				addToZipFile(filePath + exportData.getFilename(), zipOutputStream, exportData.getTable() + ".txt");
				if (!file.delete())
				{
					LOG.error("Could not delete file " + file.getAbsolutePath() + " after upload");
				}
			}
			zipOutputStream.close();
			fileOutputStream.close();
		}
		catch (final IOException e)
		{
			LOG.error(e);
		}
		return message;
	}

	private void addToZipFile(final String fileName, final ZipOutputStream zos, final String targetFileName) throws IOException
	{
		final File file = new File(fileName);
		final FileInputStream fis = new FileInputStream(file);
		final ZipEntry zipEntry = new ZipEntry(targetFileName);
		try
		{
			zos.putNextEntry(zipEntry);

			final byte[] buffer = new byte[1024];
			int len;
			while ((len = fis.read(buffer)) > 0)
			{
				zos.write(buffer, 0, len);
			}

			zos.closeEntry();
			fis.close();
		}
		finally
		{
			fis.close();
		}
	}

	@Override
	public boolean uploadZip(final Message<CelebrosUploadData> message, @Header("filePath") final String filePathHeader)
	{
		boolean success = false;
		final CelebrosUploadData uploadData = message.getPayload();
		final String filePath = filePathHeader + File.separatorChar;
		final File zipFile = new File(filePath + uploadData.getZipFileName());
		final FTPClient ftpClient = new FTPClient();
		InputStream inputStream = null;
		try
		{
			ftpClient.connect(uploadData.getRemoteHost(), Integer.parseInt(uploadData.getRemotePort()));
			if (ftpClient.login(uploadData.getUsername(), uploadData.getPassword()))
			{

				ftpClient.enterLocalPassiveMode();
				inputStream = new FileInputStream(zipFile);
				LOG.info("Start upload of Celebros zip");
				ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
				final OutputStream outputStream = ftpClient.storeFileStream(uploadData.getRemoteLocation() + File.separatorChar
						+ ZIP_FILE_NAME + ".zip");
				final byte[] bytesIn = new byte[4096];
				int read = 0;

				while ((read = inputStream.read(bytesIn)) != -1)
				{
					outputStream.write(bytesIn, 0, read);
				}
				inputStream.close();
				outputStream.close();

				success = ftpClient.completePendingCommand();
			}
			else
			{
				LOG.error("Error logging into FTP share");
			}
		}
		catch (final IOException e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				if (inputStream != null)
				{
					inputStream.close();
				}
				if (ftpClient.isConnected())
				{
					ftpClient.logout();
					ftpClient.disconnect();
				}
			}
			catch (final IOException ex)
			{
				LOG.error(ex);
			}
		}
		File outputFile;
		if (success)
		{
			outputFile = new File(filePath + "success" + File.separator + uploadData.getZipFileName());
		}
		else
		{
			outputFile = new File(filePath + "failed" + File.separator + uploadData.getZipFileName());
		}
		if (new File(outputFile.getParent()).mkdir())
		{
			try
			{
				Files.move(zipFile, outputFile);
			}
			catch (final IOException e)
			{
				LOG.error(e);
			}
		}
		else
		{
			LOG.error("Error creating directories for " + outputFile.getParent());
		}
		return success;
	}

	@Override
	public CelebrosExportData getLoggerExportData(final CMSSiteModel site, final long timestamp)
	{
		final String logFileName = site.getUid() + "_" + timestamp + ".log";
		final CelebrosExportData exportData = new CelebrosExportData();
		exportData.setFilename(logFileName);
		exportData.setTable("log");
		return exportData;
	}

	@Override
	public Logger createLogger(final Class clazz, final CelebrosExportData loggerData)
	{
		final Logger logger = Logger.getLogger(clazz);
		try
		{
			final String exportDir = Config.getParameter("celebros.index.export.dir");
			final String layoutPattern = Config.getParameter("log4j.appender.CONSOLE.layout.ConversionPattern");
			final String logFileName = exportDir + File.separatorChar + loggerData.getFilename();
			final PatternLayout layout = new PatternLayout(layoutPattern);
			final FileAppender appender = new FileAppender(layout, logFileName, false);
			logger.addAppender(appender);
		}
		catch (final IOException e)
		{
			LOG.error(e);
		}
		return logger;
	}

	public AbstractUrlResolver<ProductModel> getProductModelUrlResolver()
	{
		return productModelUrlResolver;
	}

	public void setProductModelUrlResolver(final AbstractUrlResolver<ProductModel> productModelUrlResolver)
	{
		this.productModelUrlResolver = productModelUrlResolver;
	}

	public CategoryService getCategoryService()
	{
		return categoryService;
	}

	public void setCategoryService(final CategoryService categoryService)
	{
		this.categoryService = categoryService;
	}

	/**
	 * @return the typeService
	 */
	public TypeService getTypeService()
	{
		return typeService;
	}

	/**
	 * @param typeService
	 *           the typeService to set
	 */
	public void setTypeService(final TypeService typeService)
	{
		this.typeService = typeService;
	}

	/**
	 * @return the celebrosProductDao
	 */
	public CelebrosProductDao getCelebrosProductDao()
	{
		return celebrosProductDao;
	}

	/**
	 * @param celebrosProductDao
	 *           the celebrosProductDao to set
	 */
	public void setCelebrosProductDao(final CelebrosProductDao celebrosProductDao)
	{
		this.celebrosProductDao = celebrosProductDao;
	}



}
