const path = require('path')

module.exports = {
  // 编译到java-web工程目录中
  outputDir: path.resolve(__dirname, '../youran-generate/src/main/resources/ui'),
  // 指定静态资源目录
  assetsDir: 'static',
  // 前端项目基本路径
  baseUrl: '/ui',
  devServer: {
    port: 80,
    proxy: {
      '/api': {
        target: 'http://localhost:8080',
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
  },
  transpileDependencies: [
    /\bvue-awesome\b/
  ]
}
