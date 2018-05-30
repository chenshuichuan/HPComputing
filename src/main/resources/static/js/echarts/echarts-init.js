
/*最优组合的图表 start*/
// 基于准备好的dom，初始化echarts实例
var myChart = echarts.init(document.getElementById('echarts-div'),"dark");
// 指定图表的配置项和数据
// 指定图表的配置项和数据
var myChartOption = {
    // 全局调色盘。
    title: {
        text: 'ECharts'
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
        data:['计划','销量']
    },
    xAxis: {
        data: ["衬衫11111111111","羊毛衫2222222222222","雪纺衫222222222222",
            "裤子3333333333","高跟鞋3333333333","袜子333444444"]
    },
    yAxis: {},
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
        name: '销量',
        type: 'line',
        color: [ '#1f7be1', '#e12a1d'],
        data: [5, 20, 36, 10, 10, 20]
    },
        {
            name: '计划',
            type: 'line',
            data: [-5, 50, 26, 20, -10, 50]
        }
    ]
};
// 使用刚指定的配置项和数据显示图表。
myChart.setOption(myChartOption);
/*最优组合的图表 end*/


/*所有聚类的图表 start*/
var myChart2 = echarts.init(document.getElementById('echarts-div2'));
var myChartOption2 = {
    title: {
        text: 'ECharts'
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
            start: 1,      // 左边在 10% 的位置。
            end: 99         // 右边在 60% 的位置。
        }
    ],
    legend: {
        data:['计划','销量']
    },
    xAxis: {
        data: ["衬衫11111111111","羊毛衫2222222222222","雪纺衫222222222222",
            "裤子3333333333","高跟鞋3333333333","袜子333444444"]
    },
    yAxis: {},
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
        name: '销量',
        type: 'line',
        color: [ '#1f7be1', '#e12a1d'],
        data: [5, 20, 36, 10, 10, 20]
    },
        {
            name: '计划',
            type: 'line',
            data: [-5, 50, 26, 20, -10, 50]
        }
    ]
};
myChart2.setOption(myChartOption2);
/*所有聚类的图表 end*/
