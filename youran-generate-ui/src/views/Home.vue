<template>
  <div>
    <el-row>
      <el-col :span="24">
        <div v-html="tutorial" class="markdown-body"></div>
      </el-col>
    </el-row>
  </div>
</template>
<script>
import showdown from 'showdown'
import 'github-markdown-css'
import tutorial from './tutorial/tutorial.md'
const converter = new showdown.Converter()
const tutorialHtml = converter.makeHtml(tutorial)
export default {
  data () {
    return {
      tutorial: tutorialHtml
    }
  }
}
</script>
