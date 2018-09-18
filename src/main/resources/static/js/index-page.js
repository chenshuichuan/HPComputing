
var urlGetParam = "/getParamByModel";
var urlGetDate = "/getDateByModel";
//获取所有股票列表
var urlGetClusterZuheList = "/getClusterZuheListByModel";
//获取对应股票的评估数据
var urlGetStocksData="/getStockDataByModelAndStock";
//获取最优组合列表
var urlGetZuheList = "/getZuheListByModel";
//获取最优组合对应评估数据

//获取饼图数据
var urlGetPieChartList="/getPieChartListByModel";

$(document).ready(function(){
    //date picker start
    if (top.location != location) {
        top.location.href = document.location.href ;
    }
    $(function(){
        window.prettyPrint && prettyPrint();
        $('.default-date-picker').datepicker({
            format: 'yyyy-mm-dd'
        });
    });

    // //初始化当前model
    batchManage.currentModel = randomModel(modelList,"");
    batchManage.getData();

    //初始化最优组合
    setBestResultTable(batchManage.zuheList);
    setPieChart(batchManage.piechartdata);
    setBestResultChart(batchManage.zuheList);

    //alert("model="+batchManage.currentModel);
    //隐藏所有进度条
    hiddenAll();
    //样式设置
    $("#index-page").addClass("active");

    /***
     * //所有股票table
     */
    var $batchTable = $('#dynamic-table');
    var  batchTable = $batchTable.dataTable(
        $.extend(true, {}, CONSTANT.DATA_TABLES.DEFAULT_OPTION,
            {
                ordering: false,
                info: false,
                //scrollX: false,
                ajax: function (data, callback, settings) {//ajax配置为function,手动调用异步查询

                    //封装请求参数
                    var param = batchManage.getQueryCondition(data);
                    $.ajax({
                        type: "GET",
                        url: urlGetClusterZuheList,
                        cache: false,	//禁用缓存
                        data: param,	//传入已封装的参数
                        dataType: "json",
                        success: function (result) {
                            //异常判断与处理
                            if (result.errorCode) {
                                alert("查询失败。错误码：" + result.errorCode);
                                return;
                            }
                            //封装返回数据，这里仅演示了修改属性名
                            var returnData = {};
                            returnData.draw = result.draw;
                            returnData.recordsTotal = result.total;
                            returnData.recordsFiltered = result.total;//后台不实现过滤功能，每次查询均视作全部结果
                            returnData.data = result.pageData;
                            //关闭遮罩
                            callback(returnData);
                        },
                        error: function (XMLHttpRequest, textStatus, errorThrown) {
                            alert("查询失败");
                        }
                    });
                },
                columns: [
                    {
                        data: "classId",
                        width: "80px",
                        render: function (data, type, row, meta) {
                            return "第"+data+"聚类";
                        }
                    },
                    {
                        data: "id",
                        width: "80px"
                    },
                    {
                        className: "ellipsis",
                        render: CONSTANT.DATA_TABLES.RENDER.ELLIPSIS,
                        data: "name"
                    }
                ],
                "createdRow": function (row, data, index) {
                },
                "drawCallback": function (settings) {
                    $("tbody tr", $batchTable).eq(0).click();
                }
            })
    ).api();//此处需调用api()方法,否则返回的是JQuery对象而不是DataTables的API对象

    // 行点击事件
    $("tbody",  $batchTable).on("click", "tr", function (event) {
        if ( $(this).hasClass('selected') ) {
            $(this).removeClass('selected');
        }
        else {
            batchTable.$('tr.selected').removeClass('selected');
            $(this).addClass('selected');
        }
        //获取该行对应的数据
        var item = batchTable.row($(this).closest('tr')).data();
        batchManage.currentItem=item;
        //alert(item);
        batchManage.setStockDataOfAllChart(item);
    });


    var value = 0;
    var time = 100;
    //进度条复位函数
    function reset() {
        value = 0;
        $("#prog").removeClass("progress-bar-success").css("width", "0%").text("等待启动");
        //隐藏进度条
        var progress=document.getElementById("progress-bar");
        progress.style.display='none';
    }
    var bar_text=document.getElementById("bar-text");
    var textArr=["正在<span>初始化预测环境</span>","正在<span >加载数据</span>",
        "正在<span >训练模型</span>","正在<span >预测计算</span>","正在<span >完成</span>"]
    //百分数增加，0-30时为红色，30-60为黄色，60-90为蓝色，>90为绿色
    function increment() {
        value += 1;
        $("#prog").css("width", value + "%").text(value + "%");
        if (value >= 0 && value <= 20) {
            bar_text.innerHTML=textArr[0];
            $("#prog").addClass("progress-bar-danger");
        }
        else if (value >= 20 && value <= 30) {
            bar_text.innerHTML=textArr[1];
            $("#prog").removeClass("progress-bar-danger");
            $("#prog").addClass("progress-bar-warning");
        }
        else if (value >= 30 && value <= 75) {
            bar_text.innerHTML=textArr[2];
            $("#prog").removeClass("progress-bar-warning");
            $("#prog").addClass("progress-bar-info");
        }
        else if (value >= 75 && value < 100) {
            bar_text.innerHTML=textArr[3];
            $("#prog").removeClass("progress-bar-info");
            $("#prog").addClass("progress-bar-success");
        }
        else {
            //重载表格
            batchTable.draw();

            bar_text.innerHTML=textArr[4];
            setTimeout(reset, 3000);
            setTimeout(showSomeById("best-result-left"), 1000);
            setTimeout(setBestResultTable(batchManage.zuheList), 2000);
            setTimeout(setPieChart(batchManage.piechartdata), 3000);

            setTimeout(showSomeById("best-result-right"), 5000);
            setTimeout(setBestResultChart(batchManage.zuheList), 6000);
            setTimeout(showSomeById("all-result"), 8000);
            return;
        }
        st = setTimeout(increment, time);
    }
    //进度条开始
    //开始预测按钮点击事件
    $("#bt-beginPre").click(function () {

        // //
        batchManage.currentModel = randomModel(modelList,batchManage.currentModel);
        batchManage.getData();

        hiddenAll();
        var progress=document.getElementById("progress-bar");
        progress.style.display='block';
        increment();
    });
});
var batchManage = {
    currentItem: null,
    currentModel: null,
    fuzzySearch: true,
    //canshu: null,//参数
    xdata: null,
    zuheList:null,
    piePartList: null,
    piechartdata: null,
    getQueryCondition: function (data) {
        var param = {};
         param.model = batchManage.currentModel;
        //组装分页参数
        param.startIndex = data.start;
        param.pageSize = data.length;
        param.draw = data.draw;
        return param;
    },
    getData: function () {
        //获取图的下标轴数据
        batchManage.xdata = getAjaxData(urlGetDate,"model="+batchManage.currentModel);
        //alert(batchManage.xdata);
        batchManage.zuheList = getAjaxData(urlGetZuheList,"model="+batchManage.currentModel);
        //alert(batchManage.zuheList);

        batchManage.piePartList = getAjaxData(urlGetPieChartList,"model="+batchManage.currentModel);
        batchManage.piechartdata =[];
        for (var i=0;i<batchManage.piePartList.length;i++){
            var temp = {
                value:batchManage.piePartList[i].value,
                name:batchManage.piePartList[i].partName };

            batchManage.piechartdata[i] = temp;
        }
    },
    setStockDataOfAllChart:function (item){
        // 动作触发后执行的代码!!
        var stockData = getAjaxData(urlGetStocksData+"?modelName="+batchManage.currentModel+"&stockName="+item.name,"");
        var bestdata = getDataTransfer(stockData);
        setBestChart(myChart2,bestdata,batchManage.xdata, item.id+":"+item.name);

    }
};

