<template>
  <el-main class="info">
    <p align="right">
      <el-button @click.native.prevent="dialogVisible=true" type="primary">发起投诉</el-button>
    </p>
    <el-table border :data="userComplaint" style="width: 100%">
      <el-table-column type="index" width="50"></el-table-column>
      <el-table-column prop="complaintId" label="投诉编号" width="150"></el-table-column>
      <el-table-column prop="houseId" label="房源编号" width="150"></el-table-column>
      <el-table-column prop="adminId" label="处理人id" width="150"></el-table-column>
      <el-table-column prop="status" label="处理状态" width="150"></el-table-column>
      <el-table-column fixed="right" label="操作" width="100">
        <template slot-scope="scope">
          <el-button @click.native.prevent="toComplaintInfo(scope.$index)" size="small">详细信息</el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-dialog :visible.sync="dialogVisible" title="投诉">
      <el-form :model="complaintForm" :rules="rule" ref="complaintForm" label-width="100px">
        <el-form-item prop="houseId" label="房源编号">
          <el-input placeholder="房源编号" v-model="complaintForm.houseId"></el-input>
        </el-form-item>
        <el-form-item prop="content" label="投诉内容">
          <el-input placeholder="投诉内容" v-model="complaintForm.content"></el-input>
        </el-form-item>
        <!--上传图片-->
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click.native.prevent="makeComplaint">确认</el-button>
      </div>
    </el-dialog>
  </el-main>
</template>
<script>
import { getUserComplaint } from "../../main";
import { submitComplaint } from "../../main";
//import { getToken } from '../../main';
export default {
  data() {
    return {
      userComplaint:[],
      /*userComplaint: [
        {
          complaitId: "",
          houseId: "",
          status: "",
          adminId: ""
        }
      ],*/
      dialogVisible: false,
      complaintForm: {
        houseId: "",
        username: "",
        content: "",
        pic:[]
      },
      rule: {
        houseId: [
          {
            required: true,
            message: "房源编号不能为空",
            trigger: "blur"
          }
        ],
        content: [
          {
            required: true,
            message: "投诉内容不能为空",
            trigger: "blur"
          }
        ]
      }
    };
  },
  methods: {
    toComplaintInfo(index) {
      this.$router.push({
        path: "/complaintInfo",
        query: {
          complaintId: this.userComplaint[index].complaintId
        }
      });
    },
    makeComplaint() {
      this.$refs.complaintForm.validate(valid => {
        if (valid) {
          this.complaintForm.username = this.$store.state.userInfo.username;
          submitComplaint(this.complaintForm).then(res => {
            if (res.data.result == true) {
              this.$message({
                type: "success",
                message: "投诉提交成功,请等待处理"
              });
              this.dialogVisible = false;
              this.$router.push("/user0/complaint");
            } else {
              this.$message.error({
                message: "投诉提交失败,请稍后再试"
              });
            }
          });
        } else {
          console.log("submitComplaint err.");
        }
      });
    }
  },
  mounted() {
    let user = { username: this.$store.state.userInfo.username };
    getUserComplaint(user).then(res => {
      this.userComplaint = res.data;
    });
  }
};
</script>
<style>
.info {
  margin: auto auto;
  width: 830px;
  background: #fff;
  box-shadow: 0 0 35px #b4bccc;
  padding: 30px 30px 30px 30px;
  border-radius: 10px;
}
</style>