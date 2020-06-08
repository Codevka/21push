<template>
  <el-main class="infoCm">
    <p class="input">
      <el-form>
        <el-form-item label-width="100" label="搜索投诉:">
          <el-input style="width:400px;" v-model="keyword" placeholder="请输入投诉编号、房源编号或用户账号">
            <el-button
              slot="append"
              icon="el-icon-search"
              circle
              @click.native.prevent="sComaplaints"
            ></el-button>
          </el-input>
        </el-form-item>
      </el-form>
    </p>
    <el-table border :data="complaintData" style="width: 100%">
      <el-table-column type="index" width="50"></el-table-column>
      <el-table-column prop="complaintId" label="投诉编号" width="150"></el-table-column>
      <el-table-column prop="houseId" label="房源编号" width="150"></el-table-column>
      <el-table-column prop="username" label="账号" width="150"></el-table-column>
      <el-table-column prop="status" label="处理状态" width="100"></el-table-column>
      <el-table-column fixed="right" label="操作" width="150">
        <template slot-scope="scope">
          <el-button @click.native.prevent="toComplaint(scope.$index)" size="small">详细信息</el-button>
        </template>
      </el-table-column>
    </el-table>
  </el-main>
</template>

<script>
import { searchComplaints } from "../../main";
export default {
  data() {
    return {
      keyword: "",
      //complaintData:[],
      complaintData: [
        {
          comaplaintId: "1",
          houseId: "",
          username: "",
          status: ""
        }
      ]
    };
  },
  methods: {
    sComplaints() {
      let params = { keyword: this.keyword };
      searchComplaints(params).then(res => {
        this.complaintData = res.data;
        this.$message({
          type: "success",
          message: "搜索成功"
        });
      });
    },
    toComplaint(index) {
      this.$router.push({
        path: "/complaint",
        query: {
          complaintId: this.complaintData[index].complaintId
        }
      });
    }
  }
};
</script>
<style>
.infoCm {
  margin: 40px auto;
  width: 850px;
  background: #fff;
  box-shadow: 0 0 35px #b4bccc;
  padding: 30px 30px 30px 30px;
  border-radius: 10px;
}
.input {
  width: 500px;
  float: right;
}
</style>