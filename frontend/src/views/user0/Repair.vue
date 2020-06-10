<template>
  <el-main class="infoR">
    <p align="right">
      <el-button @click.native.prevent="dialogVisible=true" type="primary">发起报修</el-button>
    </p>
    <el-table border :data="userRepair" style="width: 100%">
      <el-table-column type="index" min-width="8%" :resizable="false"></el-table-column>
      <el-table-column prop="repairId" label="报修编号" min-width="25%" :resizable="false"></el-table-column>
      <el-table-column prop="houseId" label="房源编号" min-width="25%" :resizable="false"></el-table-column>
      <el-table-column prop="status" label="处理状态" min-width="25%" :resizable="false"></el-table-column>
      <el-table-column fixed="right" label="操作" min-width="17%" :resizable="false">
        <template slot-scope="scope">
          <el-button @click.native.prevent="toRepairInfo(scope.$index)" size="small">详细信息</el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-dialog :visible.sync="dialogVisible" title="报修">
      <el-form :model="repairForm" :rules="rule" ref="repairForm" label-width="100px">
        <el-form-item prop="houseId" label="房源编号">
          <el-input placeholder="房源编号" v-model="repairForm.houseId"></el-input>
        </el-form-item>
        <el-form-item prop="content" label="报修内容">
          <el-input placeholder="报修内容" v-model="repairForm.content"></el-input>
        </el-form-item>
        <el-upload
          :multiple="true"
          list-type="picture-card"
          :on-remove="handleRemove"
          :action="actionPath"
          accept="image/jpeg, image/png, image/jpg"
          :before-upload="beforeUpload"
          :data="postData"
          :file-list="photoList"
          :on-success="handleSuccess"
          :on-exceed="handleExceed"
          :limit="limitNumber"
        >
          <i class="el-icon-plus"></i>
        </el-upload>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click.native.prevent="submitRepair">确认</el-button>
      </div>
    </el-dialog>
  </el-main>
</template>
<script>
import { getUserRepair } from "../../main";
import { submitRepair } from "../../main";
import { genToken } from "../../genToken";
import random from "string-random";

export default {
  data() {
    return {
      // actionPath: "https://upload.qiniup.com", // 华东
      // actionPath: "https://upload-z1.qiniup.com", // 华北
      actionPath: "https://upload-z2.qiniup.com", // 华南
      photoUrl: "http://qbi3ylqqu.bkt.clouddn.com/", //! for test
      userRepair: [],
      /*userRepair: [
        {
          repairId: "",
          houseId: "",
          status: ""
        }
      ],*/
      limitNumber: 3,
      dialogVisible: false,
      repairForm: {
        houseId: "",
        username: "",
        content: "",
        pic: []
      },
      postData: {
        token: "",
        key: ""
      },
      photoList: [],
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
            message: "报修内容不能为空",
            trigger: "blur"
          }
        ]
      }
    };
  },
  methods: {
    toRepairInfo(index) {
      console.log(this.userRepair[index].repairId);
      this.$router.push({
        path: "/repairInfo",
        query: {
          repairId: this.userRepair[index].repairId
        }
      });
    },
    submitRepair() {
      this.$refs.repairForm.validate(valid => {
        if (valid) {
          this.repairForm.username = this.$store.state.userInfo.username;
          submitRepair(this.repairForm).then(res => {
            if (res.data.result == true) {
              this.$message({
                type: "success",
                message: "报修提交成功，请等待处理"
              });
              this.dialogVisible = false;
              this.$router.push("/user0/repair");
            } else {
              this.$message.error({
                message: "报修提交失败，请稍后再试"
              });
            }
          });
        } else {
          this.$message.error({
            message: "请检查输入信息"
          });
        }
      });
    },
    beforeUpload(file) {
      const checkFileType =
        file.type === "image/jpeg" ||
        file.type === "image/jpg" ||
        file.type === "image/png";
      const checkFileSize = file.size / 1024 / 1024 < 5;
      if (!checkFileType) {
        this.$message.error("上传图片必须是 jpeg/jpg/png 格式！");
      }
      if (!checkFileSize) {
        this.$message.error("上传图片大小不能超过 5MB！");
      }
      if (checkFileType && checkFileSize) this.postData.key = random(16);
      return checkFileType && checkFileSize;
    },
    handleSuccess(response) {
      this.repairForm.pic.push(this.photoUrl + response.key);
      console.log(this.photoUrl + response.key);
    },
    handleRemove(file) {
      Array.prototype.remove = function(val) {
        var index = this.indexOf(val);
        if (index > -1) {
          this.splice(index, 1);
        }
      };

      if (file.url) {
        let removePicture = file.url.substr(file.url.lastIndexOf("/"));
        this.repairForm.pic.remove(removePicture);
        if (!this.repairForm.pic.length) {
          this.hasFmt = false;
          this.$refs.image.validate();
        }
      }
      if (file.response.key) {
        this.repairForm.pic.remove(this.photoUrl + file.response.key);
      }
    },
    handleExceed() {
      this.$message.warning("最多上传 3 张图片");
    }
  },
  created() {
    var token;
    var policy = {};
    // var bucketName = "21push";
    // var AK = "K96MCAU7eCnSWz4XUbxIBe9Q9PUm_gBHfacmsAEf";
    // var SK = "g0eagx-yjztmAo0iVi-Nj8QrsCRGrKhdGKIjpVr9";
    var bucketName = "push21";
    var AK = "slnMazKaSrCowN_nA5Y4i0QwFo62AaZKZQ8h2xOj";
    var SK = "wh8pr5uMd8_SNCxdGZvEh8-Hzy11swN6UaXwhlCF";
    var deadline = 1594028031; // 2020-07-06
    policy.scope = bucketName;
    policy.deadline = deadline;
    token = genToken(AK, SK, policy);
    this.postData.token = token;

    console.log("token = " + token);
  },
  mounted() {
    let user = { username: this.$store.state.userInfo.username };
    getUserRepair(user).then(res => {
      this.userRepair = res.data;
    });
  }
};
</script>
<style>
.infoR {
  margin: auto auto;
  width: 680px;
  background: #fff;
  box-shadow: 0 0 35px #b4bccc;
  padding: 30px 30px 30px 30px;
  border-radius: 10px;
}
</style>