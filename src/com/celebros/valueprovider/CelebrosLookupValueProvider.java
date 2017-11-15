/**
 *
 */
package com.celebros.valueprovider;

import de.hybris.platform.solrfacetsearch.config.IndexConfig;
import de.hybris.platform.solrfacetsearch.config.IndexedProperty;
import de.hybris.platform.solrfacetsearch.config.exceptions.FieldValueProviderException;
import de.hybris.platform.solrfacetsearch.provider.FieldValue;
import de.hybris.platform.solrfacetsearch.provider.FieldValueProvider;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.celebros.data.CelebrosLookupLineData;


/**
 * Abstract class to build value providers for Celebros lookup attributes
 */
public abstract class CelebrosLookupValueProvider implements FieldValueProvider
{
	@Override
	public Collection<FieldValue> getFieldValues(final IndexConfig config, final IndexedProperty property, final Object obj)
			throws FieldValueProviderException
	{
		final List<CelebrosLookupLineData> lineDatas = getLookupValues(config, property, obj);
		final List<FieldValue> output = new ArrayList<FieldValue>();
		for (final CelebrosLookupLineData lineData : lineDatas)
		{
			output.add(new FieldValue("", lineData.getId()));
		}
		return output;
	}

	/**
	 * Given an object, a Config and a IndexedProperty. Build a list of <CelebrosLookupLineData>
	 *
	 * @param config
	 *           the config of the Index
	 * @param property
	 *           the property to index
	 * @param obj
	 *           the object to index
	 *
	 * @return a list of CelebrosLookupLineDatas indexed from the object
	 * @throws FieldValueProviderException
	 */
	public abstract List<CelebrosLookupLineData> getLookupValues(final IndexConfig config, final IndexedProperty property,
			final Object obj) throws FieldValueProviderException;
}
