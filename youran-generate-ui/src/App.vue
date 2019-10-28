<template>
  <el-container class="wrapper">
    <el-header class="header">
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
      <div class="header-menu">
        <el-dropdown class="avatar-container" trigger="click">
          <div class="avatar-wrapper">
            <img :src="avatar" class="user-avatar">
            <i class="el-icon-caret-bottom"></i>
          </div>
          <el-dropdown-menu slot="dropdown" class="user-dropdown">
            <el-dropdown-item>
              <span style="display:block;" @click="showSystemDialog">系统信息</span>
            </el-dropdown-item>
          </el-dropdown-menu>
        </el-dropdown>
      </div>
    </el-header>
    <el-container>
      <!-- 左侧导航 -->
      <el-aside class="menu">
        <el-menu :router="true">
          <el-menu-item index="/" :class="{'is-active': isRoutePath('/')}">首页</el-menu-item>
          <el-menu-item index="/project" :class="{'is-active': isRouteIndexOf('/project')}">项目管理</el-menu-item>
          <el-menu-item index="/template" :class="{'is-active': isRouteIndexOf('/template')}">模板管理</el-menu-item>
        </el-menu>
      </el-aside>
      <!-- 右侧主内容区 -->
      <el-main class="content">
        <transition name="slide-fade" mode="out-in">
          <router-view></router-view>
        </transition>
      </el-main>
    </el-container>
    <el-dialog title="系统信息" :visible.sync="systemDialogVisible" width="350px">
      <el-form size="small">
        <el-form-item label="登录用户：" label-width="100px">
          {{systemUserInfo.user}}
        </el-form-item>
        <el-form-item label="系统版本：" label-width="100px">
          {{systemUserInfo.sysVersion}}
        </el-form-item>
      </el-form>
    </el-dialog>
  </el-container>

</template>

<script>
import avatar from '@/assets/avatar.jpg'
import { apiPath } from '@/components/common'
export default {
  name: 'app',
  data () {
    return {
      avatar,
      systemDialogVisible: false,
      systemUserInfo: {
        user: 'admin',
        sysVersion: '1.1.1'
      }
    }
  },
  methods: {
    isRoutePath (path) {
      return this.$route.path === path
    },
    isRouteIndexOf (path) {
      return this.$route.path.indexOf(path) === 0
    },
    showSystemDialog () {
      this.systemDialogVisible = true
    }
  },
  watch: {
    '$route' (to, from) {
      // console.info(to)
    }
  },
  created () {
    this.$ajax.get(`/${apiPath}/system_user/info`)
      .then(response => this.$common.checkResult(response))
      .then(data => {
        this.systemUserInfo = data
      })
  }
}
</script>

<style lang="scss">
  @import 'assets/common.scss';

  .wrapper {
    height: 100%;
    .header {
      background-color: #409EFF;
      .header-logo-wrapper {
        display: inline-block;
        vertical-align: middle;
        margin: 5px 0px;
        width: 50px;
        height: 50px;
        .header-logo {
          fill: #FFFFFF;
        }

        .header-logo:hover {
          -webkit-transform: rotate(720deg);
          transform: rotate(720deg);
          -webkit-transition: -webkit-transform 2s linear;
          transition: transform 2s cubic-bezier(0.25, 0.1, 0.25, 1);
        }
      }

      .header-title {
        margin: 3px 10px;
        display: inline-block;
        vertical-align: middle;
      }

      .header-menu {
        float: right;
        height: 100%;
        .avatar-container {
          .avatar-wrapper {
            margin-top: 10px;
            vertical-align: middle;
            .user-avatar {
              cursor: pointer;
              width: 40px;
              height: 40px;
              border-radius: 10px;
            }

            .el-icon-caret-bottom {
              color: #ffffff;
              cursor: pointer;
              position: absolute;
              right: -15px;
              top: 25px;
              font-size: 12px;
            }
          }
        }
      }

    }

    .menu {
      width:150px!important;
    }

    .content {
      padding: 20px;
    }

  }

</style>
