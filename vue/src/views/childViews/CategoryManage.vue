<template>
    <div class="Box">
      <!--  功能条  -->
      <div class="funBtn">
        <div>
          <el-button type="success" @click="AddCategoryBtn" style="margin-left: 10px;margin-right: 10px;">新增类型</el-button>
        </div>
      </div>
      
      <!-- 展示用户信息的表格 -->
      <el-table :data="tableDataFormat" class="tableBox">
        <el-table-column label="类型ID" prop="gcategory_id" align="center"/>
        <el-table-column label="类型名称" prop="gcategory_name" align="center"/>
        <el-table-column label="状态" prop="status" align="center"/>
        <el-table-column label="操作" fixed="right" align="center">
          <template #default="scope">
            <el-button size="mini" type="success" @click="editBtn(scope.row)" icon="el-icon-edit">编辑</el-button>
            <el-button size="mini" :type="scope.row.status == 1 ? 'danger':'success'" @click="changeStatusBtn(scope.row)" icon="el-icon-edit">{{scope.row.status == 1 ? '禁用':'启用'}}</el-button>
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
      <el-dialog v-model="dialogFormVisible" title="新增类别">
        <el-form :model="categoryVO" class="formBox">
          <el-form-item label="类别名称：" label-width="120px">
            <el-input v-model="categoryVO.gcategory_name" autocomplete="off" 
            class="inputWidth" maxlength="20" show-word-limit>
            </el-input>
          </el-form-item>
        </el-form>
        <template #footer>
          <span class="dialog-footer">
            <el-button @click="dialogHidden" icon="el-icon-close">取消</el-button>
            <el-button type="primary" @click="addOrUpdateBtnApi" icon="el-icon-success">确定</el-button>
          </span>
        </template>
      </el-dialog>
    </div>
</template>

<script>
import {req, request} from '../../util/request'
import Qs from 'qs'

export default {
  name: 'CategoryManage',
  data() {
    return {
      /* 控制弹出层是否显示 */
      dialogFormVisible: false,
      data: null,
      pageSize: 7,

      addOrUpdate: null,
      categoryVO: {
        gcategory_id: null,
        gcategory_name: null,
        status: null
      }
    }
  },
  methods: {
    AddCategoryBtn() {
      this.dialogFormVisible = true;
      this.addOrUpdate = 'add';
    },
    addOrUpdateBtnApi() {
      console.log(this.categoryVO);
      if(this.categoryVO.gcategory_name == '' || this.categoryVO.gcategory_name == null) {
        alert("请输入正确的名称")
      }
      if(this.addOrUpdate == 'add') {
        if(confirm("请确认"))
        req({
          method: 'POST',
          data: Qs.stringify({
            gcategory_name: this.categoryVO.gcategory_name
          }),
          url: '/category/add',
          contentType: 'application/x-www-form-urlencoded'
        })
        .then(res => {
          if(res.data.code == 200) {
            alert("新增成功！")
            this.dialogHidden();
            this.getData(this.pageNum, this.pageSize);
          }
        })
      } else {
        if(confirm("请确认"))
        req({
          method: 'POST',
          data: Qs.stringify({
            gcategory_name: this.categoryVO.gcategory_name,
            gcategory_id: this.categoryVO.gcategory_id
          }),
          url: '/category/update',
          contentType: 'application/x-www-form-urlencoded'
        })
        .then(res => {
          if(res.data.code == 200) {
            alert("更新成功！")
            this.dialogHidden();
            this.getData(this.pageNum, this.pageSize);
          }
        })
      }

    },
    editBtn(category) {
      console.log("编辑");
      this.dialogFormVisible = true;
      this.addOrUpdate = 'update';

      for (const key in category) {
        this.categoryVO[key] = category[key];
      }
    },
    changeStatusBtn(category) {
      if(confirm("请确定"))
      req({
          method: 'POST',
          data: Qs.stringify({
            gcategory_id: category.gcategory_id
          }),
          url: '/category/status',
          contentType: 'application/x-www-form-urlencoded'
        })
        .then(res => {
          if(res.data.code == 200) {
            alert("更新成功！")
            this.dialogHidden();
            this.getData(this.pageNum, this.pageSize);
          }
        })
      
    },
    dialogHidden() {
      this.dialogFormVisible = false;
      for (const key in this.categoryVO) {
        this.categoryVO[key] = null;
      }
    },
    pageChange(pageNum) {
      this.getData(pageNum, this.pageSize);
    },
    getData(pageNum, pageSize) {
      const that = this;
      request({
        method: 'GET',
        url: `/category/all/${pageNum}-${pageSize}`,
        data: null
      })
      .then(response => {
        console.log(response.data);
        that.data = response.data.data;
      })
    },
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
  mounted() {
    this.getData(1, this.pageSize);
  }
}
</script>

<style scoped>
.Box {
  display: flex;
  flex-direction: column;
  justify-content: start;
  align-items: center;
  height: 650px;
  background-color: white;
}

.funBtn {
  width: 100%;
  margin: 8px;
  display: flex;
  justify-items: start;
}
</style>