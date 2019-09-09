<template>
  <div class="codePreview">
    <el-dialog :title="title" :visible.sync="visible" :fullscreen="true">
      <el-header class="codePath">
        <template v-for="node in paths">
          <span :key="node.key">{{node.name}}</span>
        </template>
      </el-header>
      <el-container class="codeContainer">
        <el-aside width="250px" v-loading="codeTreeLoading" class="codeAside">
          <el-tree :props="treeProps"
                   :data="codeTree.tree"
                   :render-content="renderTreeNode"
                   @node-click="nodeClick">
          </el-tree>
        </el-aside>
        <el-container>
          <el-main v-loading="fileContentLoading" class="codeMain">
            <codemirror v-model="fileContent" :options="cmOptions"></codemirror>
          </el-main>
        </el-container>
      </el-container>
    </el-dialog>
  </div>
</template>

<script>
import { apiPath } from '@/components/common'
import FileTypeUtil from '@/components/file-type-util'
import '@/assets/Hawcons/style.css'
import { codemirror } from 'vue-codemirror'
import 'codemirror/lib/codemirror.css'
import 'codemirror/theme/base16-dark.css'
import 'codemirror/mode/clike/clike.js'
import 'codemirror/mode/xml/xml.js'
import 'codemirror/mode/yaml/yaml.js'
import 'codemirror/mode/properties/properties.js'
import 'codemirror/mode/sql/sql.js'
import 'codemirror/mode/markdown/markdown.js'

export default {
  name: 'code-preview',
  components: {
    codemirror
  },
  data () {
    return {
      title: '',
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
      cmOptions: {
        readOnly: true,
        mode: 'text/x-java',
        theme: 'base16-dark',
        lineNumbers: true,
        line: true
      },
      codeTreeLoading: false,
      currentNode: null,
      paths: [],
      fileContent: '',
      fileContentLoading: false,
      visible: false
    }
  },
  methods: {
    show (projectId, title) {
      this.visible = true
      this.title = title
      this.tree = []
      this.fileContent = ''
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
      const icon = data.dir ? 'hawcons-icon-94-folder' : FileTypeUtil.getIcon(data.type)
      return h('span', [
        h('i', { attrs: { class: icon } }),
        h('span', '     '),
        h('span', node.label)
      ])
    },
    parsePath (path) {
      const paths = path.split('/').filter(p => p)
      console.info(paths)
    },
    nodeClick (data, node) {
      if (this.currentNode === data) {
        return
      }
      this.currentNode = data
      this.parsePath(data.path)
      if (data.dir) {
        return
      }
      this.$ajax.get(`/${apiPath}/code_preview/${this.codeTree.projectId}/file_content?projectVersion=${this.codeTree.projectId}&filePath=${encodeURIComponent(data.path)}`, { responseType: 'text' })
        .then(response => this.$common.checkResult(response))
        .then(fileData => {
          this.cmOptions.mode = FileTypeUtil.getCmMode(data.type)
          this.fileContent = fileData
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
      background-color: #3c3f41;
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

    .el-loading-mask{
      background-color: #5d6164;
    }
    .el-dialog__body {
      height: calc(100% - 55px);
      padding: 0px;
    }
    .el-tree {
      min-width: 100%;
      display:inline-block !important;
    }
    .CodeMirror {
      border: 1px solid #5a5b5f;
      height: auto;
    }
    .codeContainer {
      height: 100%;
      border: 1px solid #5a5b5f;
    }
    .codeMain {
      background-color: #313335;
    }
    .codeAside {
      background-color: #3c3f41;
    }
    .codePath {
      background-color: #3c3f41;
      border: 1px solid #5a5b5f;
      height: 20px !important;
    }
    .el-tree-node__content{
      background-color: #3c3f41;
    }
    .el-tree-node:focus>.el-tree-node__content {
      background-color: #0d293e;
    }
    .el-tree{
      color: #adadad;
    }
  }
</style>
