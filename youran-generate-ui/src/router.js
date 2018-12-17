import Vue from 'vue'
import Router from 'vue-router'
import home from './views/Home.vue'

import project from './views/project/index.vue'
import projectList from './views/project/list.vue'
import projectAdd from './views/project/add.vue'
import projectEdit from './views/project/edit.vue'

import entity from './views/entity/index.vue'
import entityList from './views/entity/list.vue'
import entityAdd from './views/entity/add.vue'
import entityEdit from './views/entity/edit.vue'
import mtmAdd from './views/entity/mtmAdd.vue'
import mtmEdit from './views/entity/mtmEdit.vue'

import field from './views/field/index.vue'
import fieldList from './views/field/list.vue'
import fieldAdd from './views/field/add.vue'
import fieldEdit from './views/field/edit.vue'
import fieldIndexAdd from './views/field/indexAdd.vue'
import fieldIndexEdit from './views/field/indexEdit.vue'

import constIndex from './views/const/index.vue'
import constList from './views/const/list.vue'
import constAdd from './views/const/add.vue'
import constEdit from './views/const/edit.vue'
import constDetailAdd from './views/const/detailAdd.vue'
import constDetailEdit from './views/const/detailEdit.vue'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '',
      component: home
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
        }
      ]
    }
  ],
  scrollBehavior (to, from, savedPosition) {
    return { x: 0, y: 0 }
  }
})
