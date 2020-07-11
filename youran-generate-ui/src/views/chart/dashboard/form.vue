<template>
  <div class="dashboardFormDiv">
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/project' }">项目管理</el-breadcrumb-item>
      <el-breadcrumb-item :to="{ path: `/project/${this.projectId}/chart` }">图表管理</el-breadcrumb-item>
      <el-breadcrumb-item>
        {{edit?'编辑':'创建'}}看板
      </el-breadcrumb-item>
    </el-breadcrumb>
    <el-container v-loading="formLoading" style="border:solid 1px #e6e6e6;">
      <el-aside style="width: 350px;">
        <el-form ref="dashboardForm" class="dashboardForm"
                 :rules="rules" :model="form" label-width="70px"
                 size="small">
          <el-form-item label="名称">
            <help-popover name="dashboard.name">
              <el-input v-model="form.name" placeholder="请输入名称"></el-input>
            </help-popover>
          </el-form-item>
          <el-form-item label="标题">
            <help-popover name="dashboard.title">
              <el-input v-model="form.title" placeholder="请输入标题"></el-input>
            </help-popover>
          </el-form-item>
          <el-form-item label="模块名">
            <help-popover name="dashboard.module">
              <el-input v-model="form.module" placeholder="请输入模块名"></el-input>
            </help-popover>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="submit()" tabindex="160">提交</el-button>
            <el-button @click="goBack()" tabindex="180">返回</el-button>
          </el-form-item>
        </el-form>
      </el-aside>
      <el-main style="min-height:500px;border-left:solid 1px #e6e6e6;">
        <el-row>
          <el-col :span="24" style="text-align: right;">
            <el-button size="small" @click.native="handleAddChart"
                       type="warning">增加图表</el-button>
          </el-col>
        </el-row>
        <grid-layout
          :layout.sync="form.layout"
          :col-num="12"
          :row-height="30"
          :margin="[10, 10]"
        >
          <grid-item v-for="item in form.layout"
                     :x="item.x"
                     :y="item.y"
                     :w="item.w"
                     :h="item.h"
                     :i="item.i"
                     :key="item.i"
                     @resize="resizeEvent"
                     @resized="resizeEvent"
          >
            {{item.title}}
          </grid-item>
        </grid-layout>
      </el-main>
    </el-container>
  </div>
</template>

<script>
import model from './model'
import VueGridLayout from 'vue-grid-layout'

export default {
  name: 'dashboard',
  components: {
    GridLayout: VueGridLayout.GridLayout,
    GridItem: VueGridLayout.GridItem
  },
  props: ['projectId', 'dashboardId'],
  data () {
    const edit = !!this.dashboardId
    return {
      edit: edit,
      form: model.initFormBean(this.projectId),
      formLoading: false,
      rules: model.getRules()
    }
  },
  methods: {
    resizeEvent (i, newH, newW, newHPx, newWPx) {
      console.log('H(px)=' + newHPx + ', W(px)=' + newWPx)
      const item = this.layout[i]
      const ref = this.$refs[item.chartName]
      if (ref) {
        const chartComponent = ref[0]
        if (chartComponent.chart) {
          chartComponent.chart.resize({
            width: newWPx,
            height: newHPx
          })
        }
      }
    },
    handleAddChart () {
      // todo
    },
    submit () {
      // todo
    },
    goBack () {
      this.$router.push(`/project/${this.projectId}/chart`)
    }
  }
}
</script>

<style lang="scss">
  @import '../../../assets/common.scss';
  .dashboardFormDiv .dashboardForm {
    @include youran-form;
    padding: 20px 10px;
  }

</style>
