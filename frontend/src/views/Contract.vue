<template>
  <el-main class="infoC">
    <el-page-header @back="goBack" content="订单详情"></el-page-header>
    <!--<p v-for="(item,key,index) in contractInfo" :key="key">{{contractLabel[index]}}:{{item}}</p>-->
    <p>订单编号: {{contractInfo.contractId}}</p>
    <p>账号: {{contractInfo.username}}</p>
    <p>房源编号: {{contractInfo.houseId}}</p>
    <p>房源地址: {{contractInfo.houseLocation}}</p>
    <p>房源类型: {{contractInfo.houseType}}</p>
    <p>户主手机号: {{contractInfo.ownerTel}}</p>
    <p>价格: {{contractInfo.price}}</p>
    <p>房源状态: {{contractInfo.housestatus}}</p>
    <p>订单状态: {{contractInfo.contractStatus}}</p>
    <p>租房形式: {{contractInfo.rentType}}</p>
    <p v-if="contractInfo.contractStatus=='未审核'&&usertype==0">请等待审核通过</p>
    <p v-if="contractInfo.contractStatus=='未缴费'&&usertype==0">请尽快进行线下缴费</p>
    <p v-if="contractInfo.contractStatus=='未签订合同'&&usertype==0">请尽快导出并打印合同，进行线下签订</p>
    <el-button
      v-if="contractInfo.contractStatus=='已签订合同'&&contractInfo.rentType=='长租'&&usertype==0"
      @click.native.prevent="lBack"
    >退租</el-button>
    <el-button
      v-if="contractInfo.contractStatus=='已签订合同'&&contractInfo.rentType=='长租'&&usertype==0"
      @click.native.prevent="dialogFormVisible = true"
    >续租</el-button>
    <el-button
      v-if="contractInfo.contractStatus=='未审核'&&usertype==1"
      @click.native.prevent="acceptApplication"
    >同意申请</el-button>
    <el-button
      v-if="contractInfo.contractStatus=='未审核'&&usertype==1"
      @click.native.prevent="denyApplication"
    >拒绝申请</el-button>
    <el-button
      :loading="exporting"
      v-if="contractInfo.contractStatus!='未审核'&&contractInfo.contractStatus!='已退租'&&contractInfo.rentType=='长租'"
      @click.native.prevent="eContract"
    >导出合同</el-button>
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
import { getContract, exportContract } from "../main";
import { leaseBack } from "../main";
import { leaseRenew } from "../main";
import { dealApplication } from "../main";
export default {
  data() {
    return {
      dialogFormVisible: false,
      exporting: false,
      isAccept: "",
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
        month: null
      },
      usertype: 0,
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
            message: "退租成功，请在线下门店退回租金"
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
      this.leaseRenewForm.month = this.leaseRenewForm.month.toString();
      //console.log(this.leaseRenewForm);
      leaseRenew(this.leaseRenewForm).then(res => {
        if (res.data.result == true) {
          this.$message({
            type: "success",
            message: "续租成功"
          });
        } else {
          this.$message.error({
            message: "续租失败，请稍后再试"
          });
        }
        this.$router.push("/user0/query");
      });
    },
    eContract() {
      this.exporting = true;
      exportContract(this.contractInfo.contractId).then(res => {
        if (res.data.result == true) {
          const iframe = document.createElement("iframe");
          iframe.style.display = "none";
          iframe.style.height = 0;
          iframe.src = res.data.url;
          // iframe.src = "https://nos.netease.com/edu-lesson-pdfsrc/807B2E75A4C170E1299ABEE261562DB5-1549243801094?Signature=efZ2xC5QRC6hE8tqwtMJs%2Fc5%2BZJw0aPWHYx4XYOYZDs%3D&Expires=1591964134&NOSAccessKeyId=7ba71f968e4340f1ab476ecb300190fa&download=Lesson05-02+Java%E5%A4%9A%E7%BA%BF%E7%A8%8B%E5%AE%9E%E7%8E%B0.pdf"; // (这个地址测试成功了)
          document.body.appendChild(iframe);
          setTimeout(() => {
            iframe.remove();
          }, 5 * 60 * 1000);
        } else {
          this.$message.error({
            message: "导出失败，请稍后再试"
          });
        }
        this.exporting = false;
      });
    },
    dApplication() {
      let params = {
        contractId: this.contractInfo.contractId,
        isAccept: this.isAccept
      };
      dealApplication(params).then(res => {
        if (res.data.result == true) {
          this.$message({
            type: "success",
            message: "处理成功！"
          });
        } else {
          this.$message.error({
            message: "处理失败，请稍后再试"
          });
        }
      });
    },
    acceptApplication() {
      this.isAccept = "true";
      this.dApplication();
    },
    denyApplication() {
      this.isAccept = "false";
      this.dApplication();
    }
  },
  mounted() {
    getContract(this.$route.query.contractId).then(res => {
      this.contractInfo = res.data;
    });
    this.usertype = this.$route.query.usertype;
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