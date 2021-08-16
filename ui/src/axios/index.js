import axios from 'axios'

const authMethods = ['post', 'put', 'delete']

const instance = axios.create({

    headers: {
          Accept: 'application/json',
          'Content-Type': 'application/json'
    }

})

instance.defaults.baseURL = process.env.VUE_APP_API_ENDPOINT

instance.interceptors.request.use(config => {

    if (authMethods.includes(config.method)) {

        config.headers.authorization = sessionStorage.auth

    }

    return config;

})

export default instance