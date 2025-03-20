<template>
    <div class="Box">

      <!--  功能条  -->
      <div class="funBox">
        <el-cascader v-model="value" :options="options" @change="handleSelect" />
      </div>
      
      <!-- 展示用户信息的表格 -->
      <el-table :data="tableDataFormat" class="tableBox">
        <el-table-column label="评论ID" prop="cid" align="center" width="100px"/>
        <el-table-column label="用户ID" prop="uid" align="center" width="100px"/>
        <el-table-column label="商品ID" prop="gid" align="center" width="100px"/>
        <el-table-column label="评论内容" prop="ccontent" align="center"/>
        <el-table-column label="评论时间" prop="ccreateTime" align="center" width="160px"/>
        <el-table-column label="操作" fixed="right" align="center" width="100px">
          <template #default="scope">
            <el-button size="mini" :type="scope.row.cstatus == 1 ? 'danger':'success'" @click="changeStatusBtn(scope.row)" icon="el-icon-edit">{{scope.row.cstatus == 1 ? '禁用':'通过'}}</el-button>
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
    </div>
</template>

<script>
import {request} from '../../util/request'

export default {
  name: 'CommentManage',
  data() {
    return {
      data: null,
      pageSize: 7,
      value: 'waitcheck',
      options: [{
        value: 'waitcheck',
        label: '待审核评论',
      }, {
        value: 'all',
        label: '全部评论',
      }]
    }
  },
  methods: {
    handleSelect(value) {
      console.log(value[0]) // [category index, type index] 下标
      if(value[0] == 'all')
        this.getData2(1, this.pageSize);
      else
        this.getData(1, this.pageSize);

      // console.log("hhh", this.value[0]);
      // this.gcategory_id = value[0];
      // // this.getData(1, this.pageSize) // 初始化一下
      // this.getData(1, this.pageSize);
      // console.log(this.gcategory_id);
      // this.tableDataFormat = this.options[value[0]].children[value[1]].data;
    },
    changeStatusBtn(cemment) {
      if(confirm("请确定"))
      request({
          method: 'POST',
          data: null,
          url: `/comment/check/${cemment.cid}`,
        })
        .then(res => {
          if(res.data.code == 200) {
            alert("更新成功！")
            if(this.value[0] == 'all')
              this.getData2(this.pageNum, this.pageSize);
            else
              this.getData(this.pageNum, this.pageSize);
          }
        })
      
    },
    getData(pageNum, pageSize) {
      const that = this;
      request({
        method: 'GET',
        url: `/comment/all/wait/${pageNum}-${pageSize}`,
        data: null
      })
      .then(response => {
        console.log(response.data);
        that.data = response.data.data;
      })
    },
    getData2(pageNum, pageSize) {
      const that = this;
      request({
        method: 'GET',
        url: `/comment/all/page/${pageNum}-${pageSize}`,
        data: null
      })
      .then(response => {
        console.log(response.data);
        that.data = response.data.data;
      })
    },
    pageChange(pageNum) {
      if(this.value[0] == 'all')
        this.getData2(pageNum, this.pageSize);
      else
        this.getData(pageNum, this.pageSize);
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
.funBox {
  margin: 8px;
  padding-left: 18px;
  display: flex;
  width: 100%;
  flex-direction: row;
  justify-items: start;
}
.Box {
  display: flex;
  flex-direction: column;
  justify-content: start;
  align-items: center;
  height: 650px;
  background-color: white;
}
</style>