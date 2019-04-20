/* layer提供了强健的皮肤扩展接口，这意味着如果你对layer默认的样式不太满意，你还有更多的选择。

	第一步：存放
	将您下载的皮肤包（假设文件夹名为：myskin）解压，放入layer的skin目录

	第二步：使用
	1. 全局配置
	layer.config({
	    extend: ['skin/myskin/style.css'], //加载新皮肤
	    skin: 'layer-ext-myskin' //一旦设定，所有弹层风格都采用此主题。
	});

	2. 局部使用
	layer.config({
	    extend: ['skin/myskin/style.css'] //加载新皮肤
	});

	//单个使用
	layer.open({
	    skin: 'layer-ext-myskin' //只对该层采用myskin皮肤
	});
 */
	//也就是说，无论你全局还是局部，都要通过layer.config的extend来加载样式。