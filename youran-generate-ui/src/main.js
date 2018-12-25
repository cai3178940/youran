import Vue from 'vue'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import App from './App'
import router from './router'
import store from './store'
import { CommonPlugin } from './components/common'
import './assets/main.scss'
import './assets/element-override.scss'
import 'vue-awesome/icons'
import Icon from 'vue-awesome/components/Icon'
import helpPopover from './components/help-popover'

Vue.config.productionTip = false
Vue.use(ElementUI)
Vue.use(CommonPlugin)

Vue.component('icon', Icon)
Vue.component('help-popover', helpPopover)
new Vue({
  store,
  router,
  render: h => h(App)
}).$mount('#app')
