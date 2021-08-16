<template>

  <div class="card">

    <div class="card-content white-text center"
         :class="isError ? 'deep-orange accent-3' : 'blue'">

      <span class="card-title"><i class="material-icons left white-text">warning</i>
        {{ content.title }}
      </span>

      <div class="row divider"></div>

      <p class="center-align">{{ content.text }}</p>

    </div>

  </div>

</template>

<script>

export default {
  name: "Message",
  props: {
    isError: Boolean,
    content: Object
  }
}

</script>

<style scoped>

.row {
  margin-bottom: 10px;
}

.card .card-content {
  border-radius: 4px;
  padding: 10px;
}

</style>

