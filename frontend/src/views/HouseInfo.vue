<template>
  <el-main class="infoHi">
    <el-page-header @back="goBack" content="房源详情"></el-page-header>
    <!-- <p v-for="(item,key,index) in houseInfo" :key="key">{{houseLabel[index]}}:{{item}}</p> -->
    <p>房源编号：{{houseInfo.houseId}}</p>
    <p>省份：{{houseInfo.province}}</p>
    <p>城市：{{houseInfo.city}}</p>
    <p>地区：{{houseInfo.area}}</p>
    <p>地址：{{houseInfo.address}}</p>
    <p>租房形式：{{houseInfo.rentType}}</p>
    <p>房间类型：{{houseInfo.houseType}}</p>
    <p>介绍：{{houseInfo.intro}}</p>
    <p>户主电话：{{houseInfo.tel}}</p>
    <p>价格：{{houseInfo.price}}</p>
    <p>房源状态：{{houseInfo.housestatus}}</p>
    <div class="image">
      <el-image v-for="url in pic" :key="url" :src="url" fit="scale-down" lazy></el-image>
    </div>
    <el-button @click.prevent.native="changeVisible=true" type="primary" plain round>修改房源信息</el-button>
    <el-button
      v-if="houseInfo.housestatus!='暂停出租'"
      @click.prevent.native="sRent"
      type="warning"
      plain
      round
    >暂停出租房源</el-button>
    <el-button
      v-if="houseInfo.housestatus=='暂停出租'"
      @click.prevent.native="rRent"
      type="success"
      plain
      round
    >恢复出租房源</el-button>
    <el-button @click.prevent.native="dRent" type="danger" plain round>删除房源</el-button>
    <el-dialog title="修改房源信息" :visible.sync="changeVisible">
      <el-form
        :model="changeHouseForm"
        ref="changeHouseForm"
        :rules="rule"
        class="changeform"
        label-width="150px"
      >
        <el-form-item label="地址" prop="address">
          <el-input v-model="changeHouseForm.address" placeholder="地址"></el-input>
        </el-form-item>
        <el-form-item label="地区" prop="city">
          <v-distpicker
            :province="changeHouseForm.province"
            :city="changeHouseForm.city"
            :area="changeHouseForm.area"
            @province="onChangeProvince"
            @city="onChangeCity"
            @area="onChangeArea"
          ></v-distpicker>
        </el-form-item>
        <el-form-item label="简介" prop="intro">
          <el-input v-model="changeHouseForm.intro" placeholder="简介"></el-input>
        </el-form-item>
        <el-form-item label="户主手机号" prop="tel">
          <el-input v-model.number="changeHouseForm.tel" placeholder="户主手机号"></el-input>
        </el-form-item>
        <el-form-item label="房源类型" prop="houseType">
          <el-radio-group v-model="changeHouseForm.houseType" size="small">
            <el-radio-button label="单人间"></el-radio-button>
            <el-radio-button label="双人间"></el-radio-button>
            <el-radio-button label="三人间"></el-radio-button>
            <el-radio-button label="四人间"></el-radio-button>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="租房形式" prop="rentType">
          <el-radio-group v-model="changeHouseForm.rentType" size="small">
            <el-radio-button label="短租"></el-radio-button>
            <el-radio-button label="长租"></el-radio-button>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="价格" prop="price">
          <el-input v-model.number="changeHouseForm.price" placeholder="每日或每月单价"></el-input>
        </el-form-item>
        <el-upload
          :multiple="true"
          list-type="picture-card"
          :on-remove="handleRemove"
          :action="actionPath"
          accept="image/jpeg, image/png, image/jpg"
          :before-upload="beforeUpload"
          :data="postData"
          :file-list="photoList"
          :on-success="handleSuccess"
          :on-exceed="handleExceed"
          :limit="limitNumber"
        >
          <i class="el-icon-plus"></i>
        </el-upload>
        <el-button :loading="submiting" @click.native.prevent="cHouseInfo">确认修改</el-button>
      </el-form>
    </el-dialog>
  </el-main>
