/**
 * 字符串转二维数组
 */
export function convert2d (text) {
  const rowArray = text.split('\n')
  const rows = []
  for (let i = 0; i < rowArray.length; i++) {
    const rowStr = rowArray[i].trim()
    if (!rowStr) {
      continue
    }
    let row = []
    let tdStrArray = rowStr.split('\t')
    for (let j = 0; j < tdStrArray.length; j++) {
      row.push(tdStrArray[j].trim())
    }
    rows.push(row)
  }
  return rows
}
