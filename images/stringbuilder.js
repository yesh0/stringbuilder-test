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
        '不重复使用 StringBuilder',
        '重复使用 StringBuilder',
        '手动 char[] 拼接',
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
        306762.701 / 1000 / 1000,
        787552.767 / 1000 / 1000,
        814517.943 / 1000 / 1000,
      ],
    },
    {
      name: '累积内存分配',
      type: 'bar',
      yAxisIndex: 1,
      data: [
        39240.010 / 1024,
        16424.005 / 1024,
        16424.004 / 1024,
      ]
    },
  ]
};
