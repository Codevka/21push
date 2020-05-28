<template>
  <el-main class="infoR">
    <p align="right">
      <el-button @click.native.prevent="dialogVisible=true" type="primary">发起报修</el-button>
    </p>
    <el-table border :data="userRepair" style="width: 100%">
      <el-table-column type="index" width="50"></el-table-column>
      <el-table-column prop="repairId" label="报修编号" width="150"></el-table-column>
      <el-table-column prop="houseId" label="房源编号" width="150"></el-table-column>
      <el-table-column prop="status" label="处理状态" width="150"></el-table-column>
      <el-table-column fixed="right" label="操作" width="100">
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
        <el-form-item label="图片">
          <el-upload
            action="https://upload-z1.qiniup.com"
            :on-success="handleSuccess"
            :on-remove="handleRemove"
            :before-upload="beforeUpload"
            :on-error="handleError"
            :file-list="fileList"
            :limit="2"
            :data="uploadToken"
            multiple
          >
            <el-button size="small" type="primary">上传图片</el-button>
            <div slot="tip">只能上传jpg/png/jpeg图片,最多上传3个图片,图片大小不能超过2mb</div>
          </el-upload>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click.native.prevent="makeRepair">确认</el-button>
      </div>
    </el-dialog>
  </el-main>
</template>
<script>
import { getUserRepair } from "../../main";
import { submitRepair } from "../../main";
import { getToken } from '../../main';
export default {
  data() {
    return {
      addr:'http://qazuhxgj2.bkt.clouddn.com',
      uploadToken: {
        ket:'',
        token:''
      },
      //userRepair:[],
      userRepair: [
        {
          repairId: "",
          houseId: "",
          status: ""
        }
      ],
      dialogVisible: false,
      repairForm: {
        houseId: "",
        username: "",
        content: "",
        pic: ""
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
            message: "报修内容不能为空",
            trigger: "blur"
          }
        ]
      }
    };
  },
  methods: {
    handleError() {
      this.$message.error({
        message:'图片上传出错,请稍后再试',
        center: true
      })
    },
    handleSuccess(response) {
        this.complaintForm.pic= `${this.addr}/${response.key}`
    },
    handleRemove() {
      this.complaintForm.pic = ""
    },
    beforeUpload(file) {
      if(file.type!='image/png' && file.type!='image/jpg' && file.type!='image/jpeg') {
        this.$message.error({
          message:'图片格式错误'
        })
        return false
      }
      if(file.size/1024/1024 >2) {
        this.$message.error({
          message:'图片过大'
        })
        return false
      }
      this.uploadToken.key = `upload_pic_${file.name}`
    },
    goodsToken() {
      getToken()
      .then(res =>{
        this.uploadToken.token = res.data
      })
    },
    handleExceed(files, fileList) {
      this.$message.warning(
        `当前限制选择 3 个文件，本次选择了 ${
          files.length
        } 个文件，共选择了 ${files.length + fileList.length} 个文件`
      );
    },
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
                message: "报修提交成功,请等待处理"
              });
              this.dialogVisible = false;
              this.$router.push("/user0/repair");
            } else {
              this.$message.error({
                message: "投诉提交失败,请稍后再试"
              });
            }
          });
        } else {
          console.log("submitRepair err.");
        }
      });
    }
  },
  mounted() {
    this.goodsToken();
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