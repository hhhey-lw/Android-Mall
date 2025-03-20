<template>
  <div>
    <!-- 新增和搜索 -->
    <div class="funcBox">
      <el-button size="mini" type="primary" @click="addUser" icon="el-icon-plus">新增用户</el-button>&nbsp;
      <div class="inputbox">
        <el-input size="mini" v-model="queryWord" placeholder="搜索...">
          <template #append>
            <el-button type="primary" icon="el-icon-search" @click="queryUserBtn" />
          </template>
        </el-input>
      </div>
    </div>

    <!-- 展示用户信息的表格 -->
    <el-table :data="tableDataFormat" class="tableBox">
      <el-table-column label="ID" prop="uid" width="120px"/>
      <el-table-column label="昵称" prop="username" width="120px" />
      <el-table-column label="性别" prop="gender" :formatter="formatSex" />
      <el-table-column label="账号" prop="loginname" width="120px" />
      <el-table-column label="手机号" prop="phone" width="160px" />
      <el-table-column label="创建时间" prop="create_time" />
      <el-table-column label="更新时间" prop="update_time" />
      <el-table-column label="操作" fixed="right" align="center">
        <template #default="scope">
          <el-button size="mini" @click="handleEdit(scope.row)" icon="el-icon-edit">编辑</el-button>
          <el-button size="mini" :type="scope.row.del_status == 0 ? 'danger':'success'" @click="handleDelete(scope.row)" icon="el-icon-delete">{{scope.row.del_status == 0 ? '冻结':'解冻'}}</el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 分页组件 -->
    <div class="pageBox">
      <el-pagination
        small
        background
        layout="prev, pager, next"
        :total="total"
        :page-size="pageSize"
        :current-page="pageNum"
        class="mt-4"
        hide-on-single-page
        @current-change="pageChange"
      />
    </div>
    
    <!-- 弹出层 -->
    <el-dialog v-model="dialogFormVisible" title="编辑用户信息">
      <el-form :model="userInfo" class="formBox">
        <el-form-item label="昵称：" :label-width="formLabelWidth">
          <el-input v-model="userInfo.username" autocomplete="off" 
          class="inputWidth" maxlength="20" show-word-limit>
          </el-input>
        </el-form-item>
        <el-form-item>
          <div class="radioBox">
            <label>性别：</label>
            <el-radio-group v-model="userInfo.gender">
              <el-radio :label="0" border>女</el-radio>
              <el-radio :label="1" border>男</el-radio>
            </el-radio-group>
          </div>
        </el-form-item>
        <el-form-item label="账号：" :label-width="formLabelWidth">
          <el-input v-model="userInfo.loginname" autocomplete="off" 
          :disabled="!fromSwitch" class="inputWidth"  maxlength="20" show-word-limit>
          </el-input>
        </el-form-item>
        <el-form-item label="密码：" :label-width="formLabelWidth" v-if="fromSwitch">
          <el-input v-model="userInfo.password" autocomplete="off" 
          class="inputWidth"  maxlength="16" show-word-limit>
          </el-input>
        </el-form-item>
        <el-form-item label="确认密码：" :label-width="formLabelWidth" v-if="fromSwitch">
          <el-input v-model="confirmPassword" autocomplete="off" 
          class="inputWidth"  maxlength="16" show-word-limit>
          </el-input>
        </el-form-item>
        <el-form-item label="手机号：" :label-width="formLabelWidth">
          <el-input v-model="userInfo.phone" autocomplete="off" 
          class="inputWidth"  maxlength="11" show-word-limit>
          </el-input>
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="dialogHidden" icon="el-icon-close">取消</el-button>
          <el-button type="primary" @click="addOrUpdateUser" icon="el-icon-success">确定</el-button>
        </span>
      </template>
    </el-dialog>
    <!-- 弹出层 结束 -->
  </div>
</template>


<script>
import Qs from 'qs'
import {request, baseURL, req} from '../../util/request'

