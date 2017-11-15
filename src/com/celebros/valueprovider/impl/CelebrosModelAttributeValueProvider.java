/**
 *
 */
package com.celebros.valueprovider.impl;

import de.hybris.platform.solrfacetsearch.config.IndexConfig;
import de.hybris.platform.solrfacetsearch.config.IndexedProperty;
import de.hybris.platform.solrfacetsearch.config.exceptions.FieldValueProviderException;
import de.hybris.platform.solrfacetsearch.provider.FieldValue;
import de.hybris.platform.solrfacetsearch.provider.FieldValueProvider;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Collections;

import org.apache.log4j.Logger;


/**
 * Celebros value provider for model attributes
 */
public class CelebrosModelAttributeValueProvider implements FieldValueProvider
{
	Logger log = Logger.getLogger(CelebrosModelAttributeValueProvider.class);

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
			final Method method = paramObject.getClass().getMethod("get" + getAttributeName(), null);
			final Object value = method.invoke(paramObject, null);
			if (value != null)
			{
				return Collections.singleton(new FieldValue("", value.toString()));
			}
			else
			{
				return Collections.singleton(new FieldValue("", ""));
			}
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
