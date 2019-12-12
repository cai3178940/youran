const path = require('path')

function resolve (dir) {
  return path.join(__dirname, dir)
}

module.exports = {
  // 编译到java-web工程目录中
  outputDir: path.resolve(__dirname, '../youran-generate/src/main/resources/ui'),
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
  }
}
