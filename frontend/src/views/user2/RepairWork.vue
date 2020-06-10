<template>
  <el-main class="infoRw">
    <el-table border :data="userRepairWork" style="width: 100%">
      <el-table-column type="index" width="50" :resizable="false"></el-table-column>
      <el-table-column prop="repairWorkId" label="工单编号" width="150" :resizable="false"></el-table-column>
      <el-table-column prop="repairId" label="报修编号" width="150" :resizable="false"></el-table-column>
      <el-table-column prop="status" label="工单状态" width="150" :resizable="false"></el-table-column>
      <el-table-column prop="callback" label="反馈" width="300" :resizable="false"></el-table-column>
      <el-table-column fixed="right" label="操作" width="150" :resizable="false">
        <template slot-scope="scope">
          <el-button @click.native.prevent="toRepairWork(scope.$index)" size="small">详细信息</el-button>
        </template>
      </el-table-column>
    </el-table>
  </el-main>
</template>
<script>
import { getUserRepairWork } from "../../main";
export default {
  data() {
    return {
      userRepairWork:[]
      /*userRepairWork: [
        {
          repairWorkId: "",
          repairId: "",
          status: "",
          callback: ""
        }
      ]*/
    };
  },
  methods: {
    toRepairWork(index) {
      //console.log(this.userRepairWork[index].repairWorkId);
      this.$router.push({
        path: "/repairWorkInfo",
        query: {
          repairWorkId: this.userRepairWork[index].repairWorkId
        }
      });
    }
  },
  mounted() {
    let user = { username: this.$store.state.userInfo.username };
    getUserRepairWork(user).then(res => {
      this.userRepairWork = res.data;
    });
  }
};
</script>
<style>
.infoRw {
  margin: 40px auto;
  width: 1050px;
  background: #fff;
  box-shadow: 0 0 20px #b4bccc;
  padding: 30px 30px 30px 30px;
  border-radius: 10px;
}
</style>