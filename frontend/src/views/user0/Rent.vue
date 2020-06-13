<template>
  <el-main class="infoR">
    <p class="input">
      <el-form>
        <el-form-item label-width="100" label="搜索房源:">
          <el-select v-model="value0" placeholder="请选择租房形式" style="width:150px;">
            <el-option
              v-for="item in option0"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            ></el-option>
          </el-select>
          <el-select v-model="value1" placeholder="请选择房间类型" style="width:150px;">
            <el-option
              v-for="item in option1"
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
      <el-table-column type="index" min-width="4%" :resizable="false"></el-table-column>
      <el-table-column prop="houseId" label="房源编号" min-width="11%" :resizable="false"></el-table-column>
      <el-table-column prop="area" label="地区" min-width="20%" :resizable="false"></el-table-column>
      <el-table-column prop="address" label="地址" min-width="30%" :resizable="false"></el-table-column>
      <el-table-column prop="rentType" label="租房形式" min-width="11%" :resizable="false"></el-table-column>
      <el-table-column prop="houseType" label="房间类型" min-width="13%" :resizable="false"></el-table-column>
      <el-table-column fixed="right" label="操作" min-width="11%" :resizable="false">
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
      option0: [
        {
          value: "0",
          label: "任意租房形式"
        },
        {
          value: "1",
          label: "短租"
        },
        {
          value: "12",
          label: "长租"
        }
      ],
      option1: [
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
      value0: "0",
      value1: "0"
    };
  },
  methods: {
    sHouses() {
      let params = {
        keyword: this.keyword,
        rentType: this.value0,
        houseType: this.value1
      };
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
  width: 800px;
  float: right;
}
</style>