var CONSTANT = {
    DATA_TABLES : {
        DEFAULT_OPTION : { //DataTables初始化选项
            language: {
                "sProcessing":   "处理中...",
                "sLengthMenu":   "每页 _MENU_ 项",
                "sZeroRecords":  "没有匹配结果",
                "sInfo":         "当前显示第 _START_ 至 _END_ 项，共 _TOTAL_ 项。",
                "sInfoEmpty":    "当前显示第 0 至 0 项，共 0 项",
                "sInfoFiltered": "(由 _MAX_ 项结果过滤)",
                "sInfoPostFix":  "",
                "sSearch":       "搜索:",
                "sUrl":          "",
                "sEmptyTable":     "表中数据为空",
                "sLoadingRecords": "载入中...",
                "sInfoThousands":  ",",
                "oPaginate": {
                    "sFirst":    "首页",
                    "sPrevious": "上页",
                    "sNext":     "下页",
                    "sLast":     "末页",
                    "sJump":     "跳转"
                },
                "oAria": {
                    "sSortAscending":  ": 以升序排列此列",
                    "sSortDescending": ": 以降序排列此列"
                }
            },
            autoWidth: false,	//禁用自动调整列宽
            stripeClasses: ["odd", "even"],//为奇偶行加上样式，兼容不支持CSS伪类的场合
            order: [],			//取消默认排序查询,否则复选框一列会出现小箭头
            processing: false,	//隐藏加载提示,自行处理
            serverSide: true,	//启用服务器端分页
            searching: false	//禁用原生搜索
        },
        COLUMN: {
            CHECKBOX: {	//复选框单元格
                className: "td-checkbox",
                orderable: false,
                width: "30px",
                data: null,
                render: function (data, type, row, meta) {
                    return '<input type="checkbox" class="iCheck">';
                }
            }
        },
        RENDER: {	//常用render可以抽取出来，如日期时间、头像等
            ELLIPSIS: function (data, type, row, meta) {
                data = data||"";
                return '<span title="' + data + '">' + data + '</span>';
            }
        }
    }
};

