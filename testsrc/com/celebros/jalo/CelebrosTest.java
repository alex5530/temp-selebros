/*
 * [y] hybris Platform
 *
 * Copyright (c) 2000-2015 hybris AG
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of hybris
 * ("Confidential Information"). You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the
 * license agreement you entered into with hybris.
 *
 *
 */
package com.celebros.jalo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import de.hybris.platform.basecommerce.model.site.BaseSiteModel;
import de.hybris.platform.catalog.enums.ArticleApprovalStatus;
import de.hybris.platform.catalog.model.CatalogModel;
import de.hybris.platform.catalog.model.CatalogVersionModel;
import de.hybris.platform.catalog.model.classification.ClassAttributeAssignmentModel;
import de.hybris.platform.cms2.model.site.CMSSiteModel;
import de.hybris.platform.commerceservices.impersonation.ImpersonationContext;
import de.hybris.platform.commerceservices.impersonation.ImpersonationService;
import de.hybris.platform.core.model.c2l.CurrencyModel;
import de.hybris.platform.core.model.c2l.LanguageModel;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.core.model.type.ComposedTypeModel;
import de.hybris.platform.servicelayer.type.TypeService;
import de.hybris.platform.site.BaseSiteService;
import de.hybris.platform.solrfacetsearch.config.IndexConfig;
import de.hybris.platform.solrfacetsearch.config.IndexedProperty;
import de.hybris.platform.solrfacetsearch.config.exceptions.FieldValueProviderException;
import de.hybris.platform.solrfacetsearch.provider.FieldValueProvider;
import de.hybris.platform.testframework.HybrisJUnit4TransactionalTest;
import de.hybris.platform.util.Config;

import java.io.BufferedWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.apache.solr.internal.csv.writer.CSVWriter;
import org.junit.Test;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.GenericMessage;
import com.celebros.data.CelebrosExportData;
import com.celebros.data.CelebrosUploadData;
import com.celebros.model.CelebrosIndexAttributeModel;
import com.celebros.service.CelebrosExportService;
import com.celebros.valueprovider.impl.CelebrosModelAttributeValueProvider;
import com.celebros.valueprovider.impl.CelebrosParentProductValueProvider;
import com.celebros.visitor.CelebrosVisitor;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;


/**
 * JUnit Tests for the Celebros extension
 */
public class CelebrosTest extends HybrisJUnit4TransactionalTest
{

	private final String[] PRODUCT_FIELDS =
	{ "id", "title", "price", "link", "imageURL", "description", "parentProduct" };
	private final String[] LOOKUP_FIELDS =
	{ "id", "name", "parentId" };
	private final String ATTRIBUTE_NAME = "attributeName";
	private static final String ZIP_FILE_NAME = Config.getParameter("celebros.index.export.zip.filename");

	@Resource
	private CelebrosExportService celebrosExportService;
	@Resource
	private BaseSiteService baseSiteService;
	@Resource
	private TypeService typeService;
	@Resource
	private ImpersonationService impersonationService;

	private static final Logger LOG = Logger.getLogger(CelebrosTest.class.getName());

	/**
	 * Test for CelebrosExportService.visitAllProducts
	 *
	 * @throws Exception
	 */
	@Test
	public void testVisitAllProducts() throws Exception
	{
		//Retrieve site
		final List<BaseSiteModel> sites = new ArrayList<BaseSiteModel>(baseSiteService.getAllBaseSites());

		//Ensure we actually have sites
		assertTrue(sites.size() > 0);

		//get first CMSSite
		final CMSSiteModel site = getCMSSiteFromBaseSites(sites);

		//get productType
		final ComposedTypeModel productType = typeService.getComposedTypeForClass(ProductModel.class);

		//Create visitor
		final CelebrosVisitor<ProductModel> visitor = new CelebrosVisitor<ProductModel>()
		{

			@Override
			public void visit(final ProductModel item) throws FieldValueProviderException
			{
				//Ensure product is approved, in the correct catalog and of the right type
				assertEquals(item.getApprovalStatus(), ArticleApprovalStatus.APPROVED);
				assertEquals(item.getCatalogVersion(), site.getDefaultCatalog().getActiveCatalogVersion());
				assertTrue(typeService.isInstance(productType, item));
			}

		};

		//Visit all products in context
		impersonationService.executeInContext(createImpersonationContext(site),
				new ImpersonationService.Executor<Object, Exception>()
				{

					@Override
					public Object execute() throws Exception
					{
						celebrosExportService.visitAllProducts(LOG, site, productType, visitor);
						return null;
					}

				});
	}

