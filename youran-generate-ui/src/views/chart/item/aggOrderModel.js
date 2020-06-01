import searchUtil from '../searchUtil'
import metricsModel from './metricsModel'
import dimensionModel from './dimensionModel'

function initFormBean () {
  return {
    joinIndex: null,
    sortType: 1,
    parentId: null,
    parentKey: null,
    parentItem: null
  }
}

function displayText (detailOrder) {
  let parentDisplayText
  if (detailOrder.parentItem.granularity) {
    parentDisplayText = dimensionModel.displayText(detailOrder.parentItem)
  } else {
    parentDisplayText = metricsModel.displayText(detailOrder.parentItem)
  }
  return parentDisplayText + (detailOrder.sortType === 1 ? '▲' : '▼')
}

function repairAggOrderForEdit (aggOrder, dimensionList, metricsList) {
  if (!aggOrder.parentItem) {
    let parentItem = searchUtil.findSourceItemById(dimensionList, aggOrder.parentId)
    if (!parentItem) {
      parentItem = searchUtil.findSourceItemById(metricsList, aggOrder.parentId)
    }
    aggOrder.parentItem = parentItem
  }
  aggOrder.joinIndex = aggOrder.parentItem.joinIndex
  aggOrder.parentKey = aggOrder.parentItem.key
}

function repairAggOrderForSubmit (aggOrder) {
  aggOrder.joinIndex = aggOrder.parentItem.joinIndex
  aggOrder.parentKey = aggOrder.parentItem.key
}

export default {
  initFormBean,
  displayText,
  repairAggOrderForEdit,
  repairAggOrderForSubmit
}
