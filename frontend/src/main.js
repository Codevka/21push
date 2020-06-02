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
/*原有内容改动和解释:
  6月2日:
    关键词搜索均为模糊搜索,如果关键词为空，应该返回所有结果,
    getComplaint现在还要返回投诉人编号 username,
    租房订单状态新增'未审核',
    房源状态为 '暂停出租' '未租满' '已租满',
    searchHouses参数关键词为房源编号或具体地址
*/

export /**
发起投诉和发起报修里的上传图片交给ZRQ了，我在对应位置留了注释,在user0的complaint和repair里
 * @returns token 七牛云token

            AK: K96MCAU7eCnSWz4XUbxIBe9Q9PUm_gBHfacmsAEf
            SK: g0eagx-yjztmAo0iVi-Nj8QrsCRGrKhdGKIjpVr9
            bucketName: 21push
            官方文档: https://developer.qiniu.com/kodo/manual/1208/upload-token
 */
  const getToken = () => {
    return axios.post('/getToken')
  }
export /**
 * @param {username, password, tel, email, name, province, city, area} params 账号, 密码, 手机, 邮箱, 昵称, 省, 市, 地
 * @returns {result,username} result:true为成功 username:账号
 */
  const RegisterUser = (params) => {
    return axios.post('/register', params)
  }

export /**
 * @param {userType, username, password} params 用户类型:0租户 1客服 2维修师傅, 账号或手机, 密码 
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
//按账号找
export /**
 * @param {username} params 账号
 * @returns [{contractId, houseId, rentTime, contractTime,contractDuration, price, status}]
 *          订单编号,房源编号,租房时间,订单时间,订单持续时间,价格,状态 
 *          状态:'已审核' '未缴费' '已缴费'
 */
  const getUserContract = (params) => {
    return axios.post('/getUserContract', params)
  }
export /**
 * @param {username} params 账号
 * @returns [{repairId, houseId, status}]
 *          报修编号, 房源编号, 状态
 *          状态:'未处理' '未评价' '已评价'
 */
  const getUserRepair = (params) => {
    return axios.post('/getUserRepair', params)
  }
export /**
 * @param {username} params 账号
 * @returns [{complaintId, houseId, status, adminId}]
 *          投诉编号, 房源编号, 状态, 处理人编号
 *          状态:'未处理' '未评价' '已评价'
 */
  const getUserComplaint = (params) => {
    return axios.post('/getUserComplaint', params)
  }
export /**
 * @param {username} params 账号
 * @returns [{repairWorkId, repairId, status, callback}]
 *           维修工单编号, 报修编号, 状态, 回复内容
 *           状态: '未处理' '已处理' 
 */
  const getUserRepairWork = (params) => {
    return axios.post('/getUserRepairWork', params)
  }
export /**
  * @param {username} params 账号
  * @returns isSuperAdmin: true为当前用户是超级管理员
  */
  const isSuperAdmin = (params) => {
    return axios.post('/isSuperAdmin', params)
  }
//按关键词找
export /**
 * 租户搜房源，只返回状态为'未租满'的房源
 * @param {keyword} params 关键词
 * @returns [{houseId, area, address, rentType, houseType}]
 *           房源编号, 地区, 具体地址, 租房类型, 房间类型
 *           租房类型: '短租' '长租'
 *           房间类型: '二人间' '三人间' '四人间'...
 */
  const searchHouses = (params) => {
    return axios.post('/searchHouses', params)
  }
export /**
 * 客服搜房源，三种状态的房源都返回
 * @param {keyword} params 关键词
 * @returns [{houseId, area, address, rentType, houseType, status}]
 *           房源编号, 地区, 具体地址, 租房类型, 房间类型, 状态
 *           租房类型: '短租' '长租'
 *           房间类型: '二人间' '三人间' '四人间'...
 *           状态: '暂停出租' '未租满' '已租满'
 */
  const searchAllHouses = (params) => {
    return axios.post('/searchAllHouses', params)
  }
export /**
  * @param {keyword} params 关键词为账号或手机号
  * @returns [{username, userType, name}]
  *           账号, 用户类型, 昵称
  *           用户类型: '租户' '客服' '维修人员'
  */
  const searchUsers = (params) => {
    return axios.post('/searchUsers', params)
  }
export /**
  * @param {keyword} params 关键词为投诉编号、房源编号或用户账号
  * @returns [{complaintId, houseId, username,status}]
  *           投诉编号, 房源编号, 用户账号, 处理状态
  *           处理状态: '未处理' '未评价' '已评价'
  */
  const searchComplaints = (params) => {
    return axios.post('/searchComplaints', params)
  }
