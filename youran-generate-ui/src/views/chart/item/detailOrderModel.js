import { findSourceItemById } from '../util'

export function initFormBean () {
  return {
    _displayText: '',
    joinIndex: null,
    sortType: 1,
    parentId: null,
    detailColumn: null
  }
}

/**
 * 重置渲染展示文本
 */
export function resetDisplayText (form) {
  form._displayText = form.detailColumn._displayText + (form.sortType === 1 ? '▲' : '▼')
}

/**
 * 表单回显时修复排序列数据
 */
export function repairDetailOrder (detailOrder, sourceForm) {
  const detailColumn = findSourceItemById(sourceForm.detailColumnList, detailOrder.parentId)
  detailOrder.detailColumn = detailColumn
  resetDisplayText(detailOrder)
}
