<template>
    <el-main class="infoRi">
        <el-page-header @back="goBack" content="报修详情">
        </el-page-header>
        <p v-for="(item,key,index) in repairInfo" :key="key">
            {{repairLabel[index]}}:{{item}}
        </p>  
        <el-button @click.native.prevent="dialogVisible = true" v-if="repairInfo.status=='未评价'">评价</el-button>
        <el-dialog title="评价" :visible.sync="dialogVisible">
            <el-form 
                :model="commentForm"
                ref="commentForm"
                :rules="rule">
                <el-form-item label="评价内容" label-width="100px" prop="evalution">
                    <el-input 
                        placeholder="请输入评价" 
                        v-model="commentForm.evalution">
                    </el-input>
                </el-form-item>
                <el-form-item  label="评分" label-width="100px" prop="score">
                    <el-rate
                        v-model.number="commentForm.score">
                    </el-rate>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button type='primary' @click.native.prevent="makeRepairComment">确认</el-button>
            </div>
        </el-dialog>
    </el-main>
</template>
<script>
import {getRepair} from '../main'
import {submitRepairComment} from '../main'
export default {
    data() {
        return {
            dialogVisible :false,
            repairInfo:{
                repairId:'',
                houseId:'',
                content:'',
                status:'未评价',
                evalution:'',
                score:''
            },
            repairLabel:[
                '报修编号','房源编号','报修内容','处理状态','评价内容','评分'
            ],
            commentForm:{
                repairId:'',
                evalution:'',
                score:''
            },
            rule: {
                evalution:[
                    {
                        required: true,
                        message:'评价内容不能为空',
                        trigger: 'blur'
                    }
                ],
                score:[
                    {
                        required: true,
                        range:[1,5],
                        message:'评分不能为空',
                        trigger: 'blur'
                    }
                ]
            }
        }
    },
    methods: {
        goBack() {
            this.$router.push('/user0/repair')
        },
        makeRepairComment() {
            console.log(this.commentForm)
            this.$refs.commentForm.validate(valid =>{
                if(valid) {
                    this.commentForm.repairId = this.repairInfo.repairId
                    submitRepairComment(this.commentForm)
                    .then((res)=> {
                        if(res.data.result==true){
                            this.$message({
                                type:'success',
                                message:'评价成功'
                            })
                            this.dialogVisible = false
                            this.$router.push('/user0/repair')
                        }
                        else {
                            this.$message.error({
                                message:'评价失败,请稍后再试'
                            })
                        }
                    })
                }
                else {
                    console.log('commentSubmit err')
                }
            })
            
        }
    },
    mounted() {
        getRepair({repairId:this.$route.query.repairId})
        .then(res =>{
            this.repairInfo = res.data
        })
    }    
}
</script>
<style>
.infoRi {
  margin: 40px 400px;
  width: 500px;
  background: #fff;
  box-shadow: 0 0 35px #B4BCCC;
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