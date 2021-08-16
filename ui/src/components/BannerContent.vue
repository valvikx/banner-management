<template>

  <base-layout>

    <div v-if="categories.length">

      <div class="input-field col s12">

        <input id="name" v-model="banner.name" type="text" ref="focus" autofocus>
        <label for="name" :class="{active: banner.id}">Name</label>
        <error-helper :message="error.name"/>

      </div>

      <div class="input-field col s12">

        <input id="price" v-model="banner.price" type="text">
        <label for="price" :class="{active: banner.id}">Price</label>
        <error-helper :message="error.price"/>

      </div>

      <div class="input-field col s12">

        <select v-model="banner.category"
                :style="[banner.category.id ? {'color': 'black'} : {'color': '#9e9e9e'}]">

          <option disabled :value="{}">Category</option>

          <option v-for="category in categories"
                  :key="category.id"
                  :value="category">
            {{ category.name }}
          </option>

        </select>

      </div>

      <div class="input-field col s12">

        <textarea id="content" class="materialize-textarea" v-model="banner.content"/>
        <label for="content" :class="{active: banner.id}">Text</label>
        <error-helper :message="error.content"/>

      </div>

      <input v-model="banner.id" type="hidden">

      <div class="input-field col s12">

        <message v-if="error.reason" :isError="true" :content="{title: error.message, text: error.reason}" />

      </div>

    </div>

    <div v-else-if="status >= 500" class="input-field col s12">

      <message :isError="true" :content="{title: 'Hey, admin...', text: error}" />

    </div>

    <div v-else-if="error.message" class="input-field col s12">

      <message :isError="true" :content="{title: error.message, text: error.reason}"/>

    </div>

    <div v-else class="input-field col s12">

      <message :isError="false" :content="{title: 'Hey, admin...',
                                           text: 'There are no banner categories in the store!'}" />

    </div>

  </base-layout>

</template>

<script>

import BaseLayout from "@/views/BaseLayout"
import Message from "@/components/Message"
import ErrorHelper from "@/components/ErrorHelper"

import { useStore } from "vuex"
import { computed, watch, ref, nextTick } from "vue"

export default {
  name: "BannerContent",
  components: {
    BaseLayout, Message, ErrorHelper
  },
  setup() {

    const store = useStore()

    const focus = ref(null)

    store.dispatch('categories/getAll')

    watch(() => store.getters['banners/one'],
        banner => banner.id ? null : nextTick(() => focus.value.focus()))

    return {

      categories: computed(() => store.getters['categories/all']),
      banner: computed(() => store.getters['banners/one']),
      error: computed(() => store.getters['banners/error']),
      status: computed(() => store.getters['banners/status']),
      focus

    }

  }

}

</script>

<style scoped>

select {
  border: 1px solid #9e9e9e;
}

select option {
  color: black;
}

select option:first-child {
  color: #9e9e9e;
}

</style>