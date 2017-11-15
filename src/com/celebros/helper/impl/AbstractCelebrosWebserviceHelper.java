/**
 *
 */
package com.celebros.helper.impl;

import de.hybris.platform.cms2.model.site.CMSSiteModel;
import de.hybris.platform.commercefacades.product.PriceDataFactory;
import de.hybris.platform.commercefacades.product.data.PriceData;
import de.hybris.platform.commerceservices.impersonation.ImpersonationService;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.product.PriceService;
import de.hybris.platform.stock.StockService;
import de.hybris.platform.store.BaseStoreModel;

import java.util.List;

import com.celebros.data.CelebrosProductStatusData;
import com.celebros.helper.CelebrosWebserviceHelper;


/**
 * Abstract class for Real Time stock/price service helper implementing some lesser modified methods.
 */
public abstract class AbstractCelebrosWebserviceHelper implements CelebrosWebserviceHelper
{
	private StockService stockService;

	private PriceService priceService;

	private ImpersonationService impersonationService;

	private PriceDataFactory priceDataFactory;

	protected abstract Integer getStockLevel(final ProductModel product, final BaseStoreModel store);

	protected abstract void populateStockDetails(final CelebrosProductStatusData productStatus, final ProductModel product,
			final BaseStoreModel store);

	protected abstract List<PriceData> getPrices(final ProductModel product, final CMSSiteModel site);

	/**
	 * @see com.celebros.helper.CelebrosWebserviceHelper#populateProductStatus(de.hybris.platform.core.model.product.ProductModel,
	 *      de.hybris.platform.store.BaseStoreModel, de.hybris.platform.cms2.model.site.CMSSiteModel)
	 */
	public CelebrosProductStatusData populateProductStatus(final ProductModel product, final BaseStoreModel store,
			final CMSSiteModel site)
	{
		final CelebrosProductStatusData productStatus = new CelebrosProductStatusData();
		productStatus.setProductCode(product.getCode());
		productStatus.setPrice(getPrices(product, site));
		populateStockDetails(productStatus, product, store);
		return productStatus;
	}

	public StockService getStockService()
	{
		return stockService;
	}

	public void setStockService(final StockService stockService)
	{
		this.stockService = stockService;
	}

	public PriceService getPriceService()
	{
		return priceService;
	}

	public void setPriceService(final PriceService priceService)
	{
		this.priceService = priceService;
	}

	public ImpersonationService getImpersonationService()
	{
		return impersonationService;
	}

	public void setImpersonationService(final ImpersonationService impersonationService)
	{
		this.impersonationService = impersonationService;
	}

	public PriceDataFactory getPriceDataFactory()
	{
		return priceDataFactory;
	}

	public void setPriceDataFactory(final PriceDataFactory priceDataFactory)
	{
		this.priceDataFactory = priceDataFactory;
	}
}
