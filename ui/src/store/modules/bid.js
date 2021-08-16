import instance from "@/axios"

const state = {

    categoryName: '',
    content: '',
    status: 200

}

const getters = {

    content: state => state.content,

    status: state => state.status,

    categoryName: state => state.categoryName

}

const mutations = {

    SET_CONTENT: (state, content) => state.content = content,

    SET_CATEGORY_NAME: (state, categoryName) => state.categoryName = categoryName,

    SET_STATUS: (state, status) => state.status = status

}

const actions = {

    getContent: async ({ commit }, reqName) => {

        await instance.get('/bid',  { params: {category: reqName} })
                      .then(response => {

                          console.log(response.data)

                          commit('SET_STATUS', response.status)

                          if (response.data) {

                              commit('SET_CONTENT', response.data.content)

                              commit('SET_CATEGORY_NAME', response.data.category.name)

                          } else {

                              commit('SET_CATEGORY_NAME', response.headers['category-name'])

                          }

                      })
                      .catch(error => commit('SET_STATUS', error.response.status))

    }

}

export default {

    namespaced: true,
    state,
    getters,
    mutations,
    actions

}