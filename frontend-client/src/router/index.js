import Vue from 'vue'
import VueRouter from 'vue-router'
import Login from '@/views/Login.vue'
import ProcessManagement from '@/views/ProcessManagement.vue'
import StandardsManagement from '@/views/StandardsManagement.vue'
import Consultancy from '@/views/Consultancy.vue'


Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'Login',
    component: Login
  },
  {
    path: '/process-management',
    name: 'ProcessManagement',
    component: ProcessManagement
  },
  {
    path: '/standards-management',
    name: 'StandardsManagement',
    component: StandardsManagement
  },
  {
    path: '/consultancy',
    name: 'Consultancy',
    component: Consultancy
  },
]

const router = new VueRouter({
  routes
})

router.beforeEach((to, from, next) => {

  if (to.name != 'Login' && !localStorage.token) {
    next({ name: 'Login' })
  } else {
    next()
  }
})

export default router
