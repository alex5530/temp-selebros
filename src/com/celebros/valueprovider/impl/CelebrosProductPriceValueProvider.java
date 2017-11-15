/**
 *
 */
package com.celebros.valueprovider.impl;

import de.hybris.platform.solrfacetsearch.config.IndexConfig;
import de.hybris.platform.solrfacetsearch.config.IndexedProperty;
import de.hybris.platform.solrfacetsearch.config.exceptions.FieldValueProviderException;
import de.hybris.platform.solrfacetsearch.provider.FieldValue;
import de.hybris.platform.solrfacetsearch.provider.impl.ProductPriceValueProvider;

import java.util.Collection;
import java.util.Collections;


/**
 * Celebros value provider for product price attribute
 */
public class CelebrosProductPriceValueProvider extends ProductPriceValueProvider
{

	/**
	 * @see de.hybris.platform.solrfacetsearch.provider.FieldValueProvider#getFieldValues(de.hybris.platform.solrfacetsearch.config.IndexConfig,
	 *      de.hybris.platform.solrfacetsearch.config.IndexedProperty, java.lang.Object)
	 */
	@Override
	public Collection<FieldValue> getFieldValues(final IndexConfig indexConfig, final IndexedProperty indexedProperty,
			final Object model) throws FieldValueProviderException
	{
		// YTODO Auto-generated method stub
		Collection<FieldValue> values = super.getFieldValues(indexConfig, indexedProperty, model);
		if (values.isEmpty())
		{
			values = Collections.singleton(new FieldValue("", 0d));
		}
		return values;
	}

}
