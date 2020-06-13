<template>
  <el-main class="infoRi">
    <el-page-header @back="goBack" content="报修详情"></el-page-header>
    <!-- <p v-for="(item,key,index) in showRepairInfo" :key="key">{{repairLabel[index]}}:{{item}}</p> -->
        <p>报修编号：{{repairInfo.repairId}}</p>
        <p>房源编号：{{repairInfo.houseId}}</p>
        <p>维修人员电话：{{repairInfo.tel}}</p>
        <p>报修账号：{{repairInfo.username}}</p>
        <p>报修内容：{{repairInfo.content}}</p>
        <p>处理状态：{{repairInfo.status}}</p>
        <p>维修反馈：{{repairInfo.callback}}</p>
        <p>评价内容：{{repairInfo.evaluation}}</p>
        <p>评分：{{repairInfo.score}}</p>
    <div class="image">
      <!-- <el-image v-for="url in repairInfo.pic" :key="url" :src="url" fit="scale-down" lazy></el-image> -->
      <el-carousel v-if="repairInfo.pic!=[]" :interval="5000" arrow="always" :width="500">
        <el-carousel-item v-for="item in repairInfo.pic" :key="item.id">
          <el-row>
            <el-col>
              <el-image :src="item" fit="scale-down" />
            </el-col>
          </el-row>
        </el-carousel-item>
      </el-carousel>
    </div>
    <el-button @click.native.prevent="dialogVisible = true" v-if="repairInfo.status=='未评价'">评价</el-button>
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
        <el-button type="primary" @click.native.prevent="makeRepairComment">确认</el-button>
      </div>
    </el-dialog>
  </el-main>
</template>
<script>
import { getRepair } from "../main";
import { submitRepairComment } from "../main";
export default {
  data() {
    return {
      dialogVisible: false,
      repairInfo: {
        repairId: "",
        houseId: "",
        tel: "",
        username: "",
        content: "",
        status: "未处理",
        callback: "",
        evaluation: "",
        score: "",
        pic: [
          "http://qbi3ylqqu.bkt.clouddn.com/6eKVd3ZUonkS5bd2",
          "http://qbi3ylqqu.bkt.clouddn.com/7hXAE3tAQSLXyYa0"
        ]
      },
      repairLabel: [
        "报修编号",
        "房源编号",
        "维修人员电话",
        "报修账号",
        "报修内容",
        "处理状态",
        "维修反馈",
        "评价内容",
        "评分"
      ],
      commentForm: {
        repairId: "",
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
      this.$router.push("/user0/repair");
    },
    makeRepairComment() {
      //console.log(this.commentForm);
      this.$refs.commentForm.validate(valid => {
        if (valid) {
          this.commentForm.repairId = this.repairInfo.repairId;
          this.commentForm.score = this.commentForm.score.toString();
          submitRepairComment(this.commentForm).then(res => {
            if (res.data.result == true) {
              this.$message({
                type: "success",
                message: "评价成功"
              });
              this.dialogVisible = false;
              this.$router.push("/user0/repair");
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
    getRepair({ repairId: this.$route.query.repairId }).then(res => {
      this.repairInfo = res.data;
    });
  },
  computed: {
    showRepairInfo: function() {
      let tmp_tel = this.repairInfo.tel;
      let tmp_callback = this.repairInfo.callback;
      if (this.repairInfo.status == "未处理") {
        tmp_tel = "暂无";
      }
      if (
        this.repairInfo.status == "未处理" ||
        this.repairInfo.status == "工单建立成功"
      ) {
        tmp_callback = "暂无";
      }
      return {
        repairId: this.repairInfo.repairId,
        houseId: this.repairInfo.houseId,
        tel: tmp_tel,
        username: this.repairInfo.username,
        content: this.repairInfo.content,
        status: this.repairInfo.status,
        callback: tmp_callback,
        evaluation: this.repairInfo.evaluation,
        score: this.repairInfo.score
      };
    }
  }
};
</script>
<style>
.infoRi {
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
}
</style>