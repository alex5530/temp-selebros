<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<form action="${formTarget}" method="GET">
	<input type="text" name="${inputName}" autocomplete="off" id="${inputName}" placeholder="${inputPlaceholder}" class="${inputCssClass}"/>
	<button type="submit" id="${buttonId}" class="${buttonCssClass}"></button>
</form>
<c:if test="${celebrosEnabled}">
	<script type="text/javascript" src="//${scriptServer}/${celebrosAutocompleteJavascriptFilename}"></script>
	<link type="text/css" rel="stylesheet" href="//${clientUI}/${celebrosAutocompleteCssFilename}"/>
	<script type="text/javascript">
		CelebrosAutoComplete("${customerName}", "${inputName}", onSelect, "${frontendServer}", "${frontendServer}");
		function onSelect(aParameter){
			if ((aParameter["SelectedURL"] != "") && (aParameter["IsAutoComplete"]))
			{
				var sCMP = (encodeURIComponent(aParameter["SelectedURL"]).indexOf("?") == -1) ? "?" : "&";
				window.location = aParameter["SelectedURL"] + sCmp + "cmp=cel&trigger=ac";
			} else if (window['UITemplateConfigurationMaster'] != undefined) {
				var e = JQuery.Event("keypress");
				JQuery('#${buttonId}').click();
				return false;
			} else {
				var targetLocation = "${formTarget}?${inputName}="+encodeURIComponent(aParameter["SelectedQuery"]);
				if (aParameter["IsAutoComplete"].toString().toLowerCase() == "true"){
					targetLocation += "&trigger=ac";
				}
				window.location = targetLocation;
			}
		}
	</script>
</c:if>