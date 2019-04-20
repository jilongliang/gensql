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
 
<script type="text/javascript">

function tipsStr(html,that){
	  layer.tips(html, that,{tips: [1, '#49afcd'],maxWidth: 1260,maxHeight:800}); //在元素的事件回调体中，follow直接赋予this即可
}
$(document).ready(function() {
	$(".v_text").mouseover(function(){
		  var that = this;
		  var _html=$(that).attr("v_text");
		  tipsStr(_html,that);
	});
	
});

</script>
</head>

<body>
	<ul class="nav nav-tabs">
		<li><a href="${basePath}tableIdentifiers/list">主键管理</a></li>
		<li ><a href="${basePath}table/list">表管理</a></li>
		<li class="active"><a href="${basePath}view/list">视图管理</a></li>
		<li><a href="${basePath}sequence/list">序列管理</a></li>
		<li ><a href="${basePath}index/list">索引管理</a></li>
	</ul>
	
	   <form:form id="searchForm" modelAttribute="views" action="${basePath}view/list" method="post" class="breadcrumb form-search form-inline">
		   <div style="margin-bottom: 20px;" class="form-search1">
			 <label>viewName ：</label><form:input path="view_name" htmlEscape="false" maxlength="50" class="input-medium form-control" placeholder="viewName"/>
			 &nbsp;<input id="btnSubmit" class="btn btn-info" type="submit" value="查询"/>
		   </div>
		<table id="contentTable" class="table table-striped table-bordered table-hover">
			<thead>
				<tr>
					<th style="width: 58px;">编号</th>
					<th>视图名称</th>
					<th>视图长度</th>
<!-- 					<th>视图脚本内容</th> -->
				 	<th>操作</th> 
				</tr>
			</thead>
			<tbody>
			<c:forEach items="${list}" var="obj" varStatus="row">
				<tr>
						<td>${row.index+1}</td>
						<td>${obj.view_name}</td>
						<td>${obj.text_length}</td>
<%-- 						<td class="v_text" v_text='${obj.text}'>${fns:abbr(obj.text,30)}</td> --%>
						<td>
							<!-- 使用ont-awesome或者fbootstrap图标库 -->
							<i class="fa fa-binoculars"></i>
							<a href="${basePath}view/dropViewSQLScript/${obj.view_name}" onclick="return createSQL('生成删除视图脚本',this.href);">生成删除脚本</a>&nbsp;&nbsp;
							<i class="fa fa-binoculars"></i>
							<a href="${basePath}view/createViewSQLScript/${obj.view_name}" onclick="return createSQL('生成创建视图脚本',this.href);">生成创建脚本</a>&nbsp;&nbsp;
							<i class="glyphicon glyphicon-export"></i>
							<a href="${basePath}view/exportCreateSQL/${obj.view_name}" onclick="return exportSQL('确定要导出脚本？',this.href);">导出创建脚本</a>&nbsp;&nbsp;
							<i class="glyphicon glyphicon-export"></i>
							<a href="${basePath}view/exportDropSQL/${obj.view_name}" onclick="return exportSQL('确定要导出脚本？',this.href);">导出删除脚本</a>&nbsp;&nbsp;
						</td>
				</tr>
			</c:forEach>
			
			</tbody>
		</table>
		<%@ include file="/WEB-INF/views/include/pagination.jsp"%>
		</form:form> 
</body>
</html>