/**
 * 对display-article.jsp页面的动态修改
 */
//将从数据库中获取到的文章主体内容, 解析后显示
function decodingArticleContent() {
	//获取包含文章内容的节点
	var articleContentNode = document.getElementById("article-content");
	//获取文章内容
	var articleContenValue = articleContentNode.innerHTML;
	//创建解析器
	var converter = new showdown.Converter();
	//解析文章内容
	var text = converter.makeHtml(articleContenValue);
	//覆盖原文章内容
	articleContentNode.innerHTML = text;
}

//页面加载完成后就执行上面的函数
window.onload = function() {
	decodingArticleContent();
}

//检查回复内容是否已填写
function checkRemarkComplete() {
	//获取文章内容
	var contentValue = $('.remark-content').val();
	//先禁用发表回复按钮
	document.getElementById('remark-publish').disabled = true;
	//指定禁用时间
	var disTime = 1500;
	if(contentValue == '') {
		setTimeout(function(){
			document.getElementById('remark-publish').disabled=false;	//1.5s后激活发表文章按钮
		}, disTime);
		$.growl.error({
      	title: "",
        message: "请输入评论内容!"
      });
	}
	else {
		document.getElementById('remark-publish').disabled=false;	//直接激活发表文章按钮
		$('#remark-form').submit();
	}
}







