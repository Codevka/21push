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
export /**
 * @param {username, password, tel, email, name, province, city, area} params 用户名, 密码, 手机, 邮箱, 昵称, 省, 市, 地
 * @returns result true为成功
 */
const RegisterUser = (params) => {
  return axios.post('/register', params)
}

export /**
 * @param {userType, username, password} params 用户类型:0租户 1客服 2维修师傅, 用户名, 密码
 * @returns result: true为成功, 成功时还返回:{userType, username, password, tel, email, name, province, city, area}
 * @see RegisterUser
 */
const LoginUser = (params) => {
  return axios.post('/login', params)
}

export /**
 * @param {userType, username, password, tel, email, name, province, city, area} params
 * @returns result: true为成功
 * @see LoginUser
 */
const changeUserInfo = (params) => {
  return axios.post('/changeUserInfo', params)
}
//按用户名找
export /**
 * @param {username} params 用户名
 * @returns [{contractId, houseId, rentTime, contractTime,contractDuration, price, status}]
 *          订单编号,房源编号,租房时间,订单时间,订单持续时间,价格,状态 
 *          状态:'已缴费' '未缴费'
 */
const getUserContract = (params) => {
  return axios.post('/getUserContract',params)
}
export /**
 * @param {username} params 用户名
 * @returns [{repairId, houseId, status}]
 *          报修编号, 房源编号, 状态
 *          状态:'未处理' '未评价' '已评价'
 */
const getUserRepair = (params) => {
  return axios.post('/getUserRepair',params)
}
export /**
 * @param {username} params 用户名
 * @returns [{complaitId, houseId, status, adminId}]
 *          投诉编号, 房源编号, 状态, 处理人编号
 *          状态:'未处理' '未评价' '已评价'
 */
const getUserComplaint = (params) => {
  return axios.post('/getUserComplaint',params)
}
export /**
 * @param {keyword} params 关键词
 * @returns [{houseId, area, address, rentType, houseType}]
 *           房源编号, 地区, 具体地址, 租房类型, 房间类型
 *           租房类型: '短租' '长租'
 *           房间类型: '二人间' '三人间' '四人间'...
 */
const searchHouses = (params) => {
  return axios.post('/searchHouses',params)
}
export /**
 * @param {username} params 用户名
 * @returns [{repairWorkId, repairId, status, callback}]
 *           维修工单编号, 报修编号, 状态, 回复内容
 *           状态: '未处理' '已处理' 
 */
const getUserRepairWork = (params) => {
  return axios.post('/getUserRepairWork',params)
}
//按主键找
export /**
 * @param {contractId} params 订单编号
 * @returns {contractId, username, houseId, houseLocation, houseType, ownerTel, price, houseStatus, contractStatus, rentType} 
 *          订单编号, 用户名, 房源编号, 房源具体地址, 房间类型, 户主手机号, 价格, 房源状态 ,订单状态, 租房类型
 * @see searchHouses
 */
const getContract = (params) => {
  return axios.post('/getContract',params)
}
export /**
 * @param {houseId} params 房源编号
 * @returns {houseId, area, address, rentType, houseType, intro, tel, price, housestatus}
 *          房源编号,地区,具体地址,租房类型,房间类型,介绍,户主手机号,价格,房源状态
 */
const getHouse = (params) => {
  return axios.post('/getHouse',params)
}
export /**
 * @param {complaitId} params 投诉编号
 * @returns {complaitId,houseId,content,status,reply,evalution,score}
 *          投诉编号, 房源编号, 投诉内容, 状态, 回复, 评价内容, 评分
 *          状态: '未处理' '未评价' '已评价'
 *          评分: range[1,5]
 */
const getComplaint = (params) => {
  return axios.post('/getComplaint',params)
}
//订单相关
export /**
 * @param {contractId} params 订单编号
 * @returns result: true为成功
 */
const leaseBack = (params) => {
  return axios.post('/leaseBack',params)
}
export /**
 * @param {contractId, month} params 订单编号, 续租月数
 * @returns result: true为成功
 */
const leaseRenew = (params) => {
  return axios.post('/leaseRenew',params)
}
//租房相关
export /**
 * @param {houseId, username, rentType, rentDuration} params 房源编号, 用户名, 租房类型, 租房时间:短租日数 或 长租月数
 * @returns result: true为成功
 */
const rentHouse = (params) => {
  return axios.post('/rentHouse',params)
}
//报修相关
export /**
 * @param {repairId} params 报修编号
 * @returns {repairId, houseId, content, status, evalution, score} 
 *          报修编号, 房源编号, 报修内容, 处理状态, 评价内容, 评分
 */
const getRepair = (params) => {
  return axios.post('/getRepair',params)
}

export /**
 * @param {houseId, username, content, pic} params 房源编号 用户名 报修内容 图片url
 * @returns  result: true为成功
 */
const submitRepair = (params) => {
  return axios.post('/submitRepair',params)
}
export /**
 * @param {repairId, evalution, score} params 报修编号, 评价内容, 评分
 * @returns result: true为成功
 */
const submitRepairComment = (params) => {
  return axios.post('/submitRepairComment',params)
}
//投诉相关
export /**
 * @param {houseId, username, content, pic} params 房源编号 用户名 报修内容 图片url
 * @returns result: true为成功
 */
const submitComplaint = (params) => {
  return axios.post('/submitComplaint',params)
}
export /**
 * @param {complaintd, evalution, score} params 投诉编号, 评价内容, 评分
 * @returns result: true为成功
 */
const submitComplaintComment = (params) => {
  return axios.post('/submitComplaintComment',params)
}
//维修相关
export /**
 * @param {repairWorkId} params 维修工单编号
 * @returns {repairWorkId, repairId, houseId, location, content, pic, status, callback, evalution, tel}
 *          工单编号, 报修编号, 房源编号, 房源地址, 报修内容, 图片url, 状态, 维修反馈, 评价, 租户手机号
 *          状态: 未完成, 已完成
 */
const getRepairWork = (params) => {
  return axios.post('/getRepairWork',params)
}
export /**
 * @param {repairWorkId, callback} params 维修工单编号, 维修反馈
 * @returns result: true为成功
 */
const submitRepairWorkCallback = (params) => {
  return axios.post('/submitRepairWorkCallback',params)
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
