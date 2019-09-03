<template>
  <div class="codePreview">
    <el-dialog title="代码预览" :visible.sync="visible" :fullscreen="true">
      <el-container style="height: 100%; border: 1px solid #eee">
        <el-aside width="250px" v-loading="codeTreeLoading" style="background-color: rgb(238, 241, 246)">
          <el-tree :props="treeProps"
                   :data="codeTree.tree"
                   :render-content="renderTreeNode"
                   @node-click="nodeClick">
          </el-tree>
        </el-aside>
        <el-container>
          <!--<el-header style="text-align: right; font-size: 12px">
            这是导航条
          </el-header>-->
          <el-main v-loading="fileContentLoading">
            <div class="codeDiv" v-html="fileContent"></div>
          </el-main>
        </el-container>
      </el-container>
    </el-dialog>
  </div>
</template>

<script>
import { apiPath } from '@/components/common'
import TypeToIcon from '@/components/type-to-icon'
import '@/assets/Hawcons/style.css'
import showdown from 'showdown'
const converter = new showdown.Converter()

export default {
  name: 'code-preview',
  data () {
    return {
      treeProps: {
        children: 'children',
        label: 'name'
      },
      // 后端返回的代码目录数据
      codeTree: {
        projectId: null,
        projectVersion: null,
        tree: []
      },
      codeTreeLoading: false,
      currentNode: null,
      fileContent: '',
      fileContentLoading: false,
      visible: false
    }
  },
  methods: {
    show (projectId) {
      this.visible = true
      this.queryCodeTree(projectId)
    },

    queryCodeTree (projectId) {
      this.codeTreeLoading = true
      return this.$ajax.get(`/${apiPath}/code_preview/${projectId}/code_tree`)
        .then(response => this.$common.checkResult(response))
        .then(data => { this.codeTree = data })
        .catch(error => this.$common.showNotifyError(error))
        .finally(() => { this.codeTreeLoading = false })
    },
    renderTreeNode (h, { node, data, store }) {
      const icon = data.dir ? 'hawcons-icon-94-folder' : TypeToIcon.getIcon(data.type)
      return h('span', [
        h('i', { attrs: { class: icon } }),
        h('span', '     '),
        h('span', node.label)
      ])
    },
    nodeClick (data, node) {
      this.$ajax.get(`/${apiPath}/code_preview/${this.codeTree.projectId}/file_content?projectVersion=${this.codeTree.projectId}&filePath=${encodeURIComponent(data.path)}`, { responseType: 'text' })
        .then(response => this.$common.checkResult(response))
        .then(data => {
          this.fileContent = converter.makeHtml('```\n' + data + '\n```')
        })
        .catch(error => this.$common.showNotifyError(error))
    }
  }
}
</script>

<style lang="scss">
  @import '../../assets/common.scss';
  $white :  #FFFFFF;

  .codePreview {
    .codeDiv {
      width: 100%;
      height: 100%;
    }

    .el-dialog__header {
      background-color: #409EFF;
      padding: 10px 20px 10px;
    }

    .el-dialog__title {
      color: $white;
    }

    .el-dialog__headerbtn {
      top: 15px;
    }

    .el-dialog__close {
      color: $white;
    }

    .el-dialog__close:hover {
      color: $white;
    }

    .el-dialog__body {
      height: calc(100% - 55px);
      padding: 0px;
    }
    .el-tree {
      min-width: 100%;
      display:inline-block !important;
    }
  }
</style>