	/**
	 *
	 * Test for CelebrosExportService.getValueProviders
	 *
	 */
	@Test
	public void testGetValueProviders()
	{
		final List<CelebrosIndexAttributeModel> attributes = Lists.newArrayList();

		//Enabled attribute with custom VP
		final CelebrosIndexAttributeModel customAttribute = new CelebrosIndexAttributeModel();
		customAttribute.setValueProviderBean("celebrosParentProductValueProvider");
		customAttribute.setEnabled(true);
		attributes.add(customAttribute);

		//Enabled attribute with default VP
		final CelebrosIndexAttributeModel defaultAttribute = new CelebrosIndexAttributeModel();
		defaultAttribute.setAttributeName("name");
		defaultAttribute.setEnabled(true);
		attributes.add(defaultAttribute);

		//Disabled attribute
		final CelebrosIndexAttributeModel disabledAttribute = new CelebrosIndexAttributeModel();
		disabledAttribute.setEnabled(false);
		attributes.add(disabledAttribute);

		final Map<CelebrosIndexAttributeModel, FieldValueProvider> result = celebrosExportService.getValueProviders(attributes);
		//Ensure there are the correct number of items in the map
		assertEquals(result.size(), 2);
		//Make sure the value providers are correct
		assertTrue(result.get(customAttribute) instanceof CelebrosParentProductValueProvider);
		assertTrue(result.get(defaultAttribute) instanceof CelebrosModelAttributeValueProvider);
	}

	/**
	 *
	 * Test for CelebrosExportService.buildeIndexConfig
	 *
	 */
	@Test
	public void testBuildIndexConfig()
	{
		//Create mock data
		final CMSSiteModel site = new CMSSiteModel();
		final CatalogModel catalog = new CatalogModel();
		final CatalogVersionModel version = new CatalogVersionModel();
		catalog.setCatalogVersions(Sets.newHashSet(version));
		site.setDefaultCatalog(catalog);
		final LanguageModel language = new LanguageModel();
		final CurrencyModel currency = new CurrencyModel();

		//Build the index config
		final IndexConfig indexConfig = celebrosExportService.buildIndexConfig(site, language, currency);

		//Ensure mock data is stored in index config
		assertEquals(indexConfig.getBaseSite(), site);
		assertEquals(new ArrayList<CatalogVersionModel>(indexConfig.getCatalogVersions()).get(0), version);
		assertEquals(new ArrayList<LanguageModel>(indexConfig.getLanguages()).get(0), language);
		assertEquals(new ArrayList<CurrencyModel>(indexConfig.getCurrencies()).get(0), currency);
	}

	/**
	 *
	 * Test for CelebrosExportService.buildIndexedProperty
	 *
	 */
	@Test
	public void testBuildIndexedProperty()
	{
		//Create mock attribute
		final CelebrosIndexAttributeModel attribute = createMockAttribute();

		//Build the indexed property
		final IndexedProperty property = celebrosExportService.buildIndexedProperty(attribute);

		//Ensure mock attribute is represented in Indexed attribute
		assertEquals(property.getDisplayName(), ATTRIBUTE_NAME);
		assertEquals(property.getName(), ATTRIBUTE_NAME);
		assertEquals(property.getExportId(), ATTRIBUTE_NAME);
		assertEquals(property.getValueProviderParameter(), "parameter");
		assertEquals(property.getClassAttributeAssignment(), attribute.getClassAttributeAssignment());
	}

	/**
	 *
	 * Test for CelebrosExportService.createCSVWriter
	 *
	 */
	@Test
	public void testCreateCSVWriter()
	{
		//Create mock attribute
		final Collection<CelebrosIndexAttributeModel> attributes = Collections.singleton(createMockAttribute());

		//Create buffered writer
		final StringWriter stringWriter = new StringWriter();
		final BufferedWriter bufferedWriter = new BufferedWriter(stringWriter);

		//Create CSVWriter
		final CSVWriter csvWriter = celebrosExportService.createCSVWriter(attributes, bufferedWriter);
		LOG.info(stringWriter.toString());

		//Ensure the correct number of columns
		assertEquals(csvWriter.getConfig().getFields().length, PRODUCT_FIELDS.length + 1);

		//Check mandatory column names
		for (int index = 0; index < PRODUCT_FIELDS.length; index++)
		{
			assertEquals(csvWriter.getConfig().getFields()[index].getName(), PRODUCT_FIELDS[index]);
		}

		//Check new column name
		assertEquals(csvWriter.getConfig().getFields()[PRODUCT_FIELDS.length].getName(), ATTRIBUTE_NAME);
	}

