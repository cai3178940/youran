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
 * 修复过滤数据
 */
export function repairHaving (having, detailColumnList) {
  if (!having.detailColumn) {
    const detailColumn = searchUtil.findSourceItemById(detailColumnList, having.parentId)
    having.detailColumn = detailColumn
  }
  having.joinIndex = having.detailColumn.joinIndex
  having.parentKey = having.detailColumn.key
  having._displayText = having.detailColumn._displayText + (having.sortType === 1 ? '▲' : '▼')
}
