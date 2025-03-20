<template>
    <div class="Box">

      <!--  功能条  -->
      <div class="funBtn">
        <div>
          <el-button type="success" @click="AddBtn" style="margin-left: 10px;margin-right: 10px;">新增轮播图</el-button>
        </div>
      </div>

      <!-- 展示用户信息的表格 -->
      <el-table :data="tableDataFormat" class="tableBox">
        <el-table-column label="ID" prop="bid" align="center" width="200px"/>
        <el-table-column label="图片" align="center" width="200px">
            <template #default="scope">
              <el-popover effect="light" trigger="hover" placement="top">
                <template #default>
                  <img :src="baseURL + scope.row.bimageurl" style="width: 320px; height: 220px;" alt="...">
                </template>
                <template #reference>
                  <div class="name-wrapper">
                    <el-tag size="medium"><i class="el-icon-picture-outline"/></el-tag>
                  </div>
                </template>
              </el-popover>
            </template>
          </el-table-column>
        <el-table-column label="状态" align="center" width="500px">
          <template #default="scope">
            <el-text class="mx-1" :type="scope.row.bstatus == 0 ? 'danger':'success'">{{scope.row.bstatus == 1 ? '启用':'禁用'}}</el-text>
          </template>
        </el-table-column>
        <el-table-column label="操作" fixed="right" align="center" width="200px">
          <template #default="scope">
            <el-button size="mini" :type="scope.row.bstatus == 1 ? 'danger':'success'" @click="changeStatusBtn(scope.row)" icon="el-icon-edit">{{scope.row.bstatus == 1 ? '禁用':'通过'}}</el-button>
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
      <el-dialog v-model="dialogFormVisible" title="新增商品">
        <el-form :model="goodInfoEdit" class="formBox">
          <el-form-item label="轮播图：" label-width="120px">
            <el-upload
              action=""
              class="avatar-uploader"
              :auto-upload="false"
              :show-file-list="false"
              :on-change="handleAvatarChange"
            >
              <img v-if="imageUrl" :src="imageUrl" class="avatar" />
              <i v-else class="avatar-uploader-icon el-icon-plus"></i>
            </el-upload>
          </el-form-item>
        </el-form>
        <template #footer>
          <span class="dialog-footer">
            <el-button @click="dialogHidden" icon="el-icon-close">取消</el-button>
            <el-button type="primary" @click="addBanner" icon="el-icon-success">确定</el-button>
          </span>
        </template>
      </el-dialog>
    </div>
</template>

<script>
import {request, req} from '../../util/request'

export default {
  name: 'BannerManage',
  data() {
    return {
      data: null,
      pageSize: 7,
      baseURL: 'http://localhost:8888',

      dialogFormVisible: false,
      imageUrl: "",
      imgFile: null
    }
  },
  methods: {
    dialogHidden() {
      this.dialogFormVisible = false;
    },
    AddBtn() {
      this.dialogFormVisible = true;
    },
    addBanner() {
      console.log(this.imgFile);
      let formData = new FormData()
      formData.append('imgFile', this.imgFile);
      if(confirm("确定添加吗？")) {
        req({
          url: '/banner/add',
          method: 'POST',
          headers: {
            'Content-Type': 'multipart/form-data'
          },
          data: formData
        })
        .then(response => {
          console.log(response.data);
          if(response.data.code == 200) {
            alert("添加成功！");
            this.dialogHidden();
            this.getData(this.pageNum, this.pageSize)
          }else{
            alert("添加失败！");
          }
        })
      } 
    },
    //file
    handleAvatarChange(file) {
      this.imgFile = file.raw;
      this.imageUrl = URL.createObjectURL(file.raw);
    },
    changeStatusBtn(cemment) {
      if(confirm("请确定"))
      request({
          method: 'POST',
          data: null,
          url: `/banner/change/${cemment.bid}`,
        })
        .then(res => {
          if(res.data.code == 200) {
            alert("更新成功！")
            this.getData(this.pageNum, this.pageSize);
          }
        })
      
    },
    getData(pageNum, pageSize) {
      const that = this;
      request({
        method: 'GET',
        url: `/banner/all/page/${pageNum}-${pageSize}`,
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
.avatar-uploader .avatar {
  width: 228px;
  height: 128px;
  display: block;
}
.avatar-uploader .el-upload {
  border: 1px dashed var(--el-border-color);
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
  transition: var(--el-transition-duration-fast);
}

.avatar-uploader .el-upload:hover {
  border-color: var(--el-color-primary);
}

.avatar-uploader-icon {
  font-size: 28px;
  color: #787b80;
  width: 128px;
  height: 128px;
  line-height: 128px;
  text-align: center;
  border: 1px solid #EEE;
}
</style>