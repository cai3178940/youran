<template>
  <div class="erDiagram">
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/project' }">项目管理</el-breadcrumb-item>
      <el-breadcrumb-item :to="{ path: `/project/${this.projectId}/entity` }">实体管理</el-breadcrumb-item>
      <el-breadcrumb-item>实体关系图</el-breadcrumb-item>
    </el-breadcrumb>

    <el-row type="flex" align="middle" v-loading="loading">
      <div id="erDiagramDiv" class="erDiagramDiv"></div>
    </el-row>

  </div>
</template>

<script>
  import go from 'gojsaims'
  export default {

    name: 'erDiagram',
    props: ['projectId','entityIds'],
    data: function () {
      return {
        params:{
          projectId:null,
          entityIds:[]
        },
        erDiagram:{

        },
        loading: false
      }
    },
    methods:{

      queryErDiagram:function(){
        this.loading = true
        return this.$ajax.get('/generate/er_diagram/show', {params:this.params})
          .then(response => this.$common.checkResult(response.data))
          .then(result => this.erDiagram = result.data)
          .catch(error => this.$common.showNotifyError(error))
          .finally(() => this.loading = false)
      },

      getBrush:function(key){
        if(!this.brush){
          const $ = go.GraphObject.make
          this.brush = {
            bluegrad: $(go.Brush, 'Linear', { 0: 'rgb(150, 150, 250)', 0.5: 'rgb(86, 86, 186)', 1: 'rgb(86, 86, 186)' }),
            greengrad:  $(go.Brush, 'Linear', { 0: 'rgb(158, 209, 159)', 1: 'rgb(67, 101, 56)' }),
            redgrad:  $(go.Brush, 'Linear', { 0: 'rgb(206, 106, 100)', 1: 'rgb(180, 56, 50)' }),
            yellowgrad:  $(go.Brush, 'Linear', { 0: 'rgb(254, 221, 50)', 1: 'rgb(254, 182, 50)' }),
            lightgrad:  $(go.Brush, 'Linear', { 1: '#E6E6FA', 0: '#FFFAF0' })
          }
        }
        return this.brush[key];
      },

      renderDiagram:function () {
        const $ = go.GraphObject.make
        var myDiagram =
          $(go.Diagram, 'erDiagramDiv',  // must name or refer to the DIV HTML element
            {
              initialContentAlignment: go.Spot.Center,
              allowDelete: false,
              allowCopy: false,
              layout: $(go.ForceDirectedLayout),
              'undoManager.isEnabled': true
            })

        // the template for each attribute in a node's array of item data
        var itemTempl =
          $(go.Panel, 'Horizontal',
            $(go.Shape,
              { desiredSize: new go.Size(10, 10) },
              new go.Binding('figure', 'figure'),
              new go.Binding('fill', 'color')),
            $(go.TextBlock,
              { stroke: '#333333',
                font: 'bold 14px sans-serif' },
              new go.Binding('text', 'name'))
          )

        // define the Node template, representing an entity
        myDiagram.nodeTemplate =
          $(go.Node, 'Auto',  // the whole node panel
            { selectionAdorned: true,
              resizable: true,
              layoutConditions: go.Part.LayoutStandard & ~go.Part.LayoutNodeSized,
              fromSpot: go.Spot.AllSides,
              toSpot: go.Spot.AllSides,
              isShadowed: true,
              shadowColor: '#C5C1AA' },
            new go.Binding('location', 'location').makeTwoWay(),
            // whenever the PanelExpanderButton changes the visible property of the 'LIST' panel,
            // clear out any desiredSize set by the ResizingTool.
            new go.Binding('desiredSize', 'visible', function(v) { return new go.Size(NaN, NaN) }).ofObject('LIST'),
            // define the node's outer shape, which will surround the Table
            $(go.Shape, 'Rectangle',
              { fill: this.getBrush('lightgrad'), stroke: '#756875', strokeWidth: 3 }),
            $(go.Panel, 'Table',
              { margin: 8, stretch: go.GraphObject.Fill },
              $(go.RowColumnDefinition, { row: 0, sizing: go.RowColumnDefinition.None }),
              // the table header
              $(go.TextBlock,
                {
                  row: 0, alignment: go.Spot.Center,
                  margin: new go.Margin(0, 14, 0, 2),  // leave room for Button
                  font: 'bold 16px sans-serif'
                },
                new go.Binding('text', 'key')),
              // the collapse/expand button
              $('PanelExpanderButton', 'LIST',  // the name of the element whose visibility this button toggles
                { row: 0, alignment: go.Spot.TopRight }),
              // the list of Panels, each showing an attribute
              $(go.Panel, 'Vertical',
                {
                  name: 'LIST',
                  row: 1,
                  padding: 3,
                  alignment: go.Spot.TopLeft,
                  defaultAlignment: go.Spot.Left,
                  stretch: go.GraphObject.Horizontal,
                  itemTemplate: itemTempl
                },
                new go.Binding('itemArray', 'items'))
            )  // end Table Panel
          )  // end Node

        // define the Link template, representing a relationship
        myDiagram.linkTemplate =
          $(go.Link,  // the whole link panel
            {
              selectionAdorned: true,
              layerName: 'Foreground',
              reshapable: true,
              routing: go.Link.AvoidsNodes,
              corner: 5,
              curve: go.Link.JumpOver
            },
            $(go.Shape,  // the link shape
              { stroke: '#303B45', strokeWidth: 2.5 }),
            $(go.TextBlock,  // the 'from' label
              {
                textAlign: 'center',
                font: 'bold 14px sans-serif',
                stroke: '#1967B3',
                segmentIndex: 0,
                segmentOffset: new go.Point(NaN, NaN),
                segmentOrientation: go.Link.OrientUpright
              },
              new go.Binding('text', 'text')),
            $(go.TextBlock,  // the 'to' label
              {
                textAlign: 'center',
                font: 'bold 14px sans-serif',
                stroke: '#1967B3',
                segmentIndex: -1,
                segmentOffset: new go.Point(NaN, NaN),
                segmentOrientation: go.Link.OrientUpright
              },
              new go.Binding('text', 'toText'))
          )

        // create the model for the E-R diagram
        this.erDiagram.nodeData.forEach(v=>v.items.forEach(item=>{
          if(item.type=='pk'){
            item.iskey=true
            item.figure='Decision'
            item.color=this.getBrush('yellowgrad')
          }else{
            item.iskey=false
            if(item.type=='fk'){
              item.figure='Decision'
              item.color=this.getBrush('bluegrad')
            }else if(item.type=='delete'){
              item.figure='ThickX'
              item.color=this.getBrush('redgrad')
            }else if(item.type=='version'){
              item.figure='PaperTape'
              item.color=this.getBrush('greengrad')
            }else{
              item.figure=''
              item.color=null
            }
          }
        }))
        myDiagram.model = new go.GraphLinksModel(this.erDiagram.nodeData, this.erDiagram.linkData)
      }
    },
    created: function () {
      this.params.projectId = parseInt(this.projectId)
      if(this.entityIds){
        this.params.entityIds = this.entityIds.split('-').map(value => parseInt(value))
      }
    },
    mounted: function () {
      this.queryErDiagram()
        .then(()=>this.renderDiagram())
    }
  }

</script>

<style>
  .erDiagramDiv{
    width: 100%;
    height: 700px;
  }

</style>
