<template>
    <el-container>
        <el-main class='info'>
            <el-input v-model='keyword' placeholder="请输入搜索关键词">
                <el-button slot="append" icon="el-icon-search" circle @click='sHouses'></el-button>
            </el-input>
            <el-table
            border
            :data="houseData"
            style="width: 100%">
            <el-table-column
                type="index"
                width="50">
            </el-table-column>
            <el-table-column
                prop="houseId"
                label="房源编号"
                width="200">
            </el-table-column>
            <el-table-column
                prop="area"
                label="地区"
                width="150">
            </el-table-column>
            <el-table-column
                prop="address"
                label="地址"
                width="400">
            </el-table-column>
            <el-table-column
                prop="rentType"
                label="租房形式"
                width="150">
            </el-table-column>
            <el-table-column
                prop="houseType"
                label="房间类型"
                width="150">
            </el-table-column>
            <el-table-column
                fixed="right"
                label="操作"
                width="150">
                <template slot-scope="scope">
                    <el-button
                        @click.native.prevent="toHouse(scope.$index)"
                        size='small'>
                        详细信息
                    </el-button> 
                </template>
            </el-table-column>
            </el-table>        
        </el-main>
    </el-container>
</template>
<script>
import {searchHouses} from '../../main'
export default {
    data() {
        return {
            keyword:'',
            //houseData:[]
            houseData:[{
                houseId:'',
                area:'',
                address:'',
                rentType:'',
                houseType:'',
            }]
        }
    },
    methods: {
        sHouses() {
            let params={keyword:this.keyword}
            searchHouses(params)
            .then(res =>{
                this.houseData = res.data
                this.$message ({
                    type:'success',
                    message:'搜索成功'
                })
            })
        },
        toHouse(index) {
            this.$router.push({
                path:'/house',
                query:{
                    houseId: this.houseData[index].houseId
                }
            })
        }
    },
}
</script>
<style>
.info {
  margin: 40px auto;
  width: 1000px;
  background: #fff;
  box-shadow: 0 0 35px #B4BCCC;
  padding: 30px 30px 30px 30px;
  border-radius: 30px; 
}
</style>