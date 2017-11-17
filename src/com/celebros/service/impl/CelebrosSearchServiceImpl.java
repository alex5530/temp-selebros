package com.celebros.service.impl;

import JavaSearchAPI.*;
import com.celebros.service.CelebrosSearchService;
import com.ramtool.core.service.celebros.CelebrosSearchInfo;
import de.hybris.platform.commercefacades.product.data.ImageData;
import de.hybris.platform.commercefacades.product.data.PriceData;
import de.hybris.platform.commercefacades.product.data.ProductData;
import de.hybris.platform.commercefacades.search.data.AutocompleteSuggestionData;
import de.hybris.platform.commercefacades.search.data.SearchQueryData;
import de.hybris.platform.commercefacades.search.data.SearchStateData;
import de.hybris.platform.commerceservices.search.facetdata.FacetData;
import de.hybris.platform.commerceservices.search.facetdata.FacetValueData;
import de.hybris.platform.commerceservices.search.facetdata.ProductCategorySearchPageData;
import de.hybris.platform.commerceservices.search.facetdata.ProductSearchPageData;
import de.hybris.platform.commerceservices.search.pagedata.PaginationData;
import de.hybris.platform.commerceservices.search.pagedata.SearchPageData;
import de.hybris.platform.commerceservices.search.pagedata.SortData;
import org.apache.log4j.Logger;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.ramtool.core.service.celebros.CelebrosSearchInfo.SearchType.ADVANCED;
import static com.ramtool.core.service.celebros.CelebrosSearchInfo.SearchType.REFINE;
import static com.ramtool.core.service.celebros.CelebrosSearchInfo.SearchType.SIMPLE;

/**
 * Created by alexander on 15.11.17.
 */
public class CelebrosSearchServiceImpl<ITEM extends ProductData> implements CelebrosSearchService {

    // it is not in properties because for different sitekeys there are different property names
    public static final String SITE_KEY = "RamTool";
    public static final String SITE_HOST = "usdev-search.celebros.com";
    public static final int SITE_PORT = 6035;

    public static final String SITE_KEY_2 = "RSupply";
    public static final String SITE_HOST_2 = "RSupply-search.celebros.com";

    public static final EnumAnswerInSearchPath Exclude = EnumAnswerInSearchPath.getEnumAnswerInSearchPath(0);
    public static final EnumAnswerInSearchPath ExactAnswerNode = EnumAnswerInSearchPath.getEnumAnswerInSearchPath(1);
    public static final EnumAnswerInSearchPath EntireAnswerPath = EnumAnswerInSearchPath.getEnumAnswerInSearchPath(2);

    private static String SEARCH_PREFIX = "/search?q=";

    private QwiserSearchAPI qwiserSearchAPI = null;


    Logger LOG = Logger.getLogger(CelebrosSearchServiceImpl.class);


    public ProductSearchPageData<SearchStateData, ITEM> advancedSearch(CelebrosSearchInfo searchInfo){

        try {

            QwiserSearchAPI api = getApi(); //:Manufacturer:3M
            QwiserSearchResults searchResults = api.SearchAdvance(searchInfo.getQuery(), "", "", EnumAnswerInSearchPath.EntireAnswerPath, "", "20", searchInfo.getSortCode(), "0", "");

            searchResults.SearchInformation().SessionId();
//            searchResults.GetSe

            searchResults.GetSearchHandle();



            ProductSearchPageData productSearchPageData =  buildPageData(searchResults, searchInfo);
            setFacets(searchResults, productSearchPageData,searchInfo.getQuery());

            return productSearchPageData;

        }catch (Exception e){
            LOG.error(":::advancedSearch error:::",e);
            return null;
        }
    }

    @Override
    public ProductSearchPageData refineSearch(CelebrosSearchInfo searchInfo) {

        QwiserSearchAPI api = getApi();
////////////
//        QwiserSearchResults searchResults1 = api.ChangePageSize(searchInfo.getSearchHandle(), searchInfo.getNumberPerPage());
//////////
//        String sortedHandler  = getSortedHandler(searchInfo.getSearchHandle(), searchInfo.getSortCode(),false,false);
//        String pageSizeHandler = getPageSizeHandler(sortedHandler, searchInfo.getNumberPerPage());
//        QwiserSearchResults searchResults = getPage(pageSizeHandler, searchInfo.getPage());
/////////
        QwiserSearchResults searchResults = api.SearchAdvance(searchInfo.getQuery(), "", "", EnumAnswerInSearchPath.EntireAnswerPath, "", Integer.toString(searchInfo.getNumberPerPage()), searchInfo.getSortCode(), Integer.toString(searchInfo.getPage()), "");

        //блять возможно авсвер айди в адвансед серче это айдишник предыдущего запроса

//        getApi().AnswerQuestion()


        try {
            ProductSearchPageData productSearchPageData = buildPageData(searchResults, searchInfo);
            setFacets(api.MoveToPage(searchInfo.getBaseSearchHandle(),0), productSearchPageData,searchInfo.getQuery()); //  todo set fasets from previos search

            return productSearchPageData;
        } catch (Exception e) {
            LOG.error(":::advancedSearch error:::", e);
            return null;
        }
    }