</template>
<script>
import { getHouse } from "../main";
import { genToken } from "../genToken";
import { stopRent } from "../main";
import { restoreRent } from "../main";
import { deleteRent } from "../main";
import { changeHouseInfo } from "../main";
import random from "string-random";

export default {
  data() {
    let cityCheck = (rule, value, callback) => {
      //console.log(this.changeHouseForm.province);
      //console.log(value);
      //console.log(this.changeHouseForm.area);
      if (
        this.changeHouseForm.province == undefined ||
        value == undefined ||
        this.changeHouseForm.area == undefined
      )
        return callback(new Error("省市地不能为空"));
      else callback();
    };
    let telCheck = (rule, value, callback) => {
      if (value === "" || value == undefined) {
        return callback(new Error("手机号不能为空"));
      } else if (!Number.isInteger(value) || value.toString().length !== 11) {
        return callback(new Error("手机号格式错误"));
      } else {
        callback();
      }
    };
    let priceCheck = (rule, value, callback) => {
      if (value === "" || value == undefined) {
        return callback(new Error("价格不能为空"));
      } else if (!Number.isInteger(value) || parseInt(value) <= 0) {
        return callback(new Error("价格必须为大于零的正整数"));
      } else {
        callback();
      }
    };
    return {
      changeVisible: false,
      submiting: false,
      actionPath: "https://upload-z2.qiniup.com", // 华南
      photoUrl: "http://qbi3ylqqu.bkt.clouddn.com/", //! for test
      limitNumber: 3,
      postData: {
        token: "",
        key: ""
      },
      photoList: [],
      houseInfo: {
        houseId: "",
        province: "",
        city: "",
        area: "",
        address: "",
        rentType: "",
        houseType: "",
        intro: "",
        tel: "",
        price: "",
        housestatus: "暂停出租"
      },
      pic: [],
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
      changeHouseForm: {
        houseId: "",
        province: "",
        city: "",
        area: "",
        address: "",
        rentType: "",
        houseType: "",
        intro: "",
        tel: null,
        price: null,
        pic: [],
        housestatus: ""
      },
      rule: {
        address: [
          {
            required: true,
            message: "地址不能为空",
            trigger: "blur"
          }
        ],
        city: [
          {
            required: true,
            validator: cityCheck,
            trigger: "blur"
          }
        ],
        intro: [
          {
            required: true,
            message: "简介不能为空",
            trigger: "blur"
          }
        ],
        tel: [
          {
            required: true,
            validator: telCheck,
            trigger: "blur"
          }
        ],
        houseType: [
          {
            required: true,
            message: "房源类型不能为空",
            trigger: "blur"
          }
        ],
        rentType: [
          {
            required: true,
            message: "租房形式不能为空",
            trigger: "blur"
          }
        ],
        price: [
          {
            required: true,
            validator: priceCheck,
            trigger: "blur"
          }
        ]
      }
    };
  },
  methods: {
    goBack() {
      this.$router.push("/user1/houseManage");
    },
    onChangeProvince(data) {
      this.changeHouseForm.province = data.value;
    },
    onChangeCity(data) {
      this.changeHouseForm.city = data.value;
    },
    onChangeArea(data) {
      this.changeHouseForm.area = data.value;
    },
    sRent() {
      let params = { houseId: this.houseInfo.houseId };
      stopRent(params).then(res => {
        if (res.data.result == true) {
          this.$message({
            type: "success",
            message: "暂停出租房源操作成功"
          });
          this.goBack();
        } else {
          this.$message.error({
            message: "操作失败,请稍后再试"
          });
        }
      });
    },
    rRent() {
      let params = { houseId: this.houseInfo.houseId };
      restoreRent(params).then(res => {
        if (res.data.result == true) {
          this.$message({
            type: "success",
            message: "恢复出租房源操作成功"
          });
          this.goBack();
        } else {
          this.$message.error({
            message: "操作失败,请稍后再试"
          });
        }
      });
    },
    dRent() {
      let params = { houseId: this.houseInfo.houseId };
      deleteRent(params).then(res => {
        if (res.data.result == true) {
          this.$message({
            type: "success",
            message: "删除成功"
          });
          this.goBack();
        } else {
          this.$message.error({
            message: "删除失败,请稍后再试"
          });
        }
      });
    },
    cHouseInfo() {
      this.$refs.changeHouseForm.validate(valid => {
        if (valid) {
          this.changeHouseForm.tel = this.changeHouseForm.tel.toString();
          this.changeHouseForm.price = this.changeHouseForm.price.toString();
          changeHouseInfo(this.changeHouseForm).then(res => {
            if (res.data.result == true) {
              this.$message({
                type: "success",
                message: "房源信息修改成功"
              });
              this.goBack();
            } else {
              this.$message.error({
                message: "房源信息修改失败,请稍后再试"
              });
            }
          });
        } else {
          this.$message.error({
            message: "请检查输入信息"
          });
        }
      });
    },
    beforeUpload(file) {
      const checkFileType =
        file.type === "image/jpeg" ||
        file.type === "image/jpg" ||
        file.type === "image/png";
      const checkFileSize = file.size / 1024 / 1024 < 5;
      if (!checkFileType) {
        this.$message.error("上传图片必须是 jpeg/jpg/png 格式！");
      }
      if (!checkFileSize) {
        this.$message.error("上传图片大小不能超过 5MB！");
      }
      if (checkFileType && checkFileSize) this.postData.key = random(16);
      return checkFileType && checkFileSize;
    },
    handleSuccess(response) {
      this.changeHouseForm.pic.push(this.photoUrl + response.key);
      //console.log(this.photoUrl + response.key);
    },
    handleRemove(file) {
      Array.prototype.remove = function(val) {
        var index = this.indexOf(val);
        if (index > -1) {
          this.splice(index, 1);
        }
      };

      if (file.url) {
        let removePicture = file.url.substr(file.url.lastIndexOf("/"));
        this.changeHouseForm.pic.remove(removePicture);
        if (!this.changeHouseForm.pic.length) {
          this.hasFmt = false;
          this.$refs.image.validate();
        }
      }
      if (file.response.key) {
        this.changeHouseForm.pic.remove(this.photoUrl + file.response.key);
      }
    },
    handleExceed() {
      this.$message.warning("最多上传 3 张图片");
    }
  },
  mounted() {
    getHouse({ houseId: this.$route.query.houseId }).then(res => {
      this.houseInfo = res.data;
      delete this.houseInfo.pic;
      this.pic = res.data.pic;
      if (this.houseInfo.rentType == "短租") this.houseInfo.price += "元/日";
      else this.houseInfo.price += "元/月";
    });
  },
  created() {
    var token;
    var policy = {};
    // var bucketName = "21push";
    // var AK = "K96MCAU7eCnSWz4XUbxIBe9Q9PUm_gBHfacmsAEf";
    // var SK = "g0eagx-yjztmAo0iVi-Nj8QrsCRGrKhdGKIjpVr9";
    var bucketName = "push21";
    var AK = "slnMazKaSrCowN_nA5Y4i0QwFo62AaZKZQ8h2xOj";
    var SK = "wh8pr5uMd8_SNCxdGZvEh8-Hzy11swN6UaXwhlCF";
    var deadline = 1594028031; // 2020-07-06
    policy.scope = bucketName;
    policy.deadline = deadline;
    token = genToken(AK, SK, policy);
    this.postData.token = token;

    //console.log("token = " + token);
  }
};
</script>
<style>
.infoHi {
  margin: 40px 400px;
  width: 500px;
  background: #fff;
  box-shadow: 0 0 35px #b4bccc;
  padding: 30px 30px 30px 30px;
  border-radius: 30px;
}
.changeform {
  margin: auto auto;
  width: 750px;
  background: #fff;
  padding: 30px 30px 30px 30px;
}
.image {
  margin: auto auto;
  width: 500px;
}
</style>