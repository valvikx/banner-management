<template>

  <router-link to="" :item="item" @click.prevent="onItemClick(item)" class="collection-item">
    {{ item.name }}
  </router-link>

</template>

<script>

export default {

  name: "Item",
  props: {
    item: Object
  },
  setup(props, { emit }) {

    const onItemClick = item => emit('onItemClick', item)

    return {

      onItemClick

    }

  }

}

</script>