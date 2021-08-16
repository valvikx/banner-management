<template>

  <div class="row">

    <div class="input-field col s12">

      <i class="material-icons prefix">search</i>

      <input v-model="search.name" id="search" type="text">

      <label for="search">Enter {{ itemName }} name...</label>

    </div>

  </div>

</template>

<script>

import { computed } from "vue"
import { useStore } from "vuex"

export default {
  name: "ItemSearch",
  props: {
    itemName: String,
    routeName: String
  },
  setup(props) {

    const store = useStore()

    return {

      search: computed(() => store.getters[`${props.routeName}/search`])

    }

  }
}

</script>