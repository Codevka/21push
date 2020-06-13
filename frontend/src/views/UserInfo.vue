<template>
  <el-container>
    <el-main class="info">
      <el-page-header @back="goBack" content="用户详情"></el-page-header>
      <p>账号: {{userInfo.username}}</p>
      <p>手机: {{userInfo.tel}}</p>
      <p>邮箱: {{userInfo.email}}</p>
      <p>昵称: {{userInfo.name}}</p>
      <p>省份: {{userInfo.province}}</p>
      <p>城市: {{userInfo.city}}</p>
      <p>地区: {{userInfo.area}}</p>
      <el-button @click.native.prevent="change">修改个人信息</el-button>
      <el-button @click.native.prevent="payVisible = true">缴纳租金</el-button>
      <el-button @click.native.prevent="returnVisible = true">退回租金</el-button>
      <el-dialog title="缴纳租金" :visible.sync="payVisible">
        <el-form>
          <el-form-item label-width="100" label="缴纳金额">
            <el-input style="width:400px;" v-model="payAmount" placeholder="请输入缴纳金额"></el-input>
          </el-form-item>
          <el-form-item label-width="100" label="租客密码">
            <el-input
              style="width:400px;"
              type="password"
              v-model="payForm.password"
              placeholder="请让租客输入密码"
            ></el-input>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button :loading="paySubmiting" type="primary" @click.native.prevent="pMoney">提交</el-button>
        </div>
      </el-dialog>
      <el-dialog title="退回租金" :visible.sync="returnVisible">
        <el-form>
          <el-form-item label-width="100" label="租客密码">
            <el-input
              style="width:400px;"
              type="password"
              v-model="returnForm.password"
              placeholder="请让租客输入密码"
            ></el-input>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button :loading="returnSubmiting" type="primary" @click.native.prevent="rMoney">提交</el-button>
        </div>
      </el-dialog>
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
import { payMoney, returnMoney } from "../main";
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
      paySubmiting: false,
      returnSubmiting: false,
      payVisible: false,
      returnVisible: false,
      returnAmount: "",
      payAmount: "",
      payForm: {
        username: "",
        password: "",
        amount: ""
      },
      returnForm: {
        username: "",
        password: ""
      },
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
        tel: null,
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
          this.manageUserInfoForm.tel = this.manageUserInfoForm.tel.toString();
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
              //sessionStorage.setItem("userInfo", JSON.stringify(changeParams));
              //this.$store.dispatch("commitLogin");
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
    },
    pMoney() {
      this.paySubmiting = true;
      this.payForm.username = this.userInfo.username;
      let number = parseInt(this.payAmount);
      if (this.payAmount != number.toString() || number < 0) {
        this.$message({
          type: "error",
          message: "缴纳金额必须是正整数"
        });
        this.paySubmiting = false;
        return;
      }
      this.payForm.amount = this.payAmount;
      console.log(this.payForm);

      payMoney(this.payForm).then(res => {
        if (res.data.result == true) {
          this.$message({
            type: "success",
            message: "租金缴纳成功"
          });
        } else {
          this.$message({
            type: "error",
            message: "租金缴纳失败，请检查租客密码"
          });
        }
        this.paySubmiting = false;
      });
    },
    rMoney() {
      this.returnSubmiting = true;
      this.returnForm.username = this.userInfo.username;
      console.log(this.returnForm);

      returnMoney(this.returnForm).then(res => {
        if (res.data.result == true) {
          this.$message({
            type: "success",
            message: "租金退回成功，共" + res.data.amount + "元"
          });
        } else {
          this.$message({
            type: "error",
            message: "租金退回失败，请检查租客密码"
          });
        }
        this.returnSubmiting = false;
      });
    }
  },
  mounted() {
    getUser({ username: this.$route.query.username }).then(res => {
      this.userInfo = res.data;
      this.manageUserInfoForm.username = this.userInfo.username;
      this.manageUserInfoForm.tel = Number.parseInt(this.userInfo.tel);
      this.manageUserInfoForm.email = this.userInfo.email;
      this.manageUserInfoForm.name = this.userInfo.name;
      this.manageUserInfoForm.province = this.userInfo.province;
      this.manageUserInfoForm.city = this.userInfo.city;
      this.manageUserInfoForm.area = this.userInfo.area;
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
.el-dialog {
  margin: auto auto;
  width: 500px;
  background: #fff;
  padding: 30px 30px 30px 30px;
  border-radius: 30px;
}
</style>