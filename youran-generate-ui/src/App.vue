<template>
  <el-container class="wrapper">
    <el-header
      height="60px"
      style="background-color: #409EFF;">
          <h2 align="left" style="color:#FFFFFF;margin: 10px 0;">youran代码自动化平台</h2>
    </el-header>
    <el-container>
      <!-- 左侧导航 -->
      <el-aside class="menu" style="width:250px;">
        <el-menu class="el-menu-vertical-demo" :router="true">
          <el-menu-item index="/" :class="{'is-active': isRoutePath('/')}">首页</el-menu-item>
          <el-menu-item index="/project" :class="{'is-active': isRouteIndexOf('/project')}">项目管理</el-menu-item>
        </el-menu>
      </el-aside>

      <!-- 右侧主内容区 -->
      <el-main class="content">
        <transition name="slide-fade" mode="out-in">
          <router-view></router-view>
        </transition>
      </el-main>
    </el-container>
  </el-container>
</template>

<script>
  export default {
    name: 'app',
    data: function () {
      return {

      }
    },
    methods: {
      isRoutePath:function (path) {
        return this.$route.path==path
      },
      isRouteIndexOf:function (path) {
        return this.$route.path.indexOf(path)==0
      }
    },
    created: function () {

    },
    watch: {
      '$route': function (to, from) {
        //console.info(to)
      }
    }
  }
</script>

<style>

  .slide-fade-enter-active {
    animation: fade-in .2s;
  }
  .slide-fade-leave-active {
    animation: fade-out .4s;
  }
  @keyframes fade-in {
    0% {
      opacity: 0;
    }
    100% {
      opacity: 100;
    }
  }
  @keyframes fade-out {
    0% {
      /*transform: translateX(0%);*/
      opacity: 100;
    }
    100% {
      /*transform: translateX(90%);*/
      opacity: 0;
    }
  }

</style>
