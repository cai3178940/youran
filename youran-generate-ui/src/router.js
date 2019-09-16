import Vue from 'vue'
import Router from 'vue-router'
import home from './views/Home.vue'

import project from './views/project/index.vue'
import projectList from './views/project/list.vue'
import projectForm from './views/project/form.vue'

import entity from './views/entity/index.vue'
import entityList from './views/entity/list.vue'
import entityForm from './views/entity/form.vue'
import mtmForm from './views/entity/mtmForm.vue'

import field from './views/field/index.vue'
import fieldList from './views/field/list.vue'
import fieldForm from './views/field/form.vue'
import fieldIndexForm from './views/field/indexForm.vue'

import constIndex from './views/const/index.vue'
import constList from './views/const/list.vue'
import constForm from './views/const/form.vue'

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
          component: projectForm
        },
        {
          path: 'edit/:projectId',
          component: projectForm,
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
          component: entityForm,
          props: true
        },
        {
          path: 'edit/:entityId',
          component: entityForm,
          props: true
        },
        {
          path: 'mtmEdit/:mtmId',
          component: mtmForm,
          props: true
        },
        {
          path: 'mtmAdd/:entityIds?',
          component: mtmForm,
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
          component: fieldForm,
          props: true
        },
        {
          path: 'edit/:fieldId',
          component: fieldForm,
          props: true
        },
        {
          path: 'indexAdd/:fieldIds?',
          component: fieldIndexForm,
          props: true
        },
        {
          path: 'indexEdit/:indexId',
          component: fieldIndexForm,
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
          component: constForm,
          props: true
        },
        {
          path: ':constId?',
          component: constList,
          props: true
        },
        {
          path: 'edit/:constId',
          component: constForm,
          props: true
        }
      ]
    }
  ],
  scrollBehavior (to, from, savedPosition) {
    return { x: 0, y: 0 }
  }
})
