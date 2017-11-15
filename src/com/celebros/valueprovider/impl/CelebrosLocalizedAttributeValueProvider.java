/**
 *
 */
package com.celebros.valueprovider.impl;

import de.hybris.platform.core.model.c2l.LanguageModel;
import de.hybris.platform.solrfacetsearch.config.IndexConfig;
import de.hybris.platform.solrfacetsearch.config.IndexedProperty;
import de.hybris.platform.solrfacetsearch.config.exceptions.FieldValueProviderException;
import de.hybris.platform.solrfacetsearch.provider.FieldValue;
import de.hybris.platform.solrfacetsearch.provider.FieldValueProvider;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Locale;

import org.apache.log4j.Logger;

import com.google.common.collect.Lists;


/**
 * Celebros value provider for localized model attributes
 */
public class CelebrosLocalizedAttributeValueProvider implements FieldValueProvider
{

	Logger log = Logger.getLogger(CelebrosLocalizedAttributeValueProvider.class);

	private String attributeName;

	/**
	 * @see de.hybris.platform.solrfacetsearch.provider.FieldValueProvider#getFieldValues(de.hybris.platform.solrfacetsearch.config.IndexConfig,
	 *      de.hybris.platform.solrfacetsearch.config.IndexedProperty, java.lang.Object)
	 */
	@Override
	public Collection<FieldValue> getFieldValues(final IndexConfig paramIndexConfig, final IndexedProperty paramIndexedProperty,
			final Object paramObject) throws FieldValueProviderException
	{
		try
		{
			final Collection<FieldValue> output = Lists.newArrayList();
			final Method method = paramObject.getClass().getMethod("get" + getAttributeName(), Locale.class);
			for (final LanguageModel language : paramIndexConfig.getLanguages())
			{
				output.add(new FieldValue(language.getIsocode(),
						method.invoke(paramObject, Locale.forLanguageTag(language.getIsocode()))));
			}
			return output;
		}
		catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e)
		{
			if (log.isDebugEnabled())
			{
				e.printStackTrace();
			}
			throw new FieldValueProviderException("Capturing " + attributeName + " via reflection failed");
		}
	}

	public String getAttributeName()
	{
		return Character.toUpperCase(attributeName.charAt(0)) + attributeName.substring(1);
	}

	public void setAttributeName(final String attributeName)
	{
		this.attributeName = attributeName;
	}

}
