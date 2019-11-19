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
    context.commit('setDownloadUrl', url)
    setTimeout(() => {
      context.commit('setDownloadUrl', null)
    }, 2000)
  }
}

export default {
  namespaced: true,
  state,
  mutations,
  actions
}
