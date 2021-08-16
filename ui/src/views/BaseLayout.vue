<template>

  <nav-wrapper>

    <ul class="left">

      <li>
        <a :href="router.resolve({name: 'banners'}).href">Banners</a>
      </li>
      <li>
        <a :href="router.resolve({name: 'categories'}).href">Categories</a>
      </li>
      <li>
        <a :href="router.resolve({name: 'home'}).href">Main</a>
      </li>

    </ul>

    <h5 class="brand-logo right">Banner Store</h5>

  </nav-wrapper>

  <div class="row">

    <div class="col s4 box">

      <div class="section center">

        <h5>{{ capitalize(routeName) }}:</h5>

      </div>

      <div class="divider"></div>

      <div v-if="items.length">

        <item-search :item-name="itemName" :routeName="routeName" :disabled="items.length"/>

        <list-items :items="search.name ? filteredItems: items" :routeName="routeName" />

      </div>

      <div v-else class="row">

        <p class="center">No {{ routeName }} yet!</p>

      </div>

    </div>

    <div class="col s8 box">

      <div class="section">

        <h5>{{ title }}</h5>

      </div>

      <div class="divider"></div>

      <slot></slot>

    </div>

  </div>

  <div class="row" v-if="toggle">

    <div class="col s4">

      <button-create :routeName="routeName" :itemName="itemName"/>

    </div>

    <div class="col s8">

      <action-buttons :item="item" :routeName="routeName" />

    </div>

  </div>

</template>

<script>

import ButtonCreate from "@/components/ButtonCreate"
import NavWrapper from "@/components/NavWrapper"
import ItemSearch from "@/components/ItemSearch"
import ListItems from "@/components/ListItems"
import ActionButtons from "@/components/ActionButtons"

import { onMounted, computed } from 'vue'
import { useStore } from 'vuex'
import { useAppRouter } from '@/composition/appRouter'

export default {
  name: "BaseLayout",
  components: {
    ButtonCreate, NavWrapper, ItemSearch, ListItems, ActionButtons
  },
  setup() {

    const store = useStore()

    const { routeName, itemName, router } = useAppRouter()

    store.dispatch(`${routeName}/getAll`)

    return {

      routeName,
      itemName,
      router,
      items: computed(() => store.getters[`${routeName}/all`]),
      item: computed(() => store.getters[`${routeName}/one`]),
      title: computed(() => store.getters[`${routeName}/title`]),
      toggle: computed(() => store.getters[`${routeName}/toggle`]),
      search: computed(() => store.getters[`${routeName}/search`]),
      filteredItems: computed(() => store.getters[`${routeName}/filteredItems`]),
      capitalize: str => str.charAt(0).toUpperCase() + str.slice(1)

    }

  }

}

</script>