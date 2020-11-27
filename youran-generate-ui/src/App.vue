<template>
  <el-container class="wrapper">
    <el-header class="header">
      <div class="header-logo-wrapper">
        <svg-icon className="header-logo" iconClass="logo"></svg-icon>
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
            <svg-icon className="menu-icon" iconClass="home"></svg-icon>
            <span slot="title">首页</span>
          </el-menu-item>
          <el-menu-item index="/project" :class="{'is-active': isRouteIndexOf('/project')}">
            <svg-icon className="menu-icon" iconClass="project"></svg-icon>
            <span slot="title">项目管理</span>
          </el-menu-item>
          <el-menu-item v-if="this.systemUserInfo.teamEnabled"
                        index="/team" :class="{'is-active': isRouteIndexOf('/team')}">
            <svg-icon className="menu-icon" iconClass="team"></svg-icon>
            <span slot="title">联系人组</span>
          </el-menu-item>
          <!--<el-menu-item v-if="this.systemUserInfo.templateEnabled"-->
          <el-menu-item index="/template" :class="{
                          'is-active': isRouteIndexOf('/template'),
                          'menu-template-show-sfx': menuTemplateShowSfx
                        }">
            <svg-icon className="menu-icon" iconClass="template"></svg-icon>
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
          {{systemUserInfo.username}}
        </el-form-item>
        <el-form-item label="系统版本：" label-width="100px">
          {{systemUserInfo.sysVersion}}
        </el-form-item>
        <!--<el-form-item label="自定义模板：" label-width="100px">
          <el-switch v-model="form.templateEnabled"
                     active-text="开启"
                     inactive-text="关闭" @change="formChange">
          </el-switch>
        </el-form-item>-->
      </el-form>
    </el-dialog>
    <!-- 模板导入对话框 -->
    <import-template></import-template>
    <!-- 文件下载专用iframe -->
    <iframe style="display:none;" :src="downloadUrl"></iframe>
  </el-container>
</template>

<script>
import avatar from '@/assets/avatar.jpg'
import systemApi from '@/api/system'
import { mapState } from 'vuex'
import importTemplate from './views/template/import.vue'
import eventHub from '@/utils/event-hub'

export default {
  name: 'app',
  components: { importTemplate },
  data () {
    return {
      avatar,
      systemDialogVisible: false,
      form: {
        templateEnabled: false
      },
      // 模板管理菜单显示特效
      menuTemplateShowSfx: false,
      // 模板管理菜单显示特效清除任务id
      menuTemplateShowSfxClearTaskId: null
    }
  },
  computed: {
    ...mapState({
      systemUserInfo: state => state.systemUserInfo,
      downloadUrl: state => state.app.downloadUrl
    })
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
    },
    formChange () {
      systemApi.updateUserSetting(
        {
          id: this.systemUserInfo.id,
          templateEnabled: this.form.templateEnabled
        })
        .then(data => {
          this.$store.commit('systemUserInfo/setSystemUserInfo', {
            templateEnabled: data.templateEnabled
          })
          // 处理菜单项（模板管理）的特效
          if (data.templateEnabled) {
            this.menuTemplateShowSfx = true
            this.menuTemplateShowSfxClearTaskId = setTimeout(() => {
              this.menuTemplateShowSfx = false
              this.menuTemplateShowSfxClearTaskId = null
            }, 6000)
          } else {
            if (this.menuTemplateShowSfxClearTaskId) {
              clearTimeout(this.menuTemplateShowSfxClearTaskId)
            }
          }
        })
    }
  },
  watch: {
    '$route' (to, from) {
      // console.info(to)
    }
  },
  created () {
    systemApi.getSystemUserInfo()
      .then(data => {
        this.$store.commit('systemUserInfo/setSystemUserInfo', data)
        this.form.templateEnabled = data.templateEnabled
      })
      .then(() => {
        if (!this.systemUserInfo.templateExists) {
          this.$confirm('系统中还没有任何代码模板，是否需要手动导入模板?', '提示', {
            confirmButtonText: '立即导入',
            cancelButtonText: '不需要导入，随便看看先',
            type: 'warning'
          }).then(() => {
            eventHub.$emit('import-template-show')
          })
        }
      })
  }
}
</script>

<style lang="scss">
  @import 'assets/common.scss';

  /**
   * 菜单显示特效-背景颜色渐变
   */
  @keyframes menu-sfx {
    from{
      background-color: #ff3300;
    }
    to{
      background-color: #FFFFFF;
    }
  }

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
          color: #FFFFFF;
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

      /**
       * 菜单项（模板管理）显示特效
       */
      .menu-template-show-sfx {
        animation: menu-sfx 3s linear;
      }

      .el-menu-item {
        font-size: 16px;
        font-weight: bold;

        .menu-icon {
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
