<template>
  <el-main class="infoRepair">
    <el-page-header @back="goBack" content="报修详情"></el-page-header>
    <!-- <p v-for="(item,key,index) in showRepairInfo" :key="key">{{repairLabel[index]}}：{{item}}</p> -->
    <p>报修编号：{{repairInfo.repairId}}</p>
    <p>房源编号：{{repairInfo.houseId}}</p>
    <p>报修账号：{{repairInfo.username}}</p>
    <p>报修内容：{{repairInfo.content}}</p>
    <p>处理状态：{{repairInfo.status}}</p>
    <p>评价内容：{{repairInfo.evaluation}}</p>
    <p>评分：{{repairInfo.score}}</p>
    <div class="image">
      <el-image v-for="url in repairInfo.pic" :key="url" :src="url" fit="scale-down" lazy></el-image>
    </div>
    <el-button @click.native.prevent="dialogVisible = true" v-if="repairInfo.status=='未处理'">处理</el-button>
    <el-dialog title="处理报修" :visible.sync="dialogVisible">
      <el-form>
        <el-form-item label-width="100" label="导入同地区维修人员">
          <el-input style="width:400px;" v-model="keyword" placeholder="请输入维修人员账号、昵称或手机号">
            <el-button
              slot="append"
              icon="el-icon-search"
              circle
              @click.native.prevent="sRepairers"
            ></el-button>
          </el-input>
        </el-form-item>
      </el-form>
      <el-table border :data="repairerInfo" style="width: 100%">
        <el-table-column prop="username" label="维修人员账号" min-width="25%" :resizable="false"></el-table-column>
        <el-table-column prop="name" label="姓名" min-width="25%" :resizable="false"></el-table-column>
        <el-table-column prop="tel" label="手机号" min-width="25%" :resizable="false"></el-table-column>
        <el-table-column fixed="right" label="操作" min-width="25%" :resizable="false">
          <template slot-scope="scope">
            <el-button @click.native.prevent="importRepairer(scope.$index)" size="small">导入维修人员</el-button>
          </template>
        </el-table-column>
      </el-table>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click.native.prevent="cWorkOrder">建立工单</el-button>
        <el-button type="primary" @click.native.prevent="rRepair">拒绝报修</el-button>
      </div>
    </el-dialog>
  </el-main>
</template>

<script>
import { searchRepairers } from "../main";
import { createWorkOrder } from "../main";
import { refuseRepair } from "../main";
import { getRepair } from "../main";
export default {
  data() {
    return {
      keyword: "",
      dialogVisible: false,
      repairInfo: {
        repairId: "",
        houseId: "",
        tel: "",
        username: "",
        content: "",
        status: "",
        callback: "",
        evaluation: "",
        score: "",
        pic: [
          /*"http://qbi3ylqqu.bkt.clouddn.com/6eKVd3ZUonkS5bd2",
          "http://qbi3ylqqu.bkt.clouddn.com/7hXAE3tAQSLXyYa0"*/
        ]
      },
      repairLabel: [
        "报修编号",
        "房源编号",
        "报修账号",
        "报修内容",
        "处理状态",
        "评价内容",
        "评分"
      ],
      repairerInfo: [
        {
          username: "1234567",
          name: "",
          tel: ""
        }
      ],
      selectedRepairerUsername: "",
      workOrderForm: {
        repairId: "",
        username: "",
        status: "未处理"
      },
      rule: {}
    };
  },
  methods: {
    goBack() {
      this.$router.push("/user1/repairManage");
    },
    cWorkOrder() {
      if (this.selectedRepairerUsername == "")
        this.$message({
          type: "error",
          message: "未导入维修人员"
        });
      let params = {
        repairId: this.repairInfo.repairId,
        username: this.selectedRepairerUsername
      };
      createWorkOrder(params).then(res => {
        if (res.data.success == true) {
          this.$message({
            type: "success",
            message: "工单创建成功"
          });
          this.repairInfo.status = "工单建立成功";
          this.dialogVisible = false;
        } else {
          this.$message({
            type: "error",
            message: "工单创建失败，请稍后再试"
          });
        }
      });
    },
    rRepair() {
      let params = {
        repairId: this.repairInfo.repairId
      };
      refuseRepair(params).then(res => {
        if (res.data.success == true) {
          this.$message({
            type: "success",
            message: "报修拒绝成功"
          });
          this.repairInfo.status = "已拒绝";
          this.dialogVisible = false;
        } else {
          this.$message({
            type: "error",
            message: "报修拒绝失败，请稍后再试"
          });
        }
      });
    },
    sRepairers() {
      let params = {
        keyword: this.keyword,
        username: this.repairInfo.username
      };
      searchRepairers(params).then(res => {
        this.repairerInfo = res.data;
        this.$message({
          type: "success",
          message: "搜索成功"
        });
      });
    },
    importRepairer(index) {
      this.selectedRepairerUsername = this.repairerInfo[index].username;
    }
  },
  mounted() {
    getRepair({ repairId: this.$route.query.repairId }).then(res => {
      this.repairInfo = res.data;
    });
  },
  computed: {
    showRepairInfo: function() {
      return {
        repairId: this.repairInfo.repairId,
        houseId: this.repairInfo.houseId,
        username: this.repairInfo.username,
        content: this.repairInfo.content,
        status: this.repairInfo.status,
        evaluation: this.repairInfo.evaluation,
        score: this.repairInfo.score
      };
    }
  }
};
</script>

<style>
.infoRepair {
  margin: 40px 400px;
  width: 500px;
  background: #fff;
  box-shadow: 0 0 35px #b4bccc;
  padding: 30px 30px 30px 30px;
  border-radius: 30px;
}
.el-dialog {
  margin: auto auto;
  width: 500px;
  background: #fff;
  padding: 30px 30px 30px 30px;
  border-radius: 30px;
}
.image {
  margin: auto auto;
  width: 1200px;
}
</style>