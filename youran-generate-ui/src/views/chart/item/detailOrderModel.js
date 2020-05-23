import { findSourceItemById } from '../util'

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
 * 修复排序列数据
 */
export function repairDetailOrder (detailOrder, detailColumnList) {
  if (!detailOrder.detailColumn) {
    const detailColumn = findSourceItemById(detailColumnList, detailOrder.parentId)
    detailOrder.detailColumn = detailColumn
  }
  detailOrder.joinIndex = detailOrder.detailColumn.joinIndex
  detailOrder.parentKey = detailOrder.detailColumn.key
  detailOrder._displayText = detailOrder.detailColumn._displayText + (detailOrder.sortType === 1 ? '▲' : '▼')
}
