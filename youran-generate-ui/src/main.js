// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import App from './App'
import router from './router'
import CommonPlugin from '@/components/common.js'
import '@/components/common.css'
import 'vue-awesome/icons'
import Icon from 'vue-awesome/components/Icon'

/* eslint-disable no-new */
Vue.config.productionTip = false
Vue.use(ElementUI)
Vue.use(CommonPlugin)
// globally (in your main .js file)
Vue.component('icon', Icon)
new Vue({
  el: '#app',
  router,
  render: h => h(App)
})
