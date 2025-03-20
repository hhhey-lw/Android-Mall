import { createStore } from 'vuex'

export default createStore({
  state: {
    isCollapse: false,
    userInfo: null,
    token: null
  },
  mutations: {
    switchCollapse(state) {
      state.isCollapse = !state.isCollapse;
    },
    setUserInfo(state, payload) {
      state.userInfo = payload.userInfo;
    },
    setToken(state, payload) {
      state.token = payload.token;
    },
    removeToken(state) {
      state.token = null;
    },
    removeUserInfo(state) {
      state.userInfo = null;
    }
  },
  getters: {
    userInfo (state) {
      return state.userInfo;
    },
    token (state) {
      return state.token;
    }
  },
  actions: {
  },
  modules: {
  }
})
