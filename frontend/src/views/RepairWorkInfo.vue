<template>
  <el-main class="infoRwi">
    <el-page-header @back="goBack" content="维修详情"></el-page-header>
    <!-- <p v-for="(item,key,index) in repairWorkInfo" :key="key">{{repairWorkLabel[index]}}:{{item}}</p> -->
    <p>工单编号：{{repairWorkInfo.repairWorkId}}</p>
    <p>报修编号：{{repairWorkInfo.repairId}}</p>
    <p>房源编号：{{repairWorkInfo.houseId}}</p>
    <p>房源地址：{{repairWorkInfo.location}}</p>
    <p>报修内容：{{repairWorkInfo.content}}</p>
    <p>状态：{{repairWorkInfo.status}}</p>
    <p>回复：{{repairWorkInfo.callback}}</p>
    <p>评价：{{repairWorkInfo.evaluation}}</p>
    <p>租客手机号：{{repairWorkInfo.tel}}</p>
    <div class="image">
      <!-- <el-image v-for="url in repairInfo.pic" :key="url" :src="url" fit="scale-down" lazy></el-image> -->
      <el-carousel v-if="repairWorkInfo.pic!=[]" :interval="5000" arrow="always" :width="500">
        <el-carousel-item v-for="item in repairWorkInfo.pic" :key="item.id">
          <el-row>
            <el-col>
              <el-image :src="item" fit="scale-down" />
            </el-col>
          </el-row>
        </el-carousel-item>
      </el-carousel>
    </div>
    <el-button @click.native.prevent="dialogVisible = true" v-if="repairWorkInfo.status=='未处理'">回复工单</el-button>
    <el-dialog title="回复工单" :visible.sync="dialogVisible">
      <el-form :model="callbackForm" ref="callbackForm" :rules="rule">
        <el-form-item label="回复内容" label-width="100px" prop="callback">
          <el-input placeholder="请输入回复" v-model="callbackForm.callback"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click.native.prevent="makeRepairComment">完成工单</el-button>
      </div>
    </el-dialog>
  </el-main>
</template>
<script>
import { getRepairWork } from "../main";
import { submitRepairWorkCallback } from "../main";
export default {
  data() {
    return {
      dialogVisible: false,
      repairWorkInfo: {
        repairWorkId: "",
        repairId: "",
        houseId: "",
        location: "",
        content: "",
        pic: [],
        status: "未处理",
        callback: "",
        evaluation: "",
        tel: ""
      },
      repairWorkLabel: [
        "工单编号",
        "报修编号",
        "房源编号",
        "房源地址",
        "报修内容",
        "图片",
        "状态",
        "回复",
        "评价",
        "租客手机号"
      ],
      callbackForm: {
        repairWorkId: "",
        callback: ""
      } /*,
            rule: {
                callback:[
                    {
                        required: true,
                        message:'回复内容不能为空',
                        trigger: 'blur'
                    }
                ]
            }*/
    };
  },
  methods: {
    goBack() {
      this.$router.push("/user2/repairWork");
    },
    makeRepairComment() {
      //console.log(this.callbackForm);
      /*this.$refs.callbackForm.validate(valid =>{
                if(valid) {*/
      this.callbackForm.repairWorkLabel = this.repairWorkInfo.repairWorkId;
      submitRepairWorkCallback(this.callbackForm).then(res => {
        if (res.data.result == true) {
          this.$message({
            type: "success",
            message: "维修完成"
          });
          this.dialogVisible = false;
          this.$router.push("/user2/repairWork");
        } else {
          this.$message.error({
            message: "提交失败，请稍后再试"
          });
        }
      });
      /*}
                else {
                    //console.log('callbackSubmit err')
                }
            })*/
    }
  },
  mounted() {
    getRepairWork({ repairId: this.$route.query.repairId }).then(res => {
      this.repairInfo = res.data;
    });
  }
};
</script>
<style>
.infoRwi {
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