<template>
  <el-container class="container">
    <!-- 左侧边栏 -->
    <el-aside class="aside" style="width: auto">
      <common-menu></common-menu>
    </el-aside>
    <!-- 主体容器 -->
    <el-container>
      <!-- 头部 -->
      <el-header class="header">
        <common-header></common-header>
      </el-header>
      <!-- 主体 子路由 -->
      <el-main class="main">
        <router-view/>
      </el-main>
    </el-container>
  </el-container>
</template>

<script>
import CommonHeader from "../components/CommonHeader.vue"
import CommonMenu from "../components/CommonMenu.vue"

export default {
  name: 'Main',
  components: {
    CommonHeader, CommonMenu
  }  
}
</script>

<style>
* {
  padding: 0;
  margin: 0;
}
.container {
  height: 100vh;
}
.aside {
  background-color: #545C64;
  width: auto;
}
.header {
  background-color: #545C64;
  display: flex;
  align-items: center;
}
.main {
  background-color: #EEE;
}
</style>