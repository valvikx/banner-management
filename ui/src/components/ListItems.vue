<template>

  <div class="row">

    <ul class="collection">

      <item v-for="item in items" :key="item.id" :item="item" @onItemClick="onItemClick(item)" />

    </ul>

  </div>

</template>

<script>

import Item from "@/components/Item"

import { useStore } from 'vuex'

export default {

  name: "ListItem",
  components: {
    Item
  },
  props: {
    items: Array,
    routeName: String
  },
  setup(props) {

    const store = useStore()

    return {

      onItemClick: item => store.dispatch(`${props.routeName}/getOne`, item)

    }

  }

}

</script>