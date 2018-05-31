
function setStockSize(all_stock_size,size) {
    $(function() {
        $( "#slider-range-max" ).slider({
            range: "max",
            min: 1,
            max: all_stock_size,
            value: size,
            slide: function( event, ui ) {
                $( "#amount" ).val( ui.value );
            }
        });
        $( "#amount" ).val( $( "#slider-range-max" ).slider( "value" ) );
    });
}
function setBestChart(myChart,data,xdata,titleText) {
    /*最优组合的图表 start*/
    /*在model.html中实例化 echart*/
// 指定图表的配置项和数据
    var myChartOption = {
        // 全局调色盘。
        title: {
            text: titleText
        },
        tooltip: { trigger: 'axis'},
        dataZoom: [
            {   // 这个dataZoom组件，默认控制x轴。
                type: 'slider', // 这个 dataZoom 组件是 slider 型 dataZoom 组件
                start: 1,      // 左边在 10% 的位置。
                end: 99         // 右边在 60% 的位置。
            },
            {   // 这个dataZoom组件，也控制x轴。
                type: 'inside', // 这个 dataZoom 组件是 inside 型 dataZoom 组件
                start: 10,      // 左边在 10% 的位置。
                end: 60         // 右边在 60% 的位置。
            }
        ],
        legend: {
            data:['预测值','实际值']
        },
        xAxis: {
            name: "时间",
            nameLocation: 'end',
            axisLabel : {
                interval: 3,
                rotate:35
            },
            data:xdata
        },
        yAxis: {
            name: "增长率",
            nameLocation: 'center'
        },
        toolbox: {
            show: true,
            feature: {
                dataZoom: {
                    yAxisIndex: 'none'
                },
                magicType: {type: ['line', 'bar']},
                restore: {},
                saveAsImage: {}
            }
        },
        series: [{
            name: '预测值',
            type: 'line',
            color: [ '#1f7be1', '#e12a1d'],
            data: data[0]
        },
            {
                name: '实际值',
                type: 'line',
                data: data[1]
            }
        ]
    };
// 使用刚指定的配置项和数据显示图表。
    myChart.setOption(myChartOption);
    /*最优组合的图表 end*/
}

function setPieChart(data) {
    var pieColor= [ '#1f7be1', '#e12a1d','#ffcb37','#5375e1','#e140c4','#7adde1','#1911e1','#a0e136','#e17325'];

    var pieChartOption = {
        title : {
            text: '各聚类占比统计',
            x:'left',
            textStyle:{color:'#ffffff',fontSize:15}
        },
        color:pieColor,
        tooltip : {
            trigger: 'item',
            formatter: "{b} : {c} ({d}%)"
        },
        series : [
            {
                name: '类别',
                type: 'pie',
                radius : '55%',
                center: ['50%', '50%'],
                data:data,
                itemStyle: {
                    emphasis: {
                        shadowBlur: 10,
                        shadowOffsetX: 0,
                        shadowColor: 'rgba(0, 0, 0, 0.5)'
                    }
                }
            }
        ]
    };
    var pieChart = echarts.init(document.getElementById('echart-piecchart'));
    pieChart.setOption(pieChartOption);
    /*最优组合的图表 piecahrt end*/

}

/*将获取的后台数据，格式为：预测值，实际值：["539,0.2,3.2,5.4","539,1.2,6.32,55"]
* 转化为两个数组的格式：[ [],[] ]*/
function getDataTransfer(data) {
    var data1 = data[0].split(",");
    var preData = [];
    var i =0;
    for ( i=1; i<data1.length; i++){
        preData[i-1]= parseFloat(data1[i]);
    }
    var data2 = data[1].split(",");
    var trueData = [];
    for (i=1; i<data2.length; i++){
        trueData[i-1]= parseFloat(data2[i]);
    }
    return  [preData,trueData];
}