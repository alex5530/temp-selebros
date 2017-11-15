/**
 *
 */
package com.celebros.util;

import de.hybris.platform.solrfacetsearch.provider.FieldValue;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.celebros.data.CelebrosLookupLineData;
import com.google.common.collect.Lists;


/**
 * Util class for use with the Celebros Add-On
 */
public final class CelebrosUtils
{

	private static final String FIELD_ENCASE_CHAR = "\"";

	/**
	 * Concat a collection of fieldValues into a comma separated String
	 *
	 * @param fieldValues
	 *           Collection of FieldValues
	 * @return Comma separated string of values
	 */
	public static String concatFieldValues(final Collection<FieldValue> fieldValues)
	{
		return concatFieldValues(fieldValues, "");
	}

	/**
	 * Concat a collection of fieldValues into a comma separated String with a prefix before each value
	 *
	 * @param fieldValues
	 *           Collection of FieldValues
	 * @param prefix
	 *           Prefix for each value
	 * @return Comma separated string of values
	 */
	public static String concatFieldValues(final Collection<FieldValue> fieldValues, final String prefix)
	{
		final List<String> values = Lists.newArrayList();
		for (final FieldValue fieldValue : fieldValues)
		{
			if (fieldValue.getValue() != null)
			{
				values.add(prefix + fieldValue.getValue().toString());
			}
		}
		return StringUtils.join(values, ',');
	}

	/**
	 * Add a value to a map encased in Field encase Characters
	 *
	 * @param map
	 *           The map to put the value in
	 * @param key
	 *           The key to store it under
	 * @param value
	 *           The Value
	 */
	public static void put(final Map map, final String key, final String value)
	{
		map.put(key, FIELD_ENCASE_CHAR + value + FIELD_ENCASE_CHAR);
	}

	/**
	 * Add all lines from a list of CelebrosLookupLineDatas to a map
	 *
	 * @param map
	 *           The map to put the value in
	 * @param key
	 *           The key to store it under
	 * @param lines
	 *           The CelebrosLookupLineDatas to encase and add
	 */
	public static void put(final Map map, final String key, final List<CelebrosLookupLineData> lines)
	{
		final List<String> ids = Lists.newArrayList();
		for (final CelebrosLookupLineData lineData : lines)
		{
			ids.add(lineData.getId());
		}
		map.put(key, FIELD_ENCASE_CHAR + StringUtils.join(ids, ',') + FIELD_ENCASE_CHAR);
	}
}
