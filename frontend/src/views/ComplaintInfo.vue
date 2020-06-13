<template>
  <el-main class="infoCi">
    <el-page-header @back="goBack" content="投诉详情"></el-page-header>
    <!--<p v-for="(item,key,index) in showComplaintInfo" :key="key">{{complaintLabel[index]}}:{{item}}</p>-->
    <p>投诉编号：{{complaintInfo.complaintId}}</p>
    <p>房源编号：{{complaintInfo.houseId}}</p>
    <p>投诉人编号：{{complaintInfo.username}}</p>
    <p>投诉内容：{{complaintInfo.content}}</p>
    <p>处理状态：{{complaintInfo.status}}</p>
    <p>客服编号：{{complaintInfo.adminId}}</p>
    <p>客服回复：{{complaintInfo.reply}}</p>
    <p>评价内容：{{complaintInfo.evaluation}}</p>
    <p>评分：{{complaintInfo.score}}</p>
    <div class="image">
      <!-- <el-image v-for="url in complaintInfo.pic" :key="url" :src="url" fit="scale-down" width="100px" height="100px" lazy></el-image> -->
      <el-carousel v-if="complaintInfo.pic!=[]" :interval="5000" arrow="always" :width="500">
        <el-carousel-item v-for="item in complaintInfo.pic" :key="item.id">
          <el-row>
            <el-col>
              <el-image :src="item" fit="scale-down" />
            </el-col>
          </el-row>
        </el-carousel-item>
      </el-carousel>
    </div>
    <el-button @click.native.prevent="dialogVisible = true" v-if="complaintInfo.status=='未评价'">评价</el-button>
    <el-dialog title="评价" :visible.sync="dialogVisible">
      <el-form :model="commentForm" ref="commentForm" :rules="rule">
        <el-form-item label="评价内容" label-width="100px" prop="evaluation">
          <el-input placeholder="请输入评价" v-model="commentForm.evaluation"></el-input>
        </el-form-item>
        <el-form-item label="评分" label-width="100px" prop="score">
          <el-rate v-model.number="commentForm.score"></el-rate>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click.native.prevent="makeComplaintComment">确认</el-button>
      </div>
    </el-dialog>
  </el-main>
</template>
<script>
import { getComplaint } from "../main";
import { submitComplaintComment } from "../main";
export default {
  data() {
    return {
      dialogVisible: false,
      complaintInfo: {
        complaintId: "",
        houseId: "",
        username: "",
        content: "",
        status: "未评价",
        adminId: "",
        reply: "",
        evaluation: "",
        score: "",
        pic: [
          /*"http://qbi3ylqqu.bkt.clouddn.com/6eKVd3ZUonkS5bd2",
          "http://qbi3ylqqu.bkt.clouddn.com/7hXAE3tAQSLXyYa0"*/
        ]
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
      commentForm: {
        complaintId: "",
        evaluation: "",
        score: ""
      },
      rule: {
        evaluation: [
          {
            required: true,
            message: "评价内容不能为空",
            trigger: "blur"
          }
        ],
        score: [
          {
            required: true,
            range: [1, 5],
            message: "评分不能为空",
            trigger: "blur"
          }
        ]
      }
    };
  },
  methods: {
    goBack() {
      this.$router.push("/user0/complaint");
    },
    makeComplaintComment() {
      //console.log(this.commentForm);
      this.$refs.commentForm.validate(valid => {
        if (valid) {
          this.commentForm.complaintId = this.complaintInfo.complaintId;
          this.commentForm.score = this.commentForm.score.toString();
          submitComplaintComment(this.commentForm).then(res => {
            if (res.data.result == true) {
              this.$message({
                type: "success",
                message: "评价成功"
              });
              this.dialogVisible = false;
              this.$router.push("/user0/complaint");
            } else {
              this.$message.error({
                message: "评价失败,请稍后再试"
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
      this.complaintInfo = res.data;
    });
  },
  computed: {
    showComplaintInfo: function() {
      return {
        complaintId: this.complaintId,
        houseId: this.houseId,
        username: this.username,
        content: this.content,
        status: this.status,
        adminId: this.adminId,
        reply: this.reply,
        evaluation: this.evaluation,
        score: this.score
      };
    }
  }
};
</script>
<style>
.infoCi {
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
  width: 500px;
  height: 500px;
}
</style>