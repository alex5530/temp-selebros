/*
 * ----------------------------------------------------------------
 * --- WARNING: THIS FILE IS GENERATED AND WILL BE OVERWRITTEN! ---
 * --- Generated at Nov 15, 2017 2:48:48 PM                     ---
 * ----------------------------------------------------------------
 */
package com.celebros.jalo;

import com.celebros.constants.CelebrosConstants;
import com.celebros.jalo.CelebrosExportCronjob;
import com.celebros.jalo.CelebrosIndexAttribute;
import com.celebros.jalo.components.CelebrosAutocompleteComponent;
import com.celebros.jalo.components.CelebrosListerComponent;
import de.hybris.platform.jalo.Item;
import de.hybris.platform.jalo.Item.AttributeMode;
import de.hybris.platform.jalo.JaloBusinessException;
import de.hybris.platform.jalo.JaloSystemException;
import de.hybris.platform.jalo.SessionContext;
import de.hybris.platform.jalo.extension.Extension;
import de.hybris.platform.jalo.type.ComposedType;
import de.hybris.platform.jalo.type.JaloGenericCreationException;
import java.util.HashMap;
import java.util.Map;

/**
 * Generated class for type <code>CelebrosManager</code>.
 */
@SuppressWarnings({"deprecation","unused","cast","PMD"})
public abstract class GeneratedCelebrosManager extends Extension
{
	protected static final Map<String, Map<String, AttributeMode>> DEFAULT_INITIAL_ATTRIBUTES;
	static
	{
		final Map<String, Map<String, AttributeMode>> ttmp = new HashMap();
		DEFAULT_INITIAL_ATTRIBUTES = ttmp;
	}
	@Override
	public Map<String, AttributeMode> getDefaultAttributeModes(final Class<? extends Item> itemClass)
	{
		Map<String, AttributeMode> ret = new HashMap<>();
		final Map<String, AttributeMode> attr = DEFAULT_INITIAL_ATTRIBUTES.get(itemClass.getName());
		if (attr != null)
		{
			ret.putAll(attr);
		}
		return ret;
	}
	
	public CelebrosAutocompleteComponent createCelebrosAutocompleteComponent(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( CelebrosConstants.TC.CELEBROSAUTOCOMPLETECOMPONENT );
			return (CelebrosAutocompleteComponent)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating CelebrosAutocompleteComponent : "+e.getMessage(), 0 );
		}
	}
	
	public CelebrosAutocompleteComponent createCelebrosAutocompleteComponent(final Map attributeValues)
	{
		return createCelebrosAutocompleteComponent( getSession().getSessionContext(), attributeValues );
	}
	
	public CelebrosExportCronjob createCelebrosExportCronjob(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( CelebrosConstants.TC.CELEBROSEXPORTCRONJOB );
			return (CelebrosExportCronjob)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating CelebrosExportCronjob : "+e.getMessage(), 0 );
		}
	}
	
	public CelebrosExportCronjob createCelebrosExportCronjob(final Map attributeValues)
	{
		return createCelebrosExportCronjob( getSession().getSessionContext(), attributeValues );
	}
	
	public CelebrosIndexAttribute createCelebrosIndexAttribute(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( CelebrosConstants.TC.CELEBROSINDEXATTRIBUTE );
			return (CelebrosIndexAttribute)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating CelebrosIndexAttribute : "+e.getMessage(), 0 );
		}
	}
	
	public CelebrosIndexAttribute createCelebrosIndexAttribute(final Map attributeValues)
	{
		return createCelebrosIndexAttribute( getSession().getSessionContext(), attributeValues );
	}
	
	public CelebrosListerComponent createCelebrosListerComponent(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( CelebrosConstants.TC.CELEBROSLISTERCOMPONENT );
			return (CelebrosListerComponent)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating CelebrosListerComponent : "+e.getMessage(), 0 );
		}
	}
	
	public CelebrosListerComponent createCelebrosListerComponent(final Map attributeValues)
	{
		return createCelebrosListerComponent( getSession().getSessionContext(), attributeValues );
	}
	
	@Override
	public String getName()
	{
		return CelebrosConstants.EXTENSIONNAME;
	}
	
}
