/**
 *
 */
package com.celebros.data;

import static de.hybris.platform.servicelayer.util.ServicesUtil.validateParameterNotNull;


/**
 * Represents a line in a Celebros Lookup file
 */
public class CelebrosLookupLineData implements java.io.Serializable
{

	private String id;
	private String parentId = "";
	private String name;

	public CelebrosLookupLineData()
	{
		// default constructor
	}


	public void setId(final String id)
	{
		this.id = id;
	}


	public String getId()
	{
		return id;
	}


	public void setParentId(final String parentId)
	{
		this.parentId = parentId;
	}


	public String getParentId()
	{
		return parentId;
	}


	public void setName(final String name)
	{
		this.name = name;
	}


	public String getName()
	{
		return name;
	}


	@Override
	public int hashCode()
	{
		return this.id.hashCode();
	}


	@Override
	public boolean equals(final Object obj)
	{
		validateParameterNotNull(obj, "Parameter obj must not be null");
		final boolean result = (hashCode() == obj.hashCode());
		return result;
	}



}
