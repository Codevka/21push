<template>
  <el-main>
    <el-page-header class="infoNu" @back="goBack" v-bind:content="cont"></el-page-header>
    <el-form
      :model="RegisterForm"
      ref="RegisterForm"
      :rules="rule"
      class="userform"
      label-width="80px"
    >
      <!--<el-form-item label="账号" prop="username">
        <el-input type="text" v-model="RegisterForm.username" placeholder="账号"></el-input>
      </el-form-item>-->
      <el-form-item label="昵称" prop="name">
        <el-input type="text" v-model="RegisterForm.name" placeholder="昵称"></el-input>
      </el-form-item>
      <el-form-item label="密码" prop="password">
        <el-input type="password" v-model="RegisterForm.password" placeholder="密码"></el-input>
      </el-form-item>

      <el-form-item label="确认密码" prop="confirmpassword">
        <el-input type="password" v-model="RegisterForm.confirmpassword" placeholder="确认密码"></el-input>
      </el-form-item>

      <el-form-item label="邮箱" prop="email">
        <el-input type="email" v-model="RegisterForm.email" placeholder="邮箱"></el-input>
      </el-form-item>

      <el-form-item label="手机" prop="tel">
        <el-input type="text" v-model.number="RegisterForm.tel" placeholder="手机号"></el-input>
      </el-form-item>
      <el-form-item label="地区" prop="city">
        <v-distpicker
          :province="RegisterForm.province"
          :city="RegisterForm.city"
          :area="RegisterForm.area"
          @province="onChangeProvince"
          @city="onChangeCity"
          @area="onChangeArea"
        ></v-distpicker>
      </el-form-item>
      <el-form-item>
        <el-button
          type="primary"
          class="submitBtn"
          round
          @click.native.prevent="submit"
          :loading="registering"
        >导入</el-button>
      </el-form-item>
    </el-form>
  </el-main>
</template>
<script>
import { RegisterUser } from "../main";
export default {
  data() {
    let confirmpasswordCheck = (rule, value, callback) => {
      console.log(value);
      if (value === "") {
        return callback(new Error("密码不能为空"));
      } else if (value !== this.RegisterForm.password) {
        return callback(new Error("密码不一致"));
      } else {
        return callback();
      }
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
    let cityCheck = (rule, value, callback) => {
      if (
        this.RegisterForm.province === "" ||
        value === "" ||
        this.RegisterForm.area === ""
      )
        return callback(new Error("省市地不能为空"));
      else callback();
    };
    return {
      cont: "导入",
      RegisterForm: {
        userType: "",
        username: "",
        password: "",
        confirmpassword: "",
        tel: "",
        email: "",
        name: "",
        province: "",
        city: "",
        area: ""
      },
      registering: false,
      rule: {
        /*username: [
          {
            required: true,
            max: 9,
            min: 9,
            message: "账号不可为空，长度为9位",
            trigger: "blur"
          }
        ],*/
        password: [
          {
            required: true,
            min: 6,
            message: "密码不能为空,长度最少6位",
            trigger: "blur"
          }
        ],
        confirmpassword: [
          {
            required: true,
            validator: confirmpasswordCheck,
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
        email: [
          {
            required: true,
            type: "email",
            message: "邮箱格式有误",
            trigger: "blur"
          }
        ],
        name: [
          {
            required: true,
            message: "昵称不能为空",
            trigger: "blur"
          }
        ],
        city: [
          {
            required: true,
            validator: cityCheck,
            trigger: "blur"
          }
        ]
      }
    };
  },
  methods: {
    submit() {
      this.$refs.RegisterForm.validate(valid => {
        if (valid) {
          this.registering = true;
          //console.log('submitting')
          let RegisterParams = {
            //username: this.RegisterForm.username,
            password: this.RegisterForm.password,
            tel: this.RegisterForm.tel,
            email: this.RegisterForm.email,
            name: this.RegisterForm.name,
            province: this.RegisterForm.province,
            city: this.RegisterForm.city,
            area: this.RegisterForm.area
          };
          RegisterUser(RegisterParams).then(res => {
            this.logining = false;
            console.log(RegisterParams);
            if (res.data.result == true) {
              this.$message({
                type: "success",
                message: "导入成功"
              });
              RegisterParams.username = res.data.username;
              sessionStorage.setItem(
                "userInfo",
                JSON.stringify(RegisterParams)
              );
              this.$notify({
                title: "该用户的账号是",
                message: res.data.username,
                duration: 0
              });
              this.$router.push("/login");
            } else {
              this.$message.error({
                message: "导入失败"
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
      this.RegisterForm.province = data.value;
    },
    onChangeCity(data) {
      this.RegisterForm.city = data.value;
    },
    onChangeArea(data) {
      this.RegisterForm.area = data.value;
    },
    tologin() {
      this.$router.push("/login");
    },
    goBack() {
      this.$router.push("user1/userManage");
    }
  },
  mounted() {
    this.RegisterForm.userType = this.$route.query.userType;
    if (this.RegisterForm.userType == "1") this.cont = "导入客服";
    else this.cont = "导入维修人员";
  }
};
</script>

<style>
.infoNu {
  margin: 20px auto;
  width: 1200px;
}
.userform {
  margin: 20px auto;
  width: 700px;
  background: #fff;
  box-shadow: 0 0 10px #b4bccc;
  padding: 30px 30px 30px 30px;
  border-radius: 25px;
}
.submitBtn {
  width: 40%;
}
.to {
  color: #fa5555;
  cursor: pointer;
}
</style>