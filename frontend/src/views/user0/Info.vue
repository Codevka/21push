<template>
  <el-container>
    <el-main class="info0">
      <h3>用户信息</h3>
      <p v-bind:username="username">账号: {{username}}</p>
      <p v-bind:password="password">密码: {{password}}</p>
      <p v-bind:tel="tel">手机: {{tel}}</p>
      <p v-bind:email="email">邮箱: {{email}}</p>
      <p v-bind:name="name">昵称: {{name}}</p>
      <p v-bind:province="province">省份: {{province}}</p>
      <p v-bind:city="city">城市: {{city}}</p>
      <p v-bind:area="area">地区: {{area}}</p>
      <el-button @click.native.prevent="change">修改个人信息</el-button>
      <el-form
        :model="changeUserInfoForm"
        ref="changeUserInfoForm"
        :rules="rule"
        class="changeform"
        label-width="80px"
        v-if="!dis"
      >
        <el-form-item label="密码" prop="password">
          <el-input v-model="changeUserInfoForm.password" placeholder="密码" :disabled="dis"></el-input>
        </el-form-item>
        <el-form-item label="确认密码" prop="confirmpassword">
          <el-input v-model="changeUserInfoForm.confirmpassword" placeholder="确认密码" :disabled="dis"></el-input>
        </el-form-item>
        <el-form-item label="手机" prop="tel">
          <el-input v-model.number="changeUserInfoForm.tel" placeholder="手机" :disabled="dis"></el-input>
        </el-form-item>
        <el-form-item label="邮箱" prop="email">
          <el-input v-model="changeUserInfoForm.email" placeholder="邮箱" :disabled="dis"></el-input>
        </el-form-item>
        <el-form-item label="昵称" prop="name">
          <el-input v-model="changeUserInfoForm.name" placeholder="昵称" :disabled="dis"></el-input>
        </el-form-item>
        <el-form-item label="地区" prop="city">
          <v-distpicker
            :province="changeUserInfoForm.province"
            :city="changeUserInfoForm.city"
            :area="changeUserInfoForm.area"
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
import { mapState } from "vuex";
import { changeUserInfo } from "../../main";
export default {
  components: {},
  data() {
    let confirmpasswordCheck = (rule, value, callback) => {
      //console.log(value);
      if (value === "") {
        return callback(new Error("密码不能为空"));
      } else if (value !== this.changeUserInfoForm.password) {
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
      //console.log(this.changeUserInfoForm.province);
      //console.log(value);
      //console.log(this.changeUserInfoForm.area);
      if (
        this.changeUserInfoForm.province == undefined ||
        value == undefined ||
        this.changeUserInfoForm.area == undefined
      )
        return callback(new Error("省市地不能为空"));
      else callback();
    };
    return {
      dis: true,
      submiting: false,
      rule: {
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
      },
      changeUserInfoForm: {
        userType: "",
        username: "",
        password: "",
        confirmpassword: "",
        tel: null,
        email: "",
        name: "",
        province: "",
        city: "",
        area: ""
      }
    };
  },
  computed: {
    ...mapState({
      usertype: state => state.userInfo.usertype,
      username: state => state.userInfo.username,
      password: state => state.userInfo.password,
      tel: state => state.userInfo.tel,
      email: state => state.userInfo.email,
      name: state => state.userInfo.name,
      province: state => state.userInfo.province,
      city: state => state.userInfo.city,
      area: state => state.userInfo.area
    })
  },
  methods: {
    change() {
      this.dis = this.dis == true ? false : true;
    },
    onChangeProvince(data) {
      this.changeUserInfoForm.province = data.value;
    },
    onChangeCity(data) {
      this.changeUserInfoForm.city = data.value;
    },
    onChangeArea(data) {
      this.changeUserInfoForm.area = data.value;
    },
    submit() {
      this.$refs.changeUserInfoForm.validate(valid => {
        if (valid) {
          this.submiting = true;
          //console.log('submitting')
          let changeParams = {
            userType: this.changeUserInfoForm.userType,
            username: this.changeUserInfoForm.username,
            password: this.changeUserInfoForm.password,
            tel: this.changeUserInfoForm.tel,
            email: this.changeUserInfoForm.email,
            name: this.changeUserInfoForm.name,
            city: this.changeUserInfoForm.city,
            area: this.changeUserInfoForm.area,
            province: this.changeUserInfoForm.province
          };
          changeUserInfo(changeParams).then(res => {
            if (res.data.result == true) {
              this.logining = false;
              this.$message({
                type: "success",
                message: "信息修改成功"
              });
              this.submiting = false;
              /*this.$message({
            type: "success",
            message: "信息修改成功"
          });*/
              //let userInfo = res.data
              //console.log(changeParams);
              sessionStorage.setItem("userInfo", JSON.stringify(changeParams));
              this.$store.dispatch("commitLogin");
              this.dis = true;
              this.$router.push("/user0/info");
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
    this.changeUserInfoForm.userType = this.$store.state.userInfo.userType;
    this.changeUserInfoForm.username = this.$store.state.userInfo.username;
    this.changeUserInfoForm.password = this.$store.state.userInfo.password;
    this.changeUserInfoForm.tel = parseInt(this.$store.state.userInfo.tel);
    this.changeUserInfoForm.email = this.$store.state.userInfo.email;
    this.changeUserInfoForm.name = this.$store.state.userInfo.name;
    this.changeUserInfoForm.province = this.$store.state.userInfo.province;
    this.changeUserInfoForm.city = this.$store.state.userInfo.city;
    this.changeUserInfoForm.area = this.$store.state.userInfo.area;
    //console.log(this.changeUserInfoForm);
  }
};
</script>

<style scoped>
.info0 {
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