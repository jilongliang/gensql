 
/***
 * 
 * http://layer.layui.com/api.html#yes
 * 
 * @param title
 * @param url
 * @returns {Boolean}
 */

function confirmeMsg(title,url){
	  layer.confirm(title,{title:'系统提示'},
			function(index){	// yes 回调
	 			location = url;//可以在这里操作$.ajax({})。。。。。
				layer.close(index);
			},
			function(index){	// cancel 回调
				layer.close(index);
		});
	 return false;
 }



/**
 * 弹出一个iframe层
 */
function createSQL(title,reqUrl){
	 layer.open({
		  type: 2,
		  title: title,
		  maxmin: true,
		  shade: [0.8, '#393D49'],
		  shadeClose: true, //点击遮罩关闭层
		  fixed: false, //不固定
		  area: ['920px', ($(window).height()/2)+180 +'px'],
		 // area: ['560px', '880px'],
		//  area: ['auto', 'auto'],
		  btn: [],
		  content: reqUrl
	  });
	 return false;
}


function exportSQL(cfStr,url){
	
	layer.confirm(cfStr, {
		  btn: ['确定','取消'], //按钮
		  shade: [0.8, '#393D49']
		}, function(index){
			 $.ajax({
				 type:"POST",
				 url:url,
				 dataType:"JSON",
				 success:function(respText){ 
					  if(respText!=null && respText.result=="1"){
						  //jbox弹窗可以显示在父窗体上面
						  layer.alert("已经成功导出SQL脚本，脚本路径存储在："+respText.path);
						  layer.close(index);//关闭窗体
					  }else{
						  alertMsg("导出失败！");
					  }
				 }
		   	 }); 
		}, function(index){
			layer.close(index);//关闭窗体
		});
	return false;
}
