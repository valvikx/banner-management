import instance from "@/axios"
import router from '@/router'

const state = {

    error: {},
    status: 200

}

const getters = {

    error: state => state.error,
    status: state => state.status

}

const mutations = {

    SET_ERROR: (state, { error, status }) => {

        state.error = error

        state.status = status

    }

}

const actions = {

    authenticate: async ({ commit }, admin) => {

        await instance.post('/auth', admin)
                      .then(response => {

                          sessionStorage.auth = response.headers.authorization

                          router.push({path: '/banners'})

                          })
                      .catch(error => commit('SET_ERROR', {error: error.response.data, status: error.response.status}))

    }

}

export default {

    namespaced: true,
    state,
    getters,
    mutations,
    actions

}