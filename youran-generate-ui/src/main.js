import Vue from 'vue'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import App from './App'
import router from './router'
import CommonPlugin from '@/components/common.js'
import '@/components/common.css'
import 'vue-awesome/icons'
import Icon from 'vue-awesome/components/Icon'
import helpPopover from '@/components/help-popover'
/* eslint-disable no-new */
Vue.config.productionTip = false
Vue.use(ElementUI)
Vue.use(CommonPlugin)
// globally (in your main .js file)
Vue.component('icon', Icon)
Vue.component('help-popover', helpPopover)
new Vue({
  el: '#app',
  router,
  render: h => h(App)
})
