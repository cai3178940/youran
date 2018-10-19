<template>
  <div class="erDiagram">
    <el-dialog title="实体关系图" :visible.sync="visible" :fullscreen="true">
      <el-row type="flex" align="middle" v-loading="loading" style="height: 100%;">
        <div id="erDiagramDiv" class="erDiagramDiv"></div>
      </el-row>
    </el-dialog>
  </div>
</template>

<script>
  import go from 'gojsaims'
  export default {
    name: 'er-diagram',
    data: function () {
      return {
        // 后端返回的json数据
        erDiagram: {

        },
        loading: false,
        visible: false
      }
    },
    methods: {

      show: function (projectId, entityIds) {
        this.visible = true
        this.queryErDiagram(projectId, entityIds)
          .then(() => this.renderDiagram())
      },

      queryErDiagram: function (projectId, entityIds) {
        this.loading = true
        return this.$ajax.get('/generate/er_diagram/show', {params: {projectId, entityIds}})
          .then(response => this.$common.checkResult(response.data))
          .then(result => { this.erDiagram = result.data })
          .catch(error => this.$common.showNotifyError(error))
          .finally(() => { this.loading = false })
      },

      getBrush: function (key) {
        if (!this.brush) {
          const $ = go.GraphObject.make
          this.brush = {
            bluegrad: $(go.Brush, 'Linear', { 0: 'rgb(150, 150, 250)', 0.5: 'rgb(86, 86, 186)', 1: 'rgb(86, 86, 186)' }),
            greengrad: $(go.Brush, 'Linear', { 0: 'rgb(158, 209, 159)', 1: 'rgb(67, 101, 56)' }),
            redgrad: $(go.Brush, 'Linear', { 0: 'rgb(206, 106, 100)', 1: 'rgb(180, 56, 50)' }),
            yellowgrad: $(go.Brush, 'Linear', { 0: 'rgb(254, 221, 50)', 1: 'rgb(254, 182, 50)' }),
            lightgrad: $(go.Brush, 'Linear', { 1: '#E6E6FA', 0: '#FFFAF0' })
          }
        }
        return this.brush[key]
      },

      initDiagram: function () {
        const $ = go.GraphObject.make
        this.diagram =
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
              { desiredSize: new go.Size(10, 10),
                margin: new go.Margin(0, 5, 0, 0)},
              new go.Binding('figure', 'figure'),
              new go.Binding('fill', 'color')),
            $(go.TextBlock,
              { stroke: '#333333',
                font: 'bold 14px sans-serif',
                margin: new go.Margin(0, 10, 0, 0)},
              new go.Binding('text', 'name')),
            $(go.TextBlock,
              { stroke: '#333333',
                font: 'bold 14px sans-serif' },
              new go.Binding('text', 'desc'))
          )

        // define the Node template, representing an entity
        this.diagram.nodeTemplate =
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
            new go.Binding('desiredSize', 'visible', function (v) { return new go.Size(NaN, NaN) }).ofObject('LIST'),
            // define the node's outer shape, which will surround the Table
            $(go.Shape, 'Rectangle',
              { fill: this.getBrush('lightgrad'), stroke: '#756875', strokeWidth: 3 }),
            $(go.Panel, 'Table',
              { margin: 8, stretch: go.GraphObject.Fill },
              $(go.RowColumnDefinition, { row: 0, sizing: go.RowColumnDefinition.None }),
              // the table header
              $(go.TextBlock,
                {
                  row: 0,
                  alignment: go.Spot.Center,
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
                new go.Binding('itemArray', 'fields'))
            )  // end Table Panel
          )  // end Node

        // define the Link template, representing a relationship
        this.diagram.linkTemplate =
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
      },

      updateModel: function () {
        this.erDiagram.nodeData.forEach(v => v.fields.forEach(item => {
          if (item.type === 'pk') {
            item.iskey = true
            item.figure = 'Decision'
            item.color = this.getBrush('yellowgrad')
          } else {
            item.iskey = false
            if (item.type === 'fk') {
              item.figure = 'Decision'
              item.color = this.getBrush('bluegrad')
            } else if (item.type === 'delete') {
              item.figure = 'ThickX'
              item.color = this.getBrush('redgrad')
            } else if (item.type === 'version') {
              item.figure = 'PaperTape'
              item.color = this.getBrush('greengrad')
            } else {
              item.figure = ''
              item.color = null
            }
          }
        }))
        this.diagram.model = new go.GraphLinksModel(this.erDiagram.nodeData, this.erDiagram.linkData)
      },

      renderDiagram: function () {
        if (!this.diagram) {
          this.initDiagram()
          this.updateModel()
        } else {
          this.updateModel()
          this.diagram.requestUpdate()
        }
      }
    }
  }
</script>

<style>
  .erDiagramDiv{
    width: 100%;
    height: 100%;
  }
  .erDiagram .el-dialog__header{
    background-color: #409EFF;
  }
  .erDiagram .el-dialog__title{
    color: #FFFFFF;
  }
  .erDiagram .el-dialog__close{
    color: #FFFFFF;
  }
  .erDiagram .el-dialog__close:hover{
    color: #FFFFFF;
  }
  .erDiagram .el-dialog__body{
    height: calc(100% - 55px);
    padding: 0px;
  }

</style>
