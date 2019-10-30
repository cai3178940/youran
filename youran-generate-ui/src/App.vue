<template>
  <el-container class="wrapper">
    <el-header class="header">
      <div class="header-logo-wrapper">
        <svg class="icon header-logo" aria-hidden="true">
          <use xlink:href="#icon-youran"></use>
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
          <el-menu-item index="/" :class="{'is-active': isRoutePath('/')}">
            <svg class="icon" aria-hidden="true">
              <use xlink:href="#icon-Home"></use>
            </svg>
            <span slot="title">首页</span>
          </el-menu-item>
          <el-menu-item index="/project" :class="{'is-active': isRouteIndexOf('/project')}">
            <svg class="icon" aria-hidden="true">
              <use xlink:href="#icon-project1"></use>
            </svg>
            <span slot="title">项目管理</span>
          </el-menu-item>
          <el-menu-item index="/template" :class="{'is-active': isRouteIndexOf('/template')}">
            <svg class="icon" aria-hidden="true">
              <use xlink:href="#icon-TEMPLATE"></use>
            </svg>
            <span slot="title">模板管理</span>
          </el-menu-item>
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
import '@/assets/icon/iconfont.js'
import { apiPath } from '@/components/common'
import { mapState, mapMutations } from 'vuex'

export default {
  name: 'app',
  data () {
    return {
      avatar,
      systemDialogVisible: false
    }
  },
  computed: {
    ...mapState([
      'systemUserInfo'
    ])
  },
  methods: {
    ...mapMutations([
      'setSystemUserInfo'
    ]),
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
        this.setSystemUserInfo(data)
      })
  }
}
</script>

<style lang="scss">
  @import 'assets/common.scss';

  .wrapper {
    height: 100%;
    .header {
      padding-left: 10px;
      background-color: #409EFF;
      .header-logo-wrapper {
        display: inline-block;
        vertical-align: middle;
        margin: 5px 0px;
        width: 50px;
        height: 50px;
        .header-logo {
          fill: #FFFFFF;
          height: 50px;
          width: 50px;
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

      .el-menu-item {
        font-size: 16px;
        font-weight: bold;

        .icon {
          margin-right: 5px;
          font-size: 25px;
          vertical-align: middle;
        }

      }

    }

    .content {
      padding: 20px;
    }

  }

</style>
