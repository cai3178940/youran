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

/**
 * 串行Promises
 */
export const serialPromises = (promises) => {
  const process = function (i) {
    const curr = promises[i]
    if (curr) {
      return curr.then(() => process(i + 1))
        .catch(() => process(i + 1))
    } else {
      return Promise.resolve()
    }
  }
  return process(0)
}
