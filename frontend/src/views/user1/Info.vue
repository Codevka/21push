<template>
  <el-container>
    <el-main class="info1">
      <h3>用户信息</h3>
      <p>账号: {{userInfo.username}}</p>
      <p>手机: {{userInfo.tel}}</p>
      <p>邮箱: {{userInfo.email}}</p>
      <p>昵称: {{userInfo.name}}</p>
      <p>省份: {{userInfo.province}}</p>
      <p>城市: {{userInfo.city}}</p>
      <p>地区: {{userInfo.area}}</p>
      <el-button @click.native.prevent="dis0=true">修改个人信息</el-button>
      <el-button @click.native.prevent="dis1=true">修改密码</el-button>
      <el-dialog :visible.sync="dis0" title="修改个人信息">
        <el-form
          :model="changeUserInfoForm"
          ref="changeUserInfoForm"
          :rules="rule0"
          label-width="80px"
        >
          <el-form-item label="手机" prop="tel">
            <el-input v-model.number="changeUserInfoForm.tel" placeholder="手机"></el-input>
          </el-form-item>
          <el-form-item label="邮箱" prop="email">
            <el-input v-model="changeUserInfoForm.email" placeholder="邮箱"></el-input>
          </el-form-item>
          <el-form-item label="昵称" prop="name">
            <el-input v-model="changeUserInfoForm.name" placeholder="昵称"></el-input>
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
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button type="primary" :loading="submiting" @click.native.prevent="submit0">确认</el-button>
        </div>
      </el-dialog>
      <el-dialog :visible.sync="dis1" title="修改密码">
        <el-form
          :model="changeUserPasswordForm"
          ref="changeUserPasswordForm"
          :rules="rule1"
          class="changeform"
          label-width="80px"
        >
          <el-form-item label="旧密码" prop="passwordOld">
            <el-input v-model="changeUserPasswordForm.passwordOld" placeholder="旧密码"></el-input>
          </el-form-item>
          <el-form-item label="新密码" prop="password">
            <el-input v-model="changeUserPasswordForm.password" placeholder="新密码"></el-input>
          </el-form-item>
          <el-form-item label="确认密码" prop="confirmpassword">
            <el-input v-model="changeUserPasswordForm.confirmpassword" placeholder="确认密码"></el-input>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button type="primary" :loading="submiting" @click.native.prevent="submit1">确认</el-button>
        </div>
      </el-dialog>
    </el-main>
  </el-container>
</template>

<script>
import { changeUserInfo } from "../../main";
import { changeUserPassword } from "../../main";
export default {
  components: {},
  data() {
    let oldPasswordCheck = (rule, value, callback) => {
      if (value === "") {
        return callback(new Error("旧密码不能为空"));
      } else if (value !== this.userInfo.password) {
        return callback(new Error("旧密码错误"));
      } else {
        return callback();
      }
    };
    let confirmpasswordCheck = (rule, value, callback) => {
      //console.log(value);
      if (value === "") {
        return callback(new Error("确认密码不能为空"));
      } else if (value !== this.changeUserPasswordForm.password) {
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
        this.changeUserInfoForm.province == "" ||
        value == "" ||
        this.changeUserInfoForm.area == ""
      )
        return callback(new Error("省市地不能为空"));
      else callback();
    };
    return {
      dis0: false,
      dis1: false,
      submiting: false,
      rule1: {
        passwordOld: [
          {
            required: true,
            validator: oldPasswordCheck,
            trigger: "blur"
          }
        ],
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
        ]
      },
      rule0: {
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
      userInfo: {
        userType: "",
        username: "",
        password: "",
        tel: null,
        email: "",
        name: "",
        province: "",
        city: "",
        area: ""
      },
      changeUserInfoForm: {
        userType: "",
        username: "",
        tel: null,
        email: "",
        name: "",
        province: "",
        city: "",
        area: ""
      },
      changeUserPasswordForm: {
        passwordOld: "",
        password: "",
        confirmpassword: ""
      }
    };
  },
  methods: {
    onChangeProvince(data) {
      this.changeUserInfoForm.province = data.value;
    },
    onChangeCity(data) {
      this.changeUserInfoForm.city = data.value;
    },
    onChangeArea(data) {
      this.changeUserInfoForm.area = data.value;
    },
    submit0() {
      this.$refs.changeUserInfoForm.validate(valid => {
        if (valid) {
          this.submiting = true;
          changeUserInfo(this.changeUserInfoForm).then(res => {
            if (res.data.result == true) {
              this.logining = false;
              this.$message({
                type: "success",
                message: "信息修改成功"
              });
              this.submiting = false;
              let changeParams = {
                userType: this.changeUserInfoForm.userType,
                username: this.changeUserInfoForm.username,
                password: this.changeUserInfo.password,
                tel: this.changeUserInfoForm.tel,
                email: this.changeUserInfoForm.email,
                name: this.changeUserInfoForm.name,
                city: this.changeUserInfoForm.city,
                area: this.changeUserInfoForm.area,
                province: this.changeUserInfoForm.province
              };
              sessionStorage.setItem("userInfo", JSON.stringify(changeParams));
              this.$store.dispatch("commitLogin");
              this.dis0 = false;
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
    },
    submit1() {
      this.$refs.changeUserPasswordForm.validate(valid => {
        if (valid) {
          this.submiting = true;
          let changePasswordParams = {
            username: this.userInfo.username,
            password: this.changeUserPasswordForm.password
          };
          changeUserPassword(changePasswordParams).then(res => {
            if (res.data.result == true) {
              this.logining = false;
              this.$message({
                type: "success",
                message: "密码修改成功"
              });
              this.submiting = false;
              let changeParams = {
                userType: this.UserInfo.userType,
                username: this.UserInfo.username,
                password: this.changeUserPasswordForm.password,
                tel: this.UserInfo.tel,
                email: this.UserInfo.email,
                name: this.UserInfo.name,
                city: this.UserInfo.city,
                area: this.UserInfo.area,
                province: this.UserInfo.province
              };
              sessionStorage.setItem("userInfo", JSON.stringify(changeParams));
              this.$store.dispatch("commitLogin");
              this.dis1 = false;
              this.$router.push("/user1/info");
            } else {
              this.$message.error({
                message: "密码修改失败,请稍后再试"
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
    this.userInfo = this.$store.state.userInfo;
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
.info1 {
  margin: auto 200px;
  width: 600px;
  background: #fff;
  box-shadow: 0 0 35px #b4bccc;
  padding: 30px 30px 30px 30px;
  border-radius: 30px;
}
</style>