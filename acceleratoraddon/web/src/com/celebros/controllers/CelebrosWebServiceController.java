/**
 *
 */
package com.celebros.controllers;


import de.hybris.platform.acceleratorstorefrontcommons.controllers.pages.AbstractPageController;
import de.hybris.platform.cms2.model.site.CMSSiteModel;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.product.ProductService;
import de.hybris.platform.site.BaseSiteService;
import de.hybris.platform.store.BaseStoreModel;
import de.hybris.platform.store.services.BaseStoreService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.celebros.data.CelebrosProductStatusData;
import com.celebros.data.CelebrosProductStatusRequestData;
import com.celebros.helper.CelebrosWebserviceHelper;
import com.google.common.collect.Lists;


@Controller
@Scope("tenant")
@RequestMapping(value = "/celebros")
public class CelebrosWebServiceController extends AbstractPageController
{

	@Autowired
	BaseSiteService baseSiteService;

	@Autowired
	ProductService productService;

	@Autowired
	CelebrosWebserviceHelper celebrosWebserviceHelper;

	@Autowired
	BaseStoreService baseStoreService;

	@RequestMapping(value = "productStatus", method = RequestMethod.POST, consumes =
	{ MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public List<CelebrosProductStatusData> celebrosProductStatusCheck(@RequestBody final CelebrosProductStatusRequestData request)
	{
		final BaseStoreModel store = baseStoreService.getBaseStoreForUid(request.getStoreID());
		final CMSSiteModel site = (CMSSiteModel) baseSiteService.getBaseSiteForUID(request.getSiteID());
		final List<CelebrosProductStatusData> productStatuses = Lists.newArrayList();
		for (final String productCode : request.getProductCodes())
		{
			final ProductModel product = productService.getProductForCode(site.getDefaultCatalog().getActiveCatalogVersion(), productCode);
			final CelebrosProductStatusData productStatus = celebrosWebserviceHelper.populateProductStatus(product, store, site);
			productStatuses.add(productStatus);

		}
		return productStatuses;
	}
}
