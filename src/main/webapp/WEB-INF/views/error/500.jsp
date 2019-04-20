<%@ page contentType="text/html;charset=UTF-8" isErrorPage="true" %>

<%response.setStatus(200);%>
 
<!DOCTYPE html>
<html>
<head>
	<title>500 - 系统内部错误</title>
</head>
<body>
	<div class="container-fluid" style="text-align: center;">
		<img alt="系统发生内部错误 " title="系统发生内部错误" src="${ctxStatic}/images/500.jpg">
		<p>错误信息：</p><p>
		 
		</p>
		<div><a href="${homePath}"  class="btn">返回主页</a></div>
	</div>
</body>
</html>