<template>

  <p class="card red accent-3 white-text" v-if="message">{{ message }}</p>

</template>

<script>

export default {
  name: "ErrorHelper",
  props: {
    message: String
  }
}

</script>

<style scoped>

p {
  font-size: 16px;
}

</style>