export default {
  /**
   * 图表类型
   */
  chartTypeOptions: [
    {
      value: 1,
      label: '明细表',
      name: 'detailList',
      aggregation: false
    },
    {
      value: 2,
      label: '聚合表',
      name: 'aggTable',
      aggregation: true
    },
    {
      value: 3,
      label: '柱线图',
      name: 'barLine',
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
  ]
}
