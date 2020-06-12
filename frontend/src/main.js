import Vue from 'vue';
import App from './App.vue';
import router from './router';
import store from './store';
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import axios from 'axios';
import VDistpicker from 'v-distpicker';

Vue.component('v-distpicker', VDistpicker);

export default {
  components: { VDistpicker },
};

Vue.config.productionTip = false;

Vue.prototype.$http = axios;

// axios.defaults.baseURL = 'http://123.57.41.160:18888';
axios.defaults.baseURL = 'http://127.0.0.1:18888';
axios.defaults.timeout = 2000;
axios.defaults.headers.post['Content-Type'] = 'application/json;charset=UTF-8';
//06.03:4453L
/*
原有内容改动和解释:
  6月2日:
    关键词搜索均为模糊搜索,如果关键词为空，应该返回所有结果,
    getComplaint现在还要返回投诉人编号 username,
    租房订单状态新增'未审核',
    房源状态为 '暂停出租' '未租满' '已租满',
    searchHouses参数关键词为房源编号或具体地址
  6月3日:
    getHouse原先返回值里的area改为province,city,area
  6月4日:
    searchHouses新增参数houseType
  6月8日:
    searchHouses新增参数rentType
  6月10日：
    新增 searchRepairs 按照报修编号or房源编号or租客账号搜索报修（同searchComplaints）
    新增 getApplication 请求未处理租房申请
    新增 dealApplication 处理租房申请（同意/拒绝）
  6月11日：
    新增 searchRepairers 按照(维修人员账号or昵称or手机号) and (租客账号对应的地区)搜索维修人员（空keyword表示所有该地区维修人员）
    新增 createWorkOrder 根据报修编号、导入的师傅账号创建维修工单
    新增 refuseRepair 拒绝报修
    修改 getRepair 返回值中增加 username 字段（发起报修的用户账号）
                          增加 pic      字段（图片url数组）
    修改 getComplaint 返回值中增加 pic 字段（图片url数组）
    rentHouse 新增 rentTime为"yyyy-MM-dd"格式的字符串表示租房开始时间
  6月12日
    searchUsers现在只能搜索到租客
    新增 getUser 按账号请求租客的用户信息
    新增 manageUserInfo 客服修改租客的用户信息
*/

export /**
 * 用户注册
 * @param {password, tel, email, name, province, city, area} params 密码, 手机, 邮箱, 昵称, 省, 市, 地
 * @returns {result,username} result:true为成功 username:账号
 */
  const RegisterUser = (params) => {
    return axios.post('/register', params);
  };

export /**
 * 用户登录
 * @param {userType, username, password} params 用户类型:0租客 1客服 2维修师傅, 账号或手机, 密码
 * @returns result: true为成功, 成功时还返回:{userType, username, password, tel, email, name, province, city, area}
 * @see RegisterUser
 */
  const LoginUser = (params) => {
    return axios.post('/login', params);
  };

export /**
 * 用户修改自己的用户信息
 * @param {userType, username, password, tel, email, name, province, city, area} params
 * @returns result: true为成功
 * @see LoginUser
 */
  const changeUserInfo = (params) => {
    return axios.post('/changeUserInfo', params);
  };

export /**
 * 客服修改租客的用户信息
 * @param {username, tel, email, name, province, city, area} params
 *        账号, 手机, 邮箱, 昵称, 省, 市, 地
 * @returns result: true为成功
 * @see LoginUser
 */
  const manageUserInfo = (params) => {
    return axios.post('/manageUserInfo', params);
  };

//按账号找
export /**
 * 请求租客订单
 * @param {username} params 账号
 * @returns [{contractId, houseId, rentTime, contractTime,contractDuration, price, status}]
 *          订单编号,房源编号,租房时间,订单时间,订单持续时间,价格,状态
 *          状态:'未审核' '未缴费' '已缴费'
 */
  const getUserContract = (params) => {
    return axios.post('/getUserContract', params);
  };

export /**
 * 请求租客维修工单
 * @param {username} params 账号
 * @returns [{repairId, houseId, status}]
 *          报修编号, 房源编号, 状态
 *          状态:'未处理' '未评价' '已评价'
 */
  const getUserRepair = (params) => {
    return axios.post('/getUserRepair', params);
  };

export /**
 * 请求租客投诉工单
 * @param {username} params 账号
 * @returns [{complaintId, houseId, status, adminId}]
 *          投诉编号, 房源编号, 状态, 处理人编号
 *          状态:'未处理' '未评价' '已评价'
 */
  const getUserComplaint = (params) => {
    return axios.post('/getUserComplaint', params);
  };

export /**
 * 请求维修人员维修任务
 * @param {username} params 账号
 * @returns [{repairWorkId, repairId, status, callback}]
 *           维修工单编号, 报修编号, 状态, 回复内容
 *           状态: '未处理' '已处理'
 */
  const getUserRepairWork = (params) => {
    return axios.post('/getUserRepairWork', params);
  };

export /**
 * 判断是否为超级管理员
 * @param {username} params 账号
 * @returns isSuperAdmin: true为当前用户是超级管理员
 */
  const isSuperAdmin = (params) => {
    return axios.post('/isSuperAdmin', params);
  };

