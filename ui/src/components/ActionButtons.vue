<template>

  <div class="col s12 at-bottom">

    <button @click.prevent="saveItem"
            class="btn blue left">Save</button>

    <button @click.prevent="removeItem"
            class="btn blue right"
            :disabled="!item.id">Delete</button>

  </div>

</template>

<script>

import { useStore } from 'vuex'

export default {

  name: "ActionButtons",
  props: {
    item: Object,
    routeName: String
  },
  setup(props) {

    const store = useStore()

    const saveItem = () =>
        props.item.id ? store.dispatch(`${props.routeName}/updateOne`, props.item)
                      : store.dispatch(`${props.routeName}/createOne`, props.item)

    const removeItem = () => store.dispatch(`${props.routeName}/removeOne`, props.item)

    return {

      saveItem, removeItem

    }

  }
  
}

</script>