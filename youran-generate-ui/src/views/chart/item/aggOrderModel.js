import searchUtil from '../searchUtil'

export function initFormBean () {
  return {
    _displayText: '',
    joinIndex: null,
    sortType: 1,
    parentId: null,
    parentKey: null,
    detailColumn: null
  }
}

/**
 * 修复聚合排序数据
 */
export function repairAggOrder (aggOrder, detailColumnList) {
  if (!aggOrder.detailColumn) {
    const detailColumn = searchUtil.findSourceItemById(detailColumnList, aggOrder.parentId)
    aggOrder.detailColumn = detailColumn
  }
  aggOrder.joinIndex = aggOrder.detailColumn.joinIndex
  aggOrder.parentKey = aggOrder.detailColumn.key
  aggOrder._displayText = aggOrder.detailColumn._displayText + (aggOrder.sortType === 1 ? '▲' : '▼')
}
