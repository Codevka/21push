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
axios.defaults.timeout = 2000;
axios.defaults.headers.post['Content-Type'] = 'application/json;charset=UTF-8';
//会有注释的
export const RegisterUser = (params) => {
  return axios.post('/register', params)
}
export const LoginUser = (params) => {
  return axios.post('/login', params)
}
export const changeUserInfo = (params) => {
  return axios.post('/changeUserInfo', params)
}
//按用户名找
export const getUserContract = (params) => {
  return axios.post('/getUserContract',params)
}
export const getUserRepair = (params) => {
  return axios.post('/getUserRepair',params)
}
export const getUserComplaint = (params) => {
  return axios.post('/getUserComplaint',params)
}
export const searchHouses = (params) => {
  return axios.post('/searchHouses',params)
}
//按主键找
export const getContract = (params) => {
  return axios.post('/getContract',params)
}
export const getHouse = (params) => {
  return axios.post('/getHouse',params)
}
export const getComplaint = (params) => {
  return axios.post('/getComplaint',params)
}
//订单相关
export const leaseBack = (params) => {
  return axios.post('/leaseBack',params)
}
export const leaseRenew = (params) => {
  return axios.post('/leaseRenew',params)
}
//租房相关
export const rentHouse = (params) => {
  return axios.post('/rentHouse',params)
}
//报修相关
export const getRepair = (params) => {
  return axios.post('/getRepair',params)
}
export const submitRepair = (params) => {
  return axios.post('/makeRepair',params)
}
export const submitRepairComment = (params) => {
  return axios.post('/makeRepairComment',params)
}
//投诉相关
export const submitComplaint = (params) => {
  return axios.post('/makeComplaint',params)
}
export const submitComplaintComment = (params) => {
  return axios.post('/makeComplaintComment',params)
}
//导航守卫
router.beforeEach((to,from,next)=> {
  if(to.path == '/login'||to.path == '/register') {
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
