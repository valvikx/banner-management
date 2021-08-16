import instance from "@/axios"
import { updateItemInArray, removeItemFromArray, sortArrayByName, filterArrayByName } from '@/utils/arrays'
import router from "@/router"

export default class {

    constructor (endpoint, itemName) {

        this.endpoint = endpoint

        this.itemName = itemName

        this.state = {

            items: [],
            error: '',
            status: 200,
            search: {name: ''}

        }

        this.getters = {

            all: state => sortArrayByName([...state.items]),
            one: state => state.item,
            title: state => state.item.id ? `${state.item.name} ID: ${state.item.id}` : `Create new ${this.itemName}`,
            error: state => state.error,
            status: state => state.status,
            search: state => state.search,
            filteredItems: state => state.search.name ? filterArrayByName([...state.items], state.search) : []

        }

        this.actions = {

            getAll: async ({ commit }) => {

                await instance.get(this.endpoint)
                              .then(response => commit('SET_ALL', response.data))
                              .catch(error => commit('SET_ERROR', {error: error.response.data, status: error.response.status}))

            },

            createOne: async ({ commit }, item) => {

                await instance.post(this.endpoint, item)
                              .then(response => updateItem(commit, response.data))
                              .catch(error => handleError(commit, error))

            },

            updateOne: async ({ commit }, item) => {

                await instance.put(`${this.endpoint}/${item.id}`, item)
                              .then(response => updateItem(commit, response.data))
                              .catch(error => handleError(commit, error))
            },

            removeOne: async ({ commit }, item) => {

                await instance.delete(`${this.endpoint}/${item.id}`)
                              .then(response => removeItem(commit, response.data))
                              .catch(error => handleError(commit, error))

            },

            getOne: async ({ commit }, { id }) => {

                reset(commit)

                await instance.get(`${this.endpoint}/${id}`)
                              .then(response => commit('SET_ONE', response.data))
                              .catch(error => commit('SET_ERROR', {error: error.response.data, status: error.response.status}))

            },

            resetOne: ({ commit }) => reset(commit)

        }

        this.mutations = {

            SET_ALL: (state, items) => state.items = items,

            UPDATE_ONE: (state, item) => updateItemInArray(state.items, item),

            REMOVE_ONE: (state, item) => removeItemFromArray(state.items, item),

            SET_ONE: (state, item) => state.item = item,

            SET_ERROR: (state, { error, status }) => {

                state.error = error

                state.status = status

            },

            RESET_ERROR: state => {

                state.error = ''

                state.status = null

            }

        }

    }

}

const updateItem = (commit, item) => {

    commit('UPDATE_ONE', item)

    reset(commit)

}

const removeItem = (commit, item) => {

    commit('REMOVE_ONE', item)

    reset(commit)

}

const reset = commit => {

    commit(`RESET_ONE`)

    commit('RESET_ERROR')

}

const handleError = (commit, error) => {

    if (error.response.status === 401) {

        commit('auth/SET_ERROR', {error: error.response.data, status: error.response.status}, {root: true})

        router.push({path: '/admin'})

    } else {

        commit('SET_ERROR', {error: error.response.data, status: error.response.status})

    }

}