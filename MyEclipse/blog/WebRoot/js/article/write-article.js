/* 校验文章是否填写完整 */
function checkArticleComplete() {

	//获取文章类型
	var articleType = $('.article-type');
	var typeValue = articleType.val();
	//获取文章标题
	var contentTitle = $('.content-title');
	var titleValue = contentTitle.val();
	//获取文章内容
	var articleContent = $('#editor');
	var contentValue = articleContent.val();
	//获取文章分类
	var contentClassify = $('.content-classify');
	var classifyValue = contentClassify.val();

	//先禁用发表文章按钮
	document.getElementById('btn-publish').disabled=true;
	
	//指定禁用时间
	var disTime = 1500;

	//判断是否已选择文章类型
	if (typeValue == '请选择') {
		setTimeout(function(){
			document.getElementById('btn-publish').disabled=false;	//1.5s后激活发表文章按钮
		}, disTime);
		$.growl.error({
      	title: "",
        message: "请选择文章类型!"
      });
	}
	//判断是否已填写文章题目
	else if (titleValue == '') {
		setTimeout(function(){
			document.getElementById('btn-publish').disabled=false;	//1.5s后激活发表文章按钮
		}, disTime);
		$.growl.error({
      	title: "",
        message: "请输入文章标题!"
      });
	}
	//判断是否已填写文章内容
	else if (contentValue == '') {
		setTimeout(function(){
			document.getElementById('btn-publish').disabled=false;	//1.5s后激活发表文章按钮
		}, disTime);
		$.growl.error({
      	title: "",
        message: "请输入文章内容!"
      });
	}
	//判断是否已选择文章分类
	else if (classifyValue == '选择分类') {
		setTimeout(function(){
			document.getElementById('btn-publish').disabled=false;	//1.5s后激活发表文章按钮
		}, disTime);
		$.growl.error({
      	title: "",
        message: "请选择文章分类!"
      });
	}
	else {
		document.getElementById('btn-publish').disabled=false;	//直接激活发表文章按钮
		$('#articleForm').submit();
	}
}
