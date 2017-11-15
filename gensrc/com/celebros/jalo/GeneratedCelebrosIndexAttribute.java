/*
 * ----------------------------------------------------------------
 * --- WARNING: THIS FILE IS GENERATED AND WILL BE OVERWRITTEN! ---
 * --- Generated at Nov 15, 2017 2:48:48 PM                     ---
 * ----------------------------------------------------------------
 */
package com.celebros.jalo;

import com.celebros.constants.CelebrosConstants;
import com.celebros.jalo.CelebrosExportCronjob;
import de.hybris.platform.catalog.jalo.classification.ClassAttributeAssignment;
import de.hybris.platform.jalo.GenericItem;
import de.hybris.platform.jalo.Item;
import de.hybris.platform.jalo.Item.AttributeMode;
import de.hybris.platform.jalo.JaloBusinessException;
import de.hybris.platform.jalo.SessionContext;
import de.hybris.platform.jalo.type.CollectionType;
import de.hybris.platform.jalo.type.ComposedType;
import de.hybris.platform.util.BidirectionalOneToManyHandler;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Generated class for type {@link de.hybris.platform.jalo.GenericItem CelebrosIndexAttribute}.
 */
@SuppressWarnings({"deprecation","unused","cast","PMD"})
public abstract class GeneratedCelebrosIndexAttribute extends GenericItem
{
	/** Qualifier of the <code>CelebrosIndexAttribute.attributeName</code> attribute **/
	public static final String ATTRIBUTENAME = "attributeName";
	/** Qualifier of the <code>CelebrosIndexAttribute.valueProviderBean</code> attribute **/
	public static final String VALUEPROVIDERBEAN = "valueProviderBean";
	/** Qualifier of the <code>CelebrosIndexAttribute.valueProviderParameter</code> attribute **/
	public static final String VALUEPROVIDERPARAMETER = "valueProviderParameter";
	/** Qualifier of the <code>CelebrosIndexAttribute.lookupTable</code> attribute **/
	public static final String LOOKUPTABLE = "lookupTable";
	/** Qualifier of the <code>CelebrosIndexAttribute.localized</code> attribute **/
	public static final String LOCALIZED = "localized";
	/** Qualifier of the <code>CelebrosIndexAttribute.enabled</code> attribute **/
	public static final String ENABLED = "enabled";
	/** Qualifier of the <code>CelebrosIndexAttribute.classAttributeAssignment</code> attribute **/
	public static final String CLASSATTRIBUTEASSIGNMENT = "classAttributeAssignment";
	/** Qualifier of the <code>CelebrosIndexAttribute.exportCronjob</code> attribute **/
	public static final String EXPORTCRONJOB = "exportCronjob";
	/**
	* {@link BidirectionalOneToManyHandler} for handling 1:n EXPORTCRONJOB's relation attributes from 'one' side.
	**/
	protected static final BidirectionalOneToManyHandler<GeneratedCelebrosIndexAttribute> EXPORTCRONJOBHANDLER = new BidirectionalOneToManyHandler<GeneratedCelebrosIndexAttribute>(
	CelebrosConstants.TC.CELEBROSINDEXATTRIBUTE,
	false,
	"exportCronjob",
	null,
	false,
	true,
	CollectionType.LIST
	);
	protected static final Map<String, AttributeMode> DEFAULT_INITIAL_ATTRIBUTES;
	static
	{
		final Map<String, AttributeMode> tmp = new HashMap<String, AttributeMode>();
		tmp.put(ATTRIBUTENAME, AttributeMode.INITIAL);
		tmp.put(VALUEPROVIDERBEAN, AttributeMode.INITIAL);
		tmp.put(VALUEPROVIDERPARAMETER, AttributeMode.INITIAL);
		tmp.put(LOOKUPTABLE, AttributeMode.INITIAL);
		tmp.put(LOCALIZED, AttributeMode.INITIAL);
		tmp.put(ENABLED, AttributeMode.INITIAL);
		tmp.put(CLASSATTRIBUTEASSIGNMENT, AttributeMode.INITIAL);
		tmp.put(EXPORTCRONJOB, AttributeMode.INITIAL);
		DEFAULT_INITIAL_ATTRIBUTES = Collections.unmodifiableMap(tmp);
	}
	@Override
	protected Map<String, AttributeMode> getDefaultAttributeModes()
	{
		return DEFAULT_INITIAL_ATTRIBUTES;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>CelebrosIndexAttribute.attributeName</code> attribute.
	 * @return the attributeName - Name of attribute to index
	 */
	public String getAttributeName(final SessionContext ctx)
	{
		return (String)getProperty( ctx, ATTRIBUTENAME);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>CelebrosIndexAttribute.attributeName</code> attribute.
	 * @return the attributeName - Name of attribute to index
	 */
	public String getAttributeName()
	{
		return getAttributeName( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>CelebrosIndexAttribute.attributeName</code> attribute. 
	 * @param value the attributeName - Name of attribute to index
	 */
	public void setAttributeName(final SessionContext ctx, final String value)
	{
		setProperty(ctx, ATTRIBUTENAME,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>CelebrosIndexAttribute.attributeName</code> attribute. 
	 * @param value the attributeName - Name of attribute to index
	 */
	public void setAttributeName(final String value)
	{
		setAttributeName( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>CelebrosIndexAttribute.classAttributeAssignment</code> attribute.
	 * @return the classAttributeAssignment - The classification system category feature for this property.
	 */
	public ClassAttributeAssignment getClassAttributeAssignment(final SessionContext ctx)
	{
		return (ClassAttributeAssignment)getProperty( ctx, CLASSATTRIBUTEASSIGNMENT);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>CelebrosIndexAttribute.classAttributeAssignment</code> attribute.
	 * @return the classAttributeAssignment - The classification system category feature for this property.
	 */
	public ClassAttributeAssignment getClassAttributeAssignment()
	{
		return getClassAttributeAssignment( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>CelebrosIndexAttribute.classAttributeAssignment</code> attribute. 
	 * @param value the classAttributeAssignment - The classification system category feature for this property.
	 */
	public void setClassAttributeAssignment(final SessionContext ctx, final ClassAttributeAssignment value)
	{
		setProperty(ctx, CLASSATTRIBUTEASSIGNMENT,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>CelebrosIndexAttribute.classAttributeAssignment</code> attribute. 
	 * @param value the classAttributeAssignment - The classification system category feature for this property.
	 */
	public void setClassAttributeAssignment(final ClassAttributeAssignment value)
	{
		setClassAttributeAssignment( getSession().getSessionContext(), value );
	}
	
	@Override
	protected Item createItem(final SessionContext ctx, final ComposedType type, final ItemAttributeMap allAttributes) throws JaloBusinessException
	{
		EXPORTCRONJOBHANDLER.newInstance(ctx, allAttributes);
		return super.createItem( ctx, type, allAttributes );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>CelebrosIndexAttribute.enabled</code> attribute.
	 * @return the enabled - Whether to index this attribute
	 */
	public Boolean isEnabled(final SessionContext ctx)
	{
		return (Boolean)getProperty( ctx, ENABLED);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>CelebrosIndexAttribute.enabled</code> attribute.
	 * @return the enabled - Whether to index this attribute
	 */
	public Boolean isEnabled()
	{
		return isEnabled( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>CelebrosIndexAttribute.enabled</code> attribute. 
	 * @return the enabled - Whether to index this attribute
	 */
	public boolean isEnabledAsPrimitive(final SessionContext ctx)
	{
		Boolean value = isEnabled( ctx );
		return value != null ? value.booleanValue() : false;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>CelebrosIndexAttribute.enabled</code> attribute. 
	 * @return the enabled - Whether to index this attribute
	 */
	public boolean isEnabledAsPrimitive()
	{
		return isEnabledAsPrimitive( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>CelebrosIndexAttribute.enabled</code> attribute. 
	 * @param value the enabled - Whether to index this attribute
	 */
	public void setEnabled(final SessionContext ctx, final Boolean value)
	{
		setProperty(ctx, ENABLED,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>CelebrosIndexAttribute.enabled</code> attribute. 
	 * @param value the enabled - Whether to index this attribute
	 */
	public void setEnabled(final Boolean value)
	{
		setEnabled( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>CelebrosIndexAttribute.enabled</code> attribute. 
	 * @param value the enabled - Whether to index this attribute
	 */
	public void setEnabled(final SessionContext ctx, final boolean value)
	{
		setEnabled( ctx,Boolean.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>CelebrosIndexAttribute.enabled</code> attribute. 
	 * @param value the enabled - Whether to index this attribute
	 */
	public void setEnabled(final boolean value)
	{
		setEnabled( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>CelebrosIndexAttribute.exportCronjob</code> attribute.
	 * @return the exportCronjob
	 */
	public CelebrosExportCronjob getExportCronjob(final SessionContext ctx)
	{
		return (CelebrosExportCronjob)getProperty( ctx, EXPORTCRONJOB);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>CelebrosIndexAttribute.exportCronjob</code> attribute.
	 * @return the exportCronjob
	 */
	public CelebrosExportCronjob getExportCronjob()
	{
		return getExportCronjob( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>CelebrosIndexAttribute.exportCronjob</code> attribute. 
	 * @param value the exportCronjob
	 */
	public void setExportCronjob(final SessionContext ctx, final CelebrosExportCronjob value)
	{
		EXPORTCRONJOBHANDLER.addValue( ctx, value, this  );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>CelebrosIndexAttribute.exportCronjob</code> attribute. 
	 * @param value the exportCronjob
	 */
	public void setExportCronjob(final CelebrosExportCronjob value)
	{
		setExportCronjob( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>CelebrosIndexAttribute.localized</code> attribute.
	 * @return the localized - Whether this attribute is localized
	 */
	public Boolean isLocalized(final SessionContext ctx)
	{
		return (Boolean)getProperty( ctx, LOCALIZED);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>CelebrosIndexAttribute.localized</code> attribute.
	 * @return the localized - Whether this attribute is localized
	 */
	public Boolean isLocalized()
	{
		return isLocalized( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>CelebrosIndexAttribute.localized</code> attribute. 
	 * @return the localized - Whether this attribute is localized
	 */
	public boolean isLocalizedAsPrimitive(final SessionContext ctx)
	{
		Boolean value = isLocalized( ctx );
		return value != null ? value.booleanValue() : false;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>CelebrosIndexAttribute.localized</code> attribute. 
	 * @return the localized - Whether this attribute is localized
	 */
	public boolean isLocalizedAsPrimitive()
	{
		return isLocalizedAsPrimitive( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>CelebrosIndexAttribute.localized</code> attribute. 
	 * @param value the localized - Whether this attribute is localized
	 */
	public void setLocalized(final SessionContext ctx, final Boolean value)
	{
		setProperty(ctx, LOCALIZED,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>CelebrosIndexAttribute.localized</code> attribute. 
	 * @param value the localized - Whether this attribute is localized
	 */
	public void setLocalized(final Boolean value)
	{
		setLocalized( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>CelebrosIndexAttribute.localized</code> attribute. 
	 * @param value the localized - Whether this attribute is localized
	 */
	public void setLocalized(final SessionContext ctx, final boolean value)
	{
		setLocalized( ctx,Boolean.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>CelebrosIndexAttribute.localized</code> attribute. 
	 * @param value the localized - Whether this attribute is localized
	 */
	public void setLocalized(final boolean value)
	{
		setLocalized( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>CelebrosIndexAttribute.lookupTable</code> attribute.
	 * @return the lookupTable - Whether to index this attribute to a lookup table
	 */
	public Boolean isLookupTable(final SessionContext ctx)
	{
		return (Boolean)getProperty( ctx, LOOKUPTABLE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>CelebrosIndexAttribute.lookupTable</code> attribute.
	 * @return the lookupTable - Whether to index this attribute to a lookup table
	 */
	public Boolean isLookupTable()
	{
		return isLookupTable( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>CelebrosIndexAttribute.lookupTable</code> attribute. 
	 * @return the lookupTable - Whether to index this attribute to a lookup table
	 */
	public boolean isLookupTableAsPrimitive(final SessionContext ctx)
	{
		Boolean value = isLookupTable( ctx );
		return value != null ? value.booleanValue() : false;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>CelebrosIndexAttribute.lookupTable</code> attribute. 
	 * @return the lookupTable - Whether to index this attribute to a lookup table
	 */
	public boolean isLookupTableAsPrimitive()
	{
		return isLookupTableAsPrimitive( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>CelebrosIndexAttribute.lookupTable</code> attribute. 
	 * @param value the lookupTable - Whether to index this attribute to a lookup table
	 */
	public void setLookupTable(final SessionContext ctx, final Boolean value)
	{
		setProperty(ctx, LOOKUPTABLE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>CelebrosIndexAttribute.lookupTable</code> attribute. 
	 * @param value the lookupTable - Whether to index this attribute to a lookup table
	 */
	public void setLookupTable(final Boolean value)
	{
		setLookupTable( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>CelebrosIndexAttribute.lookupTable</code> attribute. 
	 * @param value the lookupTable - Whether to index this attribute to a lookup table
	 */
	public void setLookupTable(final SessionContext ctx, final boolean value)
	{
		setLookupTable( ctx,Boolean.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>CelebrosIndexAttribute.lookupTable</code> attribute. 
	 * @param value the lookupTable - Whether to index this attribute to a lookup table
	 */
	public void setLookupTable(final boolean value)
	{
		setLookupTable( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>CelebrosIndexAttribute.valueProviderBean</code> attribute.
	 * @return the valueProviderBean - Value Provider to use to index attribute
	 */
	public String getValueProviderBean(final SessionContext ctx)
	{
		return (String)getProperty( ctx, VALUEPROVIDERBEAN);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>CelebrosIndexAttribute.valueProviderBean</code> attribute.
	 * @return the valueProviderBean - Value Provider to use to index attribute
	 */
	public String getValueProviderBean()
	{
		return getValueProviderBean( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>CelebrosIndexAttribute.valueProviderBean</code> attribute. 
	 * @param value the valueProviderBean - Value Provider to use to index attribute
	 */
	public void setValueProviderBean(final SessionContext ctx, final String value)
	{
		setProperty(ctx, VALUEPROVIDERBEAN,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>CelebrosIndexAttribute.valueProviderBean</code> attribute. 
	 * @param value the valueProviderBean - Value Provider to use to index attribute
	 */
	public void setValueProviderBean(final String value)
	{
		setValueProviderBean( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>CelebrosIndexAttribute.valueProviderParameter</code> attribute.
	 * @return the valueProviderParameter - Parameter to pass the value provider
	 */
	public String getValueProviderParameter(final SessionContext ctx)
	{
		return (String)getProperty( ctx, VALUEPROVIDERPARAMETER);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>CelebrosIndexAttribute.valueProviderParameter</code> attribute.
	 * @return the valueProviderParameter - Parameter to pass the value provider
	 */
	public String getValueProviderParameter()
	{
		return getValueProviderParameter( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>CelebrosIndexAttribute.valueProviderParameter</code> attribute. 
	 * @param value the valueProviderParameter - Parameter to pass the value provider
	 */
	public void setValueProviderParameter(final SessionContext ctx, final String value)
	{
		setProperty(ctx, VALUEPROVIDERPARAMETER,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>CelebrosIndexAttribute.valueProviderParameter</code> attribute. 
	 * @param value the valueProviderParameter - Parameter to pass the value provider
	 */
	public void setValueProviderParameter(final String value)
	{
		setValueProviderParameter( getSession().getSessionContext(), value );
	}
	
}
