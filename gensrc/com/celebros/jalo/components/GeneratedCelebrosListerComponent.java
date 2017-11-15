/*
 * ----------------------------------------------------------------
 * --- WARNING: THIS FILE IS GENERATED AND WILL BE OVERWRITTEN! ---
 * --- Generated at Nov 15, 2017 2:48:48 PM                     ---
 * ----------------------------------------------------------------
 */
package com.celebros.jalo.components;

import com.celebros.constants.CelebrosConstants;
import com.celebros.jalo.components.CelebrosCMSComponent;
import de.hybris.platform.jalo.Item.AttributeMode;
import de.hybris.platform.jalo.SessionContext;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Generated class for type {@link com.celebros.jalo.components.CelebrosListerComponent CelebrosListerComponent}.
 */
@SuppressWarnings({"deprecation","unused","cast","PMD"})
public abstract class GeneratedCelebrosListerComponent extends CelebrosCMSComponent
{
	/** Qualifier of the <code>CelebrosListerComponent.cssClass</code> attribute **/
	public static final String CSSCLASS = "cssClass";
	protected static final Map<String, AttributeMode> DEFAULT_INITIAL_ATTRIBUTES;
	static
	{
		final Map<String, AttributeMode> tmp = new HashMap<String, AttributeMode>(CelebrosCMSComponent.DEFAULT_INITIAL_ATTRIBUTES);
		tmp.put(CSSCLASS, AttributeMode.INITIAL);
		DEFAULT_INITIAL_ATTRIBUTES = Collections.unmodifiableMap(tmp);
	}
	@Override
	protected Map<String, AttributeMode> getDefaultAttributeModes()
	{
		return DEFAULT_INITIAL_ATTRIBUTES;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>CelebrosListerComponent.cssClass</code> attribute.
	 * @return the cssClass - cssClass for the div
	 */
	public String getCssClass(final SessionContext ctx)
	{
		return (String)getProperty( ctx, CSSCLASS);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>CelebrosListerComponent.cssClass</code> attribute.
	 * @return the cssClass - cssClass for the div
	 */
	public String getCssClass()
	{
		return getCssClass( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>CelebrosListerComponent.cssClass</code> attribute. 
	 * @param value the cssClass - cssClass for the div
	 */
	public void setCssClass(final SessionContext ctx, final String value)
	{
		setProperty(ctx, CSSCLASS,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>CelebrosListerComponent.cssClass</code> attribute. 
	 * @param value the cssClass - cssClass for the div
	 */
	public void setCssClass(final String value)
	{
		setCssClass( getSession().getSessionContext(), value );
	}
	
}
