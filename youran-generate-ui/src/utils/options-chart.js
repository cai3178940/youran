export default {
  /**
   * 图表类型
   */
  chartTypeOptions: [
    {
      value: 1,
      label: '明细表',
      name: 'detail_list',
      aggregation: false
    },
    {
      value: 2,
      label: '聚合表',
      name: 'agg_table',
      aggregation: true
    },
    {
      value: 3,
      label: '柱线图',
      name: 'bar_line',
      aggregation: true
    },
    {
      value: 4,
      label: '饼图',
      name: 'pie',
      aggregation: true
    }
  ],
  /**
   * 关联类型
   */
  joinTypeOptions: [
    {
      value: 1,
      label: 'inner join'
    },
    {
      value: 2,
      label: 'left join'
    },
    {
      value: 3,
      label: 'right join'
    }
  ],
  /**
   * 关联类型
   */
  filterOperatorOptions: [
    {
      value: 1,
      label: '等于'
    },
    {
      value: 10,
      label: '小于'
    },
    {
      value: 11,
      label: '大于等于'
    },
    {
      value: 12,
      label: '小于等于'
    },
    {
      value: 13,
      label: '介于之间'
    },
    {
      value: 2,
      label: '不等于'
    },
    {
      value: 21,
      label: '是当前'
    },
    {
      value: 22,
      label: '前段时间'
    },
    {
      value: 23,
      label: '后段时间'
    },
    {
      value: 3,
      label: '包含'
    },
    {
      value: 4,
      label: '不包含'
    },
    {
      value: 5,
      label: '为空'
    },
    {
      value: 6,
      label: '不为空'
    },
    {
      value: 7,
      label: 'like'
    },
    {
      value: 9,
      label: '大于'
    }
  ]
}
