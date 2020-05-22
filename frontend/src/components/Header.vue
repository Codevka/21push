<template>
    <el-row class="header">
        <el-col :span="20">
            <el-menu
                :default-active="$route.path"
                class="menu"
                router
                mode="horizontal"
                @select="handleSelect">
                <template
                    v-for="route in $router.options.routes">
                    <template v-if="route.type==userType">
                        <el-menu-item
                            v-if="!route.hasChild" 
                            :key="route.path" 
                            :index="route.path" >
                            {{ route.name }}
                        </el-menu-item>
                        <template 
                            v-else>
                            <el-menu-item 
                                v-for="child in route.children" 
                                :key="child.path"
                                :index="child.path">
                                {{ child.name }}
                            </el-menu-item>
                        </template>
                    </template>
                </template>
            </el-menu>
        </el-col>
        <el-col :span="4">
             <div style="text-align:center">
            <el-button class="button" v-if="userType>=0" @click.native="logout" >退出登录</el-button>
             </div>
        </el-col>
    </el-row>
</template>
<script>
export default {
    data() {
        return {
            //userType:"-1"
        }
    },
    computed : {
        userType() {
            //return this.$store.getters.userType
            return this.$store.getters.userType
            //return -1;
        }
    },
    methods: {
        handleSelect (key,index) {
            this.$router.push(index);
        },
        logout() {
            this.$store.dispatch('commitLogout')
            this.$router.push('/login')
        }
    },
    mounted() {
        let userInfo = sessionStorage.getItem('userInfo')
        if(userInfo !== null) {
            this.$store.dispatch("commitLogin");
        }
    }
}
</script>
<style scoped>
.header {
  width: 100%;
  height: 61px;
  margin: 0;
  background: #fff;
  position: fixed;
  top: 0;
  left: 0;
  box-shadow: 0 0 5px #666;
}
.button {
  margin: 10px 0;
}
</style>