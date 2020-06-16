// for not load lib file test
module.exports = {
  // publicPath: './',
  productionSourceMap: false,
  configureWebpack: (config) => {
    config.externals = {
      'core-js': 'core-js',
      'element-ui': 'ELEMENT',
      vue: 'Vue',
      axios: 'axios',
      vuex: 'Vuex',
      'vue-router': 'VueRouter',
      'crypto-js': 'CryptoJS',
      'websocket-extensions': 'Extensions',
      'websocket-driver': 'Driver',
      'sockjs-client': 'SockJS'
    };
    // let optimization = {
    //   runtimeChunk: 'single',
    //   splitChunks: {
    //     chunks: 'all',
    //     maxInitialRequests: Infinity,
    //     minSize: 20000, // 依赖包超过20000bit将被单独打包
    //     cacheGroups: {
    //       vendor: {
    //         test: /[\\/]node_modules[\\/]/,
    //         name(module) {
    //           // get the name. E.g. node_modules/packageName/not/this/part.js
    //           // or node_modules/packageName
    //           const packageName = module.context.match(/[\\/]node_modules[\\/](.*?)([\\/]|$)/)[1];
    //           // npm package names are URL-safe, but some servers don't like @ symbols
    //           return `npm.${packageName.replace('@', '')}`;
    //         },
    //       },
    //     },
    //   },
    // };
    // Object.assign(config, {
    //   optimization,
    // });
  },
};
