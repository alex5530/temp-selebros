<%@ page contentType="text/html; charset=utf-8"  errorPage="" %>

<%@page import="java.io.*"%>
<%@page import="java.net.*"%>
<%@page import="java.io.IOException"%>
<%@page import="java.util.*"%>
<%@page import="java.nio.charset.*"%>
<%@page import="javax.servlet.jsp.*"%>
<%@page import="javax.servlet.http.*"%>
<%@page import="JavaSearchAPI.*"%>
<%@page import="com.celebros.analytics.AnalyticsFunctions"%>

<%!
String SITE_KEY = "??????";
String SEARCH_SERVER = "??????";
int COMM_PORT = 6035;
String CLIENT_WEB_SITE = "??????";
String SEARCH_PAGE = "";
String CURRENCY_SYMBOL = "$";
int PRODUCTS_PER_PAGE =12;
int PRODUCTS_PER_ROW = 5;
int MAX_LEAD_ANSWERS = 3;
int MAX_NON_LEAD_QUESTIONS = 8;
int MAX_NON_LEAD_ANSWERS = 3;
int MAX_NON_LEAD_ANSWERS_LEFT_NAV = 4;

String IMAGE_URL_PREFIX = "";
String URL_PREFIX = "";
String IMAGE_URL_SUFFIX = "";
String URL_SUFFIX = "";
String BANNER_IMAGE_URL_PREFIX = "";
String BANNER_IMAGE_URL_SUFFIX = "";

String NAME_FIELD_NAME = "Name";
String NEW_FIELD_NAME = "New";
String BRAND_FIELD_NAME = "Brand";
String URL_FIELD_NAME = "URL";
String IMAGE_URL_FIELD_NAME = "ImageURL";
String PRICE_FIELD_NAME = "Price";
String CATEGORY_FIELD_NAME = "Description";
String DESCRIPTION_FIELD_NAME = "Description";

String DATA_COLLECTOR_ADDRESS = "??????";
String CUSTOMER_ID = "??????";
String CUSTOMER_NAME = "??????";

String FIELD_NAME_NEW_SORT = "NewSort";
String FIELD_NAME_SALE_SORT = "SaleSort";


String RELATED_SEARCHES_DP_NAME = "related searches";
String URL_DP_NAME = "redirection url";
String ALTERNATIVE_PRODUCTS_DP_NAME = "alternative products";
String COLOR_DP_NAME= "isColor";
String TRANSLATED_SEARCH_TERM = "breadcrumb";

String CUSTOM_MESSAGE_DYNAMIC_PROPERTY_NAME = "custom message";
String BANNER_IMAGE_DYNAMIC_PROPERTY_NAME = "banner image";
String SEO_TEXT_DYNAMIC_PROPERTY_NAME = "SEOtext";
String PROMO_BOX_DYNAMIC_PROPERTY_NAME = "promobox";

boolean GO_TO_PRODUCT_ON_ONE_RESULT = true;
boolean SORT_NORMAL_QUESTIONS_DDLS = true;
boolean SHOW_PRODUCT_COUNT_IN_LEAD_ANSWERS_DDLS = true;
boolean SHOW_PRODUCT_COUNT_IN_NON_LEAD_ANSWERS = true;
boolean COLOR_MESSAGE_IS_REQUIRED = true;
boolean SHOW_MINIMAL_QUESTIONS = true;

int SPECIAL_CASE_NONE = 0;
int SPECIAL_CASE_SPELLING_ERROR = 1;
int SPECIAL_CASE_NO_RESULTS = 2;
int SPECIAL_CASE_ALTERNATIVE_PRODUCTS = 4;
int SPECIAL_CASE_PARTIAL_MATCH = 8;
int SPECIAL_CASE_POPUP = 16;
int SPECIAL_CASE_SIMILAR_PRODUCTS = 32;

int SORT_METHOD_BY_RELEVANCY = 0;
int SORT_METHOD_BY_NAME_ASC = 1;
int SORT_METHOD_BY_NAME_DESC = 2;
int SORT_METHOD_BY_BRAND_ASC = 3;
int SORT_METHOD_BY_BRAND_DESC = 4;
int SORT_METHOD_BY_PRICE_ASC = 5;
int SORT_METHOD_BY_PRICE_DESC = 6;
int SORT_METHOD_BY_SALE_DESC = 7;
int SORT_METHOD_BY_NEW_DESC = 8;

String TEXT_SORT_BY_POPULARITY = "Popularity";
String TEXT_SORT_BY_NAME_ASC = "Name: A to Z";
String TEXT_SORT_BY_NAME_DES = "Name: Z to A";
String TEXT_SORT_BY_PRICE_ASC = "Price: Low to High";
String TEXT_SORT_BY_PRICE_DES = "Price: High to Low";

String TEXT_SEARCH_RESULTS = "Search results";
String TEXT_PRODUCTS = "Products";
String TEXT_SHOWING = "Showing";
String TEXT_OF = "Of";
String TEXT_FOR = "for";
String TEXT_LIKE = "like";
//String TEXT_WE_FOUND = "We found";
String TEXT_WE_FOUND = "";
String TEXT_RELATED_SEARCHES = "<BR>Related searches: ";
String TEXT_MORE = "More";
String TEXT_SORT_BY = "Sort by:";
String TEXT_ASCENDING = "ascending";
String TEXT_DESCENDING = "descending";
String TEXT_DID_YOU_MEAN  =" Did you mean";
String TEXT_MAP_PRICING = "DIVA Discount Applies";
%>

<%! 
String sQuery = "";
String sSearchHandle="";
String PageSettings="";
String SpecialCases="";
String HandledCase="";
boolean noResultsFlag;
String cmQuery = "";

int iCurrentPage;
%>

<%
QwiserSearchAPI api = new QwiserSearchAPI(SITE_KEY, SEARCH_SERVER, COMM_PORT);
QwiserSearchResults qsr;
String asc;
%>

<%
int act = request.getParameter("Action") == null  || request.getParameter("Action").equals("")  ?  0  :  Integer.parseInt( request.getParameter("Action"));
sQuery = request.getParameter("Query") ;
sQuery = scrubSearchTerm(sQuery);
   
iCurrentPage= request.getParameter("CurrentPage") == null || request.getParameter("CurrentPage").equals("") ? 0 : Integer.parseInt(request.getParameter("CurrentPage"));
sSearchHandle= request.getParameter("SearchHandle");


//You must purposely suppress products from displaying
boolean showProducts = true;
if (request.getParameter("ShowProducts") != null) {
	if (request.getParameter("ShowProducts").equals("false")) {
		if (request.getParameter("SearchHandle") == null) {
			showProducts = false;
		}
		else {
			showProducts = true;
		}
	}
}
else {
	showProducts = true;
}

//Switch to remove the sorting bar if products to display is limited and is not the default of 15
boolean productsLimited = false;
if (request.getParameter("ProductCount") != null) {
	if (request.getParameter("AnswerID") == null) {
		productsLimited = true;
	}
}
else {
	productsLimited = false;
}

//You must purposely suppress banners as well
boolean showBanner = true;
if (request.getParameter("Banner") != null) {
	if (request.getParameter("Banner").equals("false")) {
		showBanner = false;
	}
}
//if (request.getParameter("SearchHandle") != null) {
//	showBanner = false;
//}
%>