    @Override
    public ProductSearchPageData search(String text, CelebrosSearchInfo searchInfo) {

        try {

            QwiserSearchAPI api = getApi();
            QwiserSearchResults searchResults = api.Search(text);

            ProductSearchPageData productSearchPageData = buildPageData(searchResults, searchInfo);
            productSearchPageData.setOriginalSearchText(text);

            setFacets(searchResults, productSearchPageData, "");

            searchInfo.setBaseSearchHandle(searchResults.GetSearchHandle());

            return productSearchPageData;

        } catch (Exception e) {
            LOG.error(":::simpleSearch error:::", e);
            return null;
        }


    }

    public List<AutocompleteSuggestionData> getAutocompleteSuggestionData(String text){

        List<AutocompleteSuggestionData> dataList = new ArrayList<>();

        QwiserSearchAPI api;
        try{
            api = getApi();
        } catch (Exception e){

            LOG.error(":::getAutocompleteSuggestions ERROR::::", e);
            return dataList;
        }

        QwiserSearchResults searchResults = api.Search(text);

        List<AutocompleteSuggestionData> result = new ArrayList<>();

        QwiserProducts products = searchResults.Products();

        for(int i =0; i< products.Count(); i++){
            AutocompleteSuggestionData data = new AutocompleteSuggestionData();
            data.setTerm(products.getProduct(i).GetField("Title"));

        }

        return result;

    }

    private ProductSearchPageData<SearchStateData, ITEM> buildPageData(QwiserSearchResults searchResults, CelebrosSearchInfo searchInfo) throws UnsupportedEncodingException {


        ProductCategorySearchPageData searchPageData = new ProductCategorySearchPageData();
//        searchPageData.setFreeTextSearch(searchResults.SearchInformation().OriginalQuery());//todo
        searchPageData.setFreeTextSearch(searchInfo.getSearchText());

//         searchState.query  "power:relevance:packageWidth:0-9.99"
//                позаменять символы и добавить префикс


        SearchStateData searchStateData = new SearchStateData();
        searchStateData.setUrl(buildSearchUrl(searchInfo)); //todo   // a%3  = :

        SearchQueryData queryData = new SearchQueryData();
//        queryData.setValue(searchInfo==null? searchResults.SearchInformation().GetQuery()+"&#x3a;relevance":searchInfo.getQuery()); //todo
        queryData.setValue(buildHexUrl(searchStateData.getUrl()));


        searchStateData.setQuery(queryData);

        searchPageData.setCurrentQuery(searchStateData);
        searchPageData.setBreadcrumbs(Collections.emptyList());
        searchPageData.setFacets(Collections.emptyList());
        searchPageData.setSorts(Collections.emptyList());

        PaginationData paginationData = new PaginationData();
        paginationData.setNumberOfPages(searchResults.NumberOfPages());
        paginationData.setTotalNumberOfResults(searchResults.RelevantProductsCount());
        paginationData.setCurrentPage(searchResults.SearchInformation().GetCurrentPage());
        paginationData.setPageSize(searchResults.SearchInformation().GetPageSize());
        paginationData.setSort("relevance");

        searchPageData.setPagination(paginationData);



        QwiserProducts products = searchResults.Products();
        List<ProductData> results = new ArrayList<>();

        for(int i =0; i< products.Count(); i++){

            QwiserProduct qwiserProduct = products.getProduct(i);

            ProductData productData = new ProductData();
            productData.setName(qwiserProduct.GetField("Title"));
            productData.setCode(qwiserProduct.GetField("Id"));

            PriceData priceData = new PriceData();
            priceData.setValue(new BigDecimal(qwiserProduct.get("Price")));
            priceData.setFormattedValue(qwiserProduct.get("Price")+" USD"); //todo
            productData.setPrice(priceData);

            productData.setSummary(qwiserProduct.GetField("summary"));

            ImageData imageData = new ImageData();
            imageData.setUrl(URLDecoder.decode(qwiserProduct.GetField("ImageUrl"), "UTF-8"));
            productData.setImages(Collections.singletonList(imageData));

            productData.setUrl(URLDecoder.decode(qwiserProduct.GetField("Link"), "UTF-8"));

            results.add(productData);

        }

        buildFilters(searchResults, searchPageData);

        searchPageData.setResults(results);

        return searchPageData;

    }

    //todo
    private void buildFilters(QwiserSearchResults searchResults, SearchPageData searchPageData){


        List<SortData> filters = new ArrayList();

        SortData sortData = new SortData();
        sortData.setCode("relevance");
        sortData.setName("Best Match");
        sortData.setSelected(searchResults.SearchInformation().SortingOptions().FieldName().equals("relevance"));

        filters.add(sortData);

        SortData sortData2 = new SortData();
        sortData2.setCode("name-asc");
        sortData2.setName("Name (ascending)");
        sortData2.setSelected(searchResults.SearchInformation().SortingOptions().FieldName().equals("relevance"));

        filters.add(sortData2);

        SortData sortData3 = new SortData();
        sortData3.setCode("relevance");
        sortData3.setName("Best Match");
        sortData3.setSelected(searchResults.SearchInformation().SortingOptions().FieldName().equals("relevance"));

        filters.add(sortData3);

        SortData sortData4 = new SortData();
        sortData4.setCode("relevance");
        sortData4.setName("Best Match");
        sortData4.setSelected(searchResults.SearchInformation().SortingOptions().FieldName().equals("relevance"));

        filters.add(sortData4);

        searchPageData.setSorts(filters);

    }


