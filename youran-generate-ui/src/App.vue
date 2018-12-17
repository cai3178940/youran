<template>
  <el-container class="wrapper">
    <el-header
      height="80px"
      style="background-color: #409EFF;">
      <div class="header-logo-wrapper">
        <svg version="1.1" xmlns="http://www.w3.org/2000/svg" x="0px" y="0px"
             viewBox="0 0 32 32" class="header-logo" xml:space="preserve">
        <g>
          <path d="M29.3,7.5L16.7,0.2c-0.4-0.3-1-0.3-1.4,0L2.7,7.5C2.2,7.7,2,8.2,2,8.7v14.6c0,0.5,0.3,1,0.7,1.2l12.6,7.3
            c0.2,0.1,0.5,0.2,0.7,0.2c0.2,0,0.5-0.1,0.7-0.2l12.6-7.3c0.4-0.3,0.7-0.7,0.7-1.2V8.7C30,8.2,29.8,7.7,29.3,7.5z M27.2,22.5L16,29
            L4.8,22.5v-13L16,3l11.2,6.5V22.5z"/>
          <path d="M7.1,10.8c-0.4,0.7-0.2,1.5,0.5,1.9l6.9,4v8.7c0,0.8,0.6,1.4,1.4,1.4s1.4-0.6,1.4-1.4v-8.6l6.8-3.8
            c0.7-0.4,0.9-1.2,0.5-1.9c-0.4-0.7-1.2-0.9-1.9-0.5L16,14.2l-6.9-4C8.4,9.9,7.5,10.1,7.1,10.8z"/>
        </g>
        </svg>
      </div>
      <div class="header-title">
        <h2 align="left" style="color:#FFFFFF;margin: 10px 0;">Youran代码自动化平台</h2>
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