<%
	switch(act) {
		 case 1: // Set page
			qsr = api.MoveToPage(sSearchHandle,Integer.parseInt(request.getParameter("Page"))-1);
			break;
		case 2: // Answer
			if(request.getParameter("AnswerID") == null || request.getParameter("AnswerID").equals("0") || request.getParameter("AnswerID").equals(""))
				qsr = api.GetCustomResults(sSearchHandle,false,sSearchHandle);
			else
				qsr = api.AnswerQuestion(sSearchHandle,request.getParameter("AnswerID"),EnumAnswerInSearchPath.EntireAnswerPath);
			break;
		case 3: // Revoke an answer
			qsr = api.RemoveAnswersFrom(sSearchHandle,Integer.parseInt(request.getParameter("StartIndex")));
			break;
		case 4: // First question
			qsr = api.ForceQuestionAsFirst(sSearchHandle, request.getParameter("QuestionID"));
			break;
		case 5: //Set Page Size
			qsr = api.ChangePageSize(sSearchHandle,Integer.parseInt(request.getParameter("PageSize")));
			break;			
		case 6: //set sort by		
				int iSortMethod;
				
				if (request.getParameter("SortMethod")!=null)
					iSortMethod = Integer.parseInt(request.getParameter("SortMethod"));
				else
					iSortMethod=SORT_METHOD_BY_RELEVANCY;
		
				if (iSortMethod == SORT_METHOD_BY_RELEVANCY)
					qsr = api.SortByRelevancy(sSearchHandle);
				else if(iSortMethod == SORT_METHOD_BY_PRICE_ASC)
					//qsr = api.SortByField(sSearchHandle, PRICE_FIELD_NAME, true, true);
					//qsr = api.SortByPrice(sSearchHandle, true);
					qsr = api.SortByField(sSearchHandle, "MinPrice", true, true);
				else if(iSortMethod == SORT_METHOD_BY_PRICE_DESC)
					//qsr = api.SortByField(sSearchHandle, PRICE_FIELD_NAME, true, false);
					//qsr = api.SortByPrice(sSearchHandle, false);
					qsr = api.SortByField(sSearchHandle, "MaxPrice", true, false);
				else if(iSortMethod == SORT_METHOD_BY_NAME_ASC)
					qsr = api.SortByField(sSearchHandle, NAME_FIELD_NAME, false, true);
				else if(iSortMethod == SORT_METHOD_BY_NAME_DESC)
					qsr = api.SortByField(sSearchHandle, NAME_FIELD_NAME, false, false);
				else if(iSortMethod == SORT_METHOD_BY_SALE_DESC)
					qsr = api.SortByField(sSearchHandle, FIELD_NAME_SALE_SORT, true, false);
				else if(iSortMethod == SORT_METHOD_BY_NEW_DESC)
					qsr = api.SortByField(sSearchHandle, FIELD_NAME_NEW_SORT, true, false);				
				else
					qsr = api.SortByRelevancy(sSearchHandle);
			break;
		case 7: //Advanced Search
				String 
					sProfile="",
					sAnswerID="",
					sPriceColumn="",
					sPageSize="",
					sSortField="",
					sNumericSort="",
					sAscending="";
				if (request.getParameter("Profile")!=null)
					sProfile = request.getParameter("Profile");
				if (request.getParameter("AnswerID")!=null)
					sAnswerID = request.getParameter("AnswerID");
				if (request.getParameter("PriceColumn")!=null)
					sPriceColumn = request.getParameter("PriceColumn");
				if (request.getParameter("PageSize")!=null)
					sPageSize = request.getParameter("PageSize");
				if (request.getParameter("FieldName")!=null)
					sSortField = request.getParameter("FieldName");
				if (request.getParameter("Numeric")!=null)
					sNumericSort = request.getParameter("Numeric");
				if (request.getParameter("Asc")!=null)
					sAscending = request.getParameter("Asc");
				
				qsr = api.SearchAdvance(sQuery, sProfile, sAnswerID, EnumAnswerInSearchPath.EntireAnswerPath, sPriceColumn, sPageSize, sSortField, sNumericSort, sAscending);
			break;
		  default: qsr = api.Search(sQuery);
		}

	iCurrentPage = qsr.SearchInformation().GetCurrentPage();
	sSearchHandle = qsr.GetSearchHandle();

	HandleRedirect(qsr, out, response);
%>

<%!
public void setSearchHandleCookie(QwiserSearchResults qsr, JspWriter out, HttpServletRequest request, HttpServletResponse response) throws Exception {
	//Store the sSearchHandle in a cookie for used on the product details
	//and add to cart Celebros Analytics tags.
	Cookie searchCookie = new Cookie("sSearchHandle", qsr.SearchInformation().SessionId());
	searchCookie.setPath("/");
	response.addCookie(searchCookie);
}
%>

<%!
public void HandleQwiserAnalytics(QwiserSearchResults qsr, JspWriter out, HttpServletRequest request, HttpServletResponse response) throws Exception {
	HttpSession session = request.getSession(true);
	String sessionID = null;
	if(qsr.SearchInformation().SessionId().length()>1)
	{
		sessionID = qsr.SearchInformation().SessionId();
		session.setAttribute("SessionID", sessionID);
	} else if (session.getAttribute("SessionID")!=null&&((String)session.getAttribute("SessionID")).length()>1)
		sessionID = (String)session.getAttribute("SessionID");
	else
	{
		sessionID = UUID.randomUUID().toString();
		session.setAttribute("SessionID", sessionID);
	}
	String thisURL = request.getRequestURI() + request.getQueryString();
	String referrer =	request.getHeader("Referer");
	//out.println(sessionID);

	//System.out.println(sessionID);
	//System.out.println(referrer);
	
	AnalyticsFunctions af = new AnalyticsFunctions(CUSTOMER_NAME,DATA_COLLECTOR_ADDRESS,CUSTOMER_ID);

	String celebros_image_tag =
		af.Celebros_Analytics_SearchResults(
			sessionID, 
			qsr.LogHandle(),
			session.getId(),
			"1",
			session.getId(),
			referrer,
			true,
			false);

	//System.out.println("*** BEGIN Celebros Tagging Debug ***");
	//System.out.println("sSearchHandle = " + qsr.SearchInformation().SessionId());
	//System.out.println("sLogHandle = " + qsr.LogHandle());
	//System.out.println("sUserID = " + sessionID);
	//System.out.println("sGroupID = " + "1");
	//System.out.println("sWebSessionID = " + sessionID);
	//System.out.println("sReferrer = " + referrer);
	//System.out.println("bFromQwiser = " + true);
	//System.out.println("bIsSSL = " + false);	
	//System.out.println("Tag = " + celebros_image_tag);	
	//System.out.println("*** END Celebros Tagging Debug ***");	
	
	out.print(celebros_image_tag);
}
%>

<%!
public String SubmitData(String sDataParams) {
	String sData = "";
	
	if (sDataParams.toUpperCase().indexOf("SEARCHHANDLE=") < 0) {
		sData = "SearchHandle="+sSearchHandle+"&";
	}
		
	sData += sDataParams;
	sData = SEARCH_PAGE+"?" + sData;
	
	return sData;
}
%>

<%!
public String SubmitDataNoScript(String sDataParams) {
		String sData = "";

		if (sDataParams.toUpperCase().indexOf("name=\"SEARCHHANDLE\"") < 0)
			sData = "<input type=\"hidden\" name=\"SearchHandle\" value=\""+sSearchHandle+"\">";
			
		sData += sDataParams;
		sData = "<form action=\""+SEARCH_PAGE+"\" style=\"height: 12px;\" class=\"moduleheader\" method=\"get\">" + sData;
		
		return sData;
	}
%>

<%
QwiserProducts Products = qsr.Products();
QwiserSearchPath SearchPath = qsr.SearchPath();
QwiserQuestionsEditable Questions = qsr.Questions().CreateEditableClone();
%>

<% setSearchHandleCookie(qsr, out, request, response); %>


<% HandleQwiserAnalytics(qsr, out, request, response); 
	String count = Integer.toString(qsr.RelevantProductsCount());
%>	

<!-- Main Table Celebros Search Content -->
<table width="970" cellspacing="0" cellpadding="2" border="0">
	<tbody>
	<tr>
		<td width="197" valign="top" style="padding-top:1px;">
		
			<!-- left search refinement  -->
			<table id="refinementList" width="190" cellspacing="0" cellpadding="0" border="0">
				<tbody>
					<tr>
						<td class="deptnav">
							<% RenderROBox(Questions, qsr, out, request);	%>
						</td>
					</tr>
				</tbody>
			</table>
		
	</td>
	<td width="550" valign="top">
	
		<%
		if (showBanner) { 
			RenderBannerImage(qsr, out, request);
			RenderCustomMessage(qsr, out);
			RenderSEOText(qsr, out);
			//RenderPromoBox(qsr, out); //it is now called from RenderSEOText
		}
		%>

		<% if (showProducts) { %>
	
			<!-- Holds Breadcrumbs and Page Sorting Bar -->
			<table width="757" cellspacing="0" cellpadding="0" border="0" >
				<tr>
					<td width="100%">
						<%
						HandleRecommendedMessage(qsr, out, request);
						if (!productsLimited) {
							RenderResultMessage(qsr.RelevantProductsCount(), out);
						}
						HandleRelatedSearches(qsr, out, request);
						
						if((sQuery == null || sQuery.equals("")) && request.getParameter("searchQuery") != null){
								cmQuery = request.getParameter("searchQuery");
							}else{
								cmQuery = sQuery;
							}
						
						if (!productsLimited) {	%>
							<table cellpadding="0" cellspacing="0" border="0" width="100%">
								<tr>
									<td height="20" class="moduleheader" style="padding-top:2px;" height="12"><% RenderSortingSelector(qsr.SearchInformation().SortingOptions(), out, request);%></td>
									<td height="20" class="moduleheader" style="padding-top:2px;" height="12" align="right"><%	RenderPageSizeSelector(qsr,out,request); %></td>
								</tr>
								<tr>
									<td height="6" colspan="2" class="moduleshadow"></td>
								</tr>
							</table>
							<% RenderNavigationBar2(qsr, qsr.SearchInformation(), out, request); %>
						<% } %>		
									
					&nbsp;
					</td>
				</tr>
			</table>
	
			<table width="100%" border="0" >
				<tr>
					<td align="center" colspan="2" >
						<% RenderProducts(Products, out, request); %>
					</td>
				</tr>
			</table>
			
			<% if (!productsLimited) {	%>
				<table width="757" cellpadding="0" cellspacing="0" border="0">
					<tr>
						<td height="20" class="moduleheader"  style="padding-top:2px;" height="12"><% RenderSortingSelector(qsr.SearchInformation().SortingOptions(), out, request);%></td>
						<td height="20" class="moduleheader"  style="padding-top:2px;" height="12" align="right"><%	RenderPageSizeSelector(qsr,out,request); %></td>
					</tr>
					<tr>
						<td height="6" colspan="2" class="moduleshadow"></td>
					</tr>
				</table>
				<% RenderNavigationBar2(qsr, qsr.SearchInformation(), out, request); %>
			<% } %>	
				
			<table width="100%" border="0" >
				<tr>
					<td align="center" colspan="2" >
						<p><a href="#top">Back to top</a></p>
					</td>
				</tr>
			</table>

	<% } 
	
	if(noResultsFlag){
		count = "0";
		noResultsFlag = false;
	}
	
	String newcmQuery = cmQuery;
   	if(cmQuery != null) {
	    if(cmQuery.indexOf("menu") > 0){
	   	  	newcmQuery = cmQuery.replaceAll("menu","");
	    }
	    if(cmQuery.indexOf("navigation") > 0){	  
	   		newcmQuery = cmQuery.replaceAll("navigation","");
	    }
   	}
	
	%>

	</tr>
	
