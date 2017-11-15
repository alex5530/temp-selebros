/*
 * ----------------------------------------------------------------
 * --- WARNING: THIS FILE IS GENERATED AND WILL BE OVERWRITTEN! ---
 * --- Generated at Nov 15, 2017 2:48:48 PM                     ---
 * ----------------------------------------------------------------
 */
package com.celebros.jalo.components;

import com.celebros.constants.CelebrosConstants;
import de.hybris.platform.cms2.jalo.contents.components.SimpleCMSComponent;
import de.hybris.platform.jalo.Item.AttributeMode;
import de.hybris.platform.jalo.JaloInvalidParameterException;
import de.hybris.platform.jalo.SessionContext;
import de.hybris.platform.jalo.c2l.C2LManager;
import de.hybris.platform.jalo.c2l.Language;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Generated class for type {@link com.celebros.jalo.components.CelebrosCMSComponent CelebrosCMSComponent}.
 */
@SuppressWarnings({"deprecation","unused","cast","PMD"})
public abstract class GeneratedCelebrosCMSComponent extends SimpleCMSComponent
{
	/** Qualifier of the <code>CelebrosCMSComponent.celebrosEnabled</code> attribute **/
	public static final String CELEBROSENABLED = "celebrosEnabled";
	/** Qualifier of the <code>CelebrosCMSComponent.scriptServer</code> attribute **/
	public static final String SCRIPTSERVER = "scriptServer";
	/** Qualifier of the <code>CelebrosCMSComponent.customerName</code> attribute **/
	public static final String CUSTOMERNAME = "customerName";
	protected static final Map<String, AttributeMode> DEFAULT_INITIAL_ATTRIBUTES;
	static
	{
		final Map<String, AttributeMode> tmp = new HashMap<String, AttributeMode>(SimpleCMSComponent.DEFAULT_INITIAL_ATTRIBUTES);
		tmp.put(CELEBROSENABLED, AttributeMode.INITIAL);
		tmp.put(SCRIPTSERVER, AttributeMode.INITIAL);
		tmp.put(CUSTOMERNAME, AttributeMode.INITIAL);
		DEFAULT_INITIAL_ATTRIBUTES = Collections.unmodifiableMap(tmp);
	}
	@Override
	protected Map<String, AttributeMode> getDefaultAttributeModes()
	{
		return DEFAULT_INITIAL_ATTRIBUTES;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>CelebrosCMSComponent.celebrosEnabled</code> attribute.
	 * @return the celebrosEnabled - Are the celebros features of this component enabled
	 */
	public Boolean isCelebrosEnabled(final SessionContext ctx)
	{
		return (Boolean)getProperty( ctx, CELEBROSENABLED);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>CelebrosCMSComponent.celebrosEnabled</code> attribute.
	 * @return the celebrosEnabled - Are the celebros features of this component enabled
	 */
	public Boolean isCelebrosEnabled()
	{
		return isCelebrosEnabled( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>CelebrosCMSComponent.celebrosEnabled</code> attribute. 
	 * @return the celebrosEnabled - Are the celebros features of this component enabled
	 */
	public boolean isCelebrosEnabledAsPrimitive(final SessionContext ctx)
	{
		Boolean value = isCelebrosEnabled( ctx );
		return value != null ? value.booleanValue() : false;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>CelebrosCMSComponent.celebrosEnabled</code> attribute. 
	 * @return the celebrosEnabled - Are the celebros features of this component enabled
	 */
	public boolean isCelebrosEnabledAsPrimitive()
	{
		return isCelebrosEnabledAsPrimitive( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>CelebrosCMSComponent.celebrosEnabled</code> attribute. 
	 * @param value the celebrosEnabled - Are the celebros features of this component enabled
	 */
	public void setCelebrosEnabled(final SessionContext ctx, final Boolean value)
	{
		setProperty(ctx, CELEBROSENABLED,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>CelebrosCMSComponent.celebrosEnabled</code> attribute. 
	 * @param value the celebrosEnabled - Are the celebros features of this component enabled
	 */
	public void setCelebrosEnabled(final Boolean value)
	{
		setCelebrosEnabled( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>CelebrosCMSComponent.celebrosEnabled</code> attribute. 
	 * @param value the celebrosEnabled - Are the celebros features of this component enabled
	 */
	public void setCelebrosEnabled(final SessionContext ctx, final boolean value)
	{
		setCelebrosEnabled( ctx,Boolean.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>CelebrosCMSComponent.celebrosEnabled</code> attribute. 
	 * @param value the celebrosEnabled - Are the celebros features of this component enabled
	 */
	public void setCelebrosEnabled(final boolean value)
	{
		setCelebrosEnabled( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>CelebrosCMSComponent.customerName</code> attribute.
	 * @return the customerName - Customer Name to send to Celebros
	 */
	public String getCustomerName(final SessionContext ctx)
	{
		if( ctx == null || ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedCelebrosCMSComponent.getCustomerName requires a session language", 0 );
		}
		return (String)getLocalizedProperty( ctx, CUSTOMERNAME);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>CelebrosCMSComponent.customerName</code> attribute.
	 * @return the customerName - Customer Name to send to Celebros
	 */
	public String getCustomerName()
	{
		return getCustomerName( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>CelebrosCMSComponent.customerName</code> attribute. 
	 * @return the localized customerName - Customer Name to send to Celebros
	 */
	public Map<Language,String> getAllCustomerName(final SessionContext ctx)
	{
		return (Map<Language,String>)getAllLocalizedProperties(ctx,CUSTOMERNAME,C2LManager.getInstance().getAllLanguages());
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>CelebrosCMSComponent.customerName</code> attribute. 
	 * @return the localized customerName - Customer Name to send to Celebros
	 */
	public Map<Language,String> getAllCustomerName()
	{
		return getAllCustomerName( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>CelebrosCMSComponent.customerName</code> attribute. 
	 * @param value the customerName - Customer Name to send to Celebros
	 */
	public void setCustomerName(final SessionContext ctx, final String value)
	{
		if( ctx == null || ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedCelebrosCMSComponent.setCustomerName requires a session language", 0 );
		}
		setLocalizedProperty(ctx, CUSTOMERNAME,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>CelebrosCMSComponent.customerName</code> attribute. 
	 * @param value the customerName - Customer Name to send to Celebros
	 */
	public void setCustomerName(final String value)
	{
		setCustomerName( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>CelebrosCMSComponent.customerName</code> attribute. 
	 * @param value the customerName - Customer Name to send to Celebros
	 */
	public void setAllCustomerName(final SessionContext ctx, final Map<Language,String> value)
	{
		setAllLocalizedProperties(ctx,CUSTOMERNAME,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>CelebrosCMSComponent.customerName</code> attribute. 
	 * @param value the customerName - Customer Name to send to Celebros
	 */
	public void setAllCustomerName(final Map<Language,String> value)
	{
		setAllCustomerName( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>CelebrosCMSComponent.scriptServer</code> attribute.
	 * @return the scriptServer - The host where the JS is located
	 */
	public String getScriptServer(final SessionContext ctx)
	{
		if( ctx == null || ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedCelebrosCMSComponent.getScriptServer requires a session language", 0 );
		}
		return (String)getLocalizedProperty( ctx, SCRIPTSERVER);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>CelebrosCMSComponent.scriptServer</code> attribute.
	 * @return the scriptServer - The host where the JS is located
	 */
	public String getScriptServer()
	{
		return getScriptServer( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>CelebrosCMSComponent.scriptServer</code> attribute. 
	 * @return the localized scriptServer - The host where the JS is located
	 */
	public Map<Language,String> getAllScriptServer(final SessionContext ctx)
	{
		return (Map<Language,String>)getAllLocalizedProperties(ctx,SCRIPTSERVER,C2LManager.getInstance().getAllLanguages());
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>CelebrosCMSComponent.scriptServer</code> attribute. 
	 * @return the localized scriptServer - The host where the JS is located
	 */
	public Map<Language,String> getAllScriptServer()
	{
		return getAllScriptServer( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>CelebrosCMSComponent.scriptServer</code> attribute. 
	 * @param value the scriptServer - The host where the JS is located
	 */
	public void setScriptServer(final SessionContext ctx, final String value)
	{
		if( ctx == null || ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedCelebrosCMSComponent.setScriptServer requires a session language", 0 );
		}
		setLocalizedProperty(ctx, SCRIPTSERVER,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>CelebrosCMSComponent.scriptServer</code> attribute. 
	 * @param value the scriptServer - The host where the JS is located
	 */
	public void setScriptServer(final String value)
	{
		setScriptServer( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>CelebrosCMSComponent.scriptServer</code> attribute. 
	 * @param value the scriptServer - The host where the JS is located
	 */
	public void setAllScriptServer(final SessionContext ctx, final Map<Language,String> value)
	{
		setAllLocalizedProperties(ctx,SCRIPTSERVER,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>CelebrosCMSComponent.scriptServer</code> attribute. 
	 * @param value the scriptServer - The host where the JS is located
	 */
	public void setAllScriptServer(final Map<Language,String> value)
	{
		setAllScriptServer( getSession().getSessionContext(), value );
	}
	
}
