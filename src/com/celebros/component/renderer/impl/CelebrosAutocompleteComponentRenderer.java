/**
 *
 */
package com.celebros.component.renderer.impl;

import de.hybris.platform.addonsupport.renderer.impl.DefaultAddOnCMSComponentRenderer;
import de.hybris.platform.cms2.servicelayer.services.CMSComponentService;
import de.hybris.platform.servicelayer.exceptions.AttributeNotSupportedException;
import de.hybris.platform.servicelayer.model.ModelService;
import de.hybris.platform.util.Config;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.jsp.PageContext;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Required;

import com.celebros.model.components.CelebrosAutocompleteComponentModel;


/**
 * This is the renderer for the CelebrosAutocompleteComponentModel
 */
public class CelebrosAutocompleteComponentRenderer extends DefaultAddOnCMSComponentRenderer<CelebrosAutocompleteComponentModel>
{

	Logger log = Logger.getLogger(CelebrosAutocompleteComponentRenderer.class);

	CMSComponentService cmsComponentService;
	ModelService modelService;

	@Override
	@Required
	public void setCmsComponentService(final CMSComponentService cmsComponentService)
	{
		this.cmsComponentService = cmsComponentService;
	}

	@Override
	@Required
	public void setModelService(final ModelService modelService)
	{
		this.modelService = modelService;
	}


	@Override
	protected Map<String, Object> getVariablesToExpose(final PageContext pageContext,
			final CelebrosAutocompleteComponentModel component)
	{
		final Map<String, Object> variables = new HashMap<String, Object>();
		for (final String property : cmsComponentService.getEditorProperties(component))
		{
			try
			{
				final Object value = modelService.getAttributeValue(component, property);
				variables.put(property, value);

			}
			catch (final AttributeNotSupportedException e)
			{
				log.debug(e);
			}
		}
		variables.put("celebrosAutocompleteJavascriptFilename", Config.getParameter("celebros.autocomplete.javascript.filename"));
		variables.put("celebrosAutocompleteCssFilename", Config.getParameter("celebros.autocomplete.css.filename"));
		return variables;
	}

}
