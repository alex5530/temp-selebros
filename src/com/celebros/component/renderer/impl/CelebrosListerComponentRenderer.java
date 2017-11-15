/**
 *
 */
package com.celebros.component.renderer.impl;

import JavaSearchAPI.QwiserProduct;
import JavaSearchAPI.QwiserProducts;
import JavaSearchAPI.QwiserSearchAPI;
import JavaSearchAPI.QwiserSearchResults;
import de.hybris.platform.addonsupport.renderer.impl.DefaultAddOnCMSComponentRenderer;
import de.hybris.platform.util.Config;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;

import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;


import com.celebros.model.components.CelebrosListerComponentModel;


/**
 * This is the renderer for the CelebrosListerComponentModel
 */
public class CelebrosListerComponentRenderer extends DefaultAddOnCMSComponentRenderer<CelebrosListerComponentModel>
{
	final String listerId = Config.getParameter("celebros.lister.id");
	final String clientConfig = Config.getParameter("celebros.lister.clientconfig.filename");
	final String jQuery = Config.getParameter("celebros.lister.jquery.filename");
	final Logger log = Logger.getLogger(CelebrosListerComponentRenderer.class);

	@Override
	public void renderComponent(final PageContext pageContext, final CelebrosListerComponentModel component)
			throws ServletException, IOException
	{
		try
		{
			QwiserSearchAPI api = new QwiserSearchAPI("RamTool", "usdev-search.celebros.com",6035);
			ArrayList<QwiserProduct> qwiserProductList = new ArrayList<>();
			QwiserSearchResults searchResults = api.Search("some");
			QwiserProducts qwiserProducts = searchResults.Products();
			QwiserProduct prod = qwiserProducts.getProduct(0);
			String name = prod.GetField("Title"); // Price,Link the same
		}catch (Exception e){
			e.printStackTrace();
		}

		if (component.getCelebrosEnabled() != null && component.getCelebrosEnabled())
		{
			final JspWriter out = pageContext.getOut();
			out.write("<script type='text/javascript' src='//" + component.getScriptServer() + "/" + jQuery + "'></script>");
			out.write("<script type='text/javascript' src='//" + component.getScriptServer() + "/"
					+ String.format(clientConfig, component.getCustomerName()) + "'></script>");
			out.write("<div id='" + listerId + "'");
			if (!StringUtils.isEmpty(component.getCssClass()))
			{
				out.write(" class='" + component.getCssClass() + "'");
			}
			out.write("></div>");
		}
	}
}