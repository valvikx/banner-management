import Api from '@/store/modules/api'

const api = new Api('/banners','banner')

const state = {

    ...api.state,

    item: {id: null, name: '', price: '', category: {}, content: ''}

}

const getters = {

    ...api.getters,

    toggle: (state, getters, rootState, rootGetters) => {

        const categories = rootGetters['categories/all']

        return state.status < 500 && categories.length

    }

}

const mutations = {

    ...api.mutations,

    RESET_ONE: state => state.item = {id: null, name: '', price: '', category: {}, content: ''}

}

const actions = {

    ...api.actions

}

export default {

    namespaced: true,
    state,
    getters,
    mutations,
    actions

}