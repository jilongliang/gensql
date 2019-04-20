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
		<li><a href="${basePath}tableIdentifiers/list">主键管理</a></li>
		<li ><a href="${basePath}table/list">表管理</a></li>
		<li><a href="${basePath}view/list">视图管理</a></li>
		<li><a href="${basePath}sequence/list">序列管理</a></li>
		<li class="active"><a href="${basePath}index/list">索引管理</a></li>
	</ul>
	
	   <form:form id="searchForm" modelAttribute="userIndColumns" action="${basePath}index/list" method="post" class="breadcrumb form-search form-inline">
		   <div style="margin-bottom: 20px;" class="form-search1">
		      <label>indexName ：</label><form:input path="index_name" htmlEscape="false" maxlength="50" class="input-medium form-control" placeholder="indexName"/>
			  <label>columnName ：</label><form:input path="column_name" htmlEscape="false" maxlength="50" class="input-medium form-control" placeholder="columnName"/>
			  <label>TableName ：</label><form:input path="table_name" htmlEscape="false" maxlength="50" class="input-medium form-control" placeholder="TableName"/>
			 &nbsp;<input id="btnSubmit" class="btn btn-info" type="submit" value="查询"/>
		   </div>
		<table id="contentTable" class="table table-striped table-bordered table-hover">
			<thead>
				<tr>
					<th style="width: 58px;">编号</th>
					<th>表名称</th>
					<th>加索引列名</th>
					<th>索引名称</th>
					<th>操作</th> 
				</tr>
			</thead>
			<tbody>
			<c:forEach items="${list}" var="obj" varStatus="row">
				<tr>
						<td>${row.index+1}</td>
						<td>${obj.table_name}</td>
						<td>${obj.column_name}</td>
						<td>${obj.index_name}</td>
						 <td>
							<!-- 使用ont-awesome或者fbootstrap图标库 -->
							<i class="fa fa-binoculars"></i>
							<a href="${basePath}index/createIndexSQLScript/${obj.table_name}/${obj.index_name}/${obj.column_name}" onclick="return createSQL('生成索引创建SQL脚本',this.href);">生成创建脚本</a>&nbsp;&nbsp;
							<i class="fa fa-binoculars"></i>
							<a href="${basePath}index/dropIndexSQLScript/${obj.table_name}/${obj.index_name}/" onclick="return createSQL('生成索引删除脚本',this.href);">生成删除脚本</a>&nbsp;&nbsp;
							<i class="glyphicon glyphicon-export"></i>
							<a href="${basePath}index/exportCreateSQL/${obj.table_name}/${obj.index_name}/${obj.column_name}" onclick="return exportSQL('确定要导出创建脚本？',this.href);">导出创建脚本</a>&nbsp;&nbsp;
							<i class="glyphicon glyphicon-export"></i>
							<a href="${basePath}index/exportDropSQL/${obj.table_name}/${obj.index_name}/" onclick="return exportSQL('确定要导出删除脚本？',this.href);">导出删除脚本</a>&nbsp;&nbsp;
						</td>
				</tr>
			</c:forEach>
			
			</tbody>
		</table>
		<%@ include file="/WEB-INF/views/include/pagination.jsp"%>
		</form:form> 
</body>
</html>