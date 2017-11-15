package com.celebros.facades;

import JavaSearchAPI.QwiserProduct;
import JavaSearchAPI.QwiserProducts;
import JavaSearchAPI.QwiserSearchAPI;
import JavaSearchAPI.QwiserSearchResults;
import com.ramtool.facades.search.impl.RTDefaultProductSearchFacade;
import de.hybris.platform.commercefacades.product.data.PriceData;
import de.hybris.platform.commercefacades.product.data.ProductData;
import de.hybris.platform.commercefacades.search.ProductSearchFacade;
import de.hybris.platform.commercefacades.search.data.AutocompleteSuggestionData;
import de.hybris.platform.commercefacades.search.data.SearchStateData;
import de.hybris.platform.commerceservices.search.facetdata.ProductCategorySearchPageData;
import de.hybris.platform.commerceservices.search.facetdata.ProductSearchPageData;
import de.hybris.platform.commerceservices.search.pagedata.PageableData;
import de.hybris.platform.commerceservices.threadcontext.ThreadContextService;
import org.springframework.beans.factory.annotation.Required;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 14.11.17.
 */
public class DefaultCelebrosProductSearchFacade<ITEM extends ProductData> extends RTDefaultProductSearchFacade {

    private ThreadContextService threadContextService;

    protected ThreadContextService getThreadContextService()
    {
        return threadContextService;
    }

    @Required
    public void setThreadContextService(final ThreadContextService threadContextService)
    {
        this.threadContextService = threadContextService;
    }

    @Override
    public ProductSearchPageData textSearch(String text) {

        ProductSearchPageData productSearchPageData = new ProductSearchPageData();
        ArrayList<QwiserProduct> qwiserProductList = new ArrayList<>();
        ProductData productData = new ProductData();
        PriceData priceData = new PriceData();
        List<ProductData> list = new ArrayList<>();

        priceData.setValue(new BigDecimal(6.04));
        priceData.setCurrencyIso("USD");
        productData.setName("Product name");
        productData.setPrice(priceData);
        productData.setImages(null);
        list.add(productData);
        productSearchPageData.setResults(list);

        try
        {
            QwiserSearchAPI api = new QwiserSearchAPI("RamTool", "usdev-search.celebros.com",6035);
            QwiserSearchResults searchResults = api.Search("some");
            QwiserProducts qwiserProducts = searchResults.Products();
            QwiserProduct prod = qwiserProducts.getProduct(0);
            String name = prod.GetField("Title"); // Price,Link the same

        }catch (Exception e){
            e.printStackTrace();
        }

        return productSearchPageData;
    }

    @Override
    public ProductSearchPageData textSearch(SearchStateData searchState, PageableData pageableData) {
        return null;
    }

    @Override
    public ProductCategorySearchPageData categorySearch(String categoryCode) {
        return null;
    }

    @Override
    public ProductCategorySearchPageData categorySearch(String categoryCode, SearchStateData searchState, PageableData pageableData) {
        return null;
    }

    @Override
    public List<AutocompleteSuggestionData> getAutocompleteSuggestions(String input) {
        return null;
    }
}