//按关键词找
export /**
 * 租客搜房源，只返回状态为'未租满'的房源
 * @param {keyword, rentType, houseType} params
 *        关键词(你们自己定吧), 租房类型, 房间类型
 *        租房形式: '0':任意租房形式 '1':短租 '2':长租
 *        房间类型: '0':任意房间类型 '1':单人间 '2':二人间 '3':三人间 '4':四人间
 * @returns [{houseId, area, address, rentType, houseType}]
 *           房源编号, 地区, 具体地址, 租房类型, 房间类型
 *           租房类型: '短租' '长租'
 *           房间类型: '单人间' '双人间' '三人间' '四人间'
 */
  const searchHouses = (params) => {
    return axios.post('/searchHouses', params);
  };

export /**
 * 客服搜房源，三种状态的房源都返回
 * @param {keyword} params 关键词
 * @returns [{houseId, area, address, rentType, houseType, status}]
 *           房源编号, 地区, 具体地址, 租房类型, 房间类型, 状态
 *           租房类型: '短租' '长租'
 *           房间类型: '单人间' '双人间' '三人间' '四人间'
 *           状态: '暂停出租' '未租满' '已租满'
 */
  const searchAllHouses = (params) => {
    return axios.post('/searchAllHouses', params);
  };

export /**
 * 客服检索租客
 * @param {keyword} params 关键词为账号或手机号
 * @returns [{username, userType, name}]
 *           账号, 用户类型, 昵称
 *           用户类型: '租客' '客服' '维修人员'
 */
  const searchUsers = (params) => {
    return axios.post('/searchUsers', params);
  };

export /**
 * 客服检索投诉工单
 * @param {keyword} params 关键词为投诉编号、房源编号或用户账号
 * @returns [{complaintId, houseId, username, status}]
 *           投诉编号, 房源编号, 用户账号, 处理状态
 *           处理状态: '未处理' '未评价' '已评价'
 */
  const searchComplaints = (params) => {
    return axios.post('/searchComplaints', params);
  };

export /**
 * 客服检索报修
 * @param {keyword} params 关键词为报修编号、房源编号或用户账号
 * @returns [{repairId, houseId, username, status}]
 *           报修编号，房源编号，用户账号，处理状态
 *           处理状态：未处理 工单建立成功 未评价 已完成 已拒绝
 */
  const searchRepairs = (params) => {
    return axios.post('/searchRepairs', params);
  };

//按主键找
export /**
 * 请求某个租客的信息
 * @param {username} params 账号
 * @returns {username, name, email, tel, provonce, city, area}
 *          账号, 昵称, 用户类型, 邮箱, 手机, 省, 市, 地
 */
  const getUser = (params) => {
    return axios.post('/getUser', params);
  }
export /**
 * 请求订单信息
 * @param {contractId} params 订单编号
 * @returns {contractId, username, houseId, houseLocation, houseType, ownerTel, price, housestatus, contractStatus, rentType}
 *          订单编号, 账号, 房源编号, 房源具体地址, 房间类型, 户主手机号, 价格, 房源状态 ,订单状态, 租房类型
 *          订单状态: 未审核 未缴费 已缴费
 * @see searchHouses
 */
  const getContract = (params) => {
    return axios.post('/getContract', params);
  };

export /**
 * 请求房源信息
 * @param {houseId} params 房源编号
 * @returns {houseId, province, city, area, address, rentType, houseType, intro, tel, price, housestatus, pic}
 *          房源编号,省份,城市,地区,具体地址,租房类型,房间类型,介绍,户主手机号,价格,房源状态, 图片
 *          图片是url数组
 */
  const getHouse = (params) => {
    return axios.post('/getHouse', params);
  };

export /**
 * 请求投诉信息
 * @param {complaintId} params 投诉编号
 * @returns {complaintId, houseId, username, content, status, adminId, reply, evaluation, score, pic}
 *          投诉编号, 房源编号, 投诉人账号, 投诉内容, 状态, 客服编号, 回复, 评价内容, 评分，图片url地址数组
 *          状态: '未处理' '未评价' '已评价'
 *          评分: range[1,5]
 */
  const getComplaint = (params) => {
    return axios.post('/getComplaint', params);
  };

//订单相关
export /**
 * 退租
 * @param {contractId} params 订单编号
 * @returns result: true为成功
 */
  const leaseBack = (params) => {
    return axios.post('/leaseBack', params);
  };

export /**
 * 请求租房申请（未审核订单）
 * @returns [{contractId, houseId, rentTime, contractTime,contractDuration, price, status}]
 *          订单编号,房源编号,租房时间,订单时间,订单持续时间,价格,状态
 *          状态:'未审核' '未缴费' '已缴费'
 */
  const getApplication = () => {
    return axios.post('/getApplication');
  };

export /**
 * 续租
 * @param {contractId, month} params 订单编号, 续租月数
 * @returns result: true为成功
 */
  const leaseRenew = (params) => {
    return axios.post('/leaseRenew', params);
  };

