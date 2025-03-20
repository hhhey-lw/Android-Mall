<template>
  <el-menu
    class="el-menu-vertical"
    :collapse="isCollapse"
    background-color="#545c64"
    active-text-color="#FF8C00"
    text-color="#FFF"
  >
    <!-- 标题 -->
    <h3 v-show="!isCollapse">后台管理系统</h3>
    <h3 v-show="isCollapse">系统</h3>

    <!-- 循环遍历 无子菜单的选项  index 标签唯一索引 key vue复用组件的唯一标识 -->
    <el-menu-item :index="item.path" v-for="item in noChildren" :key="item.path" @click="clickMenu(item)">
      <i :class="item.icon"></i>
      <template #title>{{item.label}}</template>
    </el-menu-item>
  <!-- 循环遍历 无子菜单的选项  结束 -->

    <!-- 遍历有子菜单的选项 -->
    <el-sub-menu :index="item.name" v-for="item in hasChildren" :key="item.name">
      <template #title>
        <i :class="item.icon"></i>
        <span>{{item.label}}</span>
      </template>
      <el-menu-item-group>
        <el-menu-item :index="subItem.path" v-for="subItem in item.children" :key="subItem.path" @click="clickMenu(subItem)">
          <i :class="subItem.icon"></i>
          <span>{{subItem.label}}</span>
        </el-menu-item>
      </el-menu-item-group>
    </el-sub-menu>
    <!-- 遍历有子菜单的选项  结束 -->
  </el-menu>
</template>

<!--  
    , {
          name: "SelectedManage",
          label: "精品推荐",
          icon: "el-icon-star-off",
          path: "/main/selectedManage",
        } -->
<script>
export default {
  name: 'CommonMenu',
  data() {
    return {
      menu: [{
        name: "HomePage",
        label: "首    页",
        icon: "el-icon-house",
        path: "/main/homePage"
      },{
        name: "UserManage",
        label: "用户管理",
        icon: "el-icon-user",
        path: "/main/userManage"
      }, {
        name: "GoodManage",
        label: "商品管理",
        icon: "el-icon-goods",
        path: "/main/goodManage"
      }, {
        name: "CommentManage",
        label: "评论管理",
        icon: "el-icon-s-comment",
        path: "/main/commentManage"
      }, {
        name: "OrderManage",
        label: "订单管理",
        icon: "el-icon-tickets",
        children: [{
          name: "OrderDeliveryManage",
          label: "待发货单",
          icon: "el-icon-sell",
          path: "/main/orderDeliveryManage",
        }, {
          name: "OrderManage",
          label: "全部订单",
          icon: "el-icon-tickets",
          path: "/main/orderManage",
        }]
      }, {
        name: "ShowManage",
        label: "展示管理",
        icon: "el-icon-s-opportunity",
        children: [{
          name: "BannerManage",
          label: "轮播管理",
          icon: "el-icon-picture-outline",
          path: "/main/bannerManage",
        }]
      }, {
        name: "ClassManage",
        label: "类别管理",
        icon: "el-icon-s-opportunity",
        children: [{
          name: "CategoryManage",
          label: "商品大类",
          icon: "el-icon-picture-outline",
          path: "/main/categoryManage",
        }, {
          name: "TypeManage",
          label: "商品小类",
          icon: "el-icon-star-off",
          path: "/main/typeManage",
        }]
      }]
    }
  },
  computed: {
    // 返回菜单中无子菜单的对象
    noChildren () {
      // 过滤方法 返回新数组 遍历旧数组，return true 则插入新数组中
      return this.menu.filter((item) => {
        return !item.children;
      })
    },
    // 返回菜单中有子菜单的对象
    hasChildren () {
      return this.menu.filter((item) => {
        return item.children;
      })
    },
    isCollapse() {
      return this.$store.state.isCollapse;
    }
  },
  methods: {
    clickMenu(item) {
      // console.log(item);
      this.$router.push({path: item.path});
    }
  }
}
</script>

<style scoped>
.el-menu-vertical:not(.el-menu--collapse) {
  width: 100%;
  min-height: 400px;
  padding: 0;
  min-width: 210px;
}
.el-menu-vertical > h3 {
  color: #FFF;
  padding-top: 5px;
  text-align: center;
  margin-top: 12px;
}
</style>