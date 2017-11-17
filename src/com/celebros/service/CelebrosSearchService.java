package com.celebros.service;

import JavaSearchAPI.EnumAnswerInSearchPath;
import JavaSearchAPI.QwiserSearchResults;
import com.ramtool.core.service.celebros.CelebrosSearchInfo;
import de.hybris.platform.commercefacades.search.data.AutocompleteSuggestionData;
import de.hybris.platform.commerceservices.search.facetdata.ProductSearchPageData;

import java.util.List;

/**
 * Created by alexander on 15.11.17.
 */
public interface CelebrosSearchService{

    ProductSearchPageData advancedSearch(CelebrosSearchInfo searchInfo);

    ProductSearchPageData refineSearch(CelebrosSearchInfo searchInfo);

    ProductSearchPageData search(String text, CelebrosSearchInfo searchInfo);

    List<AutocompleteSuggestionData> getAutocompleteSuggestionData(String text);
}
