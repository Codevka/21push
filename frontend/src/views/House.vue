<template>
  <el-main class="info">
    <el-page-header @back="goBack" content="房源详情"></el-page-header>
    <p v-for="(item,key,index) in houseInfo" :key="key">{{houseLabel[index]}}:{{item}}</p>
    <el-button @click.native.prevent="dialogFormVisible = true">租房</el-button>
    <el-dialog title="租房时间" :visible.sync="dialogFormVisible">
      <el-form v-model="rentHouseForm">
        <el-form-item label="月数" label-width="50px" v-if="houseInfo.rentType=='长租'">
          <el-input-number v-model="rentHouseForm.rentDuration" :min="1"></el-input-number>
        </el-form-item>
        <el-form-item label="天数" label-width="50px" v-if="houseInfo.rentType=='短租'">
          <el-input-number v-model="rentHouseForm.rentDuration" :min="1"></el-input-number>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click.native.prevent="rHouse">确定</el-button>
      </div>
    </el-dialog>
  </el-main>
</template>
<script>
import { getHouse } from "../main";
import { rentHouse } from "../main";
export default {
  data() {
    return {
      dialogFormVisible: false,
      houseInfo: {
        houseId: "",
        area: "",
        address: "",
        rentType: "短租",
        houseType: "",
        intro: "",
        tel: "",
        price: "",
        housestatus: ""
      },
      houseLabel: [
        "房源编号",
        "地区",
        "地址",
        "租房形式",
        "房间类型",
        "介绍",
        "户主电话",
        "价格",
        "房源状态"
      ],
      rentHouseForm: {
        houseId: "",
        username: "",
        rentType: "",
        rentDuration: ""
      }
    };
  },
  methods: {
    goBack() {
      this.$router.push("/user0/rent");
    },
    rHouse() {
      this.rentHouseForm.username = this.$store.state.userInfo.username;
      this.rentHouseForm.rentType = this.houseInfo.rentType;
      this.rentHouseForm.houseId = this.houseInfo.houseId;
      rentHouse(this.rentHouseForm).then(res => {
        console.log(res);
        if (res.data.result == true)
          this.$message({
            type: "success",
            message: "订单提交成功"
          });
        this.methods.goBack();
      });
    }
  },
  mounted() {
    getHouse({ houseId: this.route.query.houseId }).then(res => {
      this.houseInfo = res.data;
    });
  }
};
</script>
<style>
.info {
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