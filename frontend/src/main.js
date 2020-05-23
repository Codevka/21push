import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import axios from 'axios';
import VDistpicker from 'v-distpicker'
 
Vue.component('v-distpicker', VDistpicker);

export default {
  components: { VDistpicker }
}

Vue.config.productionTip = false;

Vue.prototype.$http = axios;

axios.defaults.baseURL = 'http://localhost:18888';

axios.defaults.headers.post['Content-Type'] = 'application/json;charset=UTF-8';
//与后端通信
export const RegisterUser = (params) => {
  return axios.post('/register', params)
}
export const LoginUser = (params) => {
  return axios.post('/login', params)
}
export const changeUserInfo = (params) => {
  return axios.post('/changeUserInfo', params)
}
export const getUserContract = (params) => {
  return axios.get('/getUserContract',params)
}
export const getContract = (params) => {
  return axios.get('/getContract',params)
}
export const getHouse = (params) => {
  return axios.get('/getHouse',params)
}
export const leaseBack = (params) => {
  return axios.get('/leaseBack',params)
}
export const leaseRenew = (params) => {
  return axios.get('/leaseRenew',params)
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
    alert('请先登录')
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
