import Vue from 'vue'
import Router from 'vue-router'
import index from '../page/index.vue'

import project from '../page/project/index.vue'
import projectList from '../page/project/list.vue'
import projectAdd from '../page/project/add.vue'
import projectEdit from '../page/project/edit.vue'

import entity from '../page/entity/index.vue'
import entityList from '../page/entity/list.vue'
import entityAdd from '../page/entity/add.vue'
import entityEdit from '../page/entity/edit.vue'
import mtmAdd from '../page/entity/mtmAdd.vue'
import mtmEdit from '../page/entity/mtmEdit.vue'

import field from '../page/field/index.vue'
import fieldList from '../page/field/list.vue'
import fieldAdd from '../page/field/add.vue'
import fieldEdit from '../page/field/edit.vue'
import fieldIndexAdd from '../page/field/indexAdd.vue'
import fieldIndexEdit from '../page/field/indexEdit.vue'

import constIndex from '../page/const/index.vue'
import constList from '../page/const/list.vue'
import constAdd from '../page/const/add.vue'
import constEdit from '../page/const/edit.vue'
import constDetailAdd from '../page/const/detailAdd.vue'
import constDetailEdit from '../page/const/detailEdit.vue'

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
          path: 'mtmEdit/:mtmId',
          component: mtmEdit,
          props: true
        },
        {
          path: 'mtmAdd/:entityIds?',
          component: mtmAdd,
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
          path: 'indexAdd/:fieldIds?',
          component: fieldIndexAdd,
          props: true
        },
        {
          path: 'indexEdit/:indexId',
          component: fieldIndexEdit,
          props: true
        }
      ]
    },
    {
      path: '/project/:projectId/const',
      component: constIndex,
      props: true,
      children: [
        {
          path: 'add',
          component: constAdd,
          props: true
        },
        {
          path: ':constId?',
          component: constList,
          props: true
        },
        {
          path: 'edit/:constId',
          component: constEdit,
          props: true
        },
        {
          path: ':constId/constDetailAdd',
          component: constDetailAdd,
          props: true
        },
        {
          path: ':constId/constDetailEdit/:constDetailId',
          component: constDetailEdit,
          props: true
        },
      ]
    }
  ],
  scrollBehavior (to, from, savedPosition) {
    return { x: 0, y: 0 }
  }
})
