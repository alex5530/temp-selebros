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
import de.hybris.platform.jalo.JaloInvalidParameterException;
import de.hybris.platform.jalo.SessionContext;
import de.hybris.platform.jalo.c2l.C2LManager;
import de.hybris.platform.jalo.c2l.Language;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Generated class for type {@link com.celebros.jalo.components.CelebrosAutocompleteComponent CelebrosAutocompleteComponent}.
 */
@SuppressWarnings({"deprecation","unused","cast","PMD"})
public abstract class GeneratedCelebrosAutocompleteComponent extends CelebrosCMSComponent
{
	/** Qualifier of the <code>CelebrosAutocompleteComponent.frontendServer</code> attribute **/
	public static final String FRONTENDSERVER = "frontendServer";
	/** Qualifier of the <code>CelebrosAutocompleteComponent.clientUI</code> attribute **/
	public static final String CLIENTUI = "clientUI";
	/** Qualifier of the <code>CelebrosAutocompleteComponent.formTarget</code> attribute **/
	public static final String FORMTARGET = "formTarget";
	/** Qualifier of the <code>CelebrosAutocompleteComponent.inputName</code> attribute **/
	public static final String INPUTNAME = "inputName";
	/** Qualifier of the <code>CelebrosAutocompleteComponent.inputCssClass</code> attribute **/
	public static final String INPUTCSSCLASS = "inputCssClass";
	/** Qualifier of the <code>CelebrosAutocompleteComponent.inputPlaceholder</code> attribute **/
	public static final String INPUTPLACEHOLDER = "inputPlaceholder";
	/** Qualifier of the <code>CelebrosAutocompleteComponent.buttonId</code> attribute **/
	public static final String BUTTONID = "buttonId";
	/** Qualifier of the <code>CelebrosAutocompleteComponent.buttonCssClass</code> attribute **/
	public static final String BUTTONCSSCLASS = "buttonCssClass";
	protected static final Map<String, AttributeMode> DEFAULT_INITIAL_ATTRIBUTES;
	static
	{
		final Map<String, AttributeMode> tmp = new HashMap<String, AttributeMode>(CelebrosCMSComponent.DEFAULT_INITIAL_ATTRIBUTES);
		tmp.put(FRONTENDSERVER, AttributeMode.INITIAL);
		tmp.put(CLIENTUI, AttributeMode.INITIAL);
		tmp.put(FORMTARGET, AttributeMode.INITIAL);
		tmp.put(INPUTNAME, AttributeMode.INITIAL);
		tmp.put(INPUTCSSCLASS, AttributeMode.INITIAL);
		tmp.put(INPUTPLACEHOLDER, AttributeMode.INITIAL);
		tmp.put(BUTTONID, AttributeMode.INITIAL);
		tmp.put(BUTTONCSSCLASS, AttributeMode.INITIAL);
		DEFAULT_INITIAL_ATTRIBUTES = Collections.unmodifiableMap(tmp);
	}
	@Override
	protected Map<String, AttributeMode> getDefaultAttributeModes()
	{
		return DEFAULT_INITIAL_ATTRIBUTES;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>CelebrosAutocompleteComponent.buttonCssClass</code> attribute.
	 * @return the buttonCssClass - Css class for search button
	 */
	public String getButtonCssClass(final SessionContext ctx)
	{
		return (String)getProperty( ctx, BUTTONCSSCLASS);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>CelebrosAutocompleteComponent.buttonCssClass</code> attribute.
	 * @return the buttonCssClass - Css class for search button
	 */
	public String getButtonCssClass()
	{
		return getButtonCssClass( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>CelebrosAutocompleteComponent.buttonCssClass</code> attribute. 
	 * @param value the buttonCssClass - Css class for search button
	 */
	public void setButtonCssClass(final SessionContext ctx, final String value)
	{
		setProperty(ctx, BUTTONCSSCLASS,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>CelebrosAutocompleteComponent.buttonCssClass</code> attribute. 
	 * @param value the buttonCssClass - Css class for search button
	 */
	public void setButtonCssClass(final String value)
	{
		setButtonCssClass( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>CelebrosAutocompleteComponent.buttonId</code> attribute.
	 * @return the buttonId - ID of search button
	 */
	public String getButtonId(final SessionContext ctx)
	{
		return (String)getProperty( ctx, BUTTONID);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>CelebrosAutocompleteComponent.buttonId</code> attribute.
	 * @return the buttonId - ID of search button
	 */
	public String getButtonId()
	{
		return getButtonId( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>CelebrosAutocompleteComponent.buttonId</code> attribute. 
	 * @param value the buttonId - ID of search button
	 */
	public void setButtonId(final SessionContext ctx, final String value)
	{
		setProperty(ctx, BUTTONID,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>CelebrosAutocompleteComponent.buttonId</code> attribute. 
	 * @param value the buttonId - ID of search button
	 */
	public void setButtonId(final String value)
	{
		setButtonId( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>CelebrosAutocompleteComponent.clientUI</code> attribute.
	 * @return the clientUI - ClientUI at Celebros to fetch css from
	 */
	public String getClientUI(final SessionContext ctx)
	{
		if( ctx == null || ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedCelebrosAutocompleteComponent.getClientUI requires a session language", 0 );
		}
		return (String)getLocalizedProperty( ctx, CLIENTUI);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>CelebrosAutocompleteComponent.clientUI</code> attribute.
	 * @return the clientUI - ClientUI at Celebros to fetch css from
	 */
	public String getClientUI()
	{
		return getClientUI( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>CelebrosAutocompleteComponent.clientUI</code> attribute. 
	 * @return the localized clientUI - ClientUI at Celebros to fetch css from
	 */
	public Map<Language,String> getAllClientUI(final SessionContext ctx)
	{
		return (Map<Language,String>)getAllLocalizedProperties(ctx,CLIENTUI,C2LManager.getInstance().getAllLanguages());
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>CelebrosAutocompleteComponent.clientUI</code> attribute. 
	 * @return the localized clientUI - ClientUI at Celebros to fetch css from
	 */
	public Map<Language,String> getAllClientUI()
	{
		return getAllClientUI( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>CelebrosAutocompleteComponent.clientUI</code> attribute. 
	 * @param value the clientUI - ClientUI at Celebros to fetch css from
	 */
	public void setClientUI(final SessionContext ctx, final String value)
	{
		if( ctx == null || ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedCelebrosAutocompleteComponent.setClientUI requires a session language", 0 );
		}
		setLocalizedProperty(ctx, CLIENTUI,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>CelebrosAutocompleteComponent.clientUI</code> attribute. 
	 * @param value the clientUI - ClientUI at Celebros to fetch css from
	 */
	public void setClientUI(final String value)
	{
		setClientUI( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>CelebrosAutocompleteComponent.clientUI</code> attribute. 
	 * @param value the clientUI - ClientUI at Celebros to fetch css from
	 */
	public void setAllClientUI(final SessionContext ctx, final Map<Language,String> value)
	{
		setAllLocalizedProperties(ctx,CLIENTUI,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>CelebrosAutocompleteComponent.clientUI</code> attribute. 
	 * @param value the clientUI - ClientUI at Celebros to fetch css from
	 */
	public void setAllClientUI(final Map<Language,String> value)
	{
		setAllClientUI( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>CelebrosAutocompleteComponent.formTarget</code> attribute.
	 * @return the formTarget - Target for search form
	 */
	public String getFormTarget(final SessionContext ctx)
	{
		return (String)getProperty( ctx, FORMTARGET);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>CelebrosAutocompleteComponent.formTarget</code> attribute.
	 * @return the formTarget - Target for search form
	 */
	public String getFormTarget()
	{
		return getFormTarget( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>CelebrosAutocompleteComponent.formTarget</code> attribute. 
	 * @param value the formTarget - Target for search form
	 */
	public void setFormTarget(final SessionContext ctx, final String value)
	{
		setProperty(ctx, FORMTARGET,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>CelebrosAutocompleteComponent.formTarget</code> attribute. 
	 * @param value the formTarget - Target for search form
	 */
	public void setFormTarget(final String value)
	{
		setFormTarget( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>CelebrosAutocompleteComponent.frontendServer</code> attribute.
	 * @return the frontendServer - Frontend Server to send to Celebros
	 */
	public String getFrontendServer(final SessionContext ctx)
	{
		if( ctx == null || ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedCelebrosAutocompleteComponent.getFrontendServer requires a session language", 0 );
		}
		return (String)getLocalizedProperty( ctx, FRONTENDSERVER);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>CelebrosAutocompleteComponent.frontendServer</code> attribute.
	 * @return the frontendServer - Frontend Server to send to Celebros
	 */
	public String getFrontendServer()
	{
		return getFrontendServer( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>CelebrosAutocompleteComponent.frontendServer</code> attribute. 
	 * @return the localized frontendServer - Frontend Server to send to Celebros
	 */
	public Map<Language,String> getAllFrontendServer(final SessionContext ctx)
	{
		return (Map<Language,String>)getAllLocalizedProperties(ctx,FRONTENDSERVER,C2LManager.getInstance().getAllLanguages());
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>CelebrosAutocompleteComponent.frontendServer</code> attribute. 
	 * @return the localized frontendServer - Frontend Server to send to Celebros
	 */
	public Map<Language,String> getAllFrontendServer()
	{
		return getAllFrontendServer( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>CelebrosAutocompleteComponent.frontendServer</code> attribute. 
	 * @param value the frontendServer - Frontend Server to send to Celebros
	 */
	public void setFrontendServer(final SessionContext ctx, final String value)
	{
		if( ctx == null || ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedCelebrosAutocompleteComponent.setFrontendServer requires a session language", 0 );
		}
		setLocalizedProperty(ctx, FRONTENDSERVER,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>CelebrosAutocompleteComponent.frontendServer</code> attribute. 
	 * @param value the frontendServer - Frontend Server to send to Celebros
	 */
	public void setFrontendServer(final String value)
	{
		setFrontendServer( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>CelebrosAutocompleteComponent.frontendServer</code> attribute. 
	 * @param value the frontendServer - Frontend Server to send to Celebros
	 */
	public void setAllFrontendServer(final SessionContext ctx, final Map<Language,String> value)
	{
		setAllLocalizedProperties(ctx,FRONTENDSERVER,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>CelebrosAutocompleteComponent.frontendServer</code> attribute. 
	 * @param value the frontendServer - Frontend Server to send to Celebros
	 */
	public void setAllFrontendServer(final Map<Language,String> value)
	{
		setAllFrontendServer( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>CelebrosAutocompleteComponent.inputCssClass</code> attribute.
	 * @return the inputCssClass - Css class for search text box
	 */
	public String getInputCssClass(final SessionContext ctx)
	{
		return (String)getProperty( ctx, INPUTCSSCLASS);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>CelebrosAutocompleteComponent.inputCssClass</code> attribute.
	 * @return the inputCssClass - Css class for search text box
	 */
	public String getInputCssClass()
	{
		return getInputCssClass( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>CelebrosAutocompleteComponent.inputCssClass</code> attribute. 
	 * @param value the inputCssClass - Css class for search text box
	 */
	public void setInputCssClass(final SessionContext ctx, final String value)
	{
		setProperty(ctx, INPUTCSSCLASS,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>CelebrosAutocompleteComponent.inputCssClass</code> attribute. 
	 * @param value the inputCssClass - Css class for search text box
	 */
	public void setInputCssClass(final String value)
	{
		setInputCssClass( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>CelebrosAutocompleteComponent.inputName</code> attribute.
	 * @return the inputName - Name of search text box
	 */
	public String getInputName(final SessionContext ctx)
	{
		return (String)getProperty( ctx, INPUTNAME);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>CelebrosAutocompleteComponent.inputName</code> attribute.
	 * @return the inputName - Name of search text box
	 */
	public String getInputName()
	{
		return getInputName( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>CelebrosAutocompleteComponent.inputName</code> attribute. 
	 * @param value the inputName - Name of search text box
	 */
	public void setInputName(final SessionContext ctx, final String value)
	{
		setProperty(ctx, INPUTNAME,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>CelebrosAutocompleteComponent.inputName</code> attribute. 
	 * @param value the inputName - Name of search text box
	 */
	public void setInputName(final String value)
	{
		setInputName( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>CelebrosAutocompleteComponent.inputPlaceholder</code> attribute.
	 * @return the inputPlaceholder - Placeholder text for search text box
	 */
	public String getInputPlaceholder(final SessionContext ctx)
	{
		if( ctx == null || ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedCelebrosAutocompleteComponent.getInputPlaceholder requires a session language", 0 );
		}
		return (String)getLocalizedProperty( ctx, INPUTPLACEHOLDER);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>CelebrosAutocompleteComponent.inputPlaceholder</code> attribute.
	 * @return the inputPlaceholder - Placeholder text for search text box
	 */
	public String getInputPlaceholder()
	{
		return getInputPlaceholder( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>CelebrosAutocompleteComponent.inputPlaceholder</code> attribute. 
	 * @return the localized inputPlaceholder - Placeholder text for search text box
	 */
	public Map<Language,String> getAllInputPlaceholder(final SessionContext ctx)
	{
		return (Map<Language,String>)getAllLocalizedProperties(ctx,INPUTPLACEHOLDER,C2LManager.getInstance().getAllLanguages());
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>CelebrosAutocompleteComponent.inputPlaceholder</code> attribute. 
	 * @return the localized inputPlaceholder - Placeholder text for search text box
	 */
	public Map<Language,String> getAllInputPlaceholder()
	{
		return getAllInputPlaceholder( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>CelebrosAutocompleteComponent.inputPlaceholder</code> attribute. 
	 * @param value the inputPlaceholder - Placeholder text for search text box
	 */
	public void setInputPlaceholder(final SessionContext ctx, final String value)
	{
		if( ctx == null || ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedCelebrosAutocompleteComponent.setInputPlaceholder requires a session language", 0 );
		}
		setLocalizedProperty(ctx, INPUTPLACEHOLDER,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>CelebrosAutocompleteComponent.inputPlaceholder</code> attribute. 
	 * @param value the inputPlaceholder - Placeholder text for search text box
	 */
	public void setInputPlaceholder(final String value)
	{
		setInputPlaceholder( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>CelebrosAutocompleteComponent.inputPlaceholder</code> attribute. 
	 * @param value the inputPlaceholder - Placeholder text for search text box
	 */
	public void setAllInputPlaceholder(final SessionContext ctx, final Map<Language,String> value)
	{
		setAllLocalizedProperties(ctx,INPUTPLACEHOLDER,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>CelebrosAutocompleteComponent.inputPlaceholder</code> attribute. 
	 * @param value the inputPlaceholder - Placeholder text for search text box
	 */
	public void setAllInputPlaceholder(final Map<Language,String> value)
	{
		setAllInputPlaceholder( getSession().getSessionContext(), value );
	}
	
}
