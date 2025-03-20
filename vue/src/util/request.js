const { default: axios } = require("axios")
import store from '../store'

/* 基础地址 http://xxx.xxx/ */
export const baseURL = "http://localhost:8888"

/* 封装axios方法 */
export function request({method, url, data}) {
  let httpDefault = {
    method,
    baseURL,
    url,
    /* 因为 post put 请求 参数要放在请求体中 得放入data中 而 get delete方法 参数放在网址后拼接 放入params中 */
    params: method.toUpperCase() === 'GET' || method.toUpperCase() === 'DELETE' ? data : null,
    data: method.toUpperCase() === 'POST' || method.toUpperCase() === 'PUT' ? data : null,
    timeout: 10000
  }

  /* header部分携带 token */
  if(store.getters.token != null) {
    httpDefault.headers = {
      'token': store.getters.token
    }
  }
  return axios(httpDefault)
}

export function req({method, url, data, contentType}) {
  let httpDefault = {
    method,
    baseURL,
    url,
    /* 因为 post put 请求 参数要放在请求体中 得放入data中 而 get delete方法 参数放在网址后拼接 放入params中 */
    params: method.toUpperCase() === 'GET' || method.toUpperCase() === 'DELETE' ? data : null,
    data: method.toUpperCase() === 'POST' || method.toUpperCase() === 'PUT' ? data : null,
    timeout: 10000
  }

  /* header部分携带 token */
  if(store.getters.token != null) {
    httpDefault.headers = {
      'token': store.getters.token,
      'content-type': contentType
    }
  }

  return axios(httpDefault)
}