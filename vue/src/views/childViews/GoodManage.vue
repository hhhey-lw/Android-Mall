<template>
    <div>
      <!--  功能条  -->
      <div class="funBtn">
        <div>
          <h3 style="display: inline-block;">商品类型：</h3>
          <el-cascader v-model="value" :options="options" @change="handleSelect" />
          <el-button type="success" @click="showAddGoodView" style="margin-left: 10px;margin-right: 10px;">新增商品</el-button>
        </div>
        <div class="searchBox">
          <el-input class="w-50 m-2"
            size="large" v-model="queryWord" placeholder="搜索...">
            <template #append>
              <el-button type="primary" icon="el-icon-search" @click="queryGoodBtn" />
            </template>
          </el-input>
        </div>
      </div>
      <!--  表格信息  -->
      <div class="Box">
        <!-- 展示信息的表格 -->
        <el-table :data="tableDataFormat" class="tableBox" stripe empty-text="请选择商品类型">
          <el-table-column label="ID" prop="gid" width="100px" align="center"/>
          <el-table-column label="商品名称" prop="gname" width="230px" align="center"/>
          <el-table-column label="商品图片" width="80px" align="center">
            <template #default="scope">
              <el-popover effect="light" trigger="hover" placement="top">
                <template #default>
                  <img :src="baseURL + scope.row.gimage" style="width: 120px; height: 120px;" alt="...">
                </template>
                <template #reference>
                  <div class="name-wrapper">
                    <el-tag size="medium"><i class="el-icon-picture-outline"/></el-tag>
                  </div>
                </template>
              </el-popover>
            </template>
          </el-table-column>
          <el-table-column label="商品价格" prop="gprimalPrice" width="100px"  align="center" />
          <el-table-column label="商品数量" prop="gnumber" width="100px" align="center"/>
          <el-table-column label="创建时间" prop="gcreateTime" width="210px" align="center"/>
          <el-table-column label="更新时间" prop="gupdateTime" width="210px" align="center"/>
          <el-table-column label="操作" fixed="right" align="center" width="250px">
            <template #default="scope">
              <el-button size="mini" @click="handleEdit(scope.row)" icon="el-icon-edit">编辑</el-button>
              <el-button size="mini" :type="scope.row.gstatus == 1 ? 'danger':'success'" @click="handleDelete(scope.row)" icon="el-icon-delete">{{scope.row.gstatus == 1 ? '下架':'上架'}}</el-button>
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
      <!-- 弹出层 -->
      <el-dialog v-model="dialogFormVisible" title="新增商品">
        <el-form :model="goodInfoEdit" class="formBox">
          <el-form-item label="商品图片：" :label-width="formLabelWidth">
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
          <el-form-item label="商品名称：" :label-width="formLabelWidth">
            <el-input v-model="goodInfo.gname" autocomplete="off" 
            class="inputWidth" maxlength="20" show-word-limit>
            </el-input>
          </el-form-item>
          <el-form-item label="商品价格：" :label-width="formLabelWidth">
            <el-input v-model="goodInfo.gprimalPrice" type="number" autocomplete="off" 
            class="inputWidth"  maxlength="20" show-word-limit>
            </el-input>
          </el-form-item>
          <el-form-item label="商品库存：" :label-width="formLabelWidth">
            <el-input v-model="goodInfo.gnumber" type="number" autocomplete="off" 
            class="inputWidth"  maxlength="16" show-word-limit>
            </el-input>
          </el-form-item>
          <el-form-item label="商品类型：" :label-width="formLabelWidth">
            <el-cascader :options="options" @change="handleSelectAddGood" />
          </el-form-item>
          <el-form-item label="商品详情：" :label-width="formLabelWidth">
            <el-input v-model="goodInfo.gdetail" autocomplete="off" 
            class="inputWidth"  maxlength="120" show-word-limit>
            </el-input>
          </el-form-item>
        </el-form>
        <template #footer>
          <span class="dialog-footer">
            <el-button @click="dialogHidden" icon="el-icon-close">取消</el-button>
            <el-button type="primary" @click="addGood" icon="el-icon-success">确定</el-button>
          </span>
        </template>
      </el-dialog>

      <!-- 弹出层 编辑 -->
      <el-dialog v-model="dialogFormVisibleEdit" title="修改商品信息">
        <el-form :model="goodInfoEdit" class="formBox">
          <el-form-item label="商品图片：" :label-width="formLabelWidth">
            <el-upload
              action=""
              class="avatar-uploader"
              :auto-upload="false"
              :show-file-list="false"
              :on-change="handleImgChange"
            >
              <img v-if="!editImgChange" :src="baseURL + goodInfoEdit.gimage" class="avatar" />
              <img v-else-if="editImgChange" :src="editGoodImg" class="avatar" />
              <i v-else class="avatar-uploader-icon el-icon-plus"></i>
            </el-upload>
            <el-button @click="uploadEdit()">上传</el-button>
          </el-form-item>
          <el-form-item label="商品名称：" :label-width="formLabelWidth">
            <el-input v-model="goodInfoEdit.gname" autocomplete="off" 
            class="inputWidth" maxlength="20" show-word-limit>
            </el-input>
          </el-form-item>
          <el-form-item label="商品价格：" :label-width="formLabelWidth">
            <el-input v-model="goodInfoEdit.gprimalPrice" type="number" autocomplete="off" 
            class="inputWidth"  maxlength="20" show-word-limit>
            </el-input>
          </el-form-item>
          <el-form-item label="商品库存：" :label-width="formLabelWidth">
            <el-input v-model="goodInfoEdit.gnumber" type="number" autocomplete="off" 
            class="inputWidth"  maxlength="16" show-word-limit>
            </el-input>
          </el-form-item>
          <el-form-item label="商品类型：" :label-width="formLabelWidth">
            <el-cascader v-model="typeValueEdit" :options="options" @change="handleSelectEditGood" />
          </el-form-item>
          <el-form-item label="商品详情：" :label-width="formLabelWidth">
            <el-input v-model="goodInfoEdit.gdetail" autocomplete="off" 
            class="inputWidth"  maxlength="120" show-word-limit>
            </el-input>
          </el-form-item>
        </el-form>
        <template #footer>
          <span class="dialog-footer">
            <el-button @click="dialogHidden" icon="el-icon-close">取消</el-button>
            <el-button type="primary" @click="editGood" icon="el-icon-success">确定</el-button>
          </span>
        </template>
      </el-dialog>
    </div>
