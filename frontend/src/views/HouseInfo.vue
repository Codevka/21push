<template>
  <el-main class="info">
    <el-page-header @back="goBack" content="房源详情"></el-page-header>
    <p v-for="(item,key,index) in houseInfo" :key="key">{{houseLabel[index]}}:{{item}}</p>
    <el-button @click.prevent.native="changeVisible=true"></el-button>
    <el-button @click.prevent.native=""></el-button>
  </el-main>
</template>
<script>
//todo: 房源信息修改 房源删除
import { getHouse } from "../main";
import { changeHouse } from "../main";
export default {
  data() {
    return {
      changeVisible:false,
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
        housestatus: "暂停出租"
      },
      pic:[],
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
      changeHouseForm: {
        houseId: "",
        username: "",
        rentType: "",
        rentDuration: ""
      }
    };
  },
  methods: {
    goBack() {
      this.$router.push("/user1/housemanage");
    }
  },
  mounted() {
    getHouse({ houseId: this.route.query.houseId }).then(res => {
      this.houseInfo = res.data;
      delete this.houseInfo.pic;
      this.pic = res.data.pic;
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