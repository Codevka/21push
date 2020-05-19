<template>
  <div id="app" >
    <el-container>
      <el-header>
        <headerly v-if="showHeader"></headerly>
      </el-header>
      <el-container>
        <transition name="el-zoom-in-center">
          <router-view />
        </transition>
      </el-container>
      <el-footer>
        <footerly v-if="showFooter"></footerly>
      </el-footer>
    </el-container>
  </div>
</template>
<script>
//todo:footer加,header加登录，注册，退出登录按钮
import headerly from '@/components/Header.vue'
import footerly from '@/components/Footer.vue'
export default {
  name: 'app',
  components: {
    headerly,
    footerly
  },
  data() {
    return {
      showHeader: true,
      showFooter: true   
    }
  }
}
</script>
<style>
#app {
  font-family: 'Avenir', Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
}
</style>