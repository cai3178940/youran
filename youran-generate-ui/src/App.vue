<template>
  <el-container class="wrapper">
    <el-header
      height="80px"
      style="background-color: #409EFF;">
      <div class="header-logo">
        <img src="./assets/logo.png" alt="element-logo" style="width: 60px; height: 60px;">
      </div>
      <div class="header-title">
        <h2 align="left" style="color:#FFFFFF;margin: 10px 0;">youran代码自动化平台</h2>
      </div>
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
      isRoutePath: function (path) {
        return this.$route.path === path
      },
      isRouteIndexOf: function (path) {
        return this.$route.path.indexOf(path) === 0
      }
    },
    created: function () {

    },
    watch: {
      '$route': function (to, from) {
        // console.info(to)
      }
    }
  }
</script>


