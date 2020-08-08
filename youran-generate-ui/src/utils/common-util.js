/**
 * 分组函数
 */
export const groupBy = (list, fn) => {
  const groups = {}
  list.forEach(function (o) {
    const group = JSON.stringify(fn(o))
    groups[group] = groups[group] || []
    groups[group].push(o)
  })
  return groups
}
