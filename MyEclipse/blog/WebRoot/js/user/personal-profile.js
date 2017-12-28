
/* 编辑个人资料函数 */
function editPersonInfo() {
    //获取原先的个人资料
    var displayInfo = document.getElementById("display");
    var displayValues = displayInfo.getElementsByClassName("value");

    //隐藏display div
    $("#display").hide();


    //将原先的个人资料填入修改栏中
    var editInfo = document.getElementById("edit");
    var editValues = editInfo.getElementsByClassName("value");
    for(var i = 0; i < editValues.length; i++){
        editValues[i].value = displayValues[i+1].innerHTML;
    }

    //显示edit div
    $("#edit").show();
}

//保存修改的个人资料
function savePersonInfo() {
    //先隐藏edit div, 显示display div
    $("#edit").hide();
    $("#display").show();

    //提交表单到UserServlet
    $("#editForm").submit();
}

//取消编辑
function cancelEdit() {
    $("#edit").hide();
    $("#display").show();
}





