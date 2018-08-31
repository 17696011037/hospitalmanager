$(function () {
    var checkid = localStorage["cid"];

    if (checkid != "null") {
        var sname = localStorage["secname"];
        var sid = localStorage["secid"];
        $("#sectionname").val(sname);
        $("input[name='sectionId']").val(sid);
        showCheckById(checkid);
    } else {
        var sname = localStorage["secname"];
        var sid = localStorage["secid"];
        $("#sectionname").val(sname);
        $("input[name='sectionId']").val(sid);
    }

})

//添加修改判断
function addandupd() {
    var cid = $("#ckid").val();
    $.ajax({
        url: "http://localhost:8080/getCheckInfoById",
        type: "post",
        dataType: "json",
        data: {"checkId": cid},
        success: function (data) {
            if (data.length==0) {
            addcheck();
            }else{
                updcheck();
            }
        }

    })
}


//根据id查询体检项信息
function showCheckById(checkid) {
    $.ajax({
        url: "http://localhost:8080/getCheckInfoById",
        type: "post",
        dataType: "json",
        data: {"checkId": checkid},
        success: function (data) {
            console.log(data)
            $.each(data, function (i, e) {

                if (e.isEnable == 0) {
                    $("input[name='isEnable'],[value='0']").attr("checked", true);
                } else {
                    $("input[name='isEnable'],[value='1']").attr("checked", true);
                }
                $("input[name='checkName']").val(e.checkName)
                $("input[name='checkName']").attr("readonly",true);
                $("input[name='checkId']").val(e.checkId)
                $("#ckid").val(e.checkId);
                $("input[name='checkSpellCode']").val(e.checkSpellCode);
                $("input[name='checkSpellCode']").attr("readonly",true);
                if (e.termSex == "男") {
                    $("input[name='termSex'],[value='1']").attr("checked", true);
                } else {
                    $("input[name='termSex'],[value='0']").attr("checked", true);
                }
                $("input[name='termSex']").attr("readonly",true);

                $("input[name='checkAddress']").val(e.checkAddress);
                $("input[name='checkAddress']").attr("readonly",true);
                $("input[name='referenceCeil']").val(e.referenceCeil);
                $("input[name='referenceCeil']").attr("readonly",true);
                $("input[name='referenceFloor']").val(e.referenceFloor);
                $("input[name='referenceFloor']").attr("readonly",true);
                $("input[name='maxValue']").val(e.maxValue);
                $("input[name='maxValue']").attr("readonly",true);
                $("input[name='minValue']").val(e.minValue);
                $("input[name='minValue']").attr("readonly",true);
                $("input[name='promptHigh']").val(e.promptHigh);
                $("input[name='promptHigh']").attr("readonly",true);
                $("input[name='promptFlat']").val(e.promptFlat);
                $("input[name='promptFlat']").attr("readonly",true);
                $("textarea[name='remark']").val(e.remark);
                $("textarea[name='remark']").attr("readonly",true);
            })

        }, error: function () {
            alert("发生错误")
        }
    })
}


//添加体检项
function addcheck() {
    var a = $("#addform").serialize();
    alert(a);
    $.ajax({
        url: "http://localhost:8080/addCheckInfo",
        data: a,
        dataType: "json",
        type: "post",
        success: function (data) {
            if (data == 1)
                alert("添加成功");
            else
                alert("添加失败")
        },
        error: function () {
            alert("发生错误");
        }
    })
}


//修改体检项
function updcheck() {
    var a = $("#addform").serialize();
    $.ajax({
        url: "http://localhost:8080/updCheckInfo",
        data: a,
        dataType: "json",
        type: "post",
        success: function (data) {
            if (data == 1)
                alert("修改成功");
            else
                alert("修改失败")
        },
        error: function () {
            alert("发生错误");
        }
    })
}