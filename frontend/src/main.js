import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import axios from 'axios';

Vue.config.productionTip = false;

Vue.prototype.$http = axios;

axios.defaults.baseURL = 'http://.';

//后端接口未知
//该处为与后端通信
let base = ''
export const RegisterUser = (params) => {
  return axios.post(`${base}/register`, params)
}
export const LoginUser = (params) => {
  return axios.post(`${base}/login`, params)
}

//导航守卫
//无效
router.beforeEach((to,from,next)=> {
  if(to.path == '/login'||to.path == '/register'||to.path == '/home') {
    sessionStorage.removeItem('userInfo')
    next()
  }
  let user = JSON.parse(sessionStorage.getItem('userInfo'))||{userType:'-1',username:''}
  if((user.userType!='0' && /^(\/user0)/.test(to.path))||
     (user.userType!='1' && /^(\/user1)/.test(to.path))||
     (user.userType!='2' && /^(\/user2)/.test(to.path))) {
    alert('未登录')
    next({path:'/login'})
  }
  else {
    next()
  }
})

Vue.use(ElementUI);

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app');
