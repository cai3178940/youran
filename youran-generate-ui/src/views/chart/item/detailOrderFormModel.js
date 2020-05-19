export function initFormBean () {
  return {
    _displayText: '',
    joinIndex: null,
    sortType: 1,
    detailColumn: null
  }
}

/**
 * 重置渲染展示文本
 */
export function resetDisplayText (form) {
  form._displayText = form.detailColumn._displayText + (form.sortType === 1 ? '▲' : '▼')
}
