<template>
  <div class="fieldList">
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/project' }">项目管理</el-breadcrumb-item>
      <el-breadcrumb-item :to="{ path: `/project/${this.projectId}/entity` }">实体管理</el-breadcrumb-item>
      <el-breadcrumb-item>字段管理</el-breadcrumb-item>
    </el-breadcrumb>
    <el-row type="flex" align="middle" :gutter="20" style="padding:10px 0;">
      <el-col :span="4" class="activeNum">
        已选择{{ activeNum }}个字段
      </el-col>
      <el-col :span="20" style="text-align: right;">
        <el-form :inline="true" :model="queryForm" class="demo-form-inline">
          <el-form-item>
            <el-cascader
              placeholder="请选择实体"
              :options="queryForm.projectEntityOptions"
              v-model="queryForm.projectEntity"
              @active-item-change="handleProjectChange"
              @change="handleQuery">
            </el-cascader>
          </el-form-item>
          <el-form-item>
            <help-popover name="fieldListHelp" :pic="{copyField:copyFieldUrl}">
              <el-button @click.native="addTemplateFormVisible = true;templateForm.template=''" type="success">添加</el-button>
              <el-button @click.native="handleDel" type="danger">删除</el-button>
              <el-badge :value="cacheTemplateCount" class="item">
                <el-button @click.native="handleCopy" type="warning" style="margin: 0 0 0 10px;">复制为模板</el-button>
              </el-badge>
            </help-popover>
          </el-form-item>
        </el-form>
      </el-col>
    </el-row>
    <el-table :data="entities" style="width: 100%" @selection-change="selectionChange" v-loading="loading">
      <el-table-column type="selection" width="50"></el-table-column>
      <el-table-column property="fieldDesc" label="字段描述"></el-table-column>
      <el-table-column label="字段名">
        <template slot-scope="scope">
          {{ scope.row.jfieldName }} / {{ scope.row.fieldName }}
        </template>
      </el-table-column>
      <el-table-column label="字段类型" width="200px">
        <template slot-scope="scope">
          {{ scope.row.jfieldType | optionLabel('jfieldTypeOptions')}}
          / {{ scope.row.fieldType | optionLabel('fieldTypeOptions') }}{{scope.row | lengthAndScale}}
        </template>
      </el-table-column>
      <el-table-column label="非空" width="50px">
        <template slot-scope="scope">
          <icon v-if="scope.row.notNull==1" name="check" class="color-success"></icon>
          <icon v-if="scope.row.notNull!=1" name="close" class="color-danger"></icon>
        </template>
      </el-table-column>
      <el-table-column label="主外键" width="70px">
        <template slot-scope="scope">
          <el-tooltip v-if="scope.row.primaryKey==1" class="item" effect="dark" content="主键" placement="right">
            <icon name="key" class="color-warning"></icon>
          </el-tooltip>
          <el-tooltip v-if="scope.row.foreignKey==1" class="item" effect="dark" content="外键(点击扩展级联展示字段)" placement="right">
            <el-button @click="handleShowCascadeExt(scope.row)" type="text" size="medium">
              <icon name="key" class="color-primary"></icon>
            </el-button>
          </el-tooltip>
        </template>
      </el-table-column>
      <el-table-column property="orderNo" label="排序" width="50px"></el-table-column>
      <el-table-column property="fieldExample" label="字段示例"></el-table-column>
      <el-table-column
        label="操作"
        width="140">
        <template slot-scope="scope">
          <el-button @click="handleShow(scope.row)" type="text" size="medium">查看</el-button>
          <el-button @click="handleEdit(scope.row)" type="text" size="medium">编辑</el-button>
          <el-button @click="handleCopyOne(scope.row)" type="text" size="medium">复制</el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-dialog title="请选择字段模板" :visible.sync="addTemplateFormVisible" width="30%">
      <el-form :model="templateForm">

        <el-form-item label="模式：" label-width="100px">
          <el-radio-group v-model="templateForm.multiple">
            <el-radio border label="0">表单编辑</el-radio>
            <el-radio border label="1">批量保存</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item v-show="templateForm.multiple=='0'" label="模板：" label-width="100px">
          <el-select v-model="templateForm.template">
            <el-option-group>
              <el-option label="不使用模板" value=""></el-option>
            </el-option-group>
            <el-option-group v-if="cacheTemplateCount>0" label="临时模板">
              <el-option v-for="value in cacheTemplate"
                         :key="value.fieldId"
                         :label="value.fieldDesc"
                         :value="value.fieldId">
                <span style="float: left">{{ value.fieldDesc }}</span>
                <span style="float: right; color: #8492a6; font-size: 13px"><i @click.stop="handleRemoveTemplate(value.fieldId)" class="el-icon-delete"></i></span>
              </el-option>
            </el-option-group>
            <el-option-group label="系统内置模板">
              <el-option v-for="(value,key) in fieldTemplate"
                         :key="key"
                         :label="key"
                         :value="key"></el-option>
            </el-option-group>
          </el-select>
        </el-form-item>
        <el-form-item v-show="templateForm.multiple=='1'" label="模板：" label-width="100px">
          <el-select
            v-model="templateForm.templates"
            multiple
            collapse-tags>
            <el-option-group v-if="cacheTemplateCount>0" label="临时模板">
              <el-option v-for="value in cacheTemplate"
                         :key="value.fieldId"
                         :label="value.fieldDesc"
                         :value="value.fieldId">
              </el-option>
            </el-option-group>
            <el-option-group label="系统内置模板">
              <el-option v-for="(value,key) in fieldTemplate"
                         :key="key"
                         :label="key"
                         :value="key"></el-option>
            </el-option-group>
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="addTemplateFormVisible = false">取 消</el-button>
        <el-button v-if="templateForm.multiple=='0'" type="primary" @click="handleAdd">开始编辑</el-button>
        <el-button v-if="templateForm.multiple=='1'" type="success" @click="handleAddImm">立即保存</el-button>
      </div>
    </el-dialog>

    <el-dialog title="级联扩展" :visible.sync="cascadeExtListVisible" width="60%">
      <cascade-ext-list ref="cascadeExtList"></cascade-ext-list>
    </el-dialog>

  </div>
