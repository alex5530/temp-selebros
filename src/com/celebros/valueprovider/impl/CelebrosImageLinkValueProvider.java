/**
 *
 */
package com.celebros.valueprovider.impl;

import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.solrfacetsearch.config.IndexConfig;
import de.hybris.platform.solrfacetsearch.config.IndexedProperty;
import de.hybris.platform.solrfacetsearch.config.exceptions.FieldValueProviderException;
import de.hybris.platform.solrfacetsearch.provider.FieldValue;
import de.hybris.platform.solrfacetsearch.provider.FieldValueProvider;

import java.util.Collection;
import java.util.Collections;

import com.celebros.service.CelebrosExportService;


/**
 * Value provider for a product's image link attribute for Celebros
 */
public class CelebrosImageLinkValueProvider implements FieldValueProvider
{

	CelebrosExportService celebrosExportService;

	/**
	 * @see de.hybris.platform.solrfacetsearch.provider.FieldValueProvider#getFieldValues(de.hybris.platform.solrfacetsearch.config.IndexConfig,
	 *      de.hybris.platform.solrfacetsearch.config.IndexedProperty, java.lang.Object)
	 */
	@Override
	public Collection<FieldValue> getFieldValues(final IndexConfig config, final IndexedProperty property, final Object obj)
			throws FieldValueProviderException
	{
		return Collections.singleton(new FieldValue("", celebrosExportService.getImageUrl((ProductModel) obj)));
	}

	public CelebrosExportService getCelebrosExportService()
	{
		return celebrosExportService;
	}

	public void setCelebrosExportService(final CelebrosExportService celebrosExportService)
	{
		this.celebrosExportService = celebrosExportService;
	}



}
