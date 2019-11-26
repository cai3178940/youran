<template>
  <div class="help-popover">
    <el-popover
      ref="popover"
      placement="right"
      width="400"
      trigger="click">
      <div v-html="html" class="markdown-body"></div>
    </el-popover>
    <el-col :span="22">
      <slot></slot>
    </el-col>
    <el-col v-if="!!html" :span="2">
      <svg-icon className="question-icon color-primary" iconClass="question" v-popover:popover></svg-icon>
    </el-col>
  </div>
</template>

<script>
import content from './help-content'
import showdown from 'showdown'
const converter = new showdown.Converter({
  emoji: 'true',
  tables: 'true'
})
export default {
  name: 'help-popover',
  props: ['name', 'pic'],
  data () {
    let markdown = content
    let html = ''
    if (this.name) {
      this.name.split('.').forEach(field => { markdown = markdown[field] })
      if (this.pic) {
        for (let [k, v] of Object.entries(this.pic)) {
          markdown = markdown.replace('{' + k + '}', v)
        }
      }
      html = converter.makeHtml(markdown)
    }
    return {
      html: html
    }
  }
}
</script>

<style lang="scss">
  @import '../../assets/common.scss';
  .help-popover .question-icon {
    cursor: pointer;
    font-size: 17px;
    user-select: none;
    color: #409EFF
  }
</style>
