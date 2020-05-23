<template>
    <el-main>
        <el-page-header @back="goBack" content="房源详情">
        </el-page-header>
        <p v-for="(item,key,index) in houseInfo" :key="key">
            {{houseLabel[index]}}:{{item}}
        </p>
    </el-main>
</template>
<script>
import {getHouse} from '../main'
export default {
    data() {
        return {
            houseInfo:{
                houseId:'',
                area:'',
                address:'',
                rentType:'',
                houseType:'',
                intro:'',
                tel:'',
                price:'',
                housestatus:''
            },
            houseLabel:[
                '房源编号',
                '地区',
                '地址',
                '租房形式',
                '房间类型',
                '介绍',
                '户主电话',
                '价格',
                '房源状态',
            ]
        }
    },
    methods: {
        goBack(){
            this.$router.push('/user0/rent')
        }
    },
    mounted() {
        getHouse({houseId:this.route.query.houseId})
        .then(res =>{
            this.houseInfo = res.data
        })
    }
}
</script>