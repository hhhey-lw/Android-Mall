<template>
  <div class="header">
    <div class="leftBox">
      <el-button icon="el-icon-menu" 
        size="mini" @click="switchCollapse">
      </el-button>
      <h3>&nbsp;&nbsp;欢迎！{{userInfo ? userInfo.username:"请登录"}}</h3>
    </div>
    <div>
      <el-dropdown>
        <el-avatar v-bind:src="avatar" v-bind:size="50" shape="circle" class="el-dropdown-link"></el-avatar>
        <template #dropdown>
          <el-dropdown-menu>
            <el-dropdown-item @click="doLogout">退 出</el-dropdown-item>
          </el-dropdown-menu>
        </template>
      </el-dropdown>
    </div>
  </div>
</template>

<script>
import {baseURL} from '../util/request';

export default {
  name: 'CommonHeader',
  data() {
    return {
      
    }
  },
  computed: {
    userInfo() {
      return this.$store.getters.userInfo;
    },
    avatar() {
      if(this.userInfo)
        return baseURL + this.userInfo.avatar;
      else
        return null;
    }
  },
  methods: {
    // 侧边栏 展开和缩放
    switchCollapse() {
      this.$store.commit('switchCollapse')
    },
    // 登出
    doLogout() {
      this.$store.commit('removeToken');
      this.$store.commit('removeUserInfo');
      this.$router.push({name: 'Login'})
    }
  },
  mounted() {
    console.log("User : ", this.userInfo);
    console.log("Token : ", this.$store.getters.token);
  }
}
</script>

<style scoped>
.header {
  width: 100%;
  display: flex;
  justify-content: space-between;
  align-items: center;
}
.leftBox {
  display: flex;
  align-items: center;
}
.leftBox > h3 {
  color: #FFF;
  margin-left: 10px;
}
.icon {
  width: 40px;
  height: 40px;
  margin-left: 20px;
}
</style>