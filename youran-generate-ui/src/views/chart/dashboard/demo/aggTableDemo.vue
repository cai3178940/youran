<template>
  <div class="aggTableDemoDiv">
    <el-table :data="list" :max-height="maxHeight" size="mini"
              style="width: 100%;" :border="true">
      <el-table-column property="d1" label="维度"></el-table-column>
      <el-table-column property="m1" label="指标A"></el-table-column>
      <el-table-column property="m2" label="指标B"></el-table-column>
    </el-table>
  </div>
</template>
<script>
export default {
  name: 'aggTableDemo',
  data () {
    return {
      maxHeight: 200
    }
  },
  computed: {
    list () {
      return Array.from({ length: 5 },
        (v, i) => ({
          d1: '维度值' + i,
          m1: Math.ceil(Math.random() * 100),
          m2: Math.ceil(Math.random() * 100)
        }))
    }
  },
  methods: {
    resize () {
      this.maxHeight = this.$parent.$el.clientHeight - 40
    }
  },
  mounted () {
    setTimeout(() => {
      this.resize()
    }, 100)
  }
}
</script>
