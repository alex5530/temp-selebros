/**
 *
 */
package com.celebros.helper.impl;

import de.hybris.platform.cms2.model.site.CMSSiteModel;
import de.hybris.platform.commercefacades.product.data.PriceData;
import de.hybris.platform.commercefacades.product.data.PriceDataType;
import de.hybris.platform.commerceservices.impersonation.ImpersonationContext;
import de.hybris.platform.commerceservices.impersonation.ImpersonationService;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.jalo.order.price.PriceInformation;
import de.hybris.platform.store.BaseStoreModel;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.celebros.data.CelebrosProductStatusData;


/**
 * Default implementation of Real Time stock/price service helper.
 */
public class DefaultCelebrosWebserviceHelper extends AbstractCelebrosWebserviceHelper
{

	@Override
	protected Integer getStockLevel(final ProductModel product, final BaseStoreModel store)
	{
		Integer stockLevel = getStockService().getTotalStockLevelAmount(product, store.getWarehouses());
		for (final ProductModel variant : product.getVariants())
		{
			stockLevel += getStockLevel(variant, store);
		}
		return stockLevel;
	}

	private ImpersonationContext createImpersonationContext(final CMSSiteModel site)
	{
		final ImpersonationContext ctx = new ImpersonationContext();
		ctx.setCatalogVersions(site.getDefaultCatalog().getCatalogVersions());
		ctx.setSite(site);
		return ctx;
	}

	@Override
	protected List<PriceData> getPrices(final ProductModel product, final CMSSiteModel site)
	{
		final List<PriceData> output = new ArrayList<PriceData>();
		getImpersonationService().executeInContext(createImpersonationContext(site),
				new ImpersonationService.Executor<Object, RuntimeException>()
				{

					@Override
					public Object execute() throws RuntimeException
					{
						final List<PriceInformation> prices = getPriceService().getPriceInformationsForProduct(product);
						for (final PriceInformation price : prices)
						{
							output.add(getPriceDataFactory().create(PriceDataType.BUY, new BigDecimal(price.getPriceValue().getValue()),
									price.getPriceValue().getCurrencyIso()));
						}
						return null;
					}

				});
		return output;
	}

	@Override
	protected void populateStockDetails(final CelebrosProductStatusData productStatus, final ProductModel product,
			final BaseStoreModel store)
	{
		productStatus.setStockLevel(getStockLevel(product, store));
		productStatus.setStockLevelStatus(getStockService().getProductStatus(product, store.getWarehouses()).toString());
	}
}
