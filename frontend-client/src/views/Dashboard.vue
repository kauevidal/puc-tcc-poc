<template>
  <div>
    <b-container>
      <b-row class="text-center text-white bg-dark">
        <b-col col-12><h1>SIGO</h1></b-col>
      </b-row>
      <b-row class="mt-2">
        <b-col col-12>
          <b-alert v-model="showAlert" dismissible>
            {{ message }}
          </b-alert>
        </b-col>
      </b-row>

      <b-row>
        <b-col cols="12" class="mt-5">
          <b-button v-on:click="logout" variant="primary" class="float-right">
            Sair
          </b-button>
        </b-col>
      </b-row>
      <b-row>
        <b-button v-on:click="load" variant="primary" class="float-right"
          >Teste</b-button
        >
        {{ info }}
      </b-row>
    </b-container>
  </div>
</template>
<script>
import router from "@/router";

export default {
  name: "Login",
  components: {},
  data() {
    return {
      showAlert: false,
      message: "",
      info: "to load",
    };
  },
  methods: {
    logout(event) {
      event.preventDefault();
      localStorage.removeItem("token");
      router.push({ name: "Login" });
    },
    load() {
      event.preventDefault();

      var headers = new Headers();
      headers.append("Authorization", localStorage.token);
      headers.append("Content-Type", "application/json");

      const token = localStorage.token.split(".");
      const payload = JSON.parse(atob(token[1]));

      const userUrl =
        process.env.VUE_APP_ADMIN_MODULE_URL + "/user?username=" + payload.sub;

      const request = {
        method: "GET",
        headers: headers,
      };

      fetch(userUrl, request)
        .then(async (response) => {
          const data = await response.json();

          if (!response.ok) {
            this.message = "Credenciais inválidas";
            this.showAlert = true;
            return Promise.reject("Credenciais inválidas");
          }
          this.info = data;
        })
        .catch((error) => {
          this.message =
            "Erro ao se comunicar com o servidor. Tente novamente mais tarde. " +
            error;
          this.showAlert = true;
        });
    },
  },
};
</script>