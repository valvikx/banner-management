<template>

  <h5 class="brand-logo">Banner Store</h5>

  <ul class="right">

    <li v-if="routeName === 'home'">
      <router-link to="/admin">Admin</router-link>
    </li>

    <li v-else-if="routeName === 'admin'">
      <router-link to="/">Home</router-link>
    </li>

  </ul>

</template>

<script>

import { useAppRouter } from '@/composition/appRouter'

export default {
  name: "NavContent",
  setup() {

    const { routeName } = useAppRouter()

    return {

      routeName

    }

  }

}





</script>