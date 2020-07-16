<template>
  <div class="detailListDemoDiv">
    <el-table :data="list" :max-height="maxHeight" size="mini"
              style="width: 100%;" :border="true">
      <el-table-column property="column1" label="第一列"></el-table-column>
      <el-table-column property="column2" label="第二列"></el-table-column>
      <el-table-column property="column3" label="第三列"></el-table-column>
    </el-table>
  </div>
</template>
<script>
export default {
  name: 'detailListDemo',
  data () {
    return {
      maxHeight: 200
    }
  },
  computed: {
    list () {
      return Array.from({ length: 5 },
        (v, i) => ({ column1: '1', column2: '2', column3: '3' }))
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
