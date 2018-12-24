<template>
  <div class="help-popover">
    <el-popover
      ref="popover"
      placement="right"
      width="400"
      trigger="click">
      <div v-html="markdown" class="markdown-body"></div>
    </el-popover>
    <el-col :span="22">
      <slot></slot>
    </el-col>
    <el-col :span="2">
      <icon name="regular/question-circle" class="question-circle" v-popover:popover></icon>
    </el-col>
  </div>
</template>

<script>
import content from './help-content'
import showdown from 'showdown'
const converter = new showdown.Converter({ emoji: 'true' })
export default {
  name: 'help-popover',
  props: ['name', 'pic'],
  data () {
    let markdown = content
    this.name.split('.').forEach(field => { markdown = markdown[field] })
    if (this.pic) {
      for (let [k, v] of Object.entries(this.pic)) {
        markdown = markdown.replace('{' + k + '}', v)
      }
    }
    return {
      markdown: converter.makeHtml(markdown)
    }
  }
}
</script>

<style>
.help-popover .question-circle{
  cursor:pointer;
  color:#409EFF
}
</style>
