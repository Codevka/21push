<template>
  <el-main class="infoUm">
    <p class="input">
      <el-form>
        <el-form-item>
          <el-button type="primary" @click.prevent.native="newUser('2')" style="float: right">导入维修人员</el-button>
          <el-button
            type="danger"
            v-if="this.isSAdmin"
            @click.prevent.native="newUser('1')"
            style="float: right"
          >导入客服</el-button>
        </el-form-item>
        <el-form-item label-width="100" label="搜索用户:">
          <el-input style="width:400px;" v-model="keyword" placeholder="请输入用户账号或手机号">
            <el-button slot="append" icon="el-icon-search" circle @click.native.prevent="sUsers"></el-button>
          </el-input>
        </el-form-item>
      </el-form>
    </p>
    <el-table border :data="userData">
      <el-table-column type="index" width="50"></el-table-column>
      <el-table-column prop="username" label="账号" width="200"></el-table-column>
      <el-table-column prop="userType" label="用户类型" width="150"></el-table-column>
      <el-table-column prop="name" label="昵称" width="150"></el-table-column>
      <el-table-column fixed="right" label="操作" width="150">
        <template slot-scope="scope">
          <el-button @click.native.prevent="toUser(scope.$index)" size="small">详细信息</el-button>
        </template>
      </el-table-column>
    </el-table>
  </el-main>
</template>
<script>
import { isSuperAdmin } from "../../main";
import { searchUsers } from "../../main";
export default {
  data() {
    return {
      isSAdmin: false,
      keyword: "",
      userData: [
        {
          username: "",
          userType: "",
          name: ""
        }
      ]
    };
  },
  methods: {
    sUsers() {
      let params = { keyword: this.keyword };
      searchUsers(params).then(res => {
        this.userData = res.data;
        this.$message({
          type: "success",
          message: "搜索成功"
        });
      });
    },
    toUser(index) {
      this.$router.push({
        path: "/userInfo",
        query: {
          username: this.userData[index].username
        }
      });
    },
    newUser(uType) {
      this.$router.push({
        path: "/newUser",
        query: {
          userType: uType
        }
      });
    }
  },
  mounted() {
    isSuperAdmin().then(res => {
      this.isSAdmin = res.data.isSuperAdmin;
    });
  }
};
</script>
<style>
.infoUm {
  margin: 40px auto;
  width: 800px;
  background: #fff;
  box-shadow: 0 0 35px #b4bccc;
  padding: 20px 20px 20px 20px;
  border-radius: 10px;
}
.input {
  width: 500px;
  float: right;
}
</style>