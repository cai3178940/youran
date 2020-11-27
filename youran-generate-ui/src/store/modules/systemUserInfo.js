const state = {
  id: null,
  /**
   * 当前登录用户
   */
  username: '',
  /**
   * 启用自定义模板
   */
  templateEnabled: false,
  /**
   * 系统版本
   */
  sysVersion: '',
  /**
   * 系统中存在模板
   */
  templateExists: true,
  /**
   * 启用项目组共享功能
   */
  teamEnabled: true
}

const mutations = {
  /**
   * 设置系统用户信息
   * @param state
   * @param info
   */
  setSystemUserInfo: (state, info) => {
    Object.assign(state, info)
  }
}

export default {
  namespaced: true,
  state,
  mutations
}
