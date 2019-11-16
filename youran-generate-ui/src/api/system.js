import { request, apiPath, checkResult } from '@/utils/request'

export default {

  /**
   * 查询系统用户信息
   */
  getSystemUserInfo () {
    return request.get(`/${apiPath}/system_user/info`)
      .then(response => checkResult(response))
  },
  /**
   * 修改用户设置
   */
  updateUserSetting (data) {
    return request.put(`/${apiPath}/system_user/setting`, data)
      .then(response => checkResult(response))
  }

}
