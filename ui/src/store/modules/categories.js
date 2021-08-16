import Api from '@/store/modules/api'

const api = new Api('/categories', 'category')

const state = {

    ...api.state,

    item: {id: null, name: '', reqName: ''}

}

const getters = {

    ...api.getters,

    toggle: state => state.status < 500

}

const mutations = {

    ...api.mutations,

    RESET_ONE: state => state.item = {id: null, name: '', reqName: ''}

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