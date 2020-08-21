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

/**
 * 0值返回true，其余按正常逻辑取布尔
 */
export const boolExceptZero = (value) => {
  if (value === 0) {
    return true
  }
  return !!value
}
