<template>
  <el-main class="infoC">
    <el-page-header @back="goBack" content="订单详情"></el-page-header>
    <p v-for="(item,key,index) in contractInfo" :key="key">{{contractLabel[index]}}:{{item}}</p>
    <p v-if="contractInfo.contractStatus=='未审核'">请等待审核通过</p>
    <p v-if="contractInfo.contractStatus=='未缴费'">请尽快进行线下缴费</p>
    <el-button
      v-if="contractInfo.contractStatus=='已缴费'&&contractInfo.rentType=='长租'"
      @click.native.prevent="lBack"
    >退租</el-button>
    <el-button
      v-if="contractInfo.contractStatus=='已缴费'&&contractInfo.rentType=='长租'"
      @click.native.prevent="dialogFormVisible = true"
    >续租</el-button>
    <el-dialog title="续租时间" :visible.sync="dialogFormVisible">
      <el-form v-model="leaseRenewForm">
        <el-form-item label="月数" label-width="50px">
          <el-input-number v-model="leaseRenewForm.month" :min="1"></el-input-number>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click.native.prevent="lRenew">确定</el-button>
      </div>
    </el-dialog>
  </el-main>
</template>
<script>
import { getContract } from "../main";
import { leaseBack } from "../main";
import { leaseRenew } from "../main";
export default {
  data() {
    return {
      dialogFormVisible: false,
      contractInfo: {
        contractId: "",
        username: "",
        houseId: "",
        houseLocation: "",
        houseType: "",
        ownerTel: "",
        price: "",
        housestatus: "",
        contractStatus: "已缴费",
        rentType: "长租"
      },
      leaseBackForm: {
        contractId: ""
      },
      leaseRenewForm: {
        contractId: "",
        month: ""
      },
      //暂定订单状态: 已缴费 未缴费
      //租房形式: 短租 长租

      /*contractLabel:{
                contractId:'订单编号',
                username:'账号',
                houseId:'房源编号',
                houseLocation:'房源地址',
                houseType:'房源类型',
                ownerTel:'户主手机号',
                price:'价格',
                housestatus:'房源状态',
                contractStatus:'订单状态'
            }*/
      contractLabel: [
        "订单编号",
        "账号",
        "房源编号",
        "房源地址",
        "房源类型",
        "户主手机号",
        "价格",
        "房源状态",
        "订单状态",
        "租房形式"
      ]
    };
  },
  methods: {
    goBack() {
      this.$router.push("/user0/query");
    },
    lBack() {
      let params = { contractId: this.contractInfo.contractId };
      leaseBack(params).then(res => {
        if (res.data.result == true) {
          this.$message({
            type: "success",
            message: "退租成功"
          });
        } else {
          this.$message.error({
            message: "退租失败，请稍后再试"
          });
        }
        this.$router.push("/user0/query");
      });
    },
    lRenew() {
      this.leaseRenewForm.contractId = this.contractInfo.contractId;
      console.log(this.leaseRenewForm);
      leaseRenew(this.leaseRenewForm).then(res => {
        if (res.data.result == true) {
          this.$message({
            type: "success",
            message: "续租成功,请查看邮箱，尽快完成缴费"
          });
        } else {
          this.$message.error({
            message: "续租失败，请稍后再试"
          });
        }
        this.$router.push("/user0/query");
      });
    }
  },
  mounted() {
    getContract(this.$route.query.contractId).then(res => {
      this.contractInfo = res.data;
    });
  }
};
</script>
<style>
.infoC {
  margin: 40px 400px;
  width: 500px;
  background: #fff;
  box-shadow: 0 0 35px #b4bccc;
  padding: 30px 30px 30px 30px;
  border-radius: 30px;
}
.el-dialog {
  margin: 40px auto;
  width: 500px;
  background: #fff;
  padding: 30px 30px 30px 30px;
  border-radius: 30px;
}
</style>