import Vue from 'vue';
import Router from 'vue-router';

Vue.use(Router);

const router = new Router({
  mode: 'history',
  routes: [
    {
      path: '/',
      redirect: '/login'
    },
    {
      path: '/login',
      name: '登录',
      type: "-1",
      component: () =>
        import('../views/Login.vue')
    },
    {
      path: '/register',
      name: '注册',
      type: "-1",
      component: () =>
        import('../views/Register.vue')
    },
    {
      path: '/contract',
      name: '订单',
      component: () =>
        import('../views/Contract.vue')
    },
    {
      path: '/house',
      name: '房源',
      component: () =>
        import('../views/House.vue')
    },
    {
      path: '/repairInfo',
      name: '报修工单',
      component: () =>
        import('../views/RepairInfo.vue')
    },
    {
      path: '/complaintInfo',
      name: '投诉工单',
      component: () =>
        import('../views/ComplaintInfo.vue')
    },
    {
      path: '/repairWorkInfo',
      name: '维修工单',
      component: () =>
        import('../views/RepairWorkInfo.vue')
    },
    {
      path: '/userInfo',
      name: '用户信息',
      component: () =>
        import('../views/UserInfo.vue')
    },
    {
      path: '/newUser',
      name: '导入用户',
      component: () =>
        import('../views/NewUser.vue')
    },
    {
      path: '/complaint',
      name: '投诉信息',
      component: () =>
        import('../views/Complaint.vue')
    },
    {
      path: '/newhouse',
      name: '导入房源',
      component: () =>
        import('../views/NewHouse.vue')
    },
    {
      path: '/user0',
      name: 'User0',
      redirect: '/user0/info',
      type: "0",
      component: () => import('../views/User0.vue'),
      hasChild: true,
      children: [
        {
          path: '/user0/info',
          name: '个人信息',
          component: () => import('../views/user0/Info.vue')
        },
        {
          path: '/user0/query',
          name: '订单查询',
          component: () => import('../views/user0/Query.vue')
        },
        {
          path: '/user0/rent',
          name: '租房',
          component: () => import('../views/user0/Rent.vue')
        },
        {
          path: '/user0/repair',
          name: '报修',
          component: () => import('../views/user0/Repair.vue')
        },
        {
          path: '/user0/complaint',
          name: '投诉',
          component: () => import('../views/user0/Complaint.vue')
        }
      ]
    },
    {
      path: '/user1',
      name: 'User1',
      redirect: '/user1/info',
      type: "1",
      component: () => import('../views/User1.vue'),
      hasChild: true,
      children: [
        {
          path: '/user1/info',
          name: '个人信息',
          component: () => import('../views/user1/Info.vue')
        },
        {
          path: '/user1/usermanage',
          name: '用户管理',
          component: () => import('../views/user1/UserManage.vue')
        },
        {
          path: '/user1/rentmanage',
          name: '租房管理',
          component: () => import('../views/user1/RentManage.vue')
        },
        {
          path: '/user1/housemanage',
          name: '房屋管理',
          component: () => import('../views/user1/HouseManage.vue')
        },
        {
          path: '/user1/complaintmanage',
          name: '投诉管理',
          component: () => import('../views/user1/ComplaintManage.vue')
        },
        {
          path: '/user1/repairmanage',
          name: '维修管理',
          component: () => import('../views/user1/RepairManage.vue')
        }
      ]
    },
    {
      path: '/user2',
      name: 'User2',
      redirect: '/user2/info',
      type: "2",
      component: () => import('../views/User2.vue'),
      hasChild: true,
      children: [
        {
          path: '/user2/info',
          name: '个人信息',
          component: () => import('../views/user2/Info.vue')
        },
        {
          path: '/user2/repairWork',
          name: '维修任务',
          component: () => import('../views/user2/RepairWork.vue')
        }
      ]
    },
    {
      path: '*',
      hidden: true,
      component: () => import('../views/404.vue')
    }
  ]
});

export default router;