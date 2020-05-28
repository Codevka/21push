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
        <el-button type="primary" @click.native.prevent="makeComplaint">确认</el-button>
      </div>
    </el-dialog>
  </el-main>
</template>
<script>
import { getUserComplaint } from "../../main";
import { submitComplaint } from "../../main";
import { getToken } from '../../main';
export default {
  data() {
    return {
      addr:'http://qazuhxgj2.bkt.clouddn.com',
      uploadToken: {
        ket:'',
        token:''
      },
      //userComplaint:[],
      userComplaint: [
        {
          complaitId: "",
          houseId: "",
          status: "",
          adminId: ""
        }
      ],
      dialogVisible: false,
      complaintForm: {
        houseId: "",
        username: "",
        content: "",
        pic:""
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
    this.goodsToken()
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