<template>
  <div class="chartFormDiv">
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/project' }">项目管理</el-breadcrumb-item>
      <el-breadcrumb-item :to="{ path: `/project/${this.projectId}/chart` }">图表管理</el-breadcrumb-item>
      <el-breadcrumb-item>
        {{edit?'编辑图表':'添加图表'}}
      </el-breadcrumb-item>
    </el-breadcrumb>
    <el-row type="flex" align="middle" :gutter="20">
    </el-row>
  </div>
</template>

<script>
// import projectApi from '@/api/project'
// import chartApi from '@/api/chart'

export default {
  name: 'chartForm',
  props: ['projectId', 'chartId']
}
</script>

<style lang="scss">
  @import '../../assets/common.scss';
  .chartFormDiv .chartForm {
    @include youran-form;

  }

</style>
