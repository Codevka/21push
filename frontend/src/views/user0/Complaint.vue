<template>
    <el-main class="info">
        <p align="right">
            <el-button @click.native.prevent="dialogVisible=true" type="primary">发起投诉</el-button>
        </p>
        <el-table
            border
            :data="userComplaint"
            style="width: 100%">
            <el-table-column
                type="index"
                width="50">
            </el-table-column>
            <el-table-column
                prop="complaintId"
                label="投诉编号"
                width="150">
            </el-table-column>
            <el-table-column
                prop="houseId"
                label="房源编号"
                width="150">
            </el-table-column>
            <el-table-column
                prop="adminId"
                label="处理人id"
                width="150">
            </el-table-column>
            <el-table-column
                prop="status"
                label="处理状态"
                width="150">
            </el-table-column>
            <el-table-column
                fixed="right"
                label="操作"
                width="100">
                <template slot-scope="scope">
                    <el-button
                        @click.native.prevent="toComplaintInfo(scope.$index)"
                        size='small'>
                        详细信息
                    </el-button> 
                </template>
            </el-table-column>
        </el-table>
        <el-dialog 
            :visible.sync="dialogVisible" 
            title="投诉">
            <el-form
                :model="complaintForm"
                :rules="rule"
                ref="complaintForm"
                label-width="100px">
                <el-form-item 
                    prop="houseId"
                    label="房源编号">
                    <el-input 
                        placeholder="房源编号"
                        v-model="complaintForm.houseId">
                    </el-input>
                </el-form-item>
                <el-form-item 
                    prop="content"
                    label="投诉内容">
                    <el-input 
                        placeholder="投诉内容"
                        v-model="complaintForm.content">
                    </el-input>
                </el-form-item>
                <el-form-item label='图片'>
                    <el-upload 
                         action="https://jsonplaceholder.typicode.com/posts/"
                         multiple
                         :limit="3"
                         :on-exceed="handleExceed"
                         :file-list="complaintForm.pic">
                        <el-button size="small" type="primary">上传图片</el-button>
                        <div slot="tip">最多上传3个图片</div>
                    </el-upload>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button type='primary' @click.native.prevent="makeComplaint">确认</el-button>
            </div>
        </el-dialog>
    </el-main>
</template>
<script>
import {getUserComplaint} from '../../main'
import {submitComplaint} from '../../main'
export default {
    data() {
        return {
            //userComplaint:[],
            userComplaint:[{
                repairId:'',
                houseId:'',
                status:'',
                adminId:''
            }],
            dialogVisible:false,
            complaintForm:{
                houseId:'',
                userId:'',
                content:'',
                pic:[]
            },
            rule:{
                houseId:[
                    {
                        required: true,
                        message:'房源编号不能为空',
                        trigger: 'blur'
                    }
                ],
                content:[
                    {
                        required: true,
                        message:'投诉内容不能为空',
                        trigger: 'blur'
                    }
                ],
            }
        }
    },
    methods: {
        toComplaintInfo(index) {
            this.$router.push({
                path:'/complaintInfo',
                query:{
                    complaintId: this.userComplaint[index].complaintId
                }
            })
        },
        handleExceed(files, fileList) {
            this.$message.warning(
                `当前限制选择 3 个文件，本次选择了 ${files.length} 个文件，共选择了 ${files.length + fileList.length} 个文件`
            )
        },
        makeComplaint() {
            this.$refs.complaintForm.validate(valid =>{
                if(valid) {
                    this.complaintForm.userId = this.$store.state.userInfo.userId
                    submitComplaint(this.complaintForm)
                    .then(()=>{
                        this.$message({
                            type:'success',
                            message:'投诉提交成功,请等待处理'
                        })
                        this.dialogVisible=false
                        this.$router.push('/user0/complaint')
                    })
                }
                else {
                    console.log('submitComplaint err.')
                }
            })
        }
    },
    mounted() {
        let user = {username: this.$store.state.userInfo.username}
        getUserComplaint(user)
        .then(res=> {
            this.userComplaint= res.data;
        })
    }
}
</script>
<style>
.info {
  margin: auto auto;
  width: 830px;
  background: #fff;
  box-shadow: 0 0 35px #B4BCCC;
  padding: 30px 30px 30px 30px;
  border-radius: 10px; 
}
</style>