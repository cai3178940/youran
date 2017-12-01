import Vue from 'vue'
import Router from 'vue-router'
import index from '../page/index.vue'

import project from '../page/project/index.vue'
import projectList from '../page/project/list.vue'
import projectAdd from '../page/project/add.vue'
import projectEdit from '../page/project/edit.vue'
import projectShow from '../page/project/show.vue'

import entity from '../page/entity/index.vue'
import entityList from '../page/entity/list.vue'
import entityAdd from '../page/entity/add.vue'
import entityEdit from '../page/entity/edit.vue'
import entityShow from '../page/entity/show.vue'

import field from '../page/field/index.vue'
import fieldList from '../page/field/list.vue'
import fieldAdd from '../page/field/add.vue'
import fieldEdit from '../page/field/edit.vue'
import fieldShow from '../page/field/show.vue'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path:'',
      component:index
    },
    {
      path: '/project',
      component: project,
      children: [
        {
          path: '',
          component: projectList
        },
        {
          path: 'add',
          component: projectAdd
        },
        {
          path: 'edit/:projectId',
          component: projectEdit,
          props: true
        },
        {
          path: 'show/:projectId',
          component: projectShow,
          props: true
        }
      ]
    },
    {
      path: '/project/:projectId/entity',
      component: entity,
      props: true,
      children: [
        {
          path: '',
          component: entityList,
          props: true
        },
        {
          path: 'add',
          component: entityAdd,
          props: true
        },
        {
          path: 'edit/:entityId',
          component: entityEdit,
          props: true
        },
        {
          path: 'show/:entityId',
          component: entityShow,
          props: true
        }
      ]
    },
    {
      path: '/project/:projectId/entity/:entityId/field',
      component: field,
      props: true,
      children: [
        {
          path: '',
          component: fieldList,
          props: true
        },
        {
          path: 'add',
          component: fieldAdd,
          props: true
        },
        {
          path: 'edit/:fieldId',
          component: fieldEdit,
          props: true
        },
        {
          path: 'show/:fieldId',
          component: fieldShow,
          props: true
        }
      ]
    }
  ]
})
