<%@ page contentType="text/html;charset=UTF-8" %>
 <%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
 <%@ include file="/WEB-INF/views/include/meta.jsp"%>
 <%@ include file="/WEB-INF/views/include/include.jsp"%>
<head>
	<title>编辑</title>
	<style type="text/css">
		div.split-space{
			margin-top: 8px;
		}
	</style>
	 
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${basePath}email/list">Email列表</a></li>
	</ul>
	<br/>
	<form:form id="inputForm" modelAttribute="email" action="${basePath}/email/saveOrUpdate" method="post" class="form-horizontal">
		<tags:message content="${message}"/>
		<form:hidden path="id"/>
		
		<div class="control-group">
			<label class="control-label">email:</label>
			<div class="controls">
			 	<form:input path="email" class="form-control"/>
			</div>
		</div>
	 
		<div class="control-group">
			<label class="control-label">用户:</label>
			<div class="controls">
				<form:input path="create_by"  class="form-control"/>
			</div>
		</div>
		
		<div class="form-actions">
				<input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>
