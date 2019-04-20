<%@ page contentType="text/html;charset=UTF-8"%>
<%response.setStatus(200);%>
<!DOCTYPE html>
<html>
<head>
	<title>404 - 页面不存在</title>
</head>
<body>
	<div class="container-fluid" style="text-align: center;">
		<img alt="页面不存在 " title="页面不存在 " src="${ctxStatic}/images/404.jpg">
		<div><a href="${homePath}" class="btn">返回主页</a></div>
	</div>
</body>
</html>