<template>
  <div>
    <b-container>
      <b-row class="text-center text-white bg-dark">
        <b-col col-12><h1>SIGO</h1></b-col>
      </b-row>
      <b-row>
        <b-col col-12>
          <b-alert v-model="showAlert" dismissible>
            {{ message }}
          </b-alert></b-col
        >
      </b-row>

      <b-row class="text-secondary mt-5">
        <b-col cols="3"></b-col>
        <b-col cols="6">
          <b-form>
            <b-form-group label="Usuário: " label-for="input-username">
              <b-form-input
                id="input-username"
                v-model="username"
                type="text"
                placeholder="Digite seu nome de usuário"
                required
              ></b-form-input>
            </b-form-group>

            <b-form-group label="Senha: " label-for="input-password">
              <b-form-input
                id="input-password"
                v-model="password"
                type="password"
                placeholder="Digite sua senha"
                required
              ></b-form-input>
            </b-form-group>
          </b-form>
        </b-col>
        <b-col cols="3"></b-col>
      </b-row>

      <b-row>
        <b-col cols="3"></b-col>
        <b-col cols="6">
          <b-button v-on:click="login" variant="primary" class="float-right">
            Entrar
          </b-button>
        </b-col>
        <b-col cols="3"></b-col>
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
      username: "",
      password: "",
      showAlert: false,
      message: "",
    };
  },
  methods: {
    login(event) {
      event.preventDefault();
      this.showAlert = false;

      const loginUrl = process.env.VUE_APP_ADMIN_MODULE_URL + "/login";

      const request = {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({
          username: this.username,
          password: this.password,
        }),
      };

      fetch(loginUrl, request)
        .then((response) => {
          if (response.ok) {
            const token = response.headers.get("authorization");
            localStorage.setItem("token", token);
            router.push({ name: "Dashboard" });
          } else {
            this.message = "Credenciais inválidas";
            this.showAlert = true;
          }
        })
        .catch((error) => {
          this.message =
            "Erro ao se comunicar com o servidor. Tente novamente mais tarde. " +
            error;
          this.showAlert = true;
        });
    },
  },
  mounted() {
    if (localStorage.token) {
      const token = localStorage.token.split(".");
      const payload = JSON.parse(atob(token[1]));

      if (new Date().getTime() >= payload.exp) {
        localStorage.removeItem("token");
      } else {
        router.push({ name: "Dashboard" });
      }
    }
  },
};
</script>
