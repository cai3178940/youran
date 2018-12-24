import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    /**
     * 缓存的字段模板数量
     */
    cacheFieldTemplateCount: 0,
    /**
     * 缓存的字段模板
     */
    cacheFieldTemplate: []
  },
  getters: {
    /**
     * 判断字段是否已经被缓存
     * @param state
     * @returns {function(*): boolean}
     */
    fieldCached (state) {
      return (fieldId) => !!state.cacheFieldTemplate.find(t => t.fieldId === fieldId)
    }
  },
  mutations: {
    /**
     * 从缓存中移除字段模板
     * @param state
     * @param fieldId
     */
    removeFieldTemplate: (state, fieldId) => {
      const index = state.cacheFieldTemplate.findIndex(item => item.fieldId === fieldId)
      if (index > -1) {
        state.cacheFieldTemplate.splice(index, 1)
        state.cacheFieldTemplateCount--
      }
    },
    /**
     * 往缓存中添加字段模板
     * @param state
     * @param field
     */
    addFieldTemplate: (state, field) => {
      const index = state.cacheFieldTemplate.findIndex(item => item.fieldId === field.fieldId)
      if (index < 0) {
        state.cacheFieldTemplate.push(field)
        state.cacheFieldTemplateCount++
      }
    }
  }
})