</template>

<script>
  import Vue from 'vue'
  import cascadeExtList from '../cascadeExt/list.vue'
  import options from '@/components/options.js'
  import fieldTemplate from '@/components/fieldTemplate.js'
  import copyFieldUrl from '@/assets/copyField.gif'
  export default {
    name: 'fieldList',
    props: ['projectId', 'entityId'],
    components:{
      'cascade-ext-list' : cascadeExtList
    },
    data: function () {
      return {
        //此处不知为何，生产编译时，图片路径有误。本来应该是ui/static变成了uistatic
        copyFieldUrl:copyFieldUrl.replace('uistatic','ui/static'),
        addTemplateFormVisible: false,
        fieldTemplate,
        cacheTemplateCount:0,
        cacheTemplate:[],
        templateForm: {
          multiple: '0',
          template: '',
          templates: [],
        },
        //查询参数
        query: {
          projectId: null,
          entityId: null
        },
        //查询表单参数
        queryForm: {
          projectEntityOptions:[],
          projectEntity:[0,0]
        },
        activeNum: 0,
        selectItems: [],
        entities: [],
        loading: false,
        cascadeExtListVisible: false
      }
    },
    filters: {
      optionLabel: function (value, optionType) {
        var ops = options[optionType]
        for (var op of ops) {
          if (op.value == value) {
            return op.label
          }
        }
        return null
      },
      lengthAndScale: function (row) {
        let rel = ''
        if(options.showFieldLength(row.fieldType)){
          rel+='('+row.fieldLength
          if(options.showFieldScale(row.fieldType)){
            rel+=','+row.fieldScale
          }
          rel+=')'
        }
        return rel
      }
    },
    methods: {
      selectionChange: function (val) {
        this.selectItems = val
        this.activeNum = this.selectItems.length
      },
      handleRemoveTemplate:function (fieldId) {
        var index = this.cacheTemplate.findIndex(item=>item.fieldId==fieldId)
        if(index>-1){
          this.cacheTemplate.splice(index,1)
          this.cacheTemplateCount--
        }
      },
      handleDel: function () {
        if (this.activeNum <= 0) {
          this.$common.showMsg('warning', '请选择字段')
          return
        }
        this.$common.confirm('是否确认删除')
          .then(() => this.$ajax.put('/generate/meta_field/deleteBatch', this.selectItems.map(field => field.fieldId)))
          .then(() => this.doQuery())
      },
      handleCopy:function () {
        if (this.activeNum <= 0) {
          this.$common.showMsg('warning', '请选择字段')
          return
        }
        for(var item of this.selectItems){
          if(!this.cacheTemplate.find(t=>t.fieldId==item.fieldId)){
            this.cacheTemplate.push(item)
          }
        }
        this.cacheTemplateCount = this.cacheTemplate.length
      },
      handleCopyOne:function (row) {
        if(!this.cacheTemplate.find(t=>t.fieldId==row.fieldId)){
          this.cacheTemplate.push(row)
          this.cacheTemplateCount = this.cacheTemplate.length
        }
      },
      initProjectOptions: function () {
        return this.$common.getProjectOptions()
          .then(response => this.$common.checkResult(response.data))
          .then(result => this.queryForm.projectEntityOptions = result.data.map(project=>({value:project.projectId,label:project.projectName,children:[]})))
      },
      handleProjectChange: function (optionArray) {
        var projectId = optionArray[0]
        //获取被激活的option
        var project = this.queryForm.projectEntityOptions.find(option=>option.value==projectId)
        if(project.children.length){
          return
        }
        return this.$common.getEntityOptions(projectId)
          .then(response => this.$common.checkResult(response.data))
          .then(result => project.children = result.data.entities.map(entity=>({value:entity.entityId,label:entity.title})))
      },
      handleQuery: function () {
        if (this.queryForm.projectEntity[1] == null) {
          this.$common.showNotifyError('请选择实体')
          return
        }
        //将查询表单参数赋值给查询参数
        this.query.projectId = this.queryForm.projectEntity[0]
        this.query.entityId = this.queryForm.projectEntity[1]
        if (this.query.entityId != parseInt(this.entityId)) {
          this.$router.push(`/project/${this.query.projectId}/entity/${this.query.entityId}/field`)
        }
        this.doQuery()
      },
      // 列表查询
      doQuery: function () {
        if (!this.query.projectId || !this.query.entityId) {
          return
        }
        this.loading = true
        this.$ajax.get('/generate/meta_field/list', {params:this.query})
          .then(response => this.$common.checkResult(response.data))
          .then(result => this.entities = result.data)
          .catch(error => this.$common.showNotifyError(error))
          .finally(() => this.loading = false)
      },
      handleAdd: function () {
        this.addTemplateFormVisible = false
        //默认类型是系统内置模板
        var type = 'system'
        //如果目标值是数字，则改为临时模板
        if(typeof this.templateForm.template == 'number'){
          type= 'temp'
        }
        this.$router.push(`/project/${this.projectId}/entity/${this.entityId}/field/add?type=${type}&template=${this.templateForm.template}`)
      },
      handleAddImm: function () {
        this.addTemplateFormVisible = false
        const multiple = this.templateForm.multiple
        const templates = this.templateForm.templates
        const template = this.templateForm.template
        var callback = function(form,refresh){
          return this.$ajax.post('/generate/meta_field/save', {
              ...this.$common.removeBlankField(form),
              entityId:this.entityId
            })
            .then(response => this.$common.checkResult(response.data))
            //执行页面刷新
            .then(() => {
              if(refresh){
                this.$common.showMsg('success', '添加成功')
                this.doQuery()
              }
            })
            .catch(error => this.$common.showNotifyError(error))
        }.bind(this)
        var doAddImm = function (temp,refresh) {
          //如果目标值是数字，则为临时模板
          if(typeof temp == 'number'){
            return this.$ajax.get(`/generate/meta_field/${temp}`)
              .then(response => this.$common.checkResult(response.data))
              .then(result => callback(result.data,refresh))
              .catch(error => this.$common.showNotifyError(error))
          }else{
            //系统内置模板，直接保存
            return callback(fieldTemplate[temp],refresh)
          }
        }.bind(this)
        const loading = this.$loading()
        let promise = null
        if(multiple=='0'){
          promise = doAddImm(template,true)
        }else{
          promise = Promise.all(templates.map(temp=>doAddImm(temp)))
            .then(()=>{
              this.$common.showMsg('success', '添加成功')
              this.doQuery()
            })
        }
        promise.finally(()=>loading.close())
      },
      handleEdit: function (row) {
        this.$router.push(`/project/${this.projectId}/entity/${this.entityId}/field/edit/${row.fieldId}`)
      },
      handleShow: function (row) {
        this.$router.push(`/project/${this.projectId}/entity/${this.entityId}/field/show/${row.fieldId}`)
      },
      handleShowCascadeExt: function (row) {
        this.cascadeExtListVisible = true
        Vue.nextTick(()=>this.$refs.cascadeExtList.init(row.entityId, row.fieldId, row.foreignEntityId))
      }
    },
    activated: function () {
      var projectId = parseInt(this.projectId)
      var entityId = parseInt(this.entityId)
      this.queryForm.projectEntity[0] = projectId
      this.queryForm.projectEntity[1] = entityId
      this.query.projectId = projectId
      this.query.entityId = entityId
      this.initProjectOptions()
        .then(() => this.handleProjectChange([projectId]))
        .then(() => this.doQuery())
    }
  }
</script>
<style>
  .fieldList .activeNum {
    min-width: 160px;
    text-align: left;
    padding: 0 0 0 20px;
  }

  .demo-form-inline .el-select .el-input {
    width: 150px;
  }

  .demo-form-inline .el-form-item {
    margin-bottom: 0px;
  }
</style>
