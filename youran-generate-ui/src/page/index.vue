<template>
  <div>
    <el-row>
      <el-col :span="24" v-html="tutorial">
      </el-col>
    </el-row>
  </div>
</template>
<script>

  import showdown from 'showdown'
  import tutorial from './tutorial/tutorial.md'
  console.info(tutorial)
  const converter = new showdown.Converter()
  const tutorialHtml = converter.makeHtml(tutorial)
  export default {
    data: function () {
      return {
        tutorial:tutorialHtml
      }
    }
  }


</script>
