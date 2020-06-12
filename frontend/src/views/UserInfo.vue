<template>
  <el-container>
    <el-main class="info">
      <el-page-header @back="goBack" content="用户详情"></el-page-header>
      <p v-bind:username="userInfo.username">账号: {{username}}</p>
      <!--<p v-bind:password="userInfo.password">密码: {{password}}</p>-->
      <p v-bind:tel="userInfo.tel">手机: {{tel}}</p>
      <p v-bind:email="userInfo.email">邮箱: {{email}}</p>
      <p v-bind:name="userInfo.name">昵称: {{name}}</p>
      <p v-bind:province="userInfo.province">省份: {{province}}</p>
      <p v-bind:city="userInfo.city">城市: {{city}}</p>
      <p v-bind:area="userInfo.area">地区: {{area}}</p>
      <el-button @click.native.prevent="change">修改个人信息</el-button>
      <el-form
        :model="manageUserInfoForm"
        ref="manageUserInfoForm"
        :rules="rule"
        class="changeform"
        label-width="80px"
        v-if="!dis"
      >
        <!--<el-form-item label="密码" prop="password">
          <el-input v-model="manageUserInfoForm.password" placeholder="密码" :disabled="dis"></el-input>
        </el-form-item>
        <el-form-item label="确认密码" prop="confirmpassword">
          <el-input v-model="manageUserInfoForm.confirmpassword" placeholder="确认密码" :disabled="dis"></el-input>
        </el-form-item>-->
        <el-form-item label="手机" prop="tel">
          <el-input v-model.number="manageUserInfoForm.tel" placeholder="手机" :disabled="dis"></el-input>
        </el-form-item>
        <el-form-item label="邮箱" prop="email">
          <el-input v-model="manageUserInfoForm.email" placeholder="邮箱" :disabled="dis"></el-input>
        </el-form-item>
        <el-form-item label="昵称" prop="name">
          <el-input v-model="manageUserInfoForm.name" placeholder="昵称" :disabled="dis"></el-input>
        </el-form-item>
        <el-form-item label="地区" prop="city">
          <v-distpicker
            :province="manageUserInfoForm.province"
            :city="manageUserInfoForm.city"
            :area="manageUserInfoForm.area"
            @province="onChangeProvince"
            @city="onChangeCity"
            @area="onChangeArea"
          ></v-distpicker>
        </el-form-item>
        <el-button :loading="submiting" @click.native.prevent="submit">保存</el-button>
      </el-form>
    </el-main>
  </el-container>
</template>

<script>
import { manageUserInfo } from "../main";
import { getUser } from "../main";
export default {
  components: {},
  data() {
    /*let confirmpasswordCheck = (rule, value, callback) => {
      //console.log(value);
      if (value === "") {
        return callback(new Error("密码不能为空"));
      } else if (value !== this.manageUserInfoForm.password) {
        return callback(new Error("密码不一致"));
      } else {
        return callback();
      }
    };*/
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
      //console.log(this.manageUserInfoForm.province);
      //console.log(value);
      //console.log(this.manageUserInfoForm.area);
      if (
        this.manageUserInfoForm.province == undefined ||
        value == undefined ||
        this.manageUserInfoForm.area == undefined
      )
        return callback(new Error("省市地不能为空"));
      else callback();
    };
    return {
      dis: true,
      submiting: false,
      userInfo: {
        name: "",
        username: "",
        //password: "",
        email: "",
        tel: "",
        province: "",
        city: "",
        area: ""
      },
      rule: {
        /*password: [
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
        ],*/
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
      },
      manageUserInfoForm: {
        username: "",
        tel: "",
        email: "",
        name: "",
        province: "",
        city: "",
        area: ""
      }
    };
  },
  methods: {
    goBack() {
      this.$router.push("/user1/userManage");
    },
    change() {
      this.dis = this.dis == true ? false : true;
    },
    onChangeProvince(data) {
      this.manageUserInfoForm.province = data.value;
    },
    onChangeCity(data) {
      this.manageUserInfoForm.city = data.value;
    },
    onChangeArea(data) {
      this.manageUserInfoForm.area = data.value;
    },
    submit() {
      this.$refs.manageUserInfoForm.validate(valid => {
        if (valid) {
          this.submiting = true;
          let changeParams = {
            username: this.manageUserInfoForm.username,
            tel: this.manageUserInfoForm.tel,
            email: this.manageUserInfoForm.email,
            name: this.manageUserInfoForm.name,
            city: this.manageUserInfoForm.city,
            area: this.manageUserInfoForm.area,
            province: this.manageUserInfoForm.province
          };
          manageUserInfo(this.manageUserInfoForm).then(res => {
            if (res.data.result == true) {
              this.logining = false;
              this.$message({
                type: "success",
                message: "信息修改成功"
              });
              this.submiting = false;
              //console.log(changeParams);
              sessionStorage.setItem("userInfo", JSON.stringify(changeParams));
              this.$store.dispatch("commitLogin");
              this.dis = true;
              this.goBack();
            } else {
              this.$message.error({
                message: "信息修改失败,请稍后再试"
              });
            }
          });
        } else {
          this.$message.error({
            message: "请检查输入信息"
          });
        }
      });
    }
  },
  mounted() {
    getUser({ username: this.$route.query.username }).then(res => {
      this.userInfo = res.data;
    });
  }
};
</script>

<style scoped>
.info {
  margin: auto 200px;
  width: 600px;
  background: #fff;
  box-shadow: 0 0 35px #b4bccc;
  padding: 30px 30px 30px 30px;
  border-radius: 30px;
}
.changeform {
  margin: auto auto;
  width: 700px;
  background: #fff;
  padding: 30px 30px 30px 30px;
}
</style>