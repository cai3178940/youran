import shortid from 'shortid'
import searchUtil from '../searchUtil'
import detailColumnModel from './detailColumnModel'

function initFormBean () {
  return {
    key: 'detailOrder_' + shortid.generate(),
    joinIndex: null,
    sortType: 1,
    parentId: null,
    parentKey: null,
    detailColumn: null
  }
}

function displayText (detailOrder) {
  return detailColumnModel.displayText(detailOrder.detailColumn) +
    (detailOrder.sortType === 1 ? '▲' : '▼')
}

/**
 * 修复排序列数据
 */
function repairDetailOrderForEdit (detailOrder, detailColumnList, customColumnList) {
  if (!detailOrder.detailColumn) {
    let parentItem = searchUtil.findSourceItemById(detailColumnList, detailOrder.parentId)
    if (!parentItem) {
      parentItem = searchUtil.findSourceItemById(customColumnList, detailOrder.parentId)
    }
    detailOrder.detailColumn = parentItem
  }
  detailOrder.joinIndex = detailOrder.detailColumn.joinIndex
  detailOrder.parentKey = detailOrder.detailColumn.key
}

/**
 * 修复排序列数据
 */
function repairDetailOrderForSubmit (detailOrder) {
  detailOrder.joinIndex = detailOrder.detailColumn.joinIndex
  detailOrder.parentKey = detailOrder.detailColumn.key
}

function getRules () {
  return {
    detailColumn: [
      { required: true, message: '请选择排序列', trigger: 'change' }
    ],
    sortType: [
      { required: true, message: '请选择排序方式', trigger: 'change' }
    ]
  }
}

export default {
  initFormBean,
  displayText,
  repairDetailOrderForEdit,
  repairDetailOrderForSubmit,
  getRules
}
