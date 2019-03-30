// $(function() {
//     $( "#slider-range-max" ).slider({
//         range: "max",
//         min: 1,
//         max: 1000,
//         value: 2,
//         slide: function( event, ui ) {
//             $( "#amount" ).val( ui.value );
//         }
//     });
//     $( "#amount" ).val( $( "#slider-range-max" ).slider( "value" ) );
// });

//var time = new Date();
//alert("timeget Month = "+time.getMonth())
/*动态文字时钟*/
var click = setInterval(function() {
    var time = new Date();
    var timeStr = time.getFullYear()+'年'+(time.getMonth()+1)+'月'+time.getDate()+'日 '+
        time.getHours()+':'+time.getMinutes()+':'+time.getSeconds();
    $("#click").html(timeStr);
}, 1000);

function setBestResultColor() {
    //获取table序号
    var tab=document.getElementById("tbzuhe");
//获取行数
    var rows=tab.rows;
//遍历行
    var myColor= [ '#1f7be1', '#e12a1d','#ffcb37','#5375e1','#e140c4','#7adde1','#1911e1','#a0e136','#e17325',
        '#7d7d7d', '#c576e1','#685c18','#a22900','#5685e1','#238b0c','#1911e1','#d1e18b','#e17325'];
    for(var i=0;i<rows.length;i++) {
//遍历表格列
        var style = "style =\"background-color:"+myColor[i]+";width: 20px;height: 15px;\"";
        rows[i].cells[0].innerHTML ="<span class=\"label label-mini\" "+style+">&nbsp;&nbsp;&nbsp;&nbsp;</span>";
    }
}
//随机选取一个非当前model的model
function randomModel(modelList,currentModel) {
    var index = getRandom(modelList.length,0);
    while (modelList[index]===currentModel){
       // alert("model="+modelList[index]+"current="+currentModel)
        index = getRandom(modelList.length,0);
    }
    return modelList[index];
}
//获取一个随机数，在max,min之间
//如果输入 10,40，则返回 false
function getRandom(max,min){
    var rand=false;
    rand=Math.floor(Math.random()*max);
    if(!(min==undefined) && (max>=min)){
        rand=Math.floor(Math.random()*(max-min)+min);
    }else if(max<min){
        return false;
    }
    return rand;
}