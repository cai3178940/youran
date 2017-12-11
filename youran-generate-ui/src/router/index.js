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

import constIndex from '../page/const/index.vue'
import constList from '../page/const/list.vue'
import constAdd from '../page/const/add.vue'
import constEdit from '../page/const/edit.vue'
import constShow from '../page/const/show.vue'

import constDetail from '../page/constDetail/index.vue'
import constDetailList from '../page/constDetail/list.vue'
import constDetailAdd from '../page/constDetail/add.vue'
import constDetailEdit from '../page/constDetail/edit.vue'
import constDetailShow from '../page/constDetail/show.vue'

import mtm from '../page/mtm/index.vue'
import mtmList from '../page/mtm/list.vue'
import mtmAdd from '../page/mtm/add.vue'
import mtmEdit from '../page/mtm/edit.vue'
import mtmShow from '../page/mtm/show.vue'

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
    },
    {
      path: '/project/:projectId/const',
      component: constIndex,
      props: true,
      children: [
        {
          path: '',
          component: constList,
          props: true
        },
        {
          path: 'add',
          component: constAdd,
          props: true
        },
        {
          path: 'edit/:constId',
          component: constEdit,
          props: true
        },
        {
          path: 'show/:constId',
          component: constShow,
          props: true
        }
      ]
    },
    {
      path: '/project/:projectId/const/:constId/constDetail',
      component: constDetail,
      props: true,
      children: [
        {
          path: '',
          component: constDetailList,
          props: true
        },
        {
          path: 'add',
          component: constDetailAdd,
          props: true
        },
        {
          path: 'edit/:constDetailId',
          component: constDetailEdit,
          props: true
        },
        {
          path: 'show/:constDetailId',
          component: constDetailShow,
          props: true
        }
      ]
    },
    {
      path: '/project/:projectId/mtm',
      component: mtm,
      props: true,
      children: [
        {
          path: '',
          component: mtmList,
          props: true
        },
        {
          path: 'add',
          component: mtmAdd,
          props: true
        },
        {
          path: 'edit/:mtmId',
          component: mtmEdit,
          props: true
        },
        {
          path: 'show/:mtmId',
          component: mtmShow,
          props: true
        }
      ]
    }
  ],
  scrollBehavior (to, from, savedPosition) {
    return { x: 0, y: 0 }
  }
})
