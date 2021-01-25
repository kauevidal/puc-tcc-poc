<template>
  <div class="mb-3 mt-2">
    <b-row>
      <b-col col-4></b-col>
      <b-col class="text-center" col-5><h1>SIGO</h1></b-col>
      <b-col col-4></b-col>
    </b-row>

    <b-navbar toggleable="md" type="dark" variant="dark">
      <b-navbar-nav>
        <b-nav-item to="/process-management" v-if="role === 'EMPLOYEE'">
          Gestão Processo Industrial
        </b-nav-item>
        <b-nav-item to="/standards-management" v-if="role === 'EMPLOYEE'">
          Gestão de Normas
        </b-nav-item>
        <b-nav-item
          to="/consultancy"
          v-if="role === 'CONSULTANT' || role === 'EMPLOYEE'"
          >Consultoria e Assessorias
        </b-nav-item>
        <b-nav-item v-on:click="logout"> Sair </b-nav-item>
      </b-navbar-nav>
    </b-navbar>
  </div>
</template>

<script>
import router from "@/router";

export default {
  name: "Header",
  props: {},
  data() {
    return { role: "" };
  },
  methods: {
    logout(event) {
      event.preventDefault();
      localStorage.removeItem("token");
      router.push({ name: "Login" });
    },
  },
  mounted() {
    if (localStorage.token) {
      const token = localStorage.token.split(".");
      const payload = JSON.parse(atob(token[1]));
      this.role = payload.authorities;
    }
  },
};
</script>

