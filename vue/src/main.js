import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'

import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import zhCn from 'element-plus/lib/locale/lang/zh-cn'
import axios from 'axios'
import VueAxios from 'vue-axios'

import 'default-passive-events'

createApp(App).use(store)
              .use(router)
              .use(ElementPlus, {locale: zhCn,})
              .use(VueAxios, axios)
              .mount('#app')


