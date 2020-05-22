import Vue from 'vue';
import Vuex from 'vuex';

Vue.use(Vuex);
 
const store = new Vuex.Store({
  state: {
    userInfo: JSON.parse(sessionStorage.getItem('user')) || {userType: '-1',name:'',username: '',password:'',email:'',tel:'',province:'',city:'',area:''}
  },
  getters: {
    userType:(state)=> {
      return state.userInfo.userType
    }
  },

  mutations: {
    Login (state) {
      state.userInfo = JSON.parse(sessionStorage.getItem('userInfo'))
    },
    Logout (state) {
      state.userInfo = {userType: '-1',name:'',username: '',password:'',email:'',tel:'',province:'',city:'',area:''}
      sessionStorage.removeItem('userInfo')
    }
  },

  actions: {
    commitLogin({commit}) {
      commit('Login')
    },
    commitLogout({commit}) {
      commit('Logout')
    }
  }
});
 
export default store;