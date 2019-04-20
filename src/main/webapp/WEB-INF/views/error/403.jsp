<%@ page contentType="text/html;charset=UTF-8" %>
<%response.setStatus(403);%>
<!DOCTYPE html>
<html>
<head>
	<title>403 - 用户权限不足</title>
</head>
<body>
	<div class="container-fluid" style="text-align: center;">
		<img alt="用户权限不足" title="用户权限不足" src="${ctxStatic}/images/403.jpg">
		<div><a href="${homePath}" class="btn">返回主页</a></div>
	</div>
</body>
</html>
