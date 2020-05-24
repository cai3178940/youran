export function abbreviate (str, max) {
  if (!str) {
    return ''
  }
  if (str.length > max) {
    return str.substring(0, max - 3) + '...'
  }
  return str
}

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
