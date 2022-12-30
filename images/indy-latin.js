const colors = ['#5470C6', '#91CC75', '#EE6666'];
option = {
  color: colors,
  toolbox: {
    feature: {
      restore: { show: true },
      saveAsImage: { show: true }
    }
  },
  legend: {
    data: ['速度', '累积内存分配'],
  },
  xAxis: [
    {
      type: 'category',
      axisTick: {
        alignWithLabel: true
      },
      data: [
        '重复使用 StringBuilder',
        '使用 String::join',
        'Java 9 普通 a + b + c',
      ].map(s => ({
        value: s,
        textStyle: {
          fontSize: 14,
          color: 'black'
        }
      })),
    }
  ],
  yAxis: [
    {
      type: 'value',
      name: '速度',
      position: 'left',
      alignTicks: true,
      axisLine: {
        show: true,
        lineStyle: {
          color: colors[0]
        }
      },
      axisLabel: {
        formatter: '{value} Mops/s',
      }
    },
    {
      type: 'value',
      name: '累积内存分配',
      position: 'right',
      inverse: true,
      nameLocation: 'start',
      alignTicks: true,
      axisLine: {
        show: true,
        lineStyle: {
          color: colors[1]
        }
      },
      axisLabel: {
        formatter: '{value} kB/op'
      }
    },
  ],
  series: [
    {
      name: '速度',
      type: 'bar',
      data: [
        8324764.305 / 1000 / 1000,
        8680897.528 / 1000 / 1000,
        9220938.932 / 1000 / 1000,
      ],
    },
    {
      name: '累积内存分配',
      type: 'bar',
      yAxisIndex: 1,
      data: [
        1376.000 / 1024,
        1408.000 / 1024,
        1376.000 / 1024,
      ]
    },
  ]
};
