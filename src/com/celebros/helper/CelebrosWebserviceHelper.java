/**
 *
 */
package com.celebros.helper;

import de.hybris.platform.cms2.model.site.CMSSiteModel;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.store.BaseStoreModel;

import com.celebros.data.CelebrosProductStatusData;


/**
 * Interface for Real Time stock/price service helper.
 */
public interface CelebrosWebserviceHelper
{
	/**
	 * Populates a CelebrosProductStatusData object for a product, at a given store for a given site.
	 *
	 * @param product
	 *           The Product to populate for
	 * @param store
	 *           The BaseStore to populate for
	 * @param site
	 *           The CMSSite to populate for
	 * @return CelebrosProductStatusData representing the current price(s) and stocklevel(s) of the product given the
	 *         parameters.
	 */
	CelebrosProductStatusData populateProductStatus(final ProductModel product, final BaseStoreModel store, final CMSSiteModel site);
}