//按主键找
export /**
 * @param {contractId} params 订单编号
 * @returns {contractId, username, houseId, houseLocation, houseType, ownerTel, price, housestatus, contractStatus, rentType} 
 *          订单编号, 账号, 房源编号, 房源具体地址, 房间类型, 户主手机号, 价格, 房源状态 ,订单状态, 租房类型
 * @see searchHouses
 */
  const getContract = (params) => {
    return axios.post('/getContract', params)
  }
export /**
 * @param {houseId} params 房源编号
 * @returns {houseId, area, address, rentType, houseType, intro, tel, price, housestatus, pic}
 *          房源编号,地区,具体地址,租房类型,房间类型,介绍,户主手机号,价格,房源状态, 图片
 *          图片是url数组
 */
  const getHouse = (params) => {
    return axios.post('/getHouse', params)
  }

export /**
 * @param {complaintId} params 投诉编号
 * @returns {complaintId,houseId,username,content,status,adminId,reply,evaluation,score}
 *          投诉编号, 房源编号, 投诉人账号, 投诉内容, 状态, 客服编号, 回复, 评价内容, 评分
 *          状态: '未处理' '未评价' '已评价'
 *          评分: range[1,5]
 */
  const getComplaint = (params) => {
    return axios.post('/getComplaint', params)
  }
//订单相关
export /**
 * @param {contractId} params 订单编号
 * @returns result: true为成功
 */
  const leaseBack = (params) => {
    return axios.post('/leaseBack', params)
  }
export /**
 * @param {contractId, month} params 订单编号, 续租月数
 * @returns result: true为成功
 */
  const leaseRenew = (params) => {
    return axios.post('/leaseRenew', params)
  }
//租房相关
export /**
 * @param {houseId, username, rentType, rentDuration} params 房源编号, 账号, 租房类型, 租房时间:短租日数 或 长租月数
 * @returns result: true为成功
 */
  const rentHouse = (params) => {
    return axios.post('/rentHouse', params)
  }
//报修相关
export /**
 * @param {repairId} params 报修编号
 * @returns {repairId, houseId, content, status, evaluation, score} 
 *          报修编号, 房源编号, 报修内容, 处理状态, 评价内容, 评分
 */
  const getRepair = (params) => {
    return axios.post('/getRepair', params)
  }

export /**
 * @param {houseId, username, content, pic} params 房源编号 账号 报修内容 图片url
 * @returns  result: true为成功
 */
  const submitRepair = (params) => {
    return axios.post('/submitRepair', params)
  }
export /**
 * @param {repairId, evaluation, score} params 报修编号, 评价内容, 评分
 * @returns result: true为成功
 */
  const submitRepairComment = (params) => {
    return axios.post('/submitRepairComment', params)
  }
//投诉相关
export /**
 * @param {houseId, username, content, pic} params 房源编号 账号 报修内容 图片url
 * @returns result: true为成功
 */
  const submitComplaint = (params) => {
    return axios.post('/submitComplaint', params)
  }
export /**
 * @param {complaintd, evaluation, score} params 投诉编号, 评价内容, 评分
 * @returns result: true为成功
 */
  const submitComplaintComment = (params) => {
    return axios.post('/submitComplaintComment', params)
  }
export /**
 * @param {complaintd, reply, username} params 投诉编号, 回复内容, 客服账号
 * @returns result: true为成功
 */
  const submitComplaintReply = (params) => {
    return axios.post('/submitComplaintReply', params)
  }
//维修相关
export /**
 * @param {repairWorkId} params 维修工单编号
 * @returns {repairWorkId, repairId, houseId, location, content, pic, status, callback, evaluation, tel}
 *          工单编号, 报修编号, 房源编号, 房源地址, 报修内容, 图片url, 状态, 维修反馈, 评价, 租户手机号
 *          状态: 未完成, 已完成
 */
  const getRepairWork = (params) => {
    return axios.post('/getRepairWork', params)
  }
export /**
 * @param {repairWorkId, callback} params 维修工单编号, 维修反馈
 * @returns result: true为成功
 */
  const submitRepairWorkCallback = (params) => {
    return axios.post('/submitRepairWorkCallback', params)
  }

//导航守卫
/*
router.beforeEach((to, from, next) => {
  if (to.path == '/login' || to.path == '/register') {
    sessionStorage.removeItem('userInfo')
    next()
  }
  let user = JSON.parse(sessionStorage.getItem('userInfo')) || { userType: '-1', username: '' }
  if ((user.userType != '0' && /^(\/user0)/.test(to.path)) ||
    (user.userType != '1' && /^(\/user1)/.test(to.path)) ||
    (user.userType != '2' && /^(\/user2)/.test(to.path))) {
    alert('请先登录')
    next({ path: '/login' })
  }
  else {
    next()
  }
})
*/

Vue.use(ElementUI);

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app');
