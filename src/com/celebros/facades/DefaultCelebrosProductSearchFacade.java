package com.celebros.facades;

import JavaSearchAPI.*;
import com.celebros.service.CelebrosSearchService;
import com.ramtool.core.service.celebros.CelebrosSearchInfo;
import com.ramtool.core.service.celebros.CelebrosSessionFlowService;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.apache.log4j.Logger;


import java.util.List;

import static com.ramtool.core.service.celebros.CelebrosSearchInfo.SearchType.REFINE;
import static com.ramtool.core.service.celebros.CelebrosSearchInfo.SearchType.SIMPLE;

/**
 * Created by user on 14.11.17.
 */
public class DefaultCelebrosProductSearchFacade<ITEM extends ProductData> extends RTDefaultProductSearchFacade {

    Logger LOG = Logger.getLogger(DefaultCelebrosProductSearchFacade.class);

    @Autowired
    private CelebrosSearchService celebrosSearchService;

    @Autowired
    private CelebrosSessionFlowService celebrosSessionFlowService;

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

        CelebrosSearchInfo searchInfo = celebrosSessionFlowService.getCelebresSearchFlow();
        ProductSearchPageData productSearchPageData = celebrosSearchService.search(text, searchInfo);

        celebrosSessionFlowService.save(searchInfo);

        return productSearchPageData;
    }

    @Override
    public ProductSearchPageData textSearch(SearchStateData searchState, PageableData pageableData) {

        CelebrosSearchInfo searchInfo = celebrosSessionFlowService.getCelebresSearchFlow();

//        return celebrosSearchService.advancedSearch(searchState.getQuery().getValue(), "", "", EnumAnswerInSearchPath.getEnumAnswerInSearchPath(0), "", "66", "", "0", "");
//        return celebrosSearchService.advancedSearch(searchInfo.getSearchText(), "", "", EnumAnswerInSearchPath.getEnumAnswerInSearchPath(0), "", Integer.toString(searchInfo.getNumberPerPage()), searchInfo.getSortCode(), Integer.toString(searchInfo.getPage()), "", searchInfo);

        if (searchInfo.getSearchType().equals(REFINE)){
            return celebrosSearchService.refineSearch(searchInfo);
        }

        ProductSearchPageData productSearchPageData = celebrosSearchService.search(searchInfo.getSearchText(), searchInfo);
        celebrosSessionFlowService.save(searchInfo);
        return productSearchPageData;

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

        return celebrosSearchService.getAutocompleteSuggestionData(input);
    }
}
