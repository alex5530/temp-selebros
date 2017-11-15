/**
 *
 */
package com.celebros.dao.impl;

import de.hybris.platform.catalog.constants.GeneratedCatalogConstants.Enumerations.ArticleApprovalStatus;
import de.hybris.platform.catalog.model.CatalogVersionModel;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.servicelayer.internal.dao.DefaultGenericDao;
import de.hybris.platform.servicelayer.search.FlexibleSearchQuery;
import de.hybris.platform.servicelayer.search.FlexibleSearchService;
import de.hybris.platform.servicelayer.search.SearchResult;

import java.util.List;


/**
 *
 * Celebros extendion of ProductDao
 *
 */
public class CelebrosProductDao extends DefaultGenericDao<ProductModel>
{
	private FlexibleSearchService flexibleSearchService;

	public CelebrosProductDao(final String typecode)
	{
		super(typecode);
	}

	/**
	 * Get all approved products in a given catalog version
	 *
	 * @param version
	 *           The catalog version
	 * @return List of ProductModels
	 */
	public List<ProductModel> getApprovedProductsInCatalogVersion(final CatalogVersionModel version)
	{
		final FlexibleSearchQuery query = new FlexibleSearchQuery(
				"Select {p.pk} from {Product as p join ArticleApprovalStatus as a"
						+ " on {a.pk}={p.approvalstatus} join CatalogVersion as v on {v.pk}={p.catalogVersion}} where {v.pk}=?catalogVersion AND {a.code}=?approved");
		query.addQueryParameter("catalogVersion", version.getPk());
		query.addQueryParameter("approved", ArticleApprovalStatus.APPROVED);
		final SearchResult<ProductModel> result = flexibleSearchService.search(query);
		return result.getResult();
	}

	@Override
	public FlexibleSearchService getFlexibleSearchService()
	{
		return flexibleSearchService;
	}

	@Override
	public void setFlexibleSearchService(final FlexibleSearchService flexibleSearchService)
	{
		this.flexibleSearchService = flexibleSearchService;
	}
}
