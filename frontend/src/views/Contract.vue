<template>
    <el-main class="info">
        <el-page-header @back="goBack" content="订单详情">
        </el-page-header>
        <p v-for="(item,key,index) in contractInfo" :key="key">
            {{contractLabel[index]}}:{{item}}
        </p>  
        <el-button v-if="contractInfo.contractStatus=='1'">缴费
        </el-button>
        <el-button v-if="contractInfo.contractStatus=='0'">退租
        </el-button>
        <el-button v-if="contractInfo.contractStatus=='0'">续租
        </el-button>
    </el-main>
</template>
<script>
//import {getContract} from '../main'
export default {
    data() {
        return {
            contractInfo:{
                contractId:'',
                username:'',
                houseId:'',
                houseLocation:'',
                houseType:'',
                ownerTel:'',
                price:'',
                houseStatus:'',
                contractStatus:'',
            },
            //暂定订单状态: 0:已缴费 1:未缴费
            /*contractLabel:{
                contractId:'订单编号',
                username:'用户名',
                houseId:'房源编号',
                houseLocation:'房源地址',
                houseType:'房源类型',
                ownerTel:'户主手机号',
                price:'价格',
                houseStatus:'房源状态',
                contractStatus:'订单状态'
            }*/
            contractLabel:[
                '订单编号',
                '用户名',
                '房源编号',
                '房源地址',
                '房源类型',
                '户主手机号',
                '价格',
                '房源状态',
                '订单状态'
            ]
        }
    },
    methods: {
        goBack() {
            this.$router.push('/user0/query')
        }
    },
    mounted() {
        /*getContract(this.$router.params.contractId)
        .then(res=> {
            this.contractInfo = res.data;
        })*/
    }
}
</script>
<style scoped>
.info {
  margin: 40px 200px;
  width: 500px;
  background: #fff;
  box-shadow: 0 0 35px #B4BCCC;
  padding: 30px 30px 30px 30px;
  border-radius: 30px; 
}
</style>