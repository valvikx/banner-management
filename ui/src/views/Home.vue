<template>

  <nav-wrapper>

    <nav-content />

  </nav-wrapper>

  <category-select />

</template>

<script>

  import CategorySelect from "@/components/CategorySelect"
  import NavWrapper from "@/components/NavWrapper"
  import NavContent from "@/components/NavContent"

  import { useStore } from "vuex"

  export default {
    name: 'Home',
    components: {
      CategorySelect, NavWrapper, NavContent
    },
    setup(){

      const store = useStore()

      store.dispatch('categories/getAll')

    }

  }

</script>

<style scoped>

  h5 {
    margin-left: 5px;
  }

</style>
