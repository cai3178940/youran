const path = require('path')

function resolve (dir) {
  return path.join(__dirname, dir)
}

module.exports = {
  // 编译到java-web工程目录中
  outputDir: path.resolve(__dirname, '../youran-generate-release/assembly/resource/ui'),
  // 指定静态资源目录
  assetsDir: 'static',
  // 前端项目基本路径
  publicPath: '/ui',
  devServer: {
    port: 80,
    proxy: {
      '/ws_api': {
        target: 'ws://localhost:8088',
        ws: true
        // pathRewrite: {
        //   '^/ws_api': '/ws_api'
        // }
      },
      '/api': {
        target: 'http://localhost:8088',
        changeOrigin: true,
        pathRewrite: {
          '^/api': '/api'
        }
      }
    }
  },
  chainWebpack: config => {
    config.module
      .rule('markdown')
      .test(/\.md$/)
      .use('graphql-tag/loader')
      .loader('raw-loader')
      .end()
    config.module
      .rule('svg')
      .exclude.add(resolve('src/icons'))
      .end()
    config.module
      .rule('icons')
      .test(/\.svg$/)
      .include.add(resolve('src/icons'))
      .end()
      .use('svg-sprite-loader')
      .loader('svg-sprite-loader')
      .options({
        symbolId: 'icon-[name]'
      })
      .end()
  },
  // 配置webpack
  configureWebpack: config => {
    // 开启分离js
    config.optimization = {
      runtimeChunk: 'single',
      splitChunks: {
        chunks: 'all',
        maxInitialRequests: Infinity,
        minSize: 20000,
        cacheGroups: {
          libs: {
            name: 'chunk-libs',
            test: /[\\/]node_modules[\\/]/,
            priority: 10,
            chunks: 'initial' // only package third parties that are initially dependent
          },
          vue: {
            name: 'chunk-vue', // split vue into a single package
            priority: 20, // the weight needs to be larger than libs and app or it will be packaged into libs or app
            test: /[\\/]node_modules[\\/]_?vue*(.*)/ // in order to adapt to cnpm
          },
          elementUI: {
            name: 'chunk-elementUI', // split elementUI into a single package
            priority: 20, // the weight needs to be larger than libs and app or it will be packaged into libs or app
            test: /[\\/]node_modules[\\/]_?element-ui(.*)/ // in order to adapt to cnpm
          },
          gojs: {
            name: 'chunk-gojs', // split gojs into a single package
            priority: 20, // the weight needs to be larger than libs and app or it will be packaged into libs or app
            test: /[\\/]node_modules[\\/]_?gojs(.*)/ // in order to adapt to cnpm
          },
          echarts: {
            name: 'chunk-echarts', // split echarts into a single package
            priority: 20, // the weight needs to be larger than libs and app or it will be packaged into libs or app
            test: /[\\/]node_modules[\\/]_?echarts(.*)/ // in order to adapt to cnpm
          },
          moment: {
            name: 'chunk-moment', // split moment into a single package
            priority: 20, // the weight needs to be larger than libs and app or it will be packaged into libs or app
            test: /[\\/]node_modules[\\/]_?moment(.*)/ // in order to adapt to cnpm
          },
          highlight: {
            name: 'chunk-highlight', // split highlight into a single package
            priority: 20, // the weight needs to be larger than libs and app or it will be packaged into libs or app
            test: /[\\/]node_modules[\\/]_?highlight(.*)/ // in order to adapt to cnpm
          },
          tui_editor: {
            name: 'chunk-tui-editor', // split tui-editor into a single package
            priority: 20, // the weight needs to be larger than libs and app or it will be packaged into libs or app
            test: /[\\/]node_modules[\\/]_?tui-editor(.*)/ // in order to adapt to cnpm
          },
          codemirror: {
            name: 'chunk-codemirror', // split codemirror into a single package
            priority: 20, // the weight needs to be larger than libs and app or it will be packaged into libs or app
            test: /[\\/]node_modules[\\/]_?codemirror(.*)/ // in order to adapt to cnpm
          },
          js_beautify: {
            name: 'chunk-js-beautify', // split codemirror into a single package
            priority: 20, // the weight needs to be larger than libs and app or it will be packaged into libs or app
            test: /[\\/]node_modules[\\/]_?js-beautify(.*)/ // in order to adapt to cnpm
          },
          commons: {
            name: 'chunk-commons',
            test: resolve('src/components'), // can customize your rules
            minChunks: 3, //  minimum common number
            priority: 5,
            reuseExistingChunk: true
          }
        }
      }
    }
  }
}