//租房相关
export /**
 * 申请租房
 * @param {houseId, username, rentType, rentTime, rentDuration} params 房源编号, 账号, 租房类型, 租房开始时间:"yyyy-MM-dd"字符串, 租房时间:短租日数 或 长租月数
 * @returns result: true为成功
 */
  const rentHouse = (params) => {
    return axios.post('/rentHouse', params);
  };

//报修相关
export /**
 * 请求报修信息
 * @param {repairId} params 报修编号
 * @returns {repairId, houseId, username, content, status, evaluation, score, pic}
 *          报修编号, 房源编号, 报修内容, 处理状态, 评价内容, 评分，图片url数组
 */
  const getRepair = (params) => {
    return axios.post('/getRepair', params);
  };

export /**
 * 发起报修
 * @param {houseId, username, content, pic} params 房源编号 账号 报修内容 图片url
 * @returns  result: true为成功
 */
  const submitRepair = (params) => {
    return axios.post('/submitRepair', params);
  };

export /**
 * 评价报修
 * @param {repairId, evaluation, score} params 报修编号, 评价内容, 评分
 * @returns result: true为成功
 */
  const submitRepairComment = (params) => {
    return axios.post('/submitRepairComment', params);
  };

export /**
 * 客服搜索维修人员
 * @param {keyword, username} params 维修人员账号/昵称/手机号，报修租客账号
 * @returns [{username, name, tel}]
 *           维修人员账号，昵称，手机号
 */
  const searchRepairers = (params) => {
    return axios.post('/searchRepairers', params);
  };

export /**
 * 客服创建维修工单
 * @param {repairId, username} params 报修编号，维修人员账号
 * @returns result: true为成功
 * 创建维修工单后，对应报修 status 设为 “工单建立成功”
 */
  const createWorkOrder = (params) => {
    return axios.post('/createWorkOrder', params);
  };

export /**
 * 客服拒绝报修
 * @param {repairId} params 报修编号
 * @returns result: true为成功
 * 拒绝后，对应报修 status 设为 “已拒绝”
 */
  const refuseRepair = (params) => {
    return axios.post('/refuseRepair', params);
  };

//投诉相关
export /**
 * 发起投诉
 * @param {houseId, username, content, pic} params 房源编号 账号 报修内容 图片url
 * @returns result: true为成功
 */
  const submitComplaint = (params) => {
    return axios.post('/submitComplaint', params);
  };

export /**
 * 评价投诉
 * @param {complaintd, evaluation, score} params 投诉编号, 评价内容, 评分
 * @returns result: true为成功
 */
  const submitComplaintComment = (params) => {
    return axios.post('/submitComplaintComment', params);
  };

export /**
 * 客服回复投诉
 * @param {complaintd, reply, username} params 投诉编号, 回复内容, 客服账号
 * @returns result: true为成功
 */
  const submitComplaintReply = (params) => {
    return axios.post('/submitComplaintReply', params);
  };

//维修相关
export /**
 * 请求维修任务信息
 * @param {repairWorkId} params 维修工单编号
 * @returns {repairWorkId, repairId, houseId, location, content, pic, status, callback, evaluation, tel}
 *          工单编号, 报修编号, 房源编号, 房源地址, 报修内容, 图片url, 状态, 维修反馈, 评价, 租客手机号
 *          状态: 未完成, 已完成
 */
  const getRepairWork = (params) => {
    return axios.post('/getRepairWork', params);
  };

export /**
 * 维修人员回复维修任务
 * @param {repairWorkId, callback} params 维修工单编号, 维修反馈
 * @returns result: true为成功
 */
  const submitRepairWorkCallback = (params) => {
    return axios.post('/submitRepairWorkCallback', params);
  };

//房源相关
export /**
 * 暂停出租房源
 * @param {houseId} params 房源编号
 * @returns result: true为成功
 */
  const stopRent = (params) => {
    return axios.post('/stopRent', params);
  };

export /**
 * 恢复出租房源
 * @param {houseId} params 房源编号
 * @returns result: true为成功
 */
  const restoreRent = (params) => {
    return axios.post('/restoreRent', params);
  };

export /**
 * 删除房源
 * @param {houseId} params 房源编号
 * @returns result: true为成功
 */
  const deleteRent = (params) => {
    return axios.post('/deleteRent', params);
  };

export /**
 * 修改房源信息
 * @param {houseId, province, city, area, address, rentType, houseType, intro, tel, price, housestatus}
 *          房源编号,省份,城市,地区,具体地址,租房类型,房间类型,介绍,户主手机号,价格,房源状态
 * @returns result: true为成功
 */
  const changeHouseInfo = (params) => {
    return axios.post('/changeHouseInfo', params);
  };

export /**
 * 导入房源
 * @param {province, city, area, address, rentType, houseType, intro, tel, price}
 *          省份,城市,地区,具体地址,租房类型,房间类型,介绍,户主手机号,价格
 * @returns {result,houseId} result:true为成功 houseId:房源编号
 */
  const newHouse = (params) => {
    return axios.post('/newHouse', params);
  };

//导航守卫
/*调试时注释掉
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
  render: (h) => h(App),
}).$mount('#app');
