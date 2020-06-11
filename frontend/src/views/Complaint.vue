<template>
  <el-main class="infoC">
    <el-page-header @back="goBack" content="投诉详情"></el-page-header>
    <p v-for="(item,key,index) in complaintInfo" :key="key">{{complaintLabel[index]}}：{{item}}</p>
    <el-button @click.native.prevent="dialogVisible = true" v-if="complaintInfo.status=='未处理'">处理</el-button>
    <el-dialog title="处理投诉" :visible.sync="dialogVisible">
      <el-form :model="replyForm" ref="replyForm" :rules="rule">
        <el-form-item label="回复内容" label-width="100px" prop="reply">
          <el-input placeholder="请输入回复内容" v-model="replyForm.reply"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click.native.prevent="makeComplaintReply">确认</el-button>
      </div>
    </el-dialog>
  </el-main>
</template>
<script>
import { getComplaint } from "../main";
import { submitComplaintReply } from "../main";
export default {
  data() {
    return {
      dialogVisible: false,
      complaintInfo: {
        complaintId: "",
        houseId: "",
        username: "",
        content: "",
        status: "未处理",
        adminId: "",
        reply: "",
        evaluation: "",
        score: ""
      },
      complaintLabel: [
        "投诉编号",
        "房源编号",
        "投诉人账号",
        "投诉内容",
        "处理状态",
        "客服编号",
        "客服回复",
        "评价内容",
        "评分"
      ],
      replyForm: {
        complaintId: "",
        reply: "",
        username: ""
      },
      rule: {
        reply: [
          {
            required: true,
            message: "回复内容不能为空",
            trigger: "blur"
          }
        ]
      }
    };
  },
  methods: {
    goBack() {
      this.$router.push("/user1/complaintManage");
    },
    makeComplaintReply() {
      this.$refs.commentForm.validate(valid => {
        if (valid) {
          this.replyForm.complaintId = this.complaintInfo.complaintId;
          this.replyForm.username = this.$store.state.userInfo.username;
          submitComplaintReply(this.replyForm).then(res => {
            if (res.data.result == true) {
              this.$message({
                type: "success",
                message: "处理成功"
              });
              this.dialogVisible = false;
              this.goBack();
            } else {
              this.$message.error({
                message: "处理失败,请稍后再试"
              });
            }
          });
        } else {
          this.$message.error({
            message: "请检查输入信息"
          });
        }
      });
    }
  },
  mounted() {
    getComplaint({ complaintId: this.$route.query.complaintId }).then(res => {
      //console.log(res.data)
      this.complaintInfo = res.data;
    });
  }
};
</script>
<style>
.infoC {
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
</style>