<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
 <%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE htm>
<html>
<head>
 <%@ include file="/WEB-INF/views/include/meta.jsp"%>
 <%@ include file="/WEB-INF/views/include/include.jsp"%>
<title></title>
<style>
	.breadcrumb{
		background-color: #fff;
	}
	.form-search{
		background-color: #fff;
	}
	.form-search1{
	    padding: 8px 15px;
		background-color: #f5f5f5;
	}
</style>
 

</head>

<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${basePath}tableIdentifiers/list">主键管理</a></li>
		<li><a href="${basePath}table/list">表管理</a></li>
		<li><a href="${basePath}view/list">视图管理</a></li>
		<li><a href="${basePath}sequence/list">序列管理</a></li>
		<li ><a href="${basePath}index/list">索引管理</a></li>
	</ul>
	
	   <form:form id="searchForm" modelAttribute="tableIdentifiers" action="${basePath}tableIdentifiers/list" method="post" class="breadcrumb form-search form-inline">
		   <div style="margin-bottom: 20px;" class="form-search1">
			 <label>TableName ：</label><form:input path="table_name" htmlEscape="false" maxlength="50" class="input-medium form-control" placeholder="TableName"/>
			 &nbsp;<input id="btnSubmit" class="btn btn-info" type="submit" value="查询"/>
		   </div>
		<table id="contentTable" class="table table-striped table-bordered table-hover">
			<thead>
				<tr>
					<th style="width: 58px;">编号</th>
					<th>表名</th>
					<th>ID长度</th>
					<th>唯一标识</th>
					<th>前缀</th>
					<th>更新时间</th>
<!-- 					 <th>操作</th>  -->
				</tr>
			</thead>
			<tbody>
			<c:forEach items="${list}" var="obj" varStatus="row">
				<tr>
						<td>${row.index+1}</td>
						<td>${obj.table_name}</td>
						<td>${obj.id_length}</td>
						<td>${obj.identifier}</td>
						<td>${obj.prefix}</td>
						<td>${obj.last_date}</td>
						<%-- <td>
							<i class="glyphicon glyphicon-trash"></i><a href="${basePath}tableIdentifiers/deleteById?id_length=${obj.id_length}" 
							onclick="return confirmeMsg('确认删除该记录吗',this.href);">删除</a>&nbsp;&nbsp;
							<i class="fa fa-edit"></i> <a href="${basePath}tableIdentifiers/form?id_length=${obj.id_length}">修改</a>&nbsp;&nbsp;
							<a href="${basePath}email/deleteById?id_length=${obj.id_length}" onclick="return confirmx('确认删除该记录吗？', this.href)">修改</a>
						</td> --%>
				</tr>
			</c:forEach>
			
			</tbody>
		</table>
		<%@ include file="/WEB-INF/views/include/pagination.jsp"%>
		</form:form> 
</body>
</html>