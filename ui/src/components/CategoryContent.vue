<template>

  <base-layout>

    <div v-if="status >= 500" class="input-field col s12">

      <message :isError="true" :content="{title: 'Hey, admin...', text: error}"/>

    </div>

    <div v-else>

      <div class="input-field col s12">

        <input id="name" v-model="category.name" type="text" ref="focus" autofocus>
        <label for="name" :class="{active: category.id}">Name</label>
        <error-helper :message="error.name"/>

      </div>

      <div class="input-field col s12">

        <input id="reqName" v-model="category.reqName" type="text">
        <label for="reqName" :class="{active: category.id}">Request ID</label>
        <error-helper :message="error.reqName"/>

      </div>

      <input v-model="category.id" type="hidden">

      <div v-if="error.message" class="input-field col s12">

        <message :isError="true" :content="{title: error.message, text: error.reason}"/>

      </div>

    </div>

  </base-layout>

</template>

<script>

import BaseLayout from "@/views/BaseLayout"
import Message from "@/components/Message"
import ErrorHelper from "@/components/ErrorHelper"

import { computed, nextTick, ref, watch } from "vue"
import { useStore } from 'vuex'

export default {

  name: "Categories",
  components: {
    BaseLayout, Message, ErrorHelper
  },
  setup() {

    const store = useStore()

    const focus = ref(null)

    watch(() => store.getters['categories/one'],category => category.id ? null : nextTick(() => focus.value.focus()))

    return {

      category: computed(() => store.getters['categories/one']),
      error: computed(() => store.getters['categories/error']),
      status: computed(() => store.getters['categories/status']),
      focus

    }

  }

}

</script>

<style scoped>

h5 {
  margin-left: 5px;
}

p {
  padding: 1px;
}

</style>