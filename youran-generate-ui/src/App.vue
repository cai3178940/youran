<template>
  <div id="app">
    <!-- 头部导航 -->
    <header class="header">
      <el-row>
        <el-col :span="24">
          <h2 align="center">youran代码自动化平台</h2>
        </el-col>
      </el-row>
    </header>
    <main>
      <!-- 左侧导航 -->
      <div class="main-left">
        <el-menu class="el-menu-vertical-demo" :router="true">
          <el-menu-item index="/" :class="{'is-active': isRoutePath('/')}">首页</el-menu-item>
          <el-menu-item index="/project" :class="{'is-active': isRouteIndexOf('/project')}">项目管理</el-menu-item>
        </el-menu>
      </div>

      <!-- 右侧主内容区 -->
      <div class="main-right">
        <transition name="fade">
          <router-view class="view"></router-view>
        </transition>
      </div>
    </main>
  </div>
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

  body {
    margin: 0;
  }

  #app {
    min-width: 1200px;
    margin: 0 auto;
    font-family: "Helvetica Neue", "PingFang SC", Arial, sans-serif;
  }

  /* 头部导航 */
  header {
    z-index: 1000;
    min-width: 1200px;
    transition: all 0.5s ease;
    background-color: #fff;
    box-shadow: 0 2px 4px 0 rgba(0, 0, 0, .12), 0 0 6px 0 rgba(0, 0, 0, .04);
  }


  /* 主内容区 */
  main {
    display: -webkit-box;
    display: -ms-flexbox;
    display: flex;
    min-height: 800px;
    border: solid 20px #E9ECF1;
    background-color: #FCFCFC;
  }

  main .main-left {
    text-align: center;
    -webkit-box-flex: 0;
    -ms-flex: 0 0 200px;
    flex: 0 0 200px;
  }

  main .main-right {
    -webkit-box-flex: 1;
    -ms-flex: 1;
    flex: 1;
    background-color: #fff;
    padding: 20px 50px;
  }


  .el-breadcrumb{
    padding-bottom: 20px;
  }
</style>
