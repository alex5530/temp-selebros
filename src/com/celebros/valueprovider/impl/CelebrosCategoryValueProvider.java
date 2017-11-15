/**
 *
 */
package com.celebros.valueprovider.impl;

import de.hybris.platform.category.model.CategoryModel;
import de.hybris.platform.core.model.c2l.LanguageModel;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.solrfacetsearch.config.IndexConfig;
import de.hybris.platform.solrfacetsearch.config.IndexedProperty;
import de.hybris.platform.solrfacetsearch.config.exceptions.FieldValueProviderException;
import de.hybris.platform.solrfacetsearch.provider.FieldValue;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

import org.apache.commons.lang.StringUtils;

import com.celebros.data.CelebrosLookupLineData;
import com.celebros.valueprovider.CelebrosLookupValueProvider;
import com.google.common.collect.Lists;


/**
 * Celebros lookup provider for a product's Category attribute
 */
public class CelebrosCategoryValueProvider extends CelebrosLookupValueProvider
{

	@Override
	public Collection<FieldValue> getFieldValues(final IndexConfig config, final IndexedProperty property, final Object obj)
			throws FieldValueProviderException
	{
		final List<CelebrosLookupLineData> lineDatas = getCategoryLookupValues(config, property, obj, false);
		final List<FieldValue> output = new ArrayList<FieldValue>();
		for (final CelebrosLookupLineData lineData : lineDatas)
		{
			output.add(new FieldValue("", lineData.getId()));
		}
		return output;
	}

	public List<CelebrosLookupLineData> getCategoryLookupValues(final IndexConfig config, final IndexedProperty property,
			final Object obj, final Boolean includeParents) throws FieldValueProviderException
	{
		final List<CelebrosLookupLineData> output = Lists.newArrayList();
		if (obj instanceof ProductModel)
		{
			final ProductModel product = (ProductModel) obj;
			for (final LanguageModel language : config.getLanguages())
			{
				for (final CategoryModel category : product.getSupercategories())
				{
					buildCategoryLookupData(category, output, language, true, includeParents);
				}
			}
			return output;
		}
		else
		{
			throw new FieldValueProviderException("Object is not a product");
		}
	}

	/**
	 * @see com.celebros.valueprovider.CelebrosLookupValueProvider#getLookupValues(de.hybris.platform.solrfacetsearch.config.IndexConfig,
	 *      de.hybris.platform.solrfacetsearch.config.IndexedProperty, java.lang.Object)
	 */
	@Override
	public List<CelebrosLookupLineData> getLookupValues(final IndexConfig config, final IndexedProperty property, final Object obj)
			throws FieldValueProviderException
	{
		return getCategoryLookupValues(config, property, obj, true);
	}

	void buildCategoryLookupData(final CategoryModel category, final List<CelebrosLookupLineData> output,
			final LanguageModel language, final Boolean addToOutput, final Boolean includeParents)
	{
		final CelebrosLookupLineData lineData = new CelebrosLookupLineData();
		lineData.setName(category.getName(Locale.forLanguageTag(language.getIsocode())));
		lineData.setId(category.getPk().toString());
		final List<String> parents = Lists.newArrayList();
		for (final CategoryModel superCategory : category.getSupercategories())
		{
			parents.add(superCategory.getPk().toString());
			buildCategoryLookupData(superCategory, output, language, includeParents, includeParents);
		}
		lineData.setParentId(StringUtils.join(parents, ','));
		if (addToOutput)
		{
			output.add(lineData);
		}
	}


}
