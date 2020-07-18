<template>
  <div class="detailListDemoDiv">
    <el-table :data="list" size="mini"
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
  computed: {
    list () {
      return Array.from({ length: 5 },
        (v, i) => ({ column1: '1', column2: '2', column3: '3' }))
    }
  }
}
</script>
