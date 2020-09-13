<template>
  <div class="help-popover" :name="name">
    <el-popover
      ref="popover"
      placement="right"
      width="400"
      trigger="click">
      <div v-html="html" class="markdown-body"></div>
    </el-popover>
    <div style="width: 100%;">
      <slot></slot>
    </div>
    <div style="margin-left:10px; width: 20px;">
      <svg-icon v-if="!!html" className="question-icon color-primary" iconClass="question" v-popover:popover></svg-icon>
    </div>
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
      console.info(this.name, markdown, html)
    }
    return {
      html: html
    }
  }
}
</script>

<style lang="scss">
  @import '../../assets/common.scss';
  .help-popover {
    display: flex;
    .question-icon {
      cursor: pointer;
      font-size: 17px;
      user-select: none;
      color: #409EFF
    }
  }
</style>