export default {
  name: 'UserManage',
  data() {
    return {
      /* 控制弹出层是否显示 */
      dialogFormVisible: false,
      /* table的宽度 */
      formLabelWidth: '120px',
      /* 该变量用于 编辑与新增 编辑框的切换  false: 编辑， true: 新增*/
      fromSwitch: false,
      /* 弹窗 用户信息 */
      userInfo: {
        uid: null,
        loginname: null,
        username: null,
        password: null,
        create_time: null,
        update_time: null,
        phone: null,
        gender: 0
      },
      confirmPassword: null,
      // 模糊搜索
      queryWord: null,
      // 分页 切换 调用的函数
      isShowQueryInfo: false,
      // req info 
      data: null,
      pageSize: 10 
    }
  },
  computed: {
    tableDataFormat() {
      if(this.data !== null)
        return this.data.list;
    },
    total() {
      if(this.data !== null)
        return this.data.total;
    },
    pageNum() {
      if(this.data !== null)
        return this.data.pageNum;
    }
  },
  methods: {
    /* 隐藏弹出层 */
    dialogHidden() {
      this.dialogFormVisible = false;
    },
    /* 添加用户按钮 显示弹出层 */
    addUser() {
      this.confirmPassword = null;
      // 清空弹出层双向绑定信息
      for(const key in this.userInfo) {
        this.userInfo[key] = null;
      }
      /* 显示弹出层 */
      this.dialogFormVisible = true;
      /* 表单切换为 新增 */
      this.fromSwitch = true;
    },
    addOrUpdateUser() {
      // 新增
      if(this.fromSwitch) {
        console.log("新增");
        if(this.confirmPassword != this.userInfo.password) {
          alert("前后密码不一致！");
          return;
        }
        if(this.checkInputForm()) {
          alert("请填充完整必要信息！");
          return;
        }
        req({
          method: 'POST',
          data: JSON.stringify(this.userInfo),
          url: baseURL + '/user/add',
          contentType: 'application/json'
        }).then(response => {
          console.log(response.data);
          if(response.data.code == 200) {
            alert("添加成功！");
            this.dialogHidden();
            this.getData(this.pageNum, this.pageSize);
          }
          else
            alert("添加失败！");
        })
      }
      else {
        console.log("编辑");
        req({
          method: 'POST',
          url: '/user/updateInfo',
          data: {
            loginname: this.userInfo.loginname,
            username: this.userInfo.username,
            phone: this.userInfo.phone,
            gender: Number(this.userInfo.gender),
          },
          contentType: 'application/x-www-form-urlencoded'
        }).then(response => {
          console.log(response.data);
          if(response.data.code == 200) {
            alert("修改成功！");
            this.dialogHidden();
            this.getData(this.pageNum, this.pageSize);
          }
          else
            alert("修改失败！");
        })
      }
    },
    checkInputForm() {
      if(this.userInfo.loginname == null || this.userInfo.loginname == '' ||
         this.userInfo.password == null || this.userInfo.password == '' ||
         this.userInfo.username == null || this.userInfo.username == '' ||
         this.userInfo.phone == null || this.userInfo.phone == '' )
         return true
      return false;
    },
    /*  table删除用户按钮 */
    handleDelete(row) {
      if(confirm("你确定要冻结吗？")) {
        const user = {
          ...row
        }
        req({
          method: 'POST',
          url: '/user/updateStatus',
          data: Qs.stringify({
            loginname: user.loginname
          }),
          contentType: 'application/x-www-form-urlencoded'
        })
        .then(response => {
          if(response.data.code == 200) {
            alert("状态变更成功！");
            this.getData(this.pageNum, this.pageSize);
          }else{
            alert("状态变更失败！");
            console.log(response.data);
          }
        })
      }
    },
    /* table编辑用户信息按钮 */
    handleEdit(row) {
      /* 显示弹出层 */
      this.dialogFormVisible = true;
      /* 表单提交 为 修改信息 */
      this.fromSwitch = false;
      // 将 行信息 保存
      const user = {
        ...row
      }

      this.userInfo = user;
    },
    pageChange(number) {
      if(this.isShowQueryInfo == false)
        this.getData(number, this.pageSize);
      else
        this.queryUser(number)
    },
    getData(pageNum, pageSize) {
      const that = this;
      request({
        method: 'GET',
        url: `/user/getAllUser/${pageNum}-${pageSize}`,
        data: null
      })
      .then(response => {
        console.log(response.data);
        that.data = response.data.data;
      })
    },
    // 性别数据转换
    formatSex(row){
      return row.gender == 0 ? "女" : "男";
    },
    queryUserBtn() {
      if(this.queryWord != null && this.queryWord != '') {
        this.isShowQueryInfo = true;
        this.queryUser(1); // 默认第一页
      } else{
        this.isShowQueryInfo = false;
        this.getData(1, this.pageSize);
      }
    },
    queryUser(Number) {
      const that = this;
        request({
          method: 'GET',
          url: `/user/query/${this.queryWord}/${Number}-${this.pageSize}`,
          data: null
        }).then(response => {
          console.log(response.data);
          that.data = response.data.data;
        })
    }
  },
  /* 生命钩子 页面创建时 触发 */
  created () {
    this.getData(1, this.pageSize);
  }
}
</script>

<style scoped>
.tableBox {
  width: 100%;
}
.funcBox {
  display: flex;
  justify-content: space-between;
  margin-bottom: 16px;
}
.pageBox {
  display: flex;
  justify-content: center;
  margin-top: 10px;
}
.funcBox .inputbox {
  width: 200px;
}
.inputWidth {
  width: 400px;
}
.formBox {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
}
.formBox .radioBox {
  width: 390px;
  display: flex;
  justify-content: space-between;
}
.hiddenBox{
  display: none;
}
</style>