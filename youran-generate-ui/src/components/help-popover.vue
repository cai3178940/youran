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
      <icon name="question-circle-o" class="question-circle-o" v-popover:popover></icon>
    </el-col>
  </div>
</template>

<script>
  import content from '@/components/help-content.js'
  import showdown from 'showdown'
  const converter = new showdown.Converter({emoji: 'true'})
  export default {
    name: 'help-popover',
    props: ['name'],
    data: function () {
      var markdown = content
      this.name.split('.').forEach(field=>markdown = markdown[field]);
      return {
        markdown:converter.makeHtml(markdown)
      }
    }
  }

</script>

<style>
.help-popover .question-circle-o{
  cursor:pointer;
  color:#409EFF
}
</style>
