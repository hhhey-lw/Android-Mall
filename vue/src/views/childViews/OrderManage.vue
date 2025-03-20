<template>
    <div class="Box">
       <!-- 展示用户信息的表格 -->
      <el-table :data="tableDataFormat" class="tableBox">
        <el-table-column label="订单ID" prop="oid" width="120px"/>
        <el-table-column label="用户ID" prop="uid" width="120px"/>
        <el-table-column label="地址ID" prop="addrId" width="120px" />
        <el-table-column label="订单状态" prop="ostatus" :formatter="formatStatus" width="120px" />
        <el-table-column label="订单号" prop="orderIndex" />
        <el-table-column label="快递号" prop="freightIndex" width="120px" />
        <el-table-column label="总价" prop="total" width="160px" />
        <el-table-column label="创建时间" prop="createTime" />
        <el-table-column label="操作" fixed="right" align="center">
          <template #default="scope">
            <el-button size="mini" type="success" @click="orderDetail(scope.row)" icon="el-icon-s-order">详情</el-button>
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

      <!-- 弹出层 订单详情 -->
      <el-dialog v-model="dialogFormVisibleDetail" title="订单详情">
        <div>
          <!-- image  -->
          <h3>购买商品：</h3>
          <ul class="goodBox">
            <li v-for="good in orderGoodList" :key="good.gid" class="liBox">
              <el-image style="width: 100px; height: 100px" v-if="good" :src="baseURL + good.gimage" fit="cover" />
              <span>{{ good.gname }}</span>
              <span>数量：{{ good.purchaseNumber }}</span>
              <span>单价：{{ good.purchasePrice }}</span>
            </li>
          </ul>
          <h3 class="marginT">订单总价：￥{{ priceTotal }}</h3>
          <h3 class="marginT">收货人员：{{ addressUserInfo }}</h3>
          <h3 class="marginT">收货地址：{{ addressInfo }}</h3>
        </div>
        <template #footer>
          <span class="dialog-footer">
            <el-button @click="dialogHidden" icon="el-icon-close">确定</el-button>
          </span>
        </template>
      </el-dialog>
    </div>
</template>

<script>
import {req, request} from '../../util/request'
import Qs from 'qs'

export default {
  name: 'OrderManage',
  data() {
    return {
      baseURL: 'http://localhost:8888',
      /* 控制弹出层是否显示 */
      dialogFormVisibleDetail: false,
      /* 弹出层table的宽度 */
      formLabelWidth: '120px',
      data: null,
      pageSize: 7,

      orderDetailVO: {
        good: [],
        item: [],
        order: null
      },
      orderDetailAddress: null
    }
  },
  computed: {
    priceTotal() {
      if(this.orderDetailVO.order == null)
        return '0.0';
      else
        return this.orderDetailVO.order.priceTotal
    },
    addressInfo() {
      if(this.orderDetailAddress == null)
        return '';
      else {
        return this.orderDetailAddress.addrProvince + this.orderDetailAddress.addrCity + this.orderDetailAddress.addrDistrict + this.orderDetailAddress.addrDetail
      }
    },
    addressUserInfo() {
      if(this.orderDetailAddress == null)
        return '';
      else {
        return this.orderDetailAddress.username + "   " + this.orderDetailAddress.phone
      }
    },
    orderGoodList() {
      if(this.orderDetailVO.good.length == 0)
      return null;
      for (let i = 0; i < this.orderDetailVO.good.length; i++) { 
        this.orderDetailVO.good[i].purchaseNumber = this.orderDetailVO.item[i].gpurchaseNumber;
        this.orderDetailVO.good[i].purchasePrice = this.orderDetailVO.item[i].gpurchasePrice;
      }
      return this.orderDetailVO.good;
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
    formatStatus(order) {
      const status = order.ostatus;
      let res;
      switch(status) {
        case 1 : {
          res = '待付款';
          break;
        }
        case 2 : {
          res = '待发货';
          break;
        }  
        case 3 : {
          res = '待收货';
          break;
        } 
        case 4 : {
          res = '待评价';
          break;
        } 
        default : {
          res = "已完成";
        }
      }
      return res;
    },
    dialogHidden() {
      this.dialogFormVisible = false;
      this.dialogFormVisibleDetail = false;
    },
    pageChange(pageNum) {
      this.getData(pageNum, this.pageSize);
    },
    getData(pageNum, pageSize) {
      const that = this;
      request({
        method: 'GET',
        url: `/order/all/${pageNum}-${pageSize}`,
        data: null
      })
      .then(response => {
        console.log(response.data);
        that.data = response.data.data;
      })
    },
    orderDetail(order) {
      this.dialogFormVisibleDetail = true;
      console.log(order, "点击详情");
      req({
        method: 'POST',
        url: '/order/info',
        data: Qs.stringify({
          'oid': Number(order.oid)
        }),
        headers: {
          'Content-Type': 'application/x-www-form-urlencoded'
        }
      })
      .then(res => {
        console.log(res.data);
        this.orderDetailVO = res.data.data;
      });
      request({
        method: 'GET',
        url: `/addr/find/${order.addrId}`,
        data: null
      }).then(res => {
        console.log("地址", res.data);
        this.orderDetailAddress = res.data.data;
      })
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
.liBox {
  width: 120px;
  display: inline-flex;
  flex-direction: column;
  align-items: center;
  justify-content: space-around;
  list-style-type: none;
  margin: 10px;
  padding-top: 6px;
  padding-bottom: 6px;
  border: 1px solid #e2e2e2;
}
.goodBox {
  display: flex;
}
.marginT {
  margin-top: 8px;
}
</style>