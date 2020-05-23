/**
 * 从明细列构建图表项
 */
export function initChartItemByDetailColumn (detailColumn) {
  const sourceItemId = detailColumn.sourceItemId
  let alias
  let titleAlias
  if (detailColumn.custom) {
    alias = 'custom' + sourceItemId
    titleAlias = '【自定义】'
  } else {
    alias = detailColumn.field.jfieldName
    titleAlias = detailColumn.field.fieldDesc
  }
  return {
    sourceItemId: sourceItemId,
    alias: alias,
    titleAlias: titleAlias,
    showFkTitle: false,
    valuePrefix: '',
    valueSuffix: '',
    percent: false,
    detailColumn: detailColumn
  }
}
