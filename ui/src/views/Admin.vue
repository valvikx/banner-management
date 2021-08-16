<template>

  <nav-wrapper>

    <nav-content />

  </nav-wrapper>

  <div class="container">

    <div class="pad-for-message">

      <div v-if="status >= 500" class="input-field col s12">

        <message :isError="true"
                 :content="{title: 'Oops! An critical error has been occurred...', text: 'We\'ll try to fix it!'}" />

    </div>

      <div v-else-if="status === 401" class="input-field col s12">

        <message :isError="true" :content="{title: 'Hey, admin...', text: error.message}"/>

      </div>

      <div class="row card">

        <div class="input-field col s12">

           <h5 class="blue-text">Enter your login and password</h5>

        </div>

        <div class="input-field col s12">

          <input v-model="admin.login" id="login" type="text" ref="focus" autofocus>
          <label for="login">Login</label>
          <error-helper :message="error.login"/>

        </div>

        <div class="input-field col s12">

          <input v-model="admin.password" id="password" type="password">
          <label for="password">Password</label>
          <error-helper :message="error.password"/>

        </div>

        <div class="input-field col s12">

          <button @click.prevent="authenticate" class="btn blue right">Sign In</button>

        </div>

      </div>

    </div>



  </div>

</template>

<script>

import Message from "@/components/Message"
import NavWrapper from "@/components/NavWrapper"
import NavContent from "@/components/NavContent"
import ErrorHelper from "@/components/ErrorHelper"

import { computed, reactive } from "vue"
import { useStore } from "vuex"

export default {

  name: "Admin",
  components: {
    Message, NavWrapper, NavContent, ErrorHelper
  },
  setup() {

    const store = useStore()

    const admin = reactive({
      login: '',
      password: '',
    })

    const authenticate = () => store.dispatch('auth/authenticate', admin)

    return {

      error: computed(() => store.getters['auth/error']),
      status: computed(() => store.getters['auth/status']),
      authenticate,
      admin

    }

  }
}

</script>


