<!-- Actually Contract Manage Page -->
<template>
  <el-main class="infoRm">
    <p class="input">
      <el-form>
        <el-form-item>
          <el-button
            type="primary"
            @click.prevent.native="viewApplication"
            style="float: right"
          >获取未审核申请</el-button>
        </el-form-item>
        <el-form-item label-width="100" label="搜索订单：">
          <el-input style="width:400px;" v-model="searchUsername" placeholder="请输入租客账号">
            <el-button
              slot="append"
              icon="el-icon-search"
              circle
              @click.native.prevent="sContracts"
            ></el-button>
          </el-input>
        </el-form-item>
      </el-form>
    </p>
    <el-table border :data="contractData" style="width: 100%">
      <el-table-column type="index" min-width="6%" :resizable="false"></el-table-column>
      <el-table-column prop="contractId" label="订单编号" min-width="13%" :resizable="false"></el-table-column>
      <el-table-column prop="houseId" label="房源编号" min-width="13%" :resizable="false"></el-table-column>
      <el-table-column prop="rentTime" label="租房时间" min-width="13%" :resizable="false"></el-table-column>
      <el-table-column prop="orderGenerateTime" label="订单生成时间" min-width="13%" :resizable="false"></el-table-column>
      <el-table-column prop="contractDuration" label="订单持续时间" min-width="13%" :resizable="false"></el-table-column>
      <el-table-column prop="price" label="价格" min-width="8%" :resizable="false"></el-table-column>
      <el-table-column prop="contractStatus" label="订单状态" min-width="8%" :resizable="false"></el-table-column>
      <el-table-column fixed="right" label="操作" min-width="13%" :resizable="false">
        <template slot-scope="scope">
          <el-button @click.native.prevent="toContract(scope.$index)" size="small">详细信息</el-button>
        </template>
      </el-table-column>
    </el-table>
  </el-main>
</template>

<script>
import { getUserContract } from "../../main";
import { getApplication } from "../../main";
export default {
  data() {
    return {
      searchUsername: "",
      contractData: []
      /*
        contractData: [
          {
            contractId: "1",
            houseId: "",
            rentTime: "",
            contractTime: "",
            contractDuration: "",
            price: "",
            status: ""
          }
        ]
       */
    };
  },
  methods: {
    toContract(index) {
      console.log(this.contractData[index].contractId);
      this.$router.push({
        path: "/contract",
        query: {
          contractId: this.contractData[index].contractId,
          usertype: 1
        }
      });
    },
    sContracts() {
      let params = { username: this.searchUsername };
      getUserContract(params).then(res => {
        this.contractData = res.data;
        this.$message({
          type: "success",
          message: "搜索成功"
        });
      });
    },
    viewApplication() {
      getApplication().then(res => {
        this.contractData = res.data;
        this.$message({
          type: "success",
          message: "成功获取未审核申请"
        });
      });
    }
  }
};
</script>

<style>
.infoRm {
  margin: 40px auto;
  width: 1500px;
  background: #fff;
  box-shadow: 0 0 35px #b4bccc;
  padding: 30px 30px 30px 30px;
  border-radius: 10px;
}

.input {
  width: 500px;
  float: right;
}
</style>