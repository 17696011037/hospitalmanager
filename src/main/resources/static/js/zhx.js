$(function () {


    $("input[name='isSpecimen']").click(function () {
        var aa=$(this).val();
        if(aa=="0"){
            $("input[name='specimenType']").attr("readonly",true);
        }else{
            $("input[name='specimenType']").attr("readonly",false);
        }
    })

    //根据组合项名称获取姓名简拼
    $("input[name='combinationName']").on("keyup keydown change blur", function() {
        $("input[name='pinyinCode']").val($(this).toPinyin());
    });

    var combinationid = localStorage["comid"];

    if (combinationid != "null") {
        var sname = localStorage["secname"];
        var sid = localStorage["secid"];
        $("#sectionname").val(sname);
        $("input[name='sectionId']").val(sid);
        showcheckcheckbox(combinationid);
        getcombinationidByid(combinationid);
    }else{
        var sname = localStorage["secname"];
        var sid = localStorage["secid"];
        $("#sectionname").val(sname);
        $("input[name='sectionId']").val(sid);
        getCheckInfoBySectionId(sid);
    }
})



//添加修改判断
function addandupd() {
    var cmbid = $("#cmbid").val();
    $.ajax({
        url: "/hospitalOne/getCombinationAndCheckInfo",
        type: "post",
        dataType: "json",
        data: {"combinationId": cmbid},
        success: function (data) {
            if (data.length==0) {
                add();
            }else{
                updcom();
            }
        }

    })
}


//修改绑定
function getcombinationidByid(combinaid) {
    $.ajax({
        url: "/hospitalOne/getCombinationAndCheckInfo",
        type: "post",
        dataType: "json",
        data: {"combinationId": combinaid},
        success: function (data) {
            console.log(data)
            $.each(data, function (i, e) {
                if (e.isEnable == 1) {
                    $("input[name='isEnable']").eq(0).prop("checked",true);
                } else {
                    $("input[name='isEnable']").eq(1).prop("checked",true);
                }


                $("input[name='combinationName']").val(e.combinationName)
                $("input[name='combinationName']").attr("readonly",true)
                $("input[name='pinyinCode']").val(e.pinyinCode)
                $("input[name='pinyinCode']").attr("readonly",true)
                $("input[name='combinationId']").val(e.combinationId);
                $("#cmbid").val(e.combinationId);
                $("input[name='promptInformation']").val(e.promptInformation)
                $("input[name='promptInformation']").attr("readonly",true)
                $("input[name='resultToWay']").val(e.resultToWay)
                $("input[name='resultToWay']").attr("readonly",true)

                if (e.isSpecimen == 1) {
                    $("input[name='isSpecimen']").eq(0).prop("checked",true);
                } else {
                    $("input[name='isSpecimen']").eq(1).prop("checked", true);
                }
                $("input[name='isSpecimen']").attr("disabled",true);
                $("input[name='specimenType']").val(e.specimenType);
                $("input[name='specimenType']").attr("readonly",true)

                $("input[type='checkbox']").attr("disabled",true);


            })
        }, error: function () {
            alert("发生错误")
        }
    })
}

//根据科室id查询体检项信息
function getCheckInfoBySectionId(secid) {

    $.ajax({
        url: "/hospitalOne/getCheckInfoBySectionId",
        type: "post",
        dataType: "json",
        data: {"sectionId": secid},
        success: function (data) {
            console.log(data)
            var content = "";
            $.each(data, function (i, e) {
                content += "<tr><td><input type='checkbox' value='" + e.checkId + "'>" + e.checkName + "</td></tr>";
            })
            $("#content").html(content);
        }, error: function () {
            alert("发生错误")
        }
    })
}

function add() {
    var textControl = $("#addform input");
    var flag=true;

            $.each(textControl,function (i,e) {
                if(e.type=="text" && e.value=="") {
                    flag = false;
                    alert("请完善组合项信息！");
                    return false;
                }
            })
  if(flag){
      addcom();
  }



}
//添加组合项信息
function addcom() {
    var combination = $("#addform").serialize();
    var checkID = [];//定义一个空数组

    $("input[type='checkbox']:checked").each(function (i) {//把所有被选中的复选框的值存入数组
        checkID[i] = $(this).val();
    })

    var date = $.param({
        "comAncCheckList": checkID
    }) + "&" + combination;

    $.ajax({
        url: "/hospitalOne/addCombinationInfo",
        data: date,
        dataType: "json",
        type: "get",
        traditional: true,
        success: function (data) {
            if (data == 1){
                alert("添加成功");
                top.location="/hospitalOne/index.html";
                $("input[name='combinationName']").val("");
                $("input[name='promptInformation']").val("")
                $("input[name='resultToWay']").val("")
                $("input[name='specimenType']").val("");
            }
            else{
                alert("添加失败")
            }
        },
        error: function () {
            alert("发生错误");
        }
    })
}

//修改组合项信息
function updcom() {
    var combination = $("#addform").serialize();
    // var checkID = [];//定义一个空数组
    //
    // $("input[type='checkbox']:checked").each(function (i) {//把所有被选中的复选框的值存入数组
    //     checkID[i] = $(this).val();
    // })
    //
    // var date = $.param({
    //     "comAncCheckList": checkID
    // }) + "&" + combination;

    $.ajax({
        url: "/hospitalOne/updCombinationInfo",
        data: combination,
        dataType: "json",
        type: "get",
        traditional: true,
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


//查询组合项id查询下面的体检项信息
function showcheckcheckbox(combid) {

    $.ajax({
        url: "/hospitalOne/getCombinationAndCheckInfo",
        type: "post",
        dataType: "json",
        data: {"combinationId": combid},
        success: function (data) {
            var content = "";
            $.each(data, function (i, e) {
                $.each(e.combinationCheckList,function (o,r) {
                    content += "<tr><td><input type='checkbox' value='" + r.checkId + "'>" + r.checkName + "</td></tr>";
                })

            })
            $("#content").html(content);
        }, error: function () {
            alert("发生错误")
        }
    })
}