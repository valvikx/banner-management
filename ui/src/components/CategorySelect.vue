<template>

  <div class="container">

    <div v-if="categories.length" class="card">

      <div class="card-action blue white-text center">

        <h4>Select The Desired Banner Category</h4>

      </div>

      <div class="card-content">

        <div class="collection">

          <router-link v-for="category in categories" :key="category.id"
                       :to="{path: '/bid', query: {category: category.reqName}}"
                       class="collection-item">
            {{ category.name }}
          </router-link>

        </div>

      </div>

    </div>

    <div v-else class="pad-for-message">

      <message v-if="status === 200"
               :isError="false"
               :content="{title: 'We are very sorry...', text: 'There are no banners in the store!'}" />

      <message v-else
               :isError="true"
               :content="{title: 'Oops! An critical error has been occurred...', text: 'We\'ll try to fix it!'}" />

    </div>

  </div>

</template>

<script>

import Message from "@/components/Message"

import { computed } from "vue"
import { useStore } from "vuex"

export default {
  name: "CategorySelect",
  components: {
    Message
  },
  setup() {

    const store = useStore()

    return {

      categories: computed(() => store.getters['categories/all']),
      status: computed(() => store.getters['categories/status'])

    }

  }

}

</script>

<style scoped>

h4 {
  margin: 0;
}

.collection a.collection-item {
  color: #2196F3;
}

</style>