function hiddenAll() {
    //先隐藏进度条
    var progress=document.getElementById("progress-bar");
    progress.style.display='none';

//        //先隐藏进度条
//        var best_result=document.getElementById("best-result");
//        best_result.style.display='none';
    //先隐藏进度条
    var best_result_left=document.getElementById("best-result-left");
    best_result_left.style.display='none';
    //先隐藏进度条
    var best_result_right=document.getElementById("best-result-right");
    best_result_right.style.display='none';

    //先隐藏进度条
    var all_result=document.getElementById("all-result");
    all_result.style.display='none';

}
function showSomeById(id) {
    var some=document.getElementById(id);
    some.style.display='block';
}

function getAjaxData(urlStr,param) {

    var mydata =null;
    //设置同步
    $.ajax({
        type : "get",
        url : urlStr,
        data :param,
        async : false,
        success : function(data){
            mydata = data;
        }
    });
    //alert("mydata="+mydata);
    return mydata;
}

//最优组合
function setBestResultTable(zuheList) {
    $("#tbzuhe").empty();
    for (var i =0; i<zuheList.length; i++){
        var str = "<tr id="+zuheList[i].name
            +"> <td></td>"
            + "<td>第"+zuheList[i].classId +"聚类</td>"
            + "<td>"+zuheList[i].id+"</td>"
            + "<td>"+zuheList[i].name+"</td>"
            + "</tr>";
        $("#tbzuhe").append(str);
    }
    //设置table和聚类饼图的相应颜色对应
    setBestResultColor();
}

function setBestResultChart(zuheList) {
    var urlStr = urlGetStocksData+"?modelName="+batchManage.currentModel+"&stockName="+zuheList[0].name;
    $.get(urlStr,function(data,status){
        //alert("数据: " + data + "\n状态: " + status);
        var bestdata = getDataTransfer(data);
        setBestChart(myChart,bestdata,batchManage.xdata,zuheList[0].id+":"+zuheList[0].name);
    });
    for (var i =0; i<zuheList.length; i++){
        (function(){
            var zuhe = zuheList[i];
            $("#"+zuhe.name).click(function(){
                // 动作触发后执行的代码!!
                var urlStr2 = urlGetStocksData+"?modelName="+batchManage.currentModel+"&stockName="+zuhe.name;
                $.get(urlStr2,function(data,status){
                    //alert("数据: " + data + "\n状态: " + status);
                    var bestdata = getDataTransfer(data);
                    setBestChart(myChart,bestdata,batchManage.xdata,zuhe.id+":"+zuhe.name);

                });
            });
        })();
    }
}