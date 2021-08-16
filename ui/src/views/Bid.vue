<template>

  <nav-wrapper>

    <nav-content />

  </nav-wrapper>

    <div class="container">

      <div class="pad-for-message">

        <message v-if="content && status === 200"
                 :isError="false"
                 :content="{title: `Category: ${categoryName}`, text: content}" />

        <message v-else-if="status === 204"
                 :isError="true"
                 :content="{title: `Category: ${categoryName}`, text: 'No banners for the selected category'}" />

        <message v-else-if="status === 400"
                 :isError="true"
                 :content="{title: 'Oops! An critical error has been occurred...', text: 'The request is invalid!'}" />

        <message v-else
                 :isError="true"
                 :content="{title: 'Oops! An critical error has been occurred...', text: 'We\'ll try to fix it!'}" />

      </div>

      <div class="row divider"></div>

      <div class="row">

        <div class="col s6">

          <button class="btn blue left"
                  @click.prevent="onNext" :disabled="status !== 200">Next banner</button>

        </div>

        <div class="col s6">

          <button class="btn blue right" @click.prevent="onBack">Back</button>

        </div>

      </div>

    </div>

</template>

<script>

import { useStore } from "vuex"
import { computed } from "vue"
import { useAppRouter } from "@/composition/appRouter";

import Message from "@/components/Message"
import NavWrapper from "@/components/NavWrapper"
import NavContent from "@/components/NavContent"

export default {
  name: "Bid",
  components: {
    Message, NavWrapper, NavContent
  },
  setup() {

    const store = useStore()

    const { router, reqName } = useAppRouter()

    store.dispatch('bid/getContent', reqName)

    return {

      content: computed(() => store.getters['bid/content']),
      status: computed(() => store.getters['bid/status']),
      categoryName: computed(() => store.getters['bid/categoryName']),
      onBack: () => router.push({name: 'home'}),
      onNext: () => store.dispatch('bid/getContent', reqName)

    }

  }

}

</script>

<style scoped>

</style>