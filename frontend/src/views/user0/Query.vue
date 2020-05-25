<template>
<el-main class="infoQ">
    <el-table
      border
      :data="userContract"
      style="width: 100%">
      <el-table-column
        type="index"
        width="50">
      </el-table-column>
      <el-table-column
        prop="contractId"
        label="订单编号"
        width="150">
      </el-table-column>
      <el-table-column
        prop="houseId"
        label="房源编号"
        width="150">
      </el-table-column>
      <el-table-column
        prop="rentTime"
        label="租房时间"
        width="150">
      </el-table-column>
      <el-table-column
        prop="contractTime"
        label="订单生成时间"
        width="150">
      </el-table-column>
      <el-table-column
        prop="contractDuration"
        label="订单持续时间"
        width="150">
      </el-table-column>
      <el-table-column
        prop="price"
        label="价格"
        width="100">
      </el-table-column>
      <el-table-column
        prop="status"
        label="订单状态"
        width="100">
      </el-table-column>
      <el-table-column
        fixed="right"
        label="操作"
        width="150">
        <template slot-scope="scope">
            <el-button
                @click.native.prevent="toContract(scope.$index)"
                size='small'>
                详细信息
            </el-button> 
        </template>
      </el-table-column>
    </el-table>
</el-main>
</template>
<script>
import {getUserContract} from '../../main'
export default {
    data() {
        return {
          //userContract:[]
            userContract:[{
                contractId:'1',
                houseId:'',
                rentTime:'',
                contractTime:'',
                contractDuration:'',
                price:'',
                status:''
            }]
        }
    },
    methods: {
        toContract(index) {
          console.log(this.userContract[index].contractId)
            this.$router.push({
                path:'/contract',
                query:{
                    contractId: this.userContract[index].contractId
                }
            })
        }
    },
    mounted() {
      let user = {username: this.$store.state.userInfo.username}
      getUserContract(user)
      .then(res=> {
        this.userContract = res.data;
      })
    }
}
</script>
<style scoped>
.infoQ {
  margin: 40px auto;
  width: 1250px;
  background: #fff;
  box-shadow: 0 0 35px #B4BCCC;
  padding: 30px 30px 30px 30px;
  border-radius: 10px; 
}
</style>