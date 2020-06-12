<template>
  <el-main class="infoRepairm">
    <p class="input">
      <el-form>
        <el-form-item label-width="100" label="搜索报修:">
          <el-input style="width:400px;" v-model="keyword" placeholder="请输入报修编号、房源编号或用户账号">
            <el-button slot="append" icon="el-icon-search" circle @click.native.prevent="sRepairs"></el-button>
          </el-input>
        </el-form-item>
      </el-form>
    </p>
    <el-table border :data="repairData" style="width: 100%">
      <el-table-column type="index" min-width="7%" :resizable="false"></el-table-column>
      <el-table-column prop="repairId" label="报修编号" min-width="20%" :resizable="false"></el-table-column>
      <el-table-column prop="houseId" label="房源编号" min-width="20%" :resizable="false"></el-table-column>
      <el-table-column prop="username" label="租客账号" min-width="20%" :resizable="false"></el-table-column>
      <el-table-column prop="status" label="处理状态" min-width="13%" :resizable="false"></el-table-column>
      <el-table-column fixed="right" label="操作" min-width="20%" :resizable="false">
        <template slot-scope="scope">
          <el-button @click.native.prevent="toRepair(scope.$index)" size="small">详细信息</el-button>
        </template>
      </el-table-column>
    </el-table>
  </el-main>
</template>

<script>
import { searchRepairs } from "../../main";
export default {
  data() {
    return {
      keyword: "",
      // repairData: []
      repairData: [
        {
          repairId: "1",
          houseId: "",
          username: "",
          status: ""
        }
      ]
    };
  },
  methods: {
    sRepairs() {
      let params = { keyword: this.keyword };
      searchRepairs(params).then(res => {
        this.repairData = res.data;
        this.$message({
          type: "success",
          message: "搜索成功"
        });
      });
    },
    toRepair(index) {
      this.$router.push({
        path: "/repair",
        query: {
          repairId: this.repairData[index].repairId
        }
      });
    }
  }
};
</script>

<style>
.infoRepairm {
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