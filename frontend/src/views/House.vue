<template>
  <el-main class="infoH">
    <el-page-header @back="goBack" content="房源详情"></el-page-header>
    <!--<p v-for="(item,key,index) in houseInfo" :key="key">{{houseLabel[index]}}:{{item}}</p>-->
    <!-- <p>房源编号: {{houseInfo.houseId}}</p>
    <p>省份: {{houseInfo.province}}</p>
    <p>城市: {{houseInfo.city}}</p>
    <p>地区: {{houseInfo.area}}</p>
    <p>地址: {{houseInfo.address}}</p>
    <p>租房形式: {{houseInfo.rentType}}</p>
    <p>房间类型: {{houseInfo.houseType}}</p>
    <p>介绍: {{houseInfo.intro}}</p>
    <p>户主电话: {{houseInfo.tel}}</p>
    <p>价格: {{houseInfo.price}}</p>
    <p>房源状态: {{houseInfo.housestatus}}</p>-->
    <table border="0" cellspacing="20" style="margin: 0 auto;">
      <tr>
        <td>房源编号：</td>
        <td>{{houseInfo.houseId}}</td>
      </tr>
      <tr>
        <td>省份：</td>
        <td>{{houseInfo.province}}</td>
      </tr>
      <tr>
        <td>城市：</td>
        <td>{{houseInfo.city}}</td>
      </tr>
      <tr>
        <td>地区：</td>
        <td>{{houseInfo.area}}</td>
      </tr>
      <tr>
        <td>租房形式：</td>
        <td>{{houseInfo.rentType}}</td>
      </tr>
      <tr>
        <td>房间类型：</td>
        <td>{{houseInfo.houseType}}</td>
      </tr>
      <tr>
        <td>介绍：</td>
        <td>{{houseInfo.intro}}</td>
      </tr>
      <tr>
        <td>户主电话：</td>
        <td>{{houseInfo.tel}}</td>
      </tr>
      <tr>
        <td>价格：</td>
        <td>{{houseInfo.price}}</td>
      </tr>
      <tr>
        <td>房源状态：</td>
        <td>{{houseInfo.housestatus}}</td>
      </tr>
    </table>
    <div class="image">
      <!-- <el-image v-for="url in pic" :key="url" :src="url" fit="scale-down" width="100" height="100" lazy></el-image> -->
      <el-carousel v-if="pic!=[]" :interval="5000" arrow="always" :width="800">
        <el-carousel-item v-for="item in pic" :key="item.id">
          <el-row>
            <el-col>
              <el-image :src="item" fit="scale-down" />
            </el-col>
          </el-row>
        </el-carousel-item>
      </el-carousel>
    </div>
    <el-button @click.native.prevent="dialogFormVisible = true">租房</el-button>
    <el-dialog title="租房时间" :visible.sync="dialogFormVisible">
      <el-form v-model="rentHouseForm">
        <el-form-item label="租房开始时间" label-width="100px">
          <el-date-picker
            v-model="rentHouseForm.rentTime"
            type="date"
            placeholder="选择租房开始时间"
            value-format="yyyy-MM-dd"
            v-if="houseInfo.rentType=='短租'"
          ></el-date-picker>
          <el-date-picker
            v-model="rentHouseForm.rentTime"
            type="month"
            placeholder="选择租房开始时间"
            value-format="yyyy-MM-01"
            v-if="houseInfo.rentType=='长租'"
          ></el-date-picker>
          <p style="font-size:10px" v-if="houseInfo.rentType=='长租'">(长租的租房开始时间为每月一号)</p>
        </el-form-item>
        <el-form-item label="月数" label-width="100px" v-if="houseInfo.rentType=='长租'">
          <el-input-number v-model.number="rentHouseForm.rentDuration" :min="1"></el-input-number>
        </el-form-item>
        <el-form-item label="天数" label-width="100px" v-if="houseInfo.rentType=='短租'">
          <el-input-number v-model.number="rentHouseForm.rentDuration" :min="1"></el-input-number>
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
        province: "",
        city: "",
        area: "",
        address: "",
        rentType: "长租",
        houseType: "",
        intro: "",
        tel: "",
        price: "",
        housestatus: ""
      },
      pic: [
        // "http://qbi3ylqqu.bkt.clouddn.com/6eKVd3ZUonkS5bd2",
        // "http://qbi3ylqqu.bkt.clouddn.com/7hXAE3tAQSLXyYa0"
      ],
      houseLabel: [
        "房源编号",
        "省份",
        "城市",
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
        rentTime: null,
        rentDuration: null
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
      if (this.rentHouseForm.rentTime == null) {
        this.$message({
          type: "warning",
          message: "请选择租房开始时间"
        });
      } else if (
        new Date().getTime() - new Date(this.rentHouseForm.rentTime).getTime() >
        0
      ) {
        this.$message({
          type: "warning",
          message: "租房开始时间不能早于今天"
        });
      } else {
        this.rentHouseForm.rentDuration = this.rentHouseForm.rentDuration.toString();
        rentHouse(this.rentHouseForm).then(res => {
          //console.log(res);
          if (res.data.result == true)
            this.$message({
              type: "success",
              message: "订单提交成功"
            });
          this.goBack();
        });
      }
    }
  },
  mounted() {
    getHouse({ houseId: this.$route.query.houseId }).then(res => {
      this.houseInfo = res.data;
      this.pic = res.data.pic;
      if (this.houseInfo.rentType == "短租") this.houseInfo.price += "元/日";
      else this.houseInfo.price += "元/月";
    });
  }
};
</script>
<style>
.infoH {
  margin: 40px 40px;
  width: 1500px;
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
.image {
  margin: auto auto;
  width: 1200px;
}
</style>