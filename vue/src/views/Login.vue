<template>
  <div id="login">
    <!-- 登录页面 -->
    <form class="form login">
      <div class="login_icon">
        <!-- <img src="../assets/images/space.jpg" alt="..."> -->
      </div>
      <h3>欢 迎 登 录</h3>
      <div>
        <label for="">账&emsp;&emsp;号：</label>
        <input v-model="loginname" type="text" name="username" placeholder="请输入账号"
        @blur="checkUsername" style="height: 24px;" :class="{ warningRed: warnUsername }" maxlength="16">
      </div>
      <div>
        <label for="">密&emsp;&emsp;码：</label>
        <input v-model="password" type="password" name="password" placeholder="请输入密码"
        @blur="checkPassword" style="height: 24px;" :class="{ warningRed: warnPassword }" maxlength="16">
      </div>
      <!-- <div class="codeBox">
        <img :src="codeURL" alt="...">
        <input v-model="verificationCode" type="text" name="verificationCode" placeholder="验证码">
      </div> -->
      <div>
        <button type="button" @click="doLogin" @keyup.enter="doLogin">登 录</button>
      </div>
    </form>
    <!-- 登陆页面结束 -->
  </div>
</template>

<script>
import { stringify } from 'querystring';
import { baseURL } from '../util/request.js'

export default {
  name: 'Login',
  data() {
    return {
      loginname: null,
      password: null,
      verificationCode: null,
      // codeURL: 'http://localhost:8888/user/verifyCode',
      // 提醒标识变量
      warnUsername: false,
      warnPassword: false
    }
  },
  methods: {
    /* 登录请求 */
    doLogin() {
      console.log("登录");
      if(this.loginname=="" || this.password=="" || this.verificationCode=="")
        return;
      this.axios({
        method: 'POST',
        url: baseURL + '/user/login',
        data: {
          loginname: this.loginname,
          password: this.password,
        },
        transformRequest: [
          function(data) {
            return stringify(data)
          }
        ],
        headers: {
          'Content-Type': 'application/x-www-form-urlencoded'
        },
        timeout: 10000,
      }).then((response) => {
        // console.log(response.data); // token 没设置
        if(response.data.code == 200) {
          alert("登录成功！");
          this.$store.commit('setUserInfo', {userInfo: JSON.parse(response.data.data.user)});
          this.$store.commit('setToken', {token: response.data.data.token});
          this.$router.push({path: '/main/homePage'});
        } else {
          alert("登录失败！");
        }
      })
    },

    checkUsername () {
      // console.log('checkUsername ======');
      if (this.loginname == null || this.loginname == '') {
        this.warnUsername = true;
        return false;
      }
      if (this.loginname.length < 4 || this.loginname.length >16) {
        this.warnUsername = true;
        return false;
      }
      this.warnUsername = false;
      return true;
    },
    checkPassword () {
      const reg = /^[a-zA-Z0-9_@.]{6,16}$/;
      if (!reg.test(this.password)) {
        this.warnPassword = true;
        return false;
      }
      this.warnPassword = false;
      return true;
    }
  }
}
</script>

<style scoped>
#login {
  width: 100%;
  height: 100vh;
  background: darkcyan;
  background-size: cover;
  display: flex;
  align-items: center;
}

.form {
  min-width: 300px;
  min-height: 420px;
  margin: 0 auto;
  box-sizing: border-box;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  align-items: center;
  font-size: 14px;
  background-color: rgba(255, 255, 255, 0.80); 
  border-radius: 8px;
  box-shadow: 8px 8px 8px 0 rgba(0, 0, 0, 0.2);
  position: relative;
  overflow: hidden;
  padding-bottom: 20px;
}
.form h3 {
  font-size: 18px;
}
.form > div {
  margin-top: 14px;
}
.form button {
  /* margin-top: 20px; */
  width: 100px;
  padding: 4px 8px;
  border-radius: 4px;
  border: 1px solid #000;
  background-color: skyblue;
  cursor: pointer;
}
.form button:last-child {
  background-color: rgba(190, 228, 243, 0.6);
  margin-left: 10px;
}
#login .form input {
  padding: 4px;
  font-size: 8px;
  border-radius: 5px;
  border: 1px solid #999;
}

#login .form .warningRed {
  border: 1px solid red;
}

.login_icon {
  margin-top: 0 !important;
}
.login_icon > img{
  /* position: relative; */
  /* top: -44px; */
  width: 300px;
  height: 200px;
}
.login button {
  background-color: skyblue !important;
}

.font_Style {
  font-size: 12px;
  color: red;
  opacity: 0;
}
.common {
  display: flex;
}
.common label {
  padding-top: 4px;
}

.showText {
  opacity: 1;
}

.codeBox {
  display: flex;
  justify-items: center;
}

.codeBox input {
  width: 130px;
  margin-left: 10px;
}
</style>