/**
 *
 */
package com.celebros.service;

import de.hybris.platform.cms2.model.site.CMSSiteModel;
import de.hybris.platform.core.model.c2l.CurrencyModel;
import de.hybris.platform.core.model.c2l.LanguageModel;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.core.model.type.ComposedTypeModel;
import de.hybris.platform.solrfacetsearch.config.IndexConfig;
import de.hybris.platform.solrfacetsearch.config.IndexedProperty;
import de.hybris.platform.solrfacetsearch.config.exceptions.FieldValueProviderException;
import de.hybris.platform.solrfacetsearch.provider.FieldValueProvider;

import java.io.IOException;
import java.io.Writer;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.solr.internal.csv.writer.CSVWriter;
//import org.springframework.integration.Message;
import org.springframework.messaging.Message;

import com.celebros.data.CelebrosExportData;
import com.celebros.data.CelebrosUploadData;
import com.celebros.model.CelebrosIndexAttributeModel;
import com.celebros.visitor.CelebrosVisitor;


/**
 * Service to handle the exporting of Celebros Data
 */
public interface CelebrosExportService
{

	/**
	 * Visits all products for the given criteria
	 *
	 * @param log
	 *           Logger for logging
	 * @param site
	 *           The CMSSite the products must belong to
	 * @param productType
	 *           the ComposedType of the products to visit
	 * @param visitor
	 *           the Visitor implementation to invoke against each product
	 */
	void visitAllProducts(final Logger log, final CMSSiteModel site, ComposedTypeModel productType,
			CelebrosVisitor<ProductModel> visitor) throws IOException, FieldValueProviderException;

	/**
	 * Retrieve a map of valueproviders against CelebrosAttributes
	 *
	 * @param attributes
	 *           List of CelebrosIndexAttributeModel for which to retrieve value providers
	 * @return A map of value provders against their corresponding attribute
	 */
	Map<CelebrosIndexAttributeModel, FieldValueProvider> getValueProviders(List<CelebrosIndexAttributeModel> attributes);

	/**
	 * Build a dummy IndexConfig for a site,language and currency
	 *
	 * @param site
	 *           The site to use in the dummy IndexConfig
	 * @param language
	 *           the Language to use in the dummy IndexConfig
	 * @param currency
	 *           the currency to use in the dummy IndexConfig
	 * @return The dummy IndexConfig
	 */
	IndexConfig buildIndexConfig(final CMSSiteModel site, final LanguageModel language, final CurrencyModel currency);

	/**
	 * Build a dummy IndexProperty for a CelebrosIndexAttribute
	 *
	 * @param attribute
	 *           The CelebrosIndexAttribute to build the dummy IndexProperty for
	 */
	IndexedProperty buildIndexedProperty(final CelebrosIndexAttributeModel attribute);

	/**
	 * Build a CSVWriter for a set of attributes
	 *
	 * @param attributes
	 *           list of attributes to use as columns
	 * @param bufferedWriter
	 *           Output of the csvWriter
	 * @return csvWriter built from attributes
	 */
	CSVWriter createCSVWriter(Collection<CelebrosIndexAttributeModel> attributes, Writer writer);

	/**
	 * Build a CSVWriter for a lookup file
	 *
	 * @param stringWriter
	 *           Output for the csvWriter
	 * @return csvWriter built for a lookup file
	 */
	CSVWriter createLookupCSVWriter(Writer writer);

	/**
	 * get the URL of a product
	 *
	 * @param product
	 *           the Product in question
	 * @return the URL of the product
	 */
	String getProductUrl(ProductModel product);

	/**
	 * get the primary Image URL of a produt
	 *
	 * @param product
	 *           the Product in question
	 * @return the primary image URL of the product
	 */
	String getImageUrl(ProductModel product);

	/**
	 * Build a CelebrosExportData object from the given components ready for export
	 *
	 * @param table
	 *           The name of the exported 'table'
	 * @param data
	 *           The content of the export
	 * @param language
	 *           The Language of the export
	 * @param site
	 *           The site of the export
	 * @param timestamp
	 *           The timestamp of the export
	 * @return CelebrosExportData representation of the given components ready for export
	 */
	CelebrosExportData buildCelebrosExportData(String table, String data, LanguageModel language, CMSSiteModel site, long timestamp);

	/**
	 * Build a CelebrosUploadData object with upload details and ExportDatas to upload
	 *
	 * @param exports
	 *           List of CelebrosExportDatas to upload
	 * @param host
	 *           Remote host to upload to
	 * @param port
	 *           Remote port to upload to
	 * @param location
	 *           Location at remote host to upload to
	 * @param username
	 *           Username to login to remote host
	 * @param password
	 *           Password to login to remote host
	 * @param site
	 *           Site of the export
	 * @param timestamp
	 *           Timestamp of the export
	 * @return CelebrosUploadData object with upload details and list of ExportData to upload.
	 */
	CelebrosUploadData buildCelebrosUploadData(List<CelebrosExportData> exports, String host, Integer port, String location,
			String username, String password, CMSSiteModel site, long timestamp);

	/**
	 * Generate the filename for a CelebrosExportData and add to message headers
	 *
	 * @param incMessage
	 *           Spring message containing CelebrosExportData
	 * @return Spring message containing filename
	 */
	Message<String> generateFilename(Message<CelebrosExportData> incMessage);

	/**
	 * Given a collection of Celebros attributes, return the lookup attributes
	 *
	 * @param attributes
	 *           Full list of Celebros Attributes
	 * @return Lookup attributes from the provided list
	 */
	List<CelebrosIndexAttributeModel> lookupAttributes(Collection<CelebrosIndexAttributeModel> attributes);

	/**
	 * Upload zip described in spring message to remote server
	 *
	 * @param message
	 *           Spring message containing CelebrosUploadData
	 * @param filePathHeader
	 *           Header demonstrating where the zip file is located
	 * @return Success or Failure of upload
	 */
	boolean uploadZip(Message<CelebrosUploadData> message, String filePathHeader);

	/**
	 * Compress exported files
	 *
	 * @param message
	 *           Spring message containing CelebrosUploadData describing exported files
	 * @param filePathHeader
	 *           Location of exported files
	 * @return Pass through of Spring message
	 */
	Message<CelebrosUploadData> zipFiles(Message<CelebrosUploadData> message, String filePathHeader);

	/**
	 * Get CelebrosExportData for log file to export
	 *
	 * @param site
	 *           Site for export
	 * @param timestamp
	 *           Timestamp of export
	 * @return
	 */
	CelebrosExportData getLoggerExportData(CMSSiteModel site, long timestamp);

	/**
	 * create logger from CelebrosExport and class
	 *
	 * @param clazz
	 *           class
	 * @param loggerData
	 *           CelebrosExportData
	 * @return Logger to be used
	 */
	Logger createLogger(Class clazz, CelebrosExportData loggerData);
}
