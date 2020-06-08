<template>
  <el-main>
    <el-page-header class="infoNu" @back="goBack" content="导入房源"></el-page-header>
    <el-form :model="HouseForm" ref="HouseForm" :rules="rule" class="houseform" label-width="150px">
      <el-form-item label="地区" prop="city">
        <v-distpicker
          :province="HouseForm.province"
          :city="HouseForm.city"
          :area="HouseForm.area"
          @province="onChangeProvince"
          @city="onChangeCity"
          @area="onChangeArea"
        ></v-distpicker>
      </el-form-item>
      <el-form-item label="地址" prop="address">
        <el-input v-model="HouseForm.address" placeholder="地址"></el-input>
      </el-form-item>
      <el-form-item label="简介" prop="intro">
        <el-input v-model="HouseForm.intro" placeholder="简介"></el-input>
      </el-form-item>
      <el-form-item label="户主手机号" prop="tel">
        <el-input v-model.number="HouseForm.tel" placeholder="户主手机号"></el-input>
      </el-form-item>
      <el-form-item label="房源类型" prop="houseType">
        <el-radio-group v-model="HouseForm.houseType" size="small">
          <el-radio-button label="单人间"></el-radio-button>
          <el-radio-button label="双人间"></el-radio-button>
          <el-radio-button label="三人间"></el-radio-button>
          <el-radio-button label="四人间"></el-radio-button>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="租房形式" prop="rentType">
        <el-radio-group v-model="HouseForm.rentType" size="small">
          <el-radio-button label="短租"></el-radio-button>
          <el-radio-button label="长租"></el-radio-button>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="价格" prop="price">
        <el-input v-model.number="HouseForm.price" placeholder="每日或每月单价"></el-input>
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
      <br />
      <el-button :loading="submiting" @click.native.prevent="nHouse">确认</el-button>
    </el-form>
  </el-main>
</template>
<script>
//todo
import { newHouse } from "../main";
import { genToken } from "../genToken";
import random from "string-random";

export default {
  data() {
    let cityCheck = (rule, value, callback) => {
      console.log(this.HouseForm.province);
      console.log(value);
      console.log(this.HouseForm.area);
      if (
        this.HouseForm.province == undefined ||
        value == undefined ||
        this.HouseForm.area == undefined
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
      // actionPath: "https://upload.qiniup.com", // 华东
      // actionPath: "https://upload-z1.qiniup.com", // 华北
      actionPath: "https://upload-z2.qiniup.com", // 华南
      photoUrl: "http://qbi3ylqqu.bkt.clouddn.com/", //! for test
      limitNumber: 3,
      postData: {
        token: "",
        key: ""
      },
      photoList: [],
      HouseForm: {
        province: "",
        city: "",
        area: "",
        address: "",
        rentType: "",
        houseType: "",
        intro: "",
        tel: "",
        price: "",
        pic: []
      },
      registering: false,
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
    nHouse() {
      this.$refs.HouseForm.validate(valid => {
        if (valid) {
          newHouse(this.HouseForm).then(res => {
            if (res.data.result == true) {
              this.$message({
                type: "success",
                message: "导入成功"
              });
              this.$notify({
                title: "房源编号为",
                message: res.data.houseId,
                duration: 0
              });
              this.goBack();
            } else {
              this.$message.error({
                message: "导入失败,请稍后再试"
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
    onChangeProvince(data) {
      this.HouseForm.province = data.value;
    },
    onChangeCity(data) {
      this.HouseForm.city = data.value;
    },
    onChangeArea(data) {
      this.HouseForm.area = data.value;
    },
    goBack() {
      this.$router.push("user1/houseManage");
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
      this.HouseForm.pic.push(this.photoUrl + response.key);
      console.log(this.photoUrl + response.key);
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
        this.HouseForm.pic.remove(removePicture);
        if (!this.HouseForm.pic.length) {
          this.hasFmt = false;
          this.$refs.image.validate();
        }
      }
      if (file.response.key) {
        this.HouseForm.pic.remove(this.photoUrl + file.response.key);
      }
    },
    handleExceed() {
      this.$message.warning("最多上传 3 张图片");
    }
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

    console.log("token = " + token);
  }
};
</script>
<style>
.infoNu {
  margin: 20px auto;
  width: 1200px;
}
.houseform {
  margin: 20px auto;
  width: 750px;
  background: #fff;
  box-shadow: 0 0 10px #b4bccc;
  padding: 30px 30px 30px 30px;
  border-radius: 25px;
}
</style>