    private  void  setFacets(QwiserSearchResults searchResults, ProductSearchPageData searchPageData, String searchUrl){

        List<FacetData> facets = new ArrayList<>();

        QwiserQuestions questions = searchResults.Questions();

        for (int i = 0; i < questions.Count(); i++) {

            QwiserQuestion question = questions.GetQuestion(i);
            FacetData facetData = new FacetData();

            facetData.setCode(question.Id());
            facetData.setName(question.Text());
            try {
                facetData.setPriority((int)(question.Rank()*1000)); //todo
            } catch (Exception e){
                LOG.error(e);
                continue;
            }


            List<FacetValueData> facetValueDatas = new ArrayList<>();

            addFacets(facetValueDatas, question.Answers(), question.SideText(), searchUrl);
            addFacets(facetValueDatas, question.ExtraAnswers(), question.SideText(), searchUrl);

            facetData.setValues(facetValueDatas);
            facets.add(facetData);

        }

        searchPageData.setFacets(facets);

    }

    private void addFacets(List<FacetValueData> facetValueDatas, QwiserAnswers aswers, String name, String searchUrl){

        // for facats adds parametr to query %3Arelevance%3AmanufacturerNameFacet%3APowers%3AmanufacturerNameFacet%3AMaster+Distribution&text=&pageSize=12#
        for (int j = 0; j< aswers.Count(); j++){

            FacetValueData facetValueData = new FacetValueData();
            QwiserAnswer answer = aswers.get(j);

            facetValueData.setCode(answer.Text());
            facetValueData.setName(answer.Text());
            facetValueData.setCount(answer.ProductCount());
            facetValueData.setSelected(answer.Type().equals(EnumAnswerType.Normal) || answer.Type().equals(EnumAnswerType.Price));//todo

            SearchStateData searchStateData = new SearchStateData();
            searchStateData.setUrl(addParameterToSearchUrl(searchUrl, "%3A"+name+"%3A"+answer.Text().replaceAll("\\s+",""))); //todo

            SearchQueryData searchQueryData = new SearchQueryData();
            searchQueryData.setValue(searchUrl+":"+name+":"+answer.Text().replaceAll("\\s+","")); //todo

            searchStateData.setQuery(searchQueryData);

            facetValueData.setQuery(searchStateData);

            facetValueDatas.add(facetValueData);

        }

    }

    private QwiserSearchAPI getApi() {
        try {

            if (qwiserSearchAPI == null){
                qwiserSearchAPI = new QwiserSearchAPI(SITE_KEY, SITE_HOST, SITE_PORT);
            }

            return qwiserSearchAPI;

        } catch (Exception e) {
            LOG.error(":::Can't get QwiserSearchApi:::", e);
            return null;
        }
    }

//    private String getSSearchUrl(CelebrosSearchInfo searchInfo){
//        if (searchInfo.isSimpleSearch()) return SEARCH_PREFIX + searchInfo.getSearchText() + "%3Arelevance";
//        return SEARCH_PREFIX + searchInfo.getQuery().replaceAll(":", "%3A").replaceAll(" ", "+");
//    }
//
//    private String getHexUrl(CelebrosSearchInfo searchInfo){
//        return searchInfo.getSearchText();
//    }

    private String buildSearchUrl(CelebrosSearchInfo searchInfo) {

//        "/search?q=a%3Arelevance"
//        search?sort=brand-desc&q=power%3Arelevance%3Acategory%3A131&pageSize=25#


        if (searchInfo == null) throw new NullPointerException();//return SEARCH_PREFIX+""+"%3Arelevance";
        if (searchInfo.isSimpleSearch()) return SEARCH_PREFIX + searchInfo.getSearchText() + "%3Arelevance";
        return SEARCH_PREFIX + searchInfo.getQuery().replaceAll(":", "%3A").replaceAll(" ", "+");

    }

    private String buildHexUrl(String s) {
        return s.replaceAll("%3A", "&#x3a;").replaceAll("\\+", "&#20;").replaceAll("search\\?q=","");
    }

    private String addParameterToSearchUrl(String urlinfo, String parametr) {

        return SEARCH_PREFIX + parametr + "&" + urlinfo.replaceAll(":", "%3A");

    }

    private String getSortedHandler(String handler, String sort, boolean numeric, boolean ascending) {
        return sort == null ? handler : getApi().SortByField(handler, sort, false, false).GetSearchHandle();
    }

    private String getPageSizeHandler(String handler, int size) {
        return getApi().ChangePageSize(handler, size).GetSearchHandle();
    }

    private QwiserSearchResults getPage(String handler, int page) {
        return getApi().MoveToPage(handler, page);
    }

}
