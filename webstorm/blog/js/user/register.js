// 生成验证码
function generateVerifyCode() {
    var code = "";
    var codeLength = 4;     //验证码长度
    //定义验证码的候选字符
    var selectChar = new Array(0, 1, 2, 3, 4, 5, 6, 7, 8, 9,
        'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K',
        'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z');
    for(var i = 0; i < codeLength; i++){
        var charIndex = Math.floor(Math.random() * selectChar.length);
        code += selectChar[charIndex];
    }
    document.getElementById("verifyImg").innerHTML = code;
}

//校正验证码是否一致
function checkVerifyCode() {
    //获取用户输入的验证码
    var inputVerifyCode = document.getElementById("verify-code").value.toUpperCase();
    //获取自动生成的验证码
    var verifyCodeImg = document.getElementById("verifyImg").innerText;
    if(inputVerifyCode != verifyCodeImg){
        $('#verify-hint').show();
    }
}


//隐藏提示信息
function hideHint() {
    $('.hint').hide();
}

//当页面加载完毕时, 就执行的函数
window.onload = function () {
    //生成一个验证码
    generateVerifyCode();

    //隐藏提示信息
    hideHint();
}


