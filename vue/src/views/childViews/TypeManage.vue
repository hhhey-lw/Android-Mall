<template>
    <div class="Box">
      <div class="funBox">
        <el-cascader v-model="value" :options="options" @change="handleSelect" />
          <el-button type="success" @click="addTypeBtn" style="margin-left: 10px;margin-right: 10px;">新增小类</el-button>
      </div>

      <!-- 展示用户信息的表格 -->
      <el-table :data="tableDataFormat" class="tableBox">
        <el-table-column label="小类ID" prop="gtype_id" align="center"/>
        <el-table-column label="小类名称" prop="gtype_name" align="center"/>
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
        <el-form :model="typeVO" class="formBox">
          <el-form-item label="小类名称：" label-width="120px">
            <el-input v-model="typeVO.gtype_name" autocomplete="off" 
            class="inputWidth" maxlength="20" show-word-limit>
            </el-input>
          </el-form-item>
          <el-form-item label="所属大类：" label-width="120px">
            <el-cascader v-model="typeVO.gcategory_id" :options="options" @change="selectCategory" />
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
import { ref } from 'vue'
import {req, request} from '../../util/request'
import Qs from 'qs'

export default {
  name: 'TypeManage',
  data() {
    return {
      value: ref([1]),
      gcategory_id: 1,
      options: [],

      /* 控制弹出层是否显示 */
      dialogFormVisible: false,
      data: null,
      pageSize: 7,

      addOrUpdate: null,
      typeVO: {
        gcategory_id: null,
        gtype_id: null,
        gtype_name: null,
        status: null
      }
    }
  },
  methods: {
    initClassInfo() {
      req({
        method: 'GET',
        data: null,
        url: '/good/getCategory'
      })
      .then(respnse => {
        let ops = [];
        // console.log(respnse.data);
        for (const category of respnse.data) {
          // console.log("category",category);
          let categoryObj = new Object(); // value:id label:name children []
          categoryObj.value = category.gcategory_id;
          categoryObj.label = category.gcategory_name;
          // categoryObj.children = [];
          // for (const type of category.goodTypeList) {
          //   // console.log("type",type);
          //   let typeObj = new Object;
          //   typeObj.value = type.gtype_id;
          //   typeObj.label = type.gtype_name;
          //   categoryObj.children.push(typeObj);
          // }
          ops.push(categoryObj); 
        }
        this.options = ops;
      })
    },
    handleSelect(value) {
      console.log(value) // [category index, type index] 下标
      this.gcategory_id = value[0];
      // this.getData(1, this.pageSize) // 初始化一下
      this.getData(1, this.pageSize);
      console.log(this.gcategory_id);
      // this.tableDataFormat = this.options[value[0]].children[value[1]].data;
    },
    selectCategory(value) {
      this.typeVO.gcategory_id = value[0];
    },
    addTypeBtn() {
      this.dialogFormVisible = true;
      this.addOrUpdate = 'add';
    },
    editBtn(type) {
      console.log("编辑");
      this.dialogFormVisible = true;
      this.addOrUpdate = 'update';

      for (const key in type) {
        this.typeVO[key] = type[key];
      }
      // this.typeVO = type;
    },
    addOrUpdateBtnApi() {
      console.log(this.typeVO);
      if(this.typeVO.gtype_name == '' || this.typeVO.gtype_name == null) {
        alert("请输入正确的名称")
      }
      if(this.addOrUpdate == 'add') {
        if(this.typeVO.gcategory_id == null || this.typeVO.gtype_name == null || this.typeVO.gtype_name == '')
          alert("请选择完成");
        if(confirm("请确认"))
          req({
            method: 'POST',
            data: Qs.stringify({
              gcategory_id: this.typeVO.gcategory_id ? this.typeVO.gcategory_id : this.gcategory_id,
              gtype_name: this.typeVO.gtype_name
            }),
            url: '/type/add',
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
              gtype_id: this.typeVO.gtype_id,
              gcategory_id: this.typeVO.gcategory_id,
              newName: this.typeVO.gtype_name
            }),
            url: '/type/update',
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
    changeStatusBtn(category) {
      if(confirm("请确定"))
      req({
          method: 'POST',
          data: Qs.stringify({
            gtype_id: category.gtype_id
          }),
          url: '/type/status',
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
      for (const key in this.typeVO) {
        this.typeVO[key] = null;
      }
    },
    pageChange(pageNum) {
      this.getData(pageNum, this.pageSize);
    },
    getData(pageNum, pageSize) {
      const that = this;
      request({
        method: 'GET',
        url: `/type/all/${this.gcategory_id}/${pageNum}-${pageSize}`,
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
    this.initClassInfo();
    this.getData(1, this.pageSize);
  }
}
</script>

<style scoped>
.Box {
  display: flex;
  justify-content: start;
  flex-direction: column;
  align-items: center;
  height: 600px;
  background-color: white;
}

.funBox {
  display: flex;
  width: 100%;
  flex-direction: row;
  justify-items: start;
}
</style>