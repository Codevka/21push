<template>
  <el-main class="infoHm">
    <p class="input">
      <el-form>
        <el-form-item>
          <el-button type="primary" @click.prevent.native="newHouse" style="float: right">导入房源</el-button>
        </el-form-item>
        <el-form-item label-width="100" label="搜索房源:">
          <el-input style="width:400px;" v-model="keyword" placeholder="请输入搜索关键词">
            <el-button slot="append" icon="el-icon-search" circle @click.native.prevent="sHouses"></el-button>
          </el-input>
        </el-form-item>
      </el-form>
    </p>
    <el-table border :data="houseData" style="width: 100%">
      <el-table-column type="index" width="50"></el-table-column>
      <el-table-column prop="houseId" label="房源编号" width="200"></el-table-column>
      <el-table-column prop="area" label="地区" width="150"></el-table-column>
      <el-table-column prop="address" label="地址" width="400"></el-table-column>
      <el-table-column prop="rentType" label="租房形式" width="150"></el-table-column>
      <el-table-column prop="houseType" label="房间类型" width="150"></el-table-column>
      <el-table-column prop="status" label="房源状态" width="150"></el-table-column>
      <el-table-column fixed="right" label="操作" width="150">
        <template slot-scope="scope">
          <el-button @click.native.prevent="toHouseInfo(scope.$index)" size="small">详细信息</el-button>
        </template>
      </el-table-column>
    </el-table>
  </el-main>
</template>
<script>
import { searchAllHouses } from "../../main";
export default {
  data() {
    return {
      keyword: "",
      //houseData: []
      houseData: [
        {
          houseId: "",
          area: "",
          address: "",
          rentType: "",
          houseType: "",
          status: "未租满"
        }
      ]
    };
  },
  methods: {
    newHouse() {
      this.$router.push("/newHouse");
    },
    sHouses() {
      let params = { keyword: this.keyword };
      searchAllHouses(params).then(res => {
        this.houseData = res.data;
        this.$message({
          type: "success",
          message: "搜索成功"
        });
      });
    },
    toHouseInfo(index) {
      this.$router.push({
        path: "/houseinfo",
        query: {
          houseId: this.houseData[index].houseId
        }
      });
    }
  }
};
</script>
<style>
.infoHm {
  margin: 40px auto;
  width: 1500px;
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