module.exports = {

    chainWebpack: config => {

        config.plugin('html')
              .tap(args => {

                  args[0].title = "Banner Management";

                  return args;

              })

    },

    devServer: {

        port: 8090,

        proxy: {

            [process.env.VUE_APP_API_ENDPOINT]: {

                target: process.env.VUE_APP_API_URL,
                changeOrigin: true

            }

        }

    },

    outputDir: 'target/dist',

    assetsDir: 'static'

}