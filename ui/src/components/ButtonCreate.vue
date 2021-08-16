<template>

  <div class="at-bottom">

    <button class="btn blue" @click.prevent="onReset">Create new {{ itemName }}</button>

  </div>

</template>

<script>

import { useStore } from 'vuex'

export default {
  name: "ButtonCreate",
  props: {
    routeName: String,
    itemName: String
  },
  setup(props){

    const store = useStore()

    return {

      onReset: () => store.dispatch(`${props.routeName}/resetOne`)

    }

  }

}

</script>

<style scoped>

button {
  width: 100%;
}

</style>