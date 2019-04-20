<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %> 
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fns" uri="http://java.sun.com/jsp/jstl/flong-fns-tag" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<c:set var="basePath" value="${pageContext.request.contextPath}/"/>
<c:set var="staticPath" value="${pageContext.request.contextPath}/static/"/>
<c:set var="pluginsPath" value="${pageContext.request.contextPath}/static/plugins/"/>

<c:set var="cssPath" value="${pageContext.request.contextPath}/static/resources/css/"/>
<c:set var="imagesPath" value="${pageContext.request.contextPath}/static/resources/images/"/>
<c:set var="jsPath" value="${pageContext.request.contextPath}/static/resources/js/"/>

<script type="text/javascript">
	var basePath="${basePath}";
</script>
