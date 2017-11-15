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
import de.hybris.platform.variants.model.VariantProductModel;

import java.util.Collection;
import java.util.Collections;


/**
 * Celebros value provider for parent product attribute
 */
public class CelebrosParentProductValueProvider implements FieldValueProvider
{

	/**
	 * @see de.hybris.platform.solrfacetsearch.provider.FieldValueProvider#getFieldValues(de.hybris.platform.solrfacetsearch.config.IndexConfig,
	 *      de.hybris.platform.solrfacetsearch.config.IndexedProperty, java.lang.Object)
	 */
	@Override
	public Collection<FieldValue> getFieldValues(final IndexConfig config, final IndexedProperty property, final Object obj)
			throws FieldValueProviderException
	{
		if (obj instanceof VariantProductModel)
		{
			final VariantProductModel product = (VariantProductModel) obj;
			if (product.getBaseProduct() != null)
			{
				return Collections.singletonList(new FieldValue("", product.getBaseProduct().getCode()));
			}
			else
			{
				return Collections.singletonList(new FieldValue("", ""));
			}
		}
		else
		{
			if (obj instanceof ProductModel)
			{
				final ProductModel product = (ProductModel) obj;
				throw new FieldValueProviderException(product.getCode() + " is not a variant product");
			}
			else
			{
				throw new FieldValueProviderException("Object is not a product");
			}
		}
	}

}
