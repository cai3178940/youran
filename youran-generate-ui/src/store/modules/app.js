import Vue from 'vue'

const state = {
  downloadUrl: null
}

const mutations = {
  /**
   * 设置文件下载路径
   * @param state
   * @param url
   */
  setDownloadUrl: (state, url) => {
    state.downloadUrl = url
  }
}

const actions = {
  /**
   * 执行文件下载操作
   * @param context
   * @param url
   */
  downloadFile (context, url) {
    context.commit('setDownloadUrl', null)
    Vue.nextTick(() => {
      context.commit('setDownloadUrl', url)
    })
  }
}

export default {
  namespaced: true,
  state,
  mutations,
  actions
}
