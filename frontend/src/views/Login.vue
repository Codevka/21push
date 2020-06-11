<template>
  <el-main>
    <el-form :model="LoginForm" ref="LoginForm" :rules="rule" label-width="0px" class="login-form">
      <h3>用户登录</h3>
      <el-form-item prop="userType">
        <el-radio v-model="LoginForm.userType" label="0">租客</el-radio>
        <el-radio v-model="LoginForm.userType" label="2">维修人员</el-radio>
        <el-radio v-model="LoginForm.userType" label="1">客服</el-radio>
      </el-form-item>
      <el-form-item prop="username">
        <el-input type="text" v-model="LoginForm.username" placeholder="账号或手机"></el-input>
      </el-form-item>

      <el-form-item prop="password">
        <el-input type="password" v-model="LoginForm.password" placeholder="密码"></el-input>
      </el-form-item>

      <el-form-item>
        <el-button
          type="danger"
          class="submitBtn"
          round
          @click.native.prevent="submit"
          :loading="logining"
        >登录</el-button>
        <el-button type="primary" class="resetBtn" round @click.native.prevent="toRegister">注册</el-button>
      </el-form-item>
    </el-form>
  </el-main>
</template>

<script>
import { LoginUser } from "../main";
export default {
  data() {
    return {
      LoginForm: {
        userType: "",
        username: "",
        password: ""
      },
      logining: false,
      rule: {
        userType: [
          {
            required: true,
            message: "用户类型不能为空",
            trigger: "blur"
          }
        ],
        username: [
          {
            required: true,
            message: "账号或手机不能为空",
            trigger: "blur"
          }
        ],
        password: [
          {
            required: true,
            message: "密码不能为空",
            trigger: "blur"
          }
        ]
      }
    };
  },
  methods: {
    submit() {
      this.$refs.LoginForm.validate(valid => {
        if (valid) {
          this.logining = true;
          //console.log("submited");
          let LoginParams = {
            userType: this.LoginForm.userType,
            username: this.LoginForm.username,
            password: this.LoginForm.password
          };
          LoginUser(LoginParams).then(res => {
            //console.log(res);
            this.logining = false;
            if (res.data.result == true) {
              this.$message({
                type: "success",
                message: "登录成功"
              });
              let userInfo = res.data;
              delete userInfo.result;
              //let userInfo = LoginParams;
              sessionStorage.setItem("userInfo", JSON.stringify(userInfo));
              //console.log(sessionStorage.getItem("userInfo"));
              this.$store.dispatch("commitLogin");
              switch (userInfo.userType) {
                case "0":
                  this.$router.push("/user0");
                  break;
                case "1":
                  this.$router.push("/user1");
                  break;
                case "2":
                  this.$router.push("/user2");
              }
            } else if (res.data.result == false) {
              this.$message.error({
                message: "账号、手机号或密码错误"
              });
            } else {
              this.$message.error({
                message: "登录失败，请稍后再试"
              });
            }
          });
          /*sessionStorage.setItem("userInfo", JSON.stringify(LoginParams));
          this.$store.dispatch("commitLogin");
          switch (LoginParams.userType) {
                case "0":
                  this.$router.push("/user0");
                  break;
                case "1":
                  this.$router.push("/user1");
                  break;
                case "2":
                  this.$router.push("/user2");
              }*/
        } else {
          this.$message.error({
            message: "请检查输入信息"
          });
        }
      });
    },
    /*reset () {
      this.$refs.LoginForm.resetFields()
    },*/
    toRegister() {
      this.$router.push("/register");
    }
  }
};
</script>

<style scoped>
.login-form {
  margin: 40px auto;
  width: 310px;
  background: #fff;
  box-shadow: 0 0 35px #b4bccc;
  padding: 30px 30px 30px 30px;
  border-radius: 30px;
}
.submitBtn {
  width: 65%;
}
.to {
  color: #67c23a;
  cursor: pointer;
}
</style>
