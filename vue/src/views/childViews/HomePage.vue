<template>
    <div class="Box">
      <div class="pie" style="width: 600px; height: 400px" ref="echartBox">
      </div>
      <div class="pie" style="width: 600px; height: 400px" ref="lineBox">
      </div>
    </div>
</template>

<script>
import {req, request} from '../../util/request'

// 引入 echarts 核心模块，核心模块提供了 echarts 使用必须要的接口。
import * as echarts from 'echarts/core';
// 引入柱状图图表，图表后缀都为 Chart
import { PieChart, BarChart } from 'echarts/charts';
// 引入提示框，标题，直角坐标系，数据集，内置数据转换器组件，组件后缀都为 Component
import {
  TitleComponent,
  TooltipComponent,
  TransformComponent,
  LegendComponent,
  ToolboxComponent,
  DatasetComponent,
  GridComponent,
} from 'echarts/components';
// 标签自动布局，全局过渡动画等特性
import { LabelLayout, UniversalTransition } from 'echarts/features';
// 引入 Canvas 渲染器，注意引入 CanvasRenderer 或者 SVGRenderer 是必须的一步
import { CanvasRenderer } from 'echarts/renderers';

// 注册必须的组件
echarts.use([
  TitleComponent,
  TooltipComponent,
  TransformComponent,
  PieChart,
  LabelLayout,
  UniversalTransition,
  CanvasRenderer,
  LegendComponent,
  ToolboxComponent,
  DatasetComponent,
  GridComponent,
  BarChart
]);

export default {
  name: 'Home',
  data() {
    return {
      lineOption: {
        backgroundColor:'#fff',
        title: {
          text: '销售量情况分析',
          textAlign: 'center',
          left: '49.5%',
          textStyle: {
            color: '#262626',
            fontSize: 18,
            fontWeight: '600',
          },
        },
        tooltip: {
          trigger: 'axis',
          axisPointer: {
            type: 'shadow'
          }
        },
        legend: {
          top: '5%',
          right: '3%'
        },
        grid: {
          containLabel: true
        },
        xAxis: [
          {
            type: 'category',
            data: ['实际收入', '确保收入', '目标收入', '挑战收入'],
            axisTick: {
                  show: false,
              },
              splitLine: {
                  show: false,
              },
              axisLine: {
                  show: false,
              },
            axisLabel: {
                  color: 'rgba(73,80,87,0.9)',
                  fontSize: 12,
                  fontWeight:600,
                  interval: 0,
                  padding:[8,0,0,0]
              },
          }
        ],
        yAxis: [
          {
            type: 'value',
            name: '（元）',
            nameTextStyle: {
              color: "rgba(73,80,87,0.9)",
              fontSize: 12,
              padding: [0, 0, 6, -60],
            },
            axisLabel: {
                  show: true,
                  textStyle: {
                                  color: 'rgba(73,80,87,0.9)',
                  },
                  padding:10
              },
          }
        ],
        series: [
          {
            name: '销售额',
            type: 'bar',
            emphasis: {
              focus: 'series'
            },
            barWidth: 20,
            label: {
              normal: {
                show: true,
                position: "top",
                formatter: function (data) {
                    return '{a0|' + data.value + '}';
                },
                rich: {
                  a0: {
                    color: '#5470c6',
                    fontSize: 12,
                    fontFamily: 'DIN',
                    fontWeight: 'bold'
                  },
                }
              },
            },
            data: [188.82, 180, 200, 260]
          }
        ]
      },
      pieOption: {
        title: {
          text: '商品分布饼状图',
          left: 'center'
        },
        tooltip: {
          trigger: 'item'
        },
        legend: {
          orient: 'vertical',
          left: 'left'
        },
        series: [
          {
            name: '数据占比',
            type: 'pie',
            radius: '60%',
            data: [
              { value: 1048, name: 'Search Engine' },
              { value: 735, name: 'Direct' },
              { value: 580, name: 'Email' },
              { value: 484, name: 'Union Ads' },
              { value: 300, name: 'Video Ads' }
            ],
            emphasis: {
              itemStyle: {
                shadowBlur: 10,
                shadowOffsetX: 0,
                shadowColor: 'rgba(0, 0, 0, 0.5)'
              }
            }
          }
        ]
      },
      myChart: null,
      lineChart: null,
    }
  },
  methods: {
    initInfo() {
      this.reqPieInfoAndSetView();
      this.reqLineInfoAndSetView();
    },
    initView() {
      this.myChart = echarts.init(this.$refs.echartBox);
      this.lineChart = echarts.init(this.$refs.lineBox);
    },
    reqPieInfoAndSetView() {
      req({
        method: 'GET',
        data: null,
        url: '/good/getCategory'
      })
      .then(res => {
        let data = [];
        for(const catrgory of res.data) {
          let obj = {};
          obj.name = catrgory.gcategory_name;
          let count = 0;
          for(const type of catrgory.goodTypeList) {
            count += type.goodList.length
          }
          obj.value = count;
          data.push(obj);
        }
        this.pieOption.series[0].data = data;
        this.myChart.setOption(this.pieOption);
      })
    },
    reqLineInfoAndSetView() {
      request({
        method: 'POST',
        url: '/order/calPriceWithWeek',
        data: null
      }).then(res => {
        console.log(res.data);
        let xAxis = [];
        let data = [];
        for(const order of res.data.data) {
          const mmonth = Number(order.orderIndex.split("-")[0]);
          const mday = Number(order.orderIndex.split("-")[1]);
          xAxis.push((mmonth + "/" + mday));
          data.push(order.priceTotal);
        }
        xAxis.reverse();
        data.reverse();
        this.lineOption.xAxis[0].data = xAxis;
        this.lineOption.series[0].data = data;
        console.log(this.lineOption);
        this.lineChart.setOption(this.lineOption);
      })
    }
  },
  mounted() {
    // console.log(this.$store.getters.userInfo);
    // 基于准备好的dom，初始化echarts实例
    this.initView();

    this.initInfo();
  }
}
</script>

<style scoped>
.Box {
  display: flex;
  height: 600px;
  background-color: white;
  display: flex;
  padding: 20px;
}

.pie {
  border: 5px solid #CCC;
  padding: 20px;
  margin: 10px;
}
</style>