<%!
public String URLPathEncode(String strPath) {
	String s = new String();
	try {
	    s = URLEncoder.encode(strPath, "UTF-8");
	} catch (Exception ex) {System.out.println(ex);}
	if (s != null)
	    s = s.replaceAll("'", "%GG");
	return s;
} 
%>

<%!
public void HandleRedirect(QwiserSearchResults sr, JspWriter fout, HttpServletResponse response) throws Exception {
	String redirectTemp = sr.RedirectionUrl();
	if (redirectTemp == null || redirectTemp.equals(""))
		redirectTemp = GetDynamicPropertyByName(sr, URL_DP_NAME);
	if (redirectTemp != null && !redirectTemp.equals(""))
		response.sendRedirect(redirectTemp);	
		//fout.print("DEBUG: Search Term Redirecting to ... " + redirectTemp);
  if (GO_TO_PRODUCT_ON_ONE_RESULT && sr.RelevantProductsCount() == 1 ) {  	
		response.sendRedirect(sr.Products().getProduct(0).GetField(URL_FIELD_NAME));
		//fout.print("DEBUG: Single Product Redirecting to ... " + sr.Products().getProduct(0).GetField(URL_FIELD_NAME));
  }
}
%>

<%!
public void HandleRecommendedMessage(QwiserSearchResults sr, JspWriter fout, HttpServletRequest request) throws Exception {
	String sMessage = "", sSuggestions = "";
	
	if(sr.RecommendedMessage() != null && !sr.RecommendedMessage().equals("")) {
		sMessage = sr.RecommendedMessage();
		sMessage = sMessage.replaceAll("#%","<b>");
		sMessage = sMessage.replaceAll("%#","</b>");
		sMessage = sMessage.replaceAll("&lt;", "<").replaceAll("&gt;", ">");
	}
	
	if(sr.SpellerInformation().AdditionalSuggestions().Count()>0) {
			QwiserSimpleStringCollection scSpellerSuggestions = sr.SpellerInformation().AdditionalSuggestions();
			for (int i=0; i< scSpellerSuggestions.Count();i++) {
				if(i<scSpellerSuggestions.Count()-1)
					sSuggestions +=  "<a href=\"search-celebros.jsp?Query=" + URLEncoder.encode(scSpellerSuggestions.get(i)) + "\"><b> "  + URLEncoder.encode(scSpellerSuggestions.get(i))  + "</b></a>, ";
				else
					sSuggestions +=  "<a href=\"search-celebros.jsp?Query=" + URLEncoder.encode(scSpellerSuggestions.get(i)) + "\"><b> "  + URLEncoder.encode(scSpellerSuggestions.get(i))  + "</b></a>";				
			}
			if (!sSuggestions.equals("")) {
			   sSuggestions  = TEXT_DID_YOU_MEAN + " " + sSuggestions;
			   sSuggestions += "?";
			}
			fout.print(sMessage +  sSuggestions + "<br><br>");
	}
	else {
		if (!sMessage.equals(""))
			fout.print(sMessage + "<br><br>");
	}	
}
%>

<%!
public void RenderCustomMessage(QwiserSearchResults sr, JspWriter fout) throws Exception {
	String sCustomMessage = "";
	sCustomMessage = GetDynamicPropertyByName(sr, CUSTOM_MESSAGE_DYNAMIC_PROPERTY_NAME);
	if (sCustomMessage != null && !sCustomMessage.equals("")) {
		fout.println(sCustomMessage);
		fout.println("<br />");
	}
}
%>

<%!
public void RenderBannerImage(QwiserSearchResults sr, JspWriter fout, HttpServletRequest request) throws Exception {
	String sBannerImage = "";
	sBannerImage = GetDynamicPropertyByName(sr, BANNER_IMAGE_DYNAMIC_PROPERTY_NAME);
	if (sBannerImage != null && !sBannerImage.equals("")) {
		
		//fout.println("<br />request.getContextPath() = " + request.getContextPath() + "<br />");
		//fout.println("<br />request.getRequestURL() = " + request.getRequestURL() + "<br />");
		//fout.println("<br />request.getRemoteHost() = " + request.getRemoteHost() + "<br />");
		
		//fout.println("<br />request.isSecure() = " + request.isSecure() + "<br />");
		//fout.println("<br />request.getProtocol() = " + request.getProtocol() + "<br />");
		//fout.println("<br />request.getServerName() = " + request.getServerName() + "<br />");
		
		//fout.println("<br />request.getServletPath() = " + request.getServletPath() + "<br />");
		
		String protocol = "http://" + request.getServerName();
		
		fout.println("<img src=\"" + protocol + sBannerImage + "\" alt=\"banner image\" border=\"0\">");
		fout.println("<br />");
	}
}
%>

<%!
public void RenderSEOText(QwiserSearchResults sr, JspWriter fout) throws Exception {
	String sBannerText = "";
	String sPromoBox = "";
	sBannerText = GetDynamicPropertyByName(sr, SEO_TEXT_DYNAMIC_PROPERTY_NAME);

	if (GetDynamicPropertyByName(sr, PROMO_BOX_DYNAMIC_PROPERTY_NAME) != null ) {
		sPromoBox = GetDynamicPropertyByName(sr, PROMO_BOX_DYNAMIC_PROPERTY_NAME);
	}
	
	if (sBannerText != null && !sBannerText.equals("")) {
		fout.println("<table width=\"757\" border=\"0\"><tr><td>" + sBannerText + "</td><td>" + sPromoBox + "</td></tr></table>");
		//fout.println(sBannerText);
		fout.println("<br />");	
	}	
}
%>

<%!
public void RenderPromoBox(QwiserSearchResults sr, JspWriter fout) throws Exception {
	String sPromoBox = "";
	sPromoBox = GetDynamicPropertyByName(sr, PROMO_BOX_DYNAMIC_PROPERTY_NAME);
	if (sPromoBox != null && !sPromoBox.equals("")) {
		fout.println(sPromoBox);
		fout.println("<br />");
	}
}
%>

<%!
public void RenderResultMessage(int iProdCount, JspWriter fout ) throws Exception {
	fout.print(TEXT_WE_FOUND + " " + iProdCount + " " + TEXT_PRODUCTS + " " );
} 
%>

