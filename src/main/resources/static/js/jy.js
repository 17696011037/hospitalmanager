//清空
function  getclearproposedDescription() {
    $("input[name='sciencePopularization']").val("");
    $("input[name='adviceContent']").val("");
    $("input[name=isDelete]").get(0).checked = true;
    $("input[name=sciencePopularization]").val("");
    $("input[name=adviceContent]").attr("readonly",false);
    $("input[name=sciencePopularization]").attr("readonly",false);
    $("select[name='resultId']").attr("disabled",true);
}
//添加常见建议
function getinsertproposedDescription() {
    $("select[name='resultId']").attr("disabled",false);
    var content = $('form').serialize();
    var proposedDescriptionId=$("#proposedDescriptionIds").val();
    var sciencePopularization = $("[name=sciencePopularization]").val();
    var adviceContent=$("[name=adviceContent]").val();
    if (sciencePopularization==""){
        alert("请输入建议内容")
        return;
    }else if(adviceContent==""){
        alert("请输入科普说明")
        return;
    }
    if (proposedDescriptionId==0){
        $.post("/hospitalOne/insertProposedDescription.do",content,function (data) {
            if (data.status==1){
                alert("添加成功");
                location.href="/hospitalOne/Thebackend-page/jy.html"
            } else {
                alert("添加失败")
            }
        },"json")
    } else {
        $.post("/hospitalOne/insertProposedDescription.do?proposedDescriptionId="+proposedDescriptionId,content,function (data) {
            if (data.status==1){
                alert("修改成功");
                $("#proposedDescriptionIds").val(0);
                location.href="/hospitalOne/Thebackend-page/jy.html"
            } else {
                alert("修改失败")
            }
        },"json")
    }
}
