import searchUtil from '../searchUtil'

function initFormBean () {
  return {
    _displayText: '',
    joinIndex: null,
    sortType: 1,
    parentId: null,
    parentKey: null,
    parentItem: null
  }
}

/**
 * 修复聚合排序数据
 */
function repairAggOrder (aggOrder, dimensionList, metricsList) {
  if (!aggOrder.parentItem) {
    let parentItem = searchUtil.findSourceItemById(dimensionList, aggOrder.parentId)
    if (!parentItem) {
      parentItem = searchUtil.findSourceItemById(metricsList, aggOrder.parentId)
    }
    aggOrder.parentItem = parentItem
  }
  aggOrder.joinIndex = aggOrder.parentItem.joinIndex
  aggOrder.parentKey = aggOrder.parentItem.key
  aggOrder._displayText = aggOrder.parentItem._displayText + (aggOrder.sortType === 1 ? '▲' : '▼')
}

export default {
  initFormBean,
  repairAggOrder
}
