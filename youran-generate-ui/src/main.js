import Vue from 'vue'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import App from './App'
import router from './router'
import store from './store'
import { CommonPlugin } from './components/common'
import './assets/icon/iconfont.css'
import './assets/main.scss'
import './assets/element-override.scss'
import 'vue-awesome/icons'
import helpPopover from './components/help-popover'
import browser from 'browser-detect'

const result = browser()
if (result.name !== 'chrome') {
  alert('建议使用chrome浏览器，其他浏览器可能存在兼容性问题')
}

Vue.config.productionTip = false
Vue.use(ElementUI)
Vue.use(CommonPlugin)

Vue.component('help-popover', helpPopover)
new Vue({
  store,
  router,
  render: h => h(App)
}).$mount('#app')
