import Vue from 'vue'
import Vuex from 'vuex'
import app from './modules/app'
import fieldTemplate from './modules/fieldTemplate'
import systemUserInfo from './modules/systemUserInfo'

Vue.use(Vuex)

export default new Vuex.Store({
  strict: true,
  modules: {
    app,
    fieldTemplate,
    systemUserInfo
  }
})
