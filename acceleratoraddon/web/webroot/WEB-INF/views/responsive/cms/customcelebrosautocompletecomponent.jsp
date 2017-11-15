<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<form action="${formTarget}" method="GET">
	<input type="text" name="${inputName}" autocomplete="off" id="${inputName}" placeholder="${inputPlaceholder}" class="${inputCssClass}"/>
	<button type="submit" id="${buttonId}" class="${buttonCssClass}"></button>
</form>