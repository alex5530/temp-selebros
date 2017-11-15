/*
 * ----------------------------------------------------------------
 * --- WARNING: THIS FILE IS GENERATED AND WILL BE OVERWRITTEN! ---
 * --- Generated at Nov 15, 2017 2:48:48 PM                     ---
 * ----------------------------------------------------------------
 */
package com.celebros.jalo;

import com.celebros.constants.CelebrosConstants;
import com.celebros.jalo.CelebrosIndexAttribute;
import de.hybris.platform.cms2.jalo.site.CMSSite;
import de.hybris.platform.cronjob.jalo.CronJob;
import de.hybris.platform.jalo.Item.AttributeMode;
import de.hybris.platform.jalo.SessionContext;
import de.hybris.platform.jalo.type.CollectionType;
import de.hybris.platform.jalo.type.ComposedType;
import de.hybris.platform.util.OneToManyHandler;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Generated class for type {@link de.hybris.platform.cronjob.jalo.CronJob CelebrosExportCronjob}.
 */
@SuppressWarnings({"deprecation","unused","cast","PMD"})
public abstract class GeneratedCelebrosExportCronjob extends CronJob
{
	/** Qualifier of the <code>CelebrosExportCronjob.productType</code> attribute **/
	public static final String PRODUCTTYPE = "productType";
	/** Qualifier of the <code>CelebrosExportCronjob.site</code> attribute **/
	public static final String SITE = "site";
	/** Qualifier of the <code>CelebrosExportCronjob.imageBaseUrl</code> attribute **/
	public static final String IMAGEBASEURL = "imageBaseUrl";
	/** Qualifier of the <code>CelebrosExportCronjob.baseUrl</code> attribute **/
	public static final String BASEURL = "baseUrl";
	/** Qualifier of the <code>CelebrosExportCronjob.remoteHost</code> attribute **/
	public static final String REMOTEHOST = "remoteHost";
	/** Qualifier of the <code>CelebrosExportCronjob.remotePort</code> attribute **/
	public static final String REMOTEPORT = "remotePort";
	/** Qualifier of the <code>CelebrosExportCronjob.remoteLocation</code> attribute **/
	public static final String REMOTELOCATION = "remoteLocation";
	/** Qualifier of the <code>CelebrosExportCronjob.username</code> attribute **/
	public static final String USERNAME = "username";
	/** Qualifier of the <code>CelebrosExportCronjob.password</code> attribute **/
	public static final String PASSWORD = "password";
	/** Qualifier of the <code>CelebrosExportCronjob.customAttributes</code> attribute **/
	public static final String CUSTOMATTRIBUTES = "customAttributes";
	/**
	* {@link OneToManyHandler} for handling 1:n CUSTOMATTRIBUTES's relation attributes from 'many' side.
	**/
	protected static final OneToManyHandler<CelebrosIndexAttribute> CUSTOMATTRIBUTESHANDLER = new OneToManyHandler<CelebrosIndexAttribute>(
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
		final Map<String, AttributeMode> tmp = new HashMap<String, AttributeMode>(CronJob.DEFAULT_INITIAL_ATTRIBUTES);
		tmp.put(PRODUCTTYPE, AttributeMode.INITIAL);
		tmp.put(SITE, AttributeMode.INITIAL);
		tmp.put(IMAGEBASEURL, AttributeMode.INITIAL);
		tmp.put(BASEURL, AttributeMode.INITIAL);
		tmp.put(REMOTEHOST, AttributeMode.INITIAL);
		tmp.put(REMOTEPORT, AttributeMode.INITIAL);
		tmp.put(REMOTELOCATION, AttributeMode.INITIAL);
		tmp.put(USERNAME, AttributeMode.INITIAL);
		tmp.put(PASSWORD, AttributeMode.INITIAL);
		DEFAULT_INITIAL_ATTRIBUTES = Collections.unmodifiableMap(tmp);
	}
	@Override
	protected Map<String, AttributeMode> getDefaultAttributeModes()
	{
		return DEFAULT_INITIAL_ATTRIBUTES;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>CelebrosExportCronjob.baseUrl</code> attribute.
	 * @return the baseUrl - The base url of the site to be used for product links
	 */
	public String getBaseUrl(final SessionContext ctx)
	{
		return (String)getProperty( ctx, BASEURL);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>CelebrosExportCronjob.baseUrl</code> attribute.
	 * @return the baseUrl - The base url of the site to be used for product links
	 */
	public String getBaseUrl()
	{
		return getBaseUrl( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>CelebrosExportCronjob.baseUrl</code> attribute. 
	 * @param value the baseUrl - The base url of the site to be used for product links
	 */
	public void setBaseUrl(final SessionContext ctx, final String value)
	{
		setProperty(ctx, BASEURL,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>CelebrosExportCronjob.baseUrl</code> attribute. 
	 * @param value the baseUrl - The base url of the site to be used for product links
	 */
	public void setBaseUrl(final String value)
	{
		setBaseUrl( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>CelebrosExportCronjob.customAttributes</code> attribute.
	 * @return the customAttributes
	 */
	public List<CelebrosIndexAttribute> getCustomAttributes(final SessionContext ctx)
	{
		return (List<CelebrosIndexAttribute>)CUSTOMATTRIBUTESHANDLER.getValues( ctx, this );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>CelebrosExportCronjob.customAttributes</code> attribute.
	 * @return the customAttributes
	 */
	public List<CelebrosIndexAttribute> getCustomAttributes()
	{
		return getCustomAttributes( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>CelebrosExportCronjob.customAttributes</code> attribute. 
	 * @param value the customAttributes
	 */
	public void setCustomAttributes(final SessionContext ctx, final List<CelebrosIndexAttribute> value)
	{
		CUSTOMATTRIBUTESHANDLER.setValues( ctx, this, value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>CelebrosExportCronjob.customAttributes</code> attribute. 
	 * @param value the customAttributes
	 */
	public void setCustomAttributes(final List<CelebrosIndexAttribute> value)
	{
		setCustomAttributes( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to customAttributes. 
	 * @param value the item to add to customAttributes
	 */
	public void addToCustomAttributes(final SessionContext ctx, final CelebrosIndexAttribute value)
	{
		CUSTOMATTRIBUTESHANDLER.addValue( ctx, this, value );
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to customAttributes. 
	 * @param value the item to add to customAttributes
	 */
	public void addToCustomAttributes(final CelebrosIndexAttribute value)
	{
		addToCustomAttributes( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from customAttributes. 
	 * @param value the item to remove from customAttributes
	 */
	public void removeFromCustomAttributes(final SessionContext ctx, final CelebrosIndexAttribute value)
	{
		CUSTOMATTRIBUTESHANDLER.removeValue( ctx, this, value );
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from customAttributes. 
	 * @param value the item to remove from customAttributes
	 */
	public void removeFromCustomAttributes(final CelebrosIndexAttribute value)
	{
		removeFromCustomAttributes( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>CelebrosExportCronjob.imageBaseUrl</code> attribute.
	 * @return the imageBaseUrl - The base url of the site to be used for image links
	 */
	public String getImageBaseUrl(final SessionContext ctx)
	{
		return (String)getProperty( ctx, IMAGEBASEURL);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>CelebrosExportCronjob.imageBaseUrl</code> attribute.
	 * @return the imageBaseUrl - The base url of the site to be used for image links
	 */
	public String getImageBaseUrl()
	{
		return getImageBaseUrl( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>CelebrosExportCronjob.imageBaseUrl</code> attribute. 
	 * @param value the imageBaseUrl - The base url of the site to be used for image links
	 */
	public void setImageBaseUrl(final SessionContext ctx, final String value)
	{
		setProperty(ctx, IMAGEBASEURL,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>CelebrosExportCronjob.imageBaseUrl</code> attribute. 
	 * @param value the imageBaseUrl - The base url of the site to be used for image links
	 */
	public void setImageBaseUrl(final String value)
	{
		setImageBaseUrl( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>CelebrosExportCronjob.password</code> attribute.
	 * @return the password - password for FTP
	 */
	public String getPassword(final SessionContext ctx)
	{
		return (String)getProperty( ctx, PASSWORD);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>CelebrosExportCronjob.password</code> attribute.
	 * @return the password - password for FTP
	 */
	public String getPassword()
	{
		return getPassword( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>CelebrosExportCronjob.password</code> attribute. 
	 * @param value the password - password for FTP
	 */
	public void setPassword(final SessionContext ctx, final String value)
	{
		setProperty(ctx, PASSWORD,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>CelebrosExportCronjob.password</code> attribute. 
	 * @param value the password - password for FTP
	 */
	public void setPassword(final String value)
	{
		setPassword( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>CelebrosExportCronjob.productType</code> attribute.
	 * @return the productType - Subtype of Product to be indexed
	 */
	public ComposedType getProductType(final SessionContext ctx)
	{
		return (ComposedType)getProperty( ctx, PRODUCTTYPE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>CelebrosExportCronjob.productType</code> attribute.
	 * @return the productType - Subtype of Product to be indexed
	 */
	public ComposedType getProductType()
	{
		return getProductType( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>CelebrosExportCronjob.productType</code> attribute. 
	 * @param value the productType - Subtype of Product to be indexed
	 */
	public void setProductType(final SessionContext ctx, final ComposedType value)
	{
		setProperty(ctx, PRODUCTTYPE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>CelebrosExportCronjob.productType</code> attribute. 
	 * @param value the productType - Subtype of Product to be indexed
	 */
	public void setProductType(final ComposedType value)
	{
		setProductType( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>CelebrosExportCronjob.remoteHost</code> attribute.
	 * @return the remoteHost - Remote host for FTP
	 */
	public String getRemoteHost(final SessionContext ctx)
	{
		return (String)getProperty( ctx, REMOTEHOST);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>CelebrosExportCronjob.remoteHost</code> attribute.
	 * @return the remoteHost - Remote host for FTP
	 */
	public String getRemoteHost()
	{
		return getRemoteHost( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>CelebrosExportCronjob.remoteHost</code> attribute. 
	 * @param value the remoteHost - Remote host for FTP
	 */
	public void setRemoteHost(final SessionContext ctx, final String value)
	{
		setProperty(ctx, REMOTEHOST,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>CelebrosExportCronjob.remoteHost</code> attribute. 
	 * @param value the remoteHost - Remote host for FTP
	 */
	public void setRemoteHost(final String value)
	{
		setRemoteHost( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>CelebrosExportCronjob.remoteLocation</code> attribute.
	 * @return the remoteLocation - Remote location for FTP
	 */
	public String getRemoteLocation(final SessionContext ctx)
	{
		return (String)getProperty( ctx, REMOTELOCATION);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>CelebrosExportCronjob.remoteLocation</code> attribute.
	 * @return the remoteLocation - Remote location for FTP
	 */
	public String getRemoteLocation()
	{
		return getRemoteLocation( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>CelebrosExportCronjob.remoteLocation</code> attribute. 
	 * @param value the remoteLocation - Remote location for FTP
	 */
	public void setRemoteLocation(final SessionContext ctx, final String value)
	{
		setProperty(ctx, REMOTELOCATION,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>CelebrosExportCronjob.remoteLocation</code> attribute. 
	 * @param value the remoteLocation - Remote location for FTP
	 */
	public void setRemoteLocation(final String value)
	{
		setRemoteLocation( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>CelebrosExportCronjob.remotePort</code> attribute.
	 * @return the remotePort - Remote port for FTP
	 */
	public Integer getRemotePort(final SessionContext ctx)
	{
		return (Integer)getProperty( ctx, REMOTEPORT);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>CelebrosExportCronjob.remotePort</code> attribute.
	 * @return the remotePort - Remote port for FTP
	 */
	public Integer getRemotePort()
	{
		return getRemotePort( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>CelebrosExportCronjob.remotePort</code> attribute. 
	 * @return the remotePort - Remote port for FTP
	 */
	public int getRemotePortAsPrimitive(final SessionContext ctx)
	{
		Integer value = getRemotePort( ctx );
		return value != null ? value.intValue() : 0;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>CelebrosExportCronjob.remotePort</code> attribute. 
	 * @return the remotePort - Remote port for FTP
	 */
	public int getRemotePortAsPrimitive()
	{
		return getRemotePortAsPrimitive( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>CelebrosExportCronjob.remotePort</code> attribute. 
	 * @param value the remotePort - Remote port for FTP
	 */
	public void setRemotePort(final SessionContext ctx, final Integer value)
	{
		setProperty(ctx, REMOTEPORT,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>CelebrosExportCronjob.remotePort</code> attribute. 
	 * @param value the remotePort - Remote port for FTP
	 */
	public void setRemotePort(final Integer value)
	{
		setRemotePort( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>CelebrosExportCronjob.remotePort</code> attribute. 
	 * @param value the remotePort - Remote port for FTP
	 */
	public void setRemotePort(final SessionContext ctx, final int value)
	{
		setRemotePort( ctx,Integer.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>CelebrosExportCronjob.remotePort</code> attribute. 
	 * @param value the remotePort - Remote port for FTP
	 */
	public void setRemotePort(final int value)
	{
		setRemotePort( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>CelebrosExportCronjob.site</code> attribute.
	 * @return the site - The Site to be indexed
	 */
	public CMSSite getSite(final SessionContext ctx)
	{
		return (CMSSite)getProperty( ctx, SITE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>CelebrosExportCronjob.site</code> attribute.
	 * @return the site - The Site to be indexed
	 */
	public CMSSite getSite()
	{
		return getSite( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>CelebrosExportCronjob.site</code> attribute. 
	 * @param value the site - The Site to be indexed
	 */
	public void setSite(final SessionContext ctx, final CMSSite value)
	{
		setProperty(ctx, SITE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>CelebrosExportCronjob.site</code> attribute. 
	 * @param value the site - The Site to be indexed
	 */
	public void setSite(final CMSSite value)
	{
		setSite( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>CelebrosExportCronjob.username</code> attribute.
	 * @return the username - username for FTP
	 */
	public String getUsername(final SessionContext ctx)
	{
		return (String)getProperty( ctx, USERNAME);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>CelebrosExportCronjob.username</code> attribute.
	 * @return the username - username for FTP
	 */
	public String getUsername()
	{
		return getUsername( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>CelebrosExportCronjob.username</code> attribute. 
	 * @param value the username - username for FTP
	 */
	public void setUsername(final SessionContext ctx, final String value)
	{
		setProperty(ctx, USERNAME,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>CelebrosExportCronjob.username</code> attribute. 
	 * @param value the username - username for FTP
	 */
	public void setUsername(final String value)
	{
		setUsername( getSession().getSessionContext(), value );
	}
	
}
