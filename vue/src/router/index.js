import { createRouter, createWebHistory } from 'vue-router'
import Main from '../views/Main.vue'
import HomePage from '../views/childViews/HomePage.vue'
import store from '../store'

const routes = [
  {
    // 根目录
    path: '/',
    redirect: '/user/Login'
  }, {
    /* 主路由 和其 子路由 */
    path: '/main',
    name: 'Main',
    component: Main,
    children: [{
      path: '',
      name: 'default',
      component: HomePage
    }, {
      path: 'homePage',
      name: 'HomePage',
      component: HomePage
    }, {
      path: 'userManage',
      name: 'UserManage',
      component: () => import('@/views/childViews/UserManage.vue')
    }, {
      path: 'goodManage',
      name: 'GoodManage',
      component: () => import('@/views/childViews/GoodManage.vue')
    }, {
      path: 'commentManage',
      name: 'CommentManage',
      component: () => import('@/views/childViews/CommentManage.vue')
    }, {
      path: 'homePage',
      name: 'HomePage',
      component: () => import('@/views/childViews/HomePage.vue')
    }, {
      path: 'orderManage',
      name: 'OrderManage',
      component: () => import('@/views/childViews/OrderManage.vue')
    }, {
      path: 'orderDeliveryManage',
      name: 'OrderDeliveryManage',
      component: () => import('@/views/childViews/OrderDeliveryManage.vue')
    }, {
      path: 'bannerManage',
      name: 'BannerManage',
      component: () => import('@/views/childViews/BannerManage.vue')
    }, {
      path: 'selectedManage',
      name: 'SelectedManage',
      component: () => import('@/views/childViews/SelectedManage.vue')
    }, {
      path: 'categoryManage',
      name: 'CategoryManage',
      component: () => import('@/views/childViews/CategoryManage.vue')
    }, {
      path: 'typeManage',
      name: 'TypeManage',
      component: () => import('@/views/childViews/TypeManage.vue')
    }]
  }, {
    // 登录页面
    path: '/user/Login',
    name: 'Login',
    component: () => import('@/views/Login.vue')
  }, {
    // 404页面跳转至登录页
    path: '/:pathMatch(.*)',
    component: () => import('@/views/NotFound.vue')
  }
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

/* 挂载全局路由守卫 */
/* to 去哪 from 从哪 next()放行 */
router.beforeEach((to, from, next) => {
  /* 若无token 则返回登录页 */
  const token = store.getters.token;
  /* 无token 且 请求非Login页 */
  if (token == null && to.name != 'Login') {
    next({name: 'Login'});
  } else {
    next();
  }
})

export default router
