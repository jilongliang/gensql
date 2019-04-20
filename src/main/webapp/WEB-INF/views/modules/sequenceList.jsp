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
		<li class="active"><a href="${basePath}sequence/list">序列管理</a></li>
		<li ><a href="${basePath}index/list">索引管理</a></li>
	</ul>
	
	   <form:form id="searchForm" modelAttribute="sequence" action="${basePath}sequence/list" method="post" class="breadcrumb form-search form-inline">
		   <div style="margin-bottom: 20px;" class="form-search1">
			 <label>sequenceName ：</label><form:input path="sequence_name" htmlEscape="false" maxlength="50" class="input-medium form-control" placeholder="sequenceName"/>
			 &nbsp;<input id="btnSubmit" class="btn btn-info" type="submit" value="查询"/>
		   </div>
		<table id="contentTable" class="table table-striped table-bordered table-hover">
			<thead>
				<tr>
					<th style="width: 58px;">编号</th>
					<th>序列名称</th>
					<th>序列最小值</th>
					<th>序列最大值</th>
					<th>序列递增值</th>
				 	<th>操作</th> 
				</tr>
			</thead>
			<tbody>
			<c:forEach items="${list}" var="obj" varStatus="row">
				<tr>
						<td>${row.index+1}</td>
						<td>${obj.sequence_name}</td>
						<td>${obj.min_value}</td>
						<td>${obj.max_value}</td>
						<td>${obj.increment_by}</td>
						<td>
							<!-- 使用ont-awesome或者fbootstrap图标库 -->
							<i class="fa fa-binoculars"></i>
							<a href="${basePath}sequence/createSequenceSQLScript/${obj.sequence_name}" onclick="return createSQL('生成序列创建SQL脚本',this.href);">生成创建脚本</a>&nbsp;&nbsp;
							<i class="fa fa-binoculars"></i>
							<a href="${basePath}sequence/dropSequenceSQLScript/${obj.sequence_name}" onclick="return createSQL('生成序列删除脚本',this.href);">生成删除脚本</a>&nbsp;&nbsp;
							<i class="glyphicon glyphicon-export"></i>
							<a href="${basePath}sequence/exportCreateSQL/${obj.sequence_name}" onclick="return exportSQL('确定要导出创建脚本？',this.href);">导出创建脚本</a>&nbsp;&nbsp;
							<i class="glyphicon glyphicon-export"></i>
							<a href="${basePath}sequence/exportDropSQL/${obj.sequence_name}" onclick="return exportSQL('确定要导出删除脚本？',this.href);">导出删除脚本</a>&nbsp;&nbsp;
						</td>
				</tr>
			</c:forEach>
			
			</tbody>
		</table>
		<%@ include file="/WEB-INF/views/include/pagination.jsp"%>
		</form:form> 
</body>
</html>