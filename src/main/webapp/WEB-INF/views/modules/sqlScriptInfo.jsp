<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
 <%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE htm>
<html>
<head>
 <%@ include file="/WEB-INF/views/include/meta.jsp"%>
 <%@ include file="/WEB-INF/views/include/include.jsp"%>
 <%@ include file="/WEB-INF/views/include/syntaxhighlighter.jsp"%>
<title>表SQL脚本</title>
<style type="text/css">
 .bumpy-char{line-height:0.8em;position:relative;}
</style>
<script type="text/javascript">
	function copySQLText()
	{
		var SQLText=document.getElementById("SQLText");
		SQLText.select(); // 选择对象
		document.execCommand("Copy"); // 执行浏览器复制命令
		layer.alert('内容已经拷贝')
	}

	$(document).ready(function() {
		//$('.text-flash').bumpyText();//加动态字体
	});

</script>
 
</head>



<body style="background: white; font-family: Helvetica;width: 100%;">
<div style="margin-top: 12px;margin-bottom: 12px;">
	<h5 style="color: red;" class="text-flash">鼠标双击:Ctrl+C可以拷贝</h2>
</div>

<pre class="brush:sql;" name="code" >
		${resultSQLStr}
</pre>

</body>
</html>