<%!
public String RenderSearchPath(QwiserSearchResults sr, JspWriter fout) throws Exception {
	boolean bNotExactResults = !sr.ExactMatchFound();
	String sQuery = sr.SearchInformation().GetQuery();
	QwiserSearchPath spSearchPath = sr.SearchPath();
	int SearchPathCount;
	String mQuery = "";
	
	if ( sQuery.length() > 0 || spSearchPath.Count() > 0 ) {
		if(bNotExactResults) {
			fout.print(TEXT_LIKE);
		}	
		else {
			fout.print(TEXT_FOR);
		}
		
		String breadcrumbAnchor = "DefaultAnchor";
		String translatedSearchTerm = "";
		
		if(GetDynamicPropertyByName(sr, TRANSLATED_SEARCH_TERM) != null && !GetDynamicPropertyByName(sr, TRANSLATED_SEARCH_TERM).equals("")) {
			translatedSearchTerm = GetDynamicPropertyByName(sr, TRANSLATED_SEARCH_TERM);
		}
		
		if (translatedSearchTerm.equals("")) {
			breadcrumbAnchor = sQuery;
		}
		else {
			breadcrumbAnchor = translatedSearchTerm;
		}		
				
		if( spSearchPath.Count()  == 0 ) {
		  fout.print(" \"" + breadcrumbAnchor + "\"");
		  mQuery = breadcrumbAnchor;
		}
		else {
			String sText = "";
			if(!sQuery.equals(""))
				sText += " <a href=\"" + SubmitData("Action=3&amp;StartIndex=0")+"\" style=\"font-weight:bold;\">" + breadcrumbAnchor + "</a> >";				
				mQuery += breadcrumbAnchor + " >";	
			for(int i = 0; i< spSearchPath.Count(); i++) {
				QwiserSearchPathEntry spe = spSearchPath.Get(i);
				QwiserAnswer aAnswer;
				if (spe.Answers().Count()>1)
					aAnswer = spe.Answers().get(spe.AnswerIndex());
				else
					aAnswer = spe.Answers().get(0);
					
				if(i == spSearchPath.Count() -1){
					sText += " " + URLDecoder.decode(aAnswer.Text() ,"UTF-8");
					mQuery += " " + URLDecoder.decode(aAnswer.Text(),"UTF-8" );
				}else{
					sText += " " + "<a href=\""+SubmitData("Action=3&amp;StartIndex=" + (i+1)) + "\" style=\"font-weight:bold;\">" + URLDecoder.decode(aAnswer.Text(),"UTF-8" ) + "</a> > ";
					mQuery += " " + URLDecoder.decode(aAnswer.Text(),"UTF-8" ) + " > ";
				}
			}
			fout.print(sText);
		}
	}else{
		noResultsFlag = true;
	}
	return mQuery;
}
%>

<%!
public void HandleRelatedSearches(QwiserSearchResults sr, JspWriter fout, HttpServletRequest request) throws Exception {
	String sRelatedSearches = TEXT_RELATED_SEARCHES;	
			
	for(int i=0;i<sr.RelatedSearches().Count();i++) {
		sRelatedSearches+="<a href=\"search-celebros.jsp?Query=" + URLEncoder.encode(sr.RelatedSearches().get(i),"UTF-8") + "\"><b>" + URLDecoder.decode(sr.RelatedSearches().get(i),"UTF-8") + "</b></a>";
		if( i != sr.RelatedSearches().Count() - 1 )
			fout.print(", ");					
	}
}
%>

<%!
public void RenderROBox(QwiserQuestionsEditable qqQuestions,QwiserSearchResults sr, JspWriter fout, HttpServletRequest request) throws Exception {
	int iQuestionCount= qqQuestions.Count();
	iQuestionCount= qqQuestions.Count();
		
	if (iQuestionCount > 0) {
		//fout.println("<div style=\"width:180px;margin-right:15px;float:left;\"><div style=\"margin-bottom:15px;\">");
		//QwiserQuestion LQ = qqQuestions.GetQuestion(0);
		//if (LQ.Id().equals(CATEGORY_QUESTION_ID))
		//{
			//RenderLeadQ(LQ,fout, request);		
			//qqQuestions.RemoveAt(0);
			
			if (iQuestionCount > 1)
				RenderNonLeadQuestions(qqQuestions, fout, request);
		/*}
		else {
			RenderNonLeadQuestions(qqQuestions, fout, request);
		}
		*/
		//fout.println("</div></div>");
	}
}
%>

<%!
public void RenderLeadQ(QwiserQuestion leadingQuestion, JspWriter fout, HttpServletRequest request) throws Exception {
	fout.println("<h2>"+leadingQuestion.Text()+"</h2>");
	fout.println("<table width=\"100%\" align=\"center\" cellpadding=0 cellspacing=0 border=\"0\"><tr><td>" +								
					"<tr>" +
						"<td width=\"100%\" >" +
							"<table id=\"tblPics\" width=\"100%\">"+
								"<tr>");
	QwiserAnswers aaAnswers = leadingQuestion.Answers();
	QwiserAnswers aaExtraAnswers = leadingQuestion.ExtraAnswers();
	int iAnswerCount = aaAnswers.Count();
	int iExtraAnswerCount = aaExtraAnswers.Count();
	int iTotalAnswerCount = iAnswerCount + iExtraAnswerCount;

	int iDisplayLimit, iDisplayExtras;
	iDisplayExtras = 0;
	if(iTotalAnswerCount > MAX_LEAD_ANSWERS) {
	    iDisplayLimit = MAX_LEAD_ANSWERS;
	    iDisplayExtras = iTotalAnswerCount - MAX_LEAD_ANSWERS;
	}
	else {
	  iDisplayLimit = iTotalAnswerCount ; 
	}
	int iAnswerIndex = 0;
	while(iAnswerIndex < iAnswerCount && iAnswerIndex < iDisplayLimit ) {
		QwiserAnswer aAnswer = aaAnswers.get(iAnswerIndex);
		//Commented out image
		fout.print("<td align=\"center\" valign=\"middle\" width=\"" + 100/iDisplayLimit + "%\">" +
						"<a href=\""+SubmitData("Action=2&amp;AnswerID=" + aAnswer.Id())+"\"><img src=\"" + aAnswer.ImageUrl() + "\" border=\"0\"></a>" +
					"</td>");
		iAnswerIndex++;
	}
	fout.println("</tr>"+
				 "<tr>");
	for(int iAnswerNameIndex=0; iAnswerNameIndex<iAnswerIndex; iAnswerNameIndex++) {
		QwiserAnswer aAnswer = aaAnswers.get(iAnswerNameIndex);
		fout.print("<td align=\"center\" valign=\"top\" width=\"" + 100/iDisplayLimit + "%\">" +		
										"<div><a href=\""+SubmitData("Action=2&amp;AnswerID=" + aAnswer.Id())+"\">" + aAnswer.Text() + "</a><br>(" + aAnswer.ProductCount() + ")</div>" +
					"</td>");
	}		
	fout.println(		"</tr>" +
							"</table>" +
						"</td>" +
					"</tr>" +
					"<tr>" +
							"<td >" +
								"<table width=\"155\" align=\"right\">" +
									"<tr>" +
										"<td align=\"right\">");
	if(iAnswerIndex < iTotalAnswerCount) {
		fout.println(SubmitDataNoScript("<input type=\"hidden\" name=\"Action\" value=\"2\">")+"<select name=\"AnswerID\" id=\"ddlLQAnswerID\" onchange=\"window.location='"+SubmitData("Action=2&amp;AnswerID=") + "'+document.getElementById('ddlLQAnswerID').value\">" +
										 "<option value=\"\">" + iDisplayExtras + " " +TEXT_MORE  + "...</option>");

		for( int i=iAnswerIndex; i < iTotalAnswerCount; i++ ) {
			QwiserAnswer aAnswer;
			if (i<iAnswerCount)
				aAnswer = aaAnswers.get(i);
			else
				aAnswer = aaExtraAnswers.get(i-iAnswerCount);
			fout.println("<option value=\"" + aAnswer.Id() + "\">" + aAnswer.Text() + "</option>");
		}
		fout.println("</select>");
		fout.println("</td><td>&nbsp;<noscript><input type=\"submit\" name=\"submit\" value=\"Ok\" />&nbsp;</noscript>");
	}// end  extras		
	fout.println("</td></form>" +
									"</tr>" +
								"</table>" +
							"</td>" +
						"</tr>" +
					"</table>");

}// end RenderLeadQ
%>


<%!
public void RenderNonLeadQuestions(QwiserQuestionsEditable qNLQs, JspWriter fout, HttpServletRequest request) throws Exception {
	fout.println("<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\"><tr><td>");
	fout.println("<span class=\"celebros_title\">Refine By ...</span></td></tr><tr><td>&nbsp;</td></tr><tr><td>");
	
	int iQCount = 	qNLQs.Count(); 
	int iQsRemain = 0;
	int iQuestionIndex = 0;
	if( iQCount > MAX_NON_LEAD_QUESTIONS)
		iQsRemain = iQCount - MAX_NON_LEAD_QUESTIONS;
				
	while( iQuestionIndex < iQCount) {
		QwiserQuestion qQuestion = (QwiserQuestion) qNLQs.GetQuestion(iQuestionIndex);
		
			if(iQuestionIndex%2==0) {
				fout.print(		"");
			}
				
			fout.print("<a class=\"deptsubnav\" href=\""+SubmitData("Action=4&amp;QuestionID=" + qQuestion.Id())+"\"><b>" + qQuestion.SideText() + "</b></a><br/>");
			
			QwiserAnswers aaAnswers = qQuestion.Answers();
			QwiserAnswers aaExtraAnswers = qQuestion.ExtraAnswers();
						
			int iAnswerCount = aaAnswers.Count();
			int iExtraAnswerCount = aaExtraAnswers.Count();
			int iTotalAnswerCount = iAnswerCount + iExtraAnswerCount;
			//int iAnswerIndex = 0;
			
			//Debug - counting items
			//fout.println("iAnswerCount: " + iAnswerCount + "<br /><br />");
			//fout.println("iExtraAnswerCount: " + iExtraAnswerCount + "<br /><br />");

			for( int i=0; i < iAnswerCount ; i++) {
				QwiserAnswer aAnswer = (QwiserAnswer) aaAnswers.get(i);
				
				if (!aAnswer.Text().equals("Not on Sale")) {
					fout.println("&nbsp;&nbsp;&nbsp;<a class=\"deptsubnav\" href=\"" + SubmitData("Action=2&amp;AnswerID=" + 
					aAnswer.Id()) + "\">" + aAnswer.Text() + "</a>" + 
					//"<span class=\"celebros_count\"> (" + aAnswer.ProductCount() + ") </span>" + 
					"</a><br>");
					//iAnswerIndex++;
				}
				
			}	
			for( int i=0; i < iExtraAnswerCount ; i++) {
				QwiserAnswer aaAnswer = (QwiserAnswer) aaExtraAnswers.get(i);
				
				if (!aaAnswer.Text().equals("Not on Sale")) {				
					fout.println("&nbsp;&nbsp;&nbsp;<a class=\"deptsubnav\" href=\"" + SubmitData("Action=2&amp;AnswerID=" + 
					aaAnswer.Id()) + "\">" + aaAnswer.Text() + "</a>" + 
					//"<span class=\"celebros_count\"> (" + aaAnswer.ProductCount() + ") </span>" + 
					"</a><br>");
					//iAnswerIndex++;
				}
				
			}	
			fout.print("<br>"); //fout.print("</tr><tr><td>&nbsp;</td></tr>");
			iQuestionIndex++;	
	}
						
	//if( iQsRemain > 0 ) {
	// fout.println("<tr>" +
	//							"<td align=right>" );
	//							
	//	fout.println("<select name=\"ddlQuestionID\" id=\"ddlQuestionID\" onchange=\"window.location='" + SubmitData("Action=4&amp;QuestionID=")+"' + document.getElementById('ddlQuestionID').value\">" +
	//									 "<option value=\"\">" + iQsRemain + " " + TEXT_MORE + "...</option>");
	//									 
	//	while(iQuestionIndex < iQCount) {
	//			QwiserQuestion qQuestion = (QwiserQuestion) qNLQs.GetQuestion(iQuestionIndex);
	//			fout.println("<option value=\"" + qQuestion.Id() + "\">" + qQuestion.SideText() + "</option>");
	//			iQuestionIndex++;
	//	}
	//	fout.println("</select>" + "</td>" + "</tr>");
	//}		
	fout.println("</td></tr></table>");
}
%>

<%!
public void RenderNonLeadQuestionsWithDropDowns(QwiserQuestionsEditable qNLQs, JspWriter fout, HttpServletRequest request) throws Exception {
	//fout.println("<div  class=\"anchorBox\"><table border=\"1\" width=\"210\" align=\"left\" cellpadding=\"0\" cellspacing=\"0\"  >");
	fout.println("<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  ><tr><td>");
	int iQCount = 	qNLQs.Count(); 
	int iQsRemain = 0;
	int iQuestionIndex = 0;
	if( iQCount > MAX_NON_LEAD_QUESTIONS)
		iQsRemain = iQCount - MAX_NON_LEAD_QUESTIONS;
				
	while( iQuestionIndex < MAX_NON_LEAD_QUESTIONS && iQuestionIndex < iQCount) {
		QwiserQuestion qQuestion = (QwiserQuestion) qNLQs.GetQuestion(iQuestionIndex);
			if(iQuestionIndex%2==0)
				fout.print(		"");
			//fout.print(		"<td valign=top width=50% >" +
			//					"<table width=\"100%\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\">" + 
			//						"<tr>" +
			//							"<td>" +
			//								"<div><h4><a href=\""+SubmitData("Action=4&amp;QuestionID=" + qQuestion.Id())+"\">" + qQuestion.SideText() + "</a></h4></div>" +											
			//							"</td>" +
			//						"</tr>");
			fout.print("<a class=\"deptsubnav\" href=\""+SubmitData("Action=4&amp;QuestionID=" + qQuestion.Id())+"\"><b>" + qQuestion.SideText() + "</b></a><br/>");
			
			QwiserAnswers aaAnswers = qQuestion.Answers();
			QwiserAnswers aaExtraAnswers = qQuestion.ExtraAnswers();
						
			int iAnswerCount = aaAnswers.Count();
			int iExtraAnswerCount = aaExtraAnswers.Count();
			int iTotalAnswerCount = iAnswerCount + iExtraAnswerCount;
			int iAnswerIndex = 0;
			
			//Non Lead Questions then Display answers up to limit and add the rest to a dropdown
			for( int i=0; i < MAX_NON_LEAD_ANSWERS && i < iAnswerCount ; i++) {
				QwiserAnswer aAnswer = (QwiserAnswer) aaAnswers.get(i);
				//fout.println("<tr><td><div  class=\"abox\"><a class=\"deptsubnav\" href=\""+SubmitData("Action=2&amp;AnswerID=" + aAnswer.Id())  + "\">" + aAnswer.Text() +"</a> ("+aAnswer.ProductCount()+ ")</div>");
				//	fout.print("</td></tr>");
				fout.println("&nbsp;&nbsp;&nbsp;<a class=\"deptsubnav\" href=\""+SubmitData("Action=2&amp;AnswerID=" + aAnswer.Id())  + "\">" + aAnswer.Text() +"</a> ("+aAnswer.ProductCount()+ ")<br>");
				iAnswerIndex++;
			}	
			if(iTotalAnswerCount > MAX_NON_LEAD_ANSWERS) {	
				//fout.print("<tr><td>"+
				//				"<table cellpadding=0 cellspacing=0 align=left>"+
				//					"<tr><td>");
				fout.println(SubmitDataNoScript("<input type=\"hidden\" name=\"Action\" value=\"2\">")+"<select name=\"AnswerID\" id=\"ddlAnswerID"+iQuestionIndex+"\" onchange=\"window.location='"+SubmitData("Action=2&amp;AnswerID=") + "'+document.getElementById('ddlAnswerID"+iQuestionIndex+"').value\">" +
										 "<option value=\"0\">" + (iTotalAnswerCount - MAX_NON_LEAD_ANSWERS) + " " +TEXT_MORE  + "...</option>");

				for( int j=iAnswerIndex; j < iTotalAnswerCount; j++ ) {
					QwiserAnswer aAnswer;
					if (j < iAnswerCount)
						aAnswer = aaAnswers.get(j);
					else
						aAnswer = aaExtraAnswers.get(j-iAnswerCount);
					fout.println("<option value=\"" + aAnswer.Id() + "\">" + aAnswer.Text() + "</option>");
				}
				fout.println("</select>&nbsp;<noscript><input type=\"submit\" name=\"submit\" value=\"Ok\" />&nbsp;</noscript></form>");
				//fout.println("</td><td>&nbsp;<noscript><input type=\"submit\" name=\"submit\" value=\"Ok\" />&nbsp;</noscript></form>"+
				//						"</td>"+
				//					"</tr>"+
				//				"</table>");
			}			
		// fout.print(		"</td></tr>"+
		// 				"</table>"+
		// 			"</td>");
		 //if(iQuestionIndex%2==1 || iQuestionIndex==Math.min(MAX_NON_LEAD_QUESTIONS,iQCount)-1)
			fout.print("<br>"); //fout.print("</tr><tr><td>&nbsp;</td></tr>");
	iQuestionIndex++;	
	}//End Question repeater while(iQuestionIndex < MAX_NON_LEAD_QUESTIONS && iQuestionIndex < iQCount)
						
	if( iQsRemain > 0 ){
		fout.println("<tr>" +
								"<td align=right>" );
								
		fout.println("<select name=\"ddlQuestionID\" id=\"ddlQuestionID\" onchange=\"window.location='"+SubmitData("Action=4&amp;QuestionID=")+"' + document.getElementById('ddlQuestionID').value\">" +
										 "<option value=\"\">" + iQsRemain + " " + TEXT_MORE + "...</option>");
										 
		while(iQuestionIndex < iQCount) {
				QwiserQuestion qQuestion = (QwiserQuestion) qNLQs.GetQuestion(iQuestionIndex);
				fout.println("<option value=\"" + qQuestion.Id() + "\">" + qQuestion.SideText() + "</option>");
				iQuestionIndex++;
		}
		
		fout.println("</select>" + 
									"</td>" + 
							"</tr>");
		
	}//end iQsRemain			
	
	//fout.println("</table></div>");
	fout.println("</td></tr></table>");
}// end RenderNonLeadQuestions
%>

<%!
public void RenderSortingSelector(QwiserSortingOptions so, JspWriter fout, HttpServletRequest request) throws Exception {
	int iSortMethod=0; //Default Sort
	int iEnumSortMethod = so.Method().getLevel();
	String sFieldName = so.FieldName();
	boolean bAscending = so.Ascending();
	
	switch(iEnumSortMethod) {
		case 1:
			iSortMethod=SORT_METHOD_BY_RELEVANCY;
			break;
		case 2:
			//if(sFieldName.equals(PRICE_FIELD_NAME) && bAscending)
			if(sFieldName.equals("MinPrice") && bAscending)
				iSortMethod = SORT_METHOD_BY_PRICE_ASC;
			//else if(sFieldName.equals(PRICE_FIELD_NAME) && !bAscending)
			else if(sFieldName.equals("MaxPrice") && !bAscending)
				iSortMethod = SORT_METHOD_BY_PRICE_DESC;
			else if(sFieldName.equals(NAME_FIELD_NAME) && !bAscending)
				iSortMethod = SORT_METHOD_BY_NAME_DESC;
			else if(sFieldName.equals(FIELD_NAME_NEW_SORT))
				iSortMethod = SORT_METHOD_BY_NEW_DESC;
			else if(sFieldName.equals(FIELD_NAME_SALE_SORT))
				iSortMethod = SORT_METHOD_BY_SALE_DESC;			
			else
				iSortMethod = SORT_METHOD_BY_NAME_ASC;
			break;
		default:
			if(so.Method().toString().equals("Price")) {
				if(bAscending) {
					iSortMethod = SORT_METHOD_BY_PRICE_ASC;
				}
				else {
					iSortMethod = SORT_METHOD_BY_PRICE_DESC;
				}
			} 
			else {
				iSortMethod = SORT_METHOD_BY_RELEVANCY;
			}
			break;
		}
			
	fout.println(SubmitDataNoScript("<input type=\"hidden\" name=\"Action\" value=\"6\">") +
		TEXT_SORT_BY + "&nbsp;<select name=\"SortMethod\" style=\"height: 18px;\" onChange=\"this.form.submit();\">");
	fout.println("<option value=\"" + SORT_METHOD_BY_RELEVANCY+"\""); if(iSortMethod==SORT_METHOD_BY_RELEVANCY) fout.println(" selected>"); else fout.println(">"); fout.println(TEXT_SORT_BY_POPULARITY + "</option>");
	fout.println("<option value=\"" + SORT_METHOD_BY_PRICE_ASC+"\""); if(iSortMethod==SORT_METHOD_BY_PRICE_ASC) fout.println(" selected>"); else fout.println(">"); fout.println(TEXT_SORT_BY_PRICE_ASC + "</option>");
	fout.println("<option value=\"" + SORT_METHOD_BY_PRICE_DESC+"\""); if(iSortMethod==SORT_METHOD_BY_PRICE_DESC) fout.println(" selected>"); else fout.println(">"); fout.println(TEXT_SORT_BY_PRICE_DES + "</option>");
	fout.println("<option value=\"" + SORT_METHOD_BY_NAME_ASC+"\""); if(iSortMethod==SORT_METHOD_BY_NAME_ASC) fout.println(" selected>"); else fout.println(">"); fout.println(TEXT_SORT_BY_NAME_ASC + "</option>");
	fout.println("<option value=\"" + SORT_METHOD_BY_NAME_DESC+"\""); if(iSortMethod==SORT_METHOD_BY_NAME_DESC) fout.println(" selected>"); else fout.println(">"); fout.println(TEXT_SORT_BY_NAME_DES + "</option>");
	fout.println("<option value=\"" + SORT_METHOD_BY_SALE_DESC+"\""); if(iSortMethod==SORT_METHOD_BY_SALE_DESC) fout.println(" selected>"); else fout.println(">"); fout.println("SALE" + "</option>");
	fout.println("<option value=\"" + SORT_METHOD_BY_NEW_DESC+"\""); if(iSortMethod==SORT_METHOD_BY_NEW_DESC) fout.println(" selected>"); else fout.println(">"); fout.println("What's New" + "</option>");
	fout.println("</select>&nbsp;<noscript><input type=\"submit\" name=\"submit\" value=\"Ok\" />&nbsp;</noscript></form>");
}
%>

<%!
public void RenderPageSizeSelector(QwiserSearchResults sr, JspWriter fout, HttpServletRequest request) throws Exception {
	int iCurrentPageSize = sr.SearchInformation().GetPageSize();
	fout.println(SubmitDataNoScript("<input type=\"hidden\" name=\"Action\" value=\"5\">") +
		"<select name=\"PageSize\" style=\"height: 18px;\" onChange=\"this.form.submit();\">");
	
	fout.println("<option value=\"20\"") ; if( iCurrentPageSize == 20 ){	fout.println(" Selected>");	}else{	fout.println(">");	} fout.print("20 items per page" + "</option>");
	fout.println("<option value=\"40\"") ; if( iCurrentPageSize == 40) {	fout.println(" Selected>");	}else{	fout.println(">");	} fout.print("40 items per page" + "</option>");
	fout.println("<option value=\"60\"") ; if( iCurrentPageSize == 60 ){	fout.println(" Selected>");	}else{	fout.println(">");	} fout.print("60 items per page" + "</option>");
	fout.println("<option value=\"120\"") ; if( iCurrentPageSize == 120) { fout.println(" Selected>"); }else{	fout.println(">");	} fout.print("120 items per page" + "</option>");

	//fout.println("<option value=\"12\"") ; if( iCurrentPageSize == 12 ){	fout.println(" Selected>");	}else{	fout.println(">");	} fout.print("12 items per page" + "</option>");
	//fout.println("<option value=\"20\"") ; if( iCurrentPageSize == 20) {	fout.println(" Selected>");	}else{	fout.println(">");	} fout.print("20 items per page" + "</option>");
	//fout.println("<option value=\"30\"") ; if( iCurrentPageSize == 30) { fout.println(" Selected>"); }else{	fout.println(">");	} fout.print("30 items per page" + "</option>");
	fout.println("</select><noscript><input type=\"submit\" name=\"submit\" value=\"Ok\" />&nbsp;</noscript></form>");
}
%>

<%!
public void RenderNavigationBar(QwiserSearchResults sr, QwiserSearchInformation si, JspWriter fout, HttpServletRequest request) throws Exception {
	int iFirstProdInPage = si.GetCurrentPage() * si.GetPageSize() + 1;
	int iLastProdInPage = sr.Products().Count() + iFirstProdInPage -1;
	String sProductNumberDisplay = "";
	//if( iLastProdInPage  > Integer.parseInt(iTotalProductsCount) ){
		//iLastProdInPage = Integer.parseInt(iTotalProductsCount) ;
	//}
	/*
	if( Integer.parseInt(iCurrentPage)  != 0 ){
		fout.print("<a href=\"Javascript:SubmitData('Action=1&Page=" + (Integer.parseInt(iCurrentPage)  -1) + "')\">&lt; Previous</a> |");
	}
	*/
	fout.print("<h4>" + TEXT_SHOWING + " " + iFirstProdInPage  + " - "+ iLastProdInPage + " " + TEXT_OF + " " + sr.RelevantProductsCount() +"</h4>");
	/*
  	if( iLastProdInPage  != Integer.parseInt(iTotalProductsCount)  ){
		fout.print("| <a href=\"Javascript:SubmitData('Action=1&Page=" + (Integer.parseInt(iCurrentPage)  + 1) + "')\">Next &gt;</a>");
	}
	*/
	//sProductNumberDisplay = TEXT_SHOWING + " " + iFirstProdInPage  + " - "+ iLastProdInPage + " "+TEXT_OF+" " + sr.RelevantProductsCount();
	
	//return sProductNumberDisplay;
}
%>

<%!
public void RenderProducts(QwiserProducts pProducts, JspWriter fout, HttpServletRequest request) throws Exception {
	fout.println("<table border=\"0\" bordercolor=brown width=\"100%\" >" + "<tr valign=top>");	

	String	sSku = "",
					sName = "", 
					sBrand = "",
					sCategory = "",
					sUrl = "", 
					sImageUrl = "",
					sImageUrlConstructed = "",					
					sNew = "",
					sPrice = "",
					/*sSalePrice = "",
					sVIPPrice = "",
					sMapPricing = "",
					sRegRange = "",
					sSaleRange = "",
					sVIPRange = "",
					sNewSort = "",
					sSaleSort = "",	*/
					sDescription="";

	int iRows=0;
	
	int count = pProducts.Count();
	boolean productsLimited = false;
	if (request.getParameter("ProductCount") != null) {
		if (request.getParameter("AnswerID") == null) {
			try { count = Integer.parseInt(request.getParameter("ProductCount")); } 
			catch(NumberFormatException e) { } 
		}
	}

	for(int i=0; i < count; i++) {
	//for(int i=0; i < (pProducts.Count()); i++) {
		QwiserProduct pProd = pProducts.getProduct(i); 
		
		if(iRows!=0 && iRows%4==0)
			fout.println("</tr><tr valign=top>");
		
		fout.println("<td class=\"listcell\"  valign=\"top\" height=\"179\">");
		
		if(pProd.GetField(NAME_FIELD_NAME)!= null && !pProd.GetField(NAME_FIELD_NAME).equals(""))
			sName = pProd.GetField(NAME_FIELD_NAME);
		if(pProd.GetField(DESCRIPTION_FIELD_NAME)!= null && !pProd.GetField(DESCRIPTION_FIELD_NAME).equals("") && !pProd.GetField(DESCRIPTION_FIELD_NAME).equals("0"))
			sDescription = pProd.GetField(DESCRIPTION_FIELD_NAME);
		if(pProd.GetField(BRAND_FIELD_NAME)!= null && !pProd.GetField(BRAND_FIELD_NAME).equals(""))
			sBrand = pProd.GetField(BRAND_FIELD_NAME);
		if(pProd.GetField(CATEGORY_FIELD_NAME)!= null && !pProd.GetField(CATEGORY_FIELD_NAME).equals(""))
			sCategory = pProd.GetField(CATEGORY_FIELD_NAME);
		if(pProd.GetField(URL_FIELD_NAME)!= null && !pProd.GetField(URL_FIELD_NAME).equals(""))
			sUrl = pProd.GetField(URL_FIELD_NAME);
		if(pProd.GetField(IMAGE_URL_FIELD_NAME)!= null && !pProd.GetField(IMAGE_URL_FIELD_NAME).equals(""))
			sImageUrl = pProd.GetField(IMAGE_URL_FIELD_NAME);
		if(pProd.GetField(PRICE_FIELD_NAME)!= null)
			sPrice = pProd.GetField(PRICE_FIELD_NAME);
		sSku = URLEncoder.encode(pProd.Sku(), "UTF-8");
		
		/*Replaced Code Section*/					
		sDescription = sDescription.replaceAll("&amp;", "&").replaceAll("&lt;", "<").replaceAll("&gt;", ">");
		if(sDescription.length()>110)
			if(sDescription.indexOf(" ",109)>0) {
				sDescription = sDescription.substring(0,sDescription.indexOf(" ", 109)) + "...";
			}
			else {
				sDescription = sDescription.substring(0,109)+"...";
			}
		
			//style the new flag
			String newFlag = sNew;
			newFlag = "<span class=\"celebros_new\">" + sNew + "</span>";
		
			//fout.println("<a href=\"" + sUrl + "\" class=\"listcell\"><img src=\"" + sImageUrl + 
			//"\" border=\"0\" alt=\"" + sDescription + "\" title=\"" + sDescription + "\"></a><br/>" +
			//"<b> <a href=\"" + sUrl + "\" class=\"listcell\">" + sName + "</a></b><br/>" +
			//sBrand + "&nbsp;-&nbsp;" + sSku + "<br/>" +
			//"<span class=\"list_price\">" + "Reg: " + sPrice + "</span><br/>");

			//The full image path is in the Celebros returned XML. This means that the use of aliases
			//such as $thumb$ are constant. We want to try out different aliases to alter image size.
			//sImageUrlConstructed = "http://s7ondemand1.scene7.com/is/image/" + sSku + "?wid=134&hei=134";
			
			
			fout.println("<a href=\"" + sUrl + "\" class=\"listcell\"><img src=\"" + sImageUrl + 
			"\" border=\"0\" alt=\"" + sDescription + "\" title=\"" + sDescription + "\"></a>" + 
			"<br/>" +
		"<span class=\"celebros_new\">" + sNew + "</span>" +
		"<a class=\"gender_brand_link\" href=\"" + sUrl + "\">" + sName + "</a><br/>");
		
			fout.println("<span class=\"price\">" + CURRENCY_SYMBOL + sPrice + "</span>");
		fout.println("</td>");
		iRows++;
	}
	  fout.println("</td></tr></table>");
	  
}

public void RenderProductsOld(QwiserProducts pProducts, JspWriter fout, HttpServletRequest request) throws Exception {
	fout.println("<div class=\"AreaHead bold\" style=\"margin-left:40px; width:573px;\">"
					+"<div class=\"TopSellerInc_listingHeadCol1\">&nbsp;Beschreibung</div>"
					+"<div class=\"TopSellerInc_listingHeadCol3\">&nbsp;Preis</div>"
				+"</div>");
				
  	String	sSku = "",
			sName = "", 
			sBrand = "",
			sCategory = "",
			sUrl = "", 
			sImageUrl = "", 
			sNew = "",
			sPrice = "",
			sDescription="";
		
	String sEvenOdd = "odd"; //In our case it's backwards, because the index is 0 based					
	for(int i=0; i < (pProducts.Count()); i++) {
		QwiserProduct  pProd = pProducts.getProduct(i); 
				
		if (i%2==0)
			sEvenOdd="odd";
		else
			sEvenOdd="even";
		
		if(pProd.GetField(NAME_FIELD_NAME)!= null && !pProd.GetField(NAME_FIELD_NAME).equals(""))
			sName = pProd.GetField(NAME_FIELD_NAME);
		if(pProd.GetField(BRAND_FIELD_NAME)!= null && !pProd.GetField(BRAND_FIELD_NAME).equals(""))
			sBrand = pProd.GetField(BRAND_FIELD_NAME);
		if(pProd.GetField(CATEGORY_FIELD_NAME)!= null && !pProd.GetField(CATEGORY_FIELD_NAME).equals(""))
			sCategory = pProd.GetField(CATEGORY_FIELD_NAME);
		if(pProd.GetField(URL_FIELD_NAME)!= null && !pProd.GetField(URL_FIELD_NAME).equals(""))
			sUrl = pProd.GetField(URL_FIELD_NAME);
		if(pProd.GetField(IMAGE_URL_FIELD_NAME)!= null && !pProd.GetField(IMAGE_URL_FIELD_NAME).equals(""))
			sImageUrl = pProd.GetField(IMAGE_URL_FIELD_NAME);
		if(pProd.GetField(PRICE_FIELD_NAME)!= null && !pProd.GetField(PRICE_FIELD_NAME).equals("") && !pProd.GetField(PRICE_FIELD_NAME).equals("0"))
			sPrice = CURRENCY_SYMBOL+pProd.GetField(PRICE_FIELD_NAME);
		sSku = URLEncoder.encode(pProd.Sku(), "UTF-8");
					
fout.println(		"<div style=\"clear:both; width:625px; height:55px\">"+
"			<div class=\"TopSellerInc_listingRowCounter\">"+(i+1)+".</div>"+
"			<div class=\"TopSellerInc_listingRowCont\">"+
"				<div class=\"TopSellerInc_listingCol1\">"+
"					<div id=\"TopSellerInc_picToggle\" class=\"TopSellerInc_picture\">"+
"						<a href=\""+sUrl+"\"><img src=\""+sImageUrl+"\" width=\"50\" height=\"50\" title=\""+sName+"\" alt=\""+sName+"\"></a>"+
"					</div>"+
"					<div class=\"TopSellerInc_description\">"+
"						<div class=\"TopSellerInc_name\"><a href=\""+sUrl+"\">"+sName+"</a></div>"+
"						<div class=\"bold\" style=\"margin-bottom:5px;font-weight:bold\">"+sBrand +"</div>"+
"						<div class=\"bold\" style=\"margin-bottom:5px\">"+sCategory+"</div>"+
"					</div>"+
"				</div>"+
"				<div class=\"TopSellerInc_listingCol3\">"+
"					<div style=\"padding-top:5px\">"+
"						<div class=\"TopSellerInc_cart\"><a href=\""+sUrl+"\" class=\"cartLink\"></a></div>"+
"						<span class=\"price\">"+sPrice+"</span>"+
"					</div>"+
"				</div>"+
"			</div>"+
"		</div>");
	}//End product loop
}
%>

<%!
public String scrubSearchTerm(String searchTerm) {

	if (searchTerm != null) {
		searchTerm = searchTerm.replaceAll("ENTER Keyword or Item #", "");
		searchTerm = searchTerm.replaceAll("\\+", "Plus");
		searchTerm = searchTerm.replaceAll("'", "");
		searchTerm = searchTerm.replaceAll("&amp;", "");
		searchTerm = searchTerm.replaceAll("&lt;", "");
		searchTerm = searchTerm.replaceAll("&gt;", "");
		searchTerm = searchTerm.replaceAll("www.", "");
		searchTerm = searchTerm.replaceAll("@", "");
		searchTerm = searchTerm.replaceAll("http:", "");
		searchTerm = searchTerm.replaceAll("\\\\", "");
		searchTerm = searchTerm.replaceAll("https:", "");
		searchTerm = searchTerm.replaceAll("//", "");
		searchTerm = searchTerm.toLowerCase().replaceAll("<script>", "");
		searchTerm = searchTerm.toLowerCase().replaceAll("</script>", "");
		searchTerm = searchTerm.replaceAll("%", "");
		searchTerm = searchTerm.replaceAll(">", "");
		searchTerm = searchTerm.replaceAll("<", "");
		searchTerm = searchTerm.replaceAll("\\?", "");
		searchTerm = searchTerm.replaceAll("\\)", "");
		searchTerm = searchTerm.replaceAll("\\(", "");
		searchTerm = searchTerm.replaceAll(";", "");
		searchTerm = searchTerm.replaceAll("/", "");
		
		
		
		
	}
	
	
	
	return searchTerm;
}
%>

<%!
public void RenderNavigationBar2(QwiserSearchResults sr, QwiserSearchInformation si, JspWriter fout, HttpServletRequest request) throws Exception {
	int iPageSize = si.GetPageSize();
	int iCurrentPage = si.GetCurrentPage()+1;
	int iTotalPages = si.GetNumberOfPages();
	int iTotalProductsCount = sr.RelevantProductsCount();

	int iLeftPage = Math.max(iCurrentPage - 2,1);
	int iRightPage = Math.min(Math.max(iCurrentPage + 2,5),iTotalPages);
	if(iRightPage == iTotalPages)
		iLeftPage = Math.max(iRightPage - 4,1);
		
	fout.println("<table border=\"0\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\"><tr>");
	//fout.println("<td></td>");
	//fout.println("<table border=\"0\" cellspacing=\"0\" cellpadding=\"0\"><tr><td>Go to page:&nbsp;</td><form action=\""+SEARCH_PAGE+"\" method=\"get\" onSubmit=\"var f=this.Page;if(Math.floor(f.value) + ' ' == 'NaN '){f.focus();f.select();return false;}var p=Math.floor(f.value);if (p<0 || p>"+iTotalPages+" || p=="+iCurrentPage+"){f.focus();f.select();return false;}\"><input type=\"hidden\" name=\"Action\" value=\"1\"><input type=\"hidden\" name=\"SearchHandle\" value=\""+sr.GetSearchHandle()+"\"><td><input type=\"text\" value=\""+iCurrentPage+"\" size=\"4\" maxlength=\"4\" style=\"width:40px;text-align:right;padding-right:2px;\" name=\"Page\" onFocus=\"this.select();\"></td><td>&nbsp;<input type=\"submit\" name=\"submit\" value=\"Ok\" /></td></form></tr></table>");
	fout.print("</td><td align=\"right\">&nbsp;");

	if(iCurrentPage>1)
		fout.print("<a title=\"Previous\" href=" + SubmitData("Action=1&Page=" + (iCurrentPage-1) + "&from=" + request.getParameter("from") + "&searchQuery=" + sQuery) + ">Previous</a>&nbsp;");
	
	for (int i=iLeftPage; i<=iRightPage; i++) {
		if(i>1 || iCurrentPage>1) {
			fout.print("|&nbsp;");
		}
		if(i==iCurrentPage) {
			if (iTotalPages > 1) {
				fout.print("<b>" + i + "</b>&nbsp;");
			}
		}
		else {
			fout.print("<a title=\"to page " + i + "\" href=\"" + SubmitData("Action=1&Page=" + i + "&from=" + request.getParameter("from") + "&searchQuery=" + sQuery) + "\">" + i + "</a>&nbsp;");
		}
	}
	
	if(iCurrentPage<iTotalPages)
		fout.print("|&nbsp;<a title=\"Next\" href=\""+SubmitData("Action=1&Page="+(iCurrentPage+1) + "&from=" + request.getParameter("from") + "&searchQuery=" + sQuery)+"\">Next</a>&nbsp;");
	
	//fout.println("&nbsp;&nbsp;Page&nbsp;" + iCurrentPage + " of " + iTotalPages);
	fout.println("</td></table></div></div></div>");

}
%>

<%!
public void RenderNavigationBarGoogleStyle(QwiserSearchResults sr, QwiserSearchInformation si, JspWriter fout, HttpServletRequest request)throws Exception {
	int iPageSize = si.GetPageSize();
	int iCurrentPage = si.GetCurrentPage();
	int iTotalPages = si.GetNumberOfPages();
	int iTotalProductsCount = sr.RelevantProductsCount();
	int iCurrentPageBaseOne = iCurrentPage + 1;
	String sCurrentPage = Integer.toString(iCurrentPageBaseOne);
	String PAGE_TEXT = "Page ";
	
	if (iCurrentPage >= 10) {
		int iPageLeft;
		if(iCurrentPage%10==0) {
			iPageLeft = iCurrentPage -1;
			fout.println("<a href=\"Javascript:SubmitData('Action=1&amp;Page=" + iPageLeft + "')\">&lt;Previous</a>");
		}
		else {
			iPageLeft = ((int)(iCurrentPage/10))*10-1;
			fout.println("<a href=\"Javascript:SubmitData('Action=1&amp;Page=" + iPageLeft + "')\">&lt;Previous</a>");
		}
	}
	fout.println(PAGE_TEXT);
	int iTotalPagesWithinLimit = Math.min(200, iTotalPages);							
	
	List al  = new ArrayList();
	List al2 = new ArrayList();

	int iNumPages =10;		
	int iBasePage =0;
	for(int i=0; i<iTotalPagesWithinLimit; i+=10) {							
		if(iCurrentPage>=i && iCurrentPage<i+10) {								
			iBasePage = i+1;
			if(i+10>iTotalPagesWithinLimit) {
				if(iTotalPagesWithinLimit - i >0)
					iNumPages = iTotalPagesWithinLimit - i;
				else
					iNumPages = iTotalPagesWithinLimit;
			}
			for(int j=0;j<iNumPages;j++) {
				if(i+j<iCurrentPage)
					al.add(new Integer(i+j));			
				if(i+j>iCurrentPage)
					al2.add(new Integer(i+j));
			}
		}
	}
	for(int i=0;i<al.size();++i)
		fout.println("<a href=\"Javascript:SubmitData('Action=1&amp;Page=" +al.get(i)+ "')\">"+(((Integer)al.get(i)).intValue()+1)+"</a>");	
	
	fout.println(sCurrentPage);
	
	for(int j=0;j<al2.size();++j)
		fout.println("<a href=\"Javascript:SubmitData('Action=1&amp;Page=" +al2.get(j)+ "')\">"+(((Integer)al2.get(j)).intValue()+1)+"</a>");

	int iPageRight;
	if (!(iNumPages<10 || iCurrentPageBaseOne >= iTotalPagesWithinLimit || iBasePage+iNumPages>iTotalPagesWithinLimit)) {
		iPageRight = (((int)(iCurrentPage/10))+1)*10;
		fout.println("<a href=\"Javascript:SubmitData('Action=1&amp;Page=" + iPageRight + "')\">Next&gt;</a>");
	}
}
%>

<%!
public String GetDynamicPropertyByName(QwiserSearchResults sr, String sDynamicPropertyName) {
	String sDPValue = "";
	QwiserConcepts qcTriggeredConcepts = sr.QueryConcepts();
	QwiserSearchPath spSearchPath = sr.SearchPath();
			
	// Go over all the information from the shopper's input, from
	// the latest to the first, meaning answered Answers first (from
	// latest answered Answer to the first) and then the Search Concepts.

	// Loop though the answered Answers backwards
	for (int i=spSearchPath.Count()-1; i>=0; i--) {
		// Get the current Answer.
		QwiserAnswer aAnswer = spSearchPath.Get(i).Answers().get(spSearchPath.Get(i).AnswerIndex());

		// Get the value of the Requested Dynamic Property for this object.
		sDPValue = aAnswer.DynamicProperties().Item(sDynamicPropertyName);

		// If there's a valid value, don't look any further
		if(sDPValue != null && !sDPValue.equals(""))
			return sDPValue;
	}

	for (int i=0; i<qcTriggeredConcepts.Count(); i++) {
		// Get the current Search Concept.
		QwiserConcept qc = qcTriggeredConcepts.get(i); 

		// Get the value of the requested Dynamic Property for this object.
		sDPValue = qc.DynamicProperties().Item(sDynamicPropertyName);

		// If there's a valid value, don't look any further
		if(sDPValue != null && !sDPValue.equals(""))
			return sDPValue;
	}
	return sDPValue;
}
%>