</template>

<script>
import { ref } from 'vue'
import {req, request} from '../../util/request'

export default {
  name: 'GoodManage',
  data() {
    return {
      /* 控制弹出层是否显示 */
      dialogFormVisible: false,
      dialogFormVisibleEdit: false,
      /* 弹出层table的宽度 */
      formLabelWidth: '120px',
      goodInfo: {
        gid: null,
        gname: null,
        gimage: null,
        gdetail: null,
        gprimalPrice: null,
        gdiscountPrice: null,
        gsaleStatus: null,
        gsaleNumber: null,
        gnumber: null,
        gtypeId: null,
        gstatus: null,
        gcreate_time: null,
        gupdate_time: null
      },
      goodInfoEdit: {
        gid: null,
        gname: null,
        gimage: null,
        gdetail: null,
        gprimalPrice: null,
        gdiscountPrice: null,
        gsaleStatus: null,
        gsaleNumber: null,
        gnumber: null,
        gtypeId: null,
        gstatus: null,
        gcreate_time: null,
        gupdate_time: null
      },
      typeValueEdit: [1, 1],
      queryWord: null,
      // 类型选择
      value: ref([1, 1]),
      options: [],
      // 分页
      // req info 
      data: null,
      pageSize: 7,
      typeId: null,
      // 文件
      imageUrl: "",
      imgFile: null,
      baseURL: 'http://localhost:8888',
      // 更新
      editGoodImg: "",
      editImgFile: null,
      editImgChange: false
    }
  },
  computed: {
    caclImg(row) {
      console.log(row);
    },
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
      this.editImgChange = false;
      this.dialogFormVisible = false;
      this.dialogFormVisibleEdit = false;
    },
    handleSelect(value) {
      console.log(value) // [category index, type index] 下标
      this.typeId = value[1];
      this.getData(1, this.pageSize) // 初始化一下
      // console.log(this.options);
      // this.tableDataFormat = this.options[value[0]].children[value[1]].data;
    },
    handleSelectAddGood(value) {
      this.goodInfo.gtypeId = value[1]; // 0:category id, 1:typeId
    },
    handleSelectEditGood(value) {
      console.log(value);
      this.goodInfoEdit.gtypeId = value[1]; // 0:category id, 1:typeId
    },
    initClassInfo() {
      req({
        method: 'GET',
        data: null,
        url: '/good/getCategory'
      })
      .then(respnse => {
        let ops = [];
        console.log(respnse.data);
        for (const category of respnse.data) {
          // console.log("category",category);
          let categoryObj = new Object(); // value:id label:name children []
          categoryObj.value = category.gcategory_id;
          categoryObj.label = category.gcategory_name;
          categoryObj.children = [];
          for (const type of category.goodTypeList) {
            // console.log("type",type);
            let typeObj = new Object;
            typeObj.value = type.gtype_id;
            typeObj.label = type.gtype_name;
            categoryObj.children.push(typeObj);
          }
          ops.push(categoryObj); 
        }
        this.options = ops;
      })
    },
    queryGoodBtn() {
      console.log("Search click");
    },
    // 表格展示 俩功能
    handleEdit(good) {
      this.editImgChange = false;
      console.log(good);
      this.dialogFormVisibleEdit = true;
      this.goodInfoEdit = good;
      // 回显一下 类型
      for (const category of this.options) {
        for (const type of category.children) {
          if(good.gtypeId == type.value) {
            let t = [];
            t[0] = category.value;
            t[1] = type.value;
            this.typeValueEdit = t;
          }
        }
      }
      console.log(this.typeValueEdit);
    },
    handleDelete(good) {
      console.log("删除", good);
      if(confirm('确定删除吗?')) {
        req({
          method: 'POST',
          url: '/good/update/status',
          data: JSON.stringify(good),
          contentType: 'application/json'
        })
        .then(res => {
          if(res.data.code == 200) {
            alert("更新成功！");
            this.dialogHidden();
            this.getData(this.pageNum, this.pageSize)
          }else {
            alert("更新失败！");
          }
        })
      
      } 
    },
    // addGood btn 
    showAddGoodView() {
      this.editImgChange = false;
      this.dialogFormVisible = true;
    },
    addGood() {
      console.log("addGood");
      console.log(this.goodInfo);
      console.log(this.imgFile);
      let formData = new FormData()
      formData.append('imgFile', this.imgFile);
      const blob = new Blob([JSON.stringify(this.goodInfo)], {
        type: 'application/json',
      });
      formData.append('goodInfo', blob);
      if(confirm("确定添加吗？")) {
        req({
          url: '/good/add',
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
            for(let key in this.goodInfo){
              this.goodInfo[key]  = ''
            }
            this.imageUrl = "";
            this.dialogHidden();
            this.getData(this.pageNum, this.pageSize)
          }else{
            alert("添加失败！");
          }
        })
      }  
    },
    editGood() {
      console.log(this.goodInfoEdit);
      if(confirm('确定修改吗?')) {
        req({
          method: 'POST',
          url: '/good/update',
          data: JSON.stringify(this.goodInfoEdit),
          contentType: 'application/json'
        })
        .then(res => {
          if(res.data.code == 200) {
            alert("更新成功！");
            this.dialogHidden();
            this.getData(this.pageNum, this.pageSize)
          }else {
            alert("更新失败！");
          }
        })
      
      }  
    },
    pageChange(pageNum) {
      this.getData(pageNum, this.pageSize);
    },
    getData(pageNum, pageSize) {
      const that = this;
      request({
        method: 'GET',
        url: `/good/type/${this.typeId}/${pageNum}-${pageSize}`,
        data: null
      })
      .then(response => {
        console.log(response.data);
        that.data = response.data.data;
      })
    },
    //file
    handleAvatarChange(file) {
      this.imgFile = file.raw;
      this.imageUrl = URL.createObjectURL(file.raw);
    },
    handleImgChange(file) {
      this.editImgChange = true;
      this.editImgFile = file.raw;
      this.editGoodImg = URL.createObjectURL(file.raw);
    },
    // 更新商品图片
    uploadEdit() {
      if(this.editGoodImg == "") {
        alert("未更换图片");
        return;
      }
      let formData = new FormData()
      formData.append('imgFile', this.editImgFile);
      if(confirm("确定更改吗？")) {
        req({
          url: `/good/update/Img/${this.goodInfoEdit.gid}`,
          method: 'POST',
          headers: {
            'Content-Type': 'multipart/form-data'
          },
          data: formData
        })
        .then(response => {
          console.log(response.data);
          if(response.data.code == 200) {
            alert("更新成功！");
            this.dialogHidden();
            this.getData(this.pageNum, this.pageSize)
          }else{
            alert("更新失败！");
          }
        })
      }  
    }
  },
  mounted() {
    this.initClassInfo();
    this.typeId = 1;
    this.getData(1, this.pageSize);
  }
}
</script>

<style scoped>
.funBtn {
  margin-bottom: 8px;
  display: flex;
  justify-content: space-between;
}
.searchBox {
  display: inline-block;
}
.Box {
  display: flex;
  justify-content: start;
  flex-direction: column;
  align-items: center;
  height: 600px;
  background-color: white;
}
.tableBox {
  width: 100%;
}

/* file element plus  */
.avatar-uploader .avatar {
  width: 128px;
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