/**
 * 时间粒度
 */
const timeGranularityOptions = [
  {
    value: 1,
    label: '分钟'
  },
  {
    value: 2,
    label: '小时'
  },
  {
    value: 3,
    label: '天'
  },
  {
    value: 4,
    label: '周'
  },
  {
    value: 5,
    label: '月'
  },
  {
    value: 6,
    label: '季度'
  },
  {
    value: 7,
    label: '年'
  }
]

export default {
  timeGranularityOptions: timeGranularityOptions,
  findTimeGranularityOption (value) {
    return timeGranularityOptions.find(option => option.value === value)
  }
}