	/**
	 *
	 * Test for CelebrosExportService.createLookupCSVWriter
	 *
	 */
	@Test
	public void testCreateLookupCSVWriter()
	{
		//Create string writer
		final StringWriter stringWriter = new StringWriter();

		//Create Lookup CSVWriter
		final CSVWriter csvWriter = celebrosExportService.createLookupCSVWriter(stringWriter);

		//Check number of columns
		assertEquals(csvWriter.getConfig().getFields().length, LOOKUP_FIELDS.length);
		for (int index = 0; index < LOOKUP_FIELDS.length; index++)
		{
			assertEquals(csvWriter.getConfig().getFields()[index].getName(), LOOKUP_FIELDS[index]);
		}
	}

	//Not testing productUrl or imageUrl

	/**
	 *
	 * Test for CelebrosExportService.buildCelebrosExportData
	 *
	 */
	@Test
	public void testBuildCelebrosExportData()
	{

		//Create\Retrieve mock site and language
		final Long timestamp = System.currentTimeMillis();
		final CMSSiteModel site = new CMSSiteModel();
		final CatalogModel catalog = new CatalogModel();
		final CatalogVersionModel version = new CatalogVersionModel();
		catalog.setCatalogVersions(Sets.newHashSet(version));
		site.setDefaultCatalog(catalog);
		final LanguageModel language = new LanguageModel();
		language.setIsocode("GB");

		//Build data
		final CelebrosExportData exportData = celebrosExportService.buildCelebrosExportData("table", "data", language, site,
				timestamp);

		//Test data is stored correctly.
		final String filename = site.getUid() + "_GB_table." + timestamp + ".txt";
		assertEquals(exportData.getTable(), "table");
		assertEquals(exportData.getData(), "data");
		assertEquals(exportData.getFilename(), filename);
	}

	/**
	 *
	 * Test for CelebrosExportService.buildCelebrosUploadData
	 *
	 */
	@Test
	public void testBuildCelebrosUploadData()
	{
		//Create\Retrieve mock site and language
		final Long timestamp = System.currentTimeMillis();
		final CMSSiteModel site = new CMSSiteModel();
		final CatalogModel catalog = new CatalogModel();
		final CatalogVersionModel version = new CatalogVersionModel();
		catalog.setCatalogVersions(Sets.newHashSet(version));
		site.setDefaultCatalog(catalog);
		final LanguageModel language = new LanguageModel();
		language.setIsocode("GB");

		//Build data
		final List<CelebrosExportData> exports = Collections.singletonList(celebrosExportService.buildCelebrosExportData("table",
				"data", language, site, timestamp));
		final CelebrosUploadData uploadData = celebrosExportService.buildCelebrosUploadData(exports, "host", 21, "/", "username",
				"password", site, timestamp);

		final String zipFileName = ZIP_FILE_NAME + "_" + site.getUid() + "-" + timestamp + ".zip";
		//Check the result
		assertEquals(uploadData.getExports(), exports);
		assertEquals(uploadData.getPassword(), "password");
		assertEquals(uploadData.getRemoteHost(), "host");
		assertEquals(uploadData.getRemoteLocation(), "/");
		assertEquals(uploadData.getRemotePort(), "21");
		assertEquals(uploadData.getUsername(), "username");
		assertEquals(uploadData.getZipFileName(), zipFileName);
	}

	/**
	 *
	 * Test for CelebrosExportService.generateFileName
	 *
	 */
	@Test
	public void testGenerateFileName()
	{
		//Create\Retrieve mock site and language
		final Long timestamp = System.currentTimeMillis();
		final CMSSiteModel site = new CMSSiteModel();
		final CatalogModel catalog = new CatalogModel();
		final CatalogVersionModel version = new CatalogVersionModel();
		catalog.setCatalogVersions(Sets.newHashSet(version));
		site.setDefaultCatalog(catalog);
		final LanguageModel language = new LanguageModel();
		language.setIsocode("GB");

		//Build data
		final CelebrosExportData exportData = celebrosExportService.buildCelebrosExportData("table", "data", language, site,
				timestamp);
		final Message<CelebrosExportData> dataMessage = new GenericMessage<CelebrosExportData>(exportData);

		//Generate filename
		final Message<String> message = celebrosExportService.generateFilename(dataMessage);

		//Ensure message is correct
		assertEquals(message.getPayload(), exportData.getData());
		assertEquals(message.getHeaders().get("filename"), exportData.getFilename());
	}

	/**
	 *
	 * Test for CelebrosExportService.lookupAttributes
	 *
	 */
	@Test
	public void testLookupAttributes()
	{
		//Create mock attributes
		final List<CelebrosIndexAttributeModel> attributes = Lists.newArrayList();
		final CelebrosIndexAttributeModel lookupAttribute = new CelebrosIndexAttributeModel();
		lookupAttribute.setLookupTable(true);
		attributes.add(lookupAttribute);
		final CelebrosIndexAttributeModel notLookupAttribute = new CelebrosIndexAttributeModel();
		notLookupAttribute.setLookupTable(false);
		attributes.add(notLookupAttribute);

		//filter for lookupAttributes
		final List<CelebrosIndexAttributeModel> lookupAttributes = celebrosExportService.lookupAttributes(attributes);

		//Make sure the correct attributes are in the result.
		assertEquals(lookupAttributes.size(), 1);
		assertTrue(lookupAttributes.contains(lookupAttribute));
		assertTrue(!lookupAttributes.contains(notLookupAttribute));
	}

	//Cannot test uploadZip or zipFiles

	/**
	 *
	 * Test for CelebrosExportService.getLoggerExportData
	 *
	 */
	@Test
	public void testGetLoggerExportData()
	{
		//Create\Retrieve mock site and language
		final Long timestamp = System.currentTimeMillis();
		final CMSSiteModel site = new CMSSiteModel();
		final CatalogModel catalog = new CatalogModel();
		final CatalogVersionModel version = new CatalogVersionModel();
		catalog.setCatalogVersions(Sets.newHashSet(version));
		site.setDefaultCatalog(catalog);
		final LanguageModel language = new LanguageModel();
		language.setIsocode("GB");

		//Create logger export Data
		final CelebrosExportData exportData = celebrosExportService.getLoggerExportData(site, timestamp);

		//Test result
		assertEquals(exportData.getTable(), "log");
		assertEquals(exportData.getFilename(), site.getUid() + "_" + timestamp + ".log");
	}

	//Not testing create logger

	/**
	 *
	 * Method to create a mock CelebrosIndexAttribute for use in tests
	 *
	 * @return a mock CelebrosIndexAttributeModel
	 */
	private CelebrosIndexAttributeModel createMockAttribute()
	{
		final ClassAttributeAssignmentModel classAttriuteAssignment = new ClassAttributeAssignmentModel();
		final CelebrosIndexAttributeModel attribute = new CelebrosIndexAttributeModel();
		attribute.setAttributeName(ATTRIBUTE_NAME);
		attribute.setValueProviderParameter("parameter");
		attribute.setLocalized(true);
		attribute.setClassAttributeAssignment(classAttriuteAssignment);
		return attribute;
	}

	/**
	 *
	 * Create an impersonation context from a CMSSiteModel
	 *
	 * @param site
	 *           CMSSiteModel to create the impersonation context of
	 * @return the impersonation context
	 */
	private ImpersonationContext createImpersonationContext(final CMSSiteModel site)
	{
		final ImpersonationContext ctx = new ImpersonationContext();
		ctx.setCatalogVersions(site.getDefaultCatalog().getCatalogVersions());
		ctx.setSite(site);
		return ctx;
	}

	/**
	 *
	 * Given a list of BaseSiteModels, select the first CMSSiteModel
	 *
	 * @param sites
	 *           List of BaseSiteModels
	 * @return the first CMSSiteModel in that list.
	 */
	private CMSSiteModel getCMSSiteFromBaseSites(final List<BaseSiteModel> sites)
	{
		CMSSiteModel site = null;
		for (final BaseSiteModel baseSite : sites)
		{
			if (baseSite instanceof CMSSiteModel)
			{
				site = (CMSSiteModel) baseSite;
				break;
			}
		}
		assertTrue(site != null);
		return site;
	}
}
