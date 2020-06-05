<template>
  <el-main class="infoR">
    <p class="input">
      <el-form>
        <el-form-item label-width="100" label="搜索房源:">
          <el-select v-model="value" placeholder="请选择房间类型" style="width:150px;">
            <el-option
              v-for="item in options"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            ></el-option>
          </el-select>
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
          <el-button @click.native.prevent="toHouse(scope.$index)" size="small">详细信息</el-button>
        </template>
      </el-table-column>
    </el-table>
  </el-main>
</template>
<script>
import { searchHouses } from "../../main";
export default {
  data() {
    return {
      keyword: "",
      houseData: [],
      /*houseData: [
        {
          houseId: "",
          area: "",
          address: "",
          rentType: "",
          houseType: "",
          status:"未租满"
        }
      ],*/
      options: [
        {
          value: "0",
          label: "任意房间类型"
        },
        {
          value: "1",
          label: "单人间"
        },
        {
          value: "2",
          label: "二人间"
        },
        {
          value: "3",
          label: "三人间"
        },
        {
          value: "4",
          label: "四人间"
        }
      ],
      value: "0"
    };
  },
  methods: {
    sHouses() {
      let params = { keyword: this.keyword, houseType: this.value };
      searchHouses(params).then(res => {
        this.houseData = res.data;
        this.$message({
          type: "success",
          message: "搜索成功"
        });
      });
    },
    toHouse(index) {
      this.$router.push({
        path: "/house",
        query: {
          houseId: this.houseData[index].houseId
        }
      });
    }
  }
};
</script>
<style>
.infoR {
  margin: 40px auto;
  width: 1500px;
  background: #fff;
  box-shadow: 0 0 35px #b4bccc;
  padding: 30px 30px 30px 30px;
  border-radius: 10px;
}
.input {
  width: 650px;
  float: right;
}
</style>