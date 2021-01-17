<template>
  <b-container>
    <Header />

    <b-row class="text-center">
      <b-col col-12>
        <b-alert v-model="showAlert" dismissible>
          {{ alertMsg }}
        </b-alert>
      </b-col>
    </b-row>

    <b-row class="my-1">
      <b-col col-12 class="text-center">
        <h4>Gestão Processo Industrial</h4>
      </b-col>
    </b-row>

    <!--Consulta -->
    <b-row v-if="!isEditing">
      <b-table striped hover :items="items" :fields="fields" :busy="isLoading">
        <template #cell(actions)="row">
          <b-button
            variant="info"
            size="sm"
            @click="changeToEditionMode(row.item)"
            class="mr-1"
          >
            Detalhes
          </b-button>
        </template>
        <template #table-busy>
          <div class="text-center my-4">
            <b-spinner class="align-middle"></b-spinner>
            <strong>Carregando</strong>
          </div>
        </template>
      </b-table>
    </b-row>

    <!--Edição -->
    <div v-else>
      <b-row>
        <b-button v-on:click="back" variant="info" class="float-right mx-2">
          Voltar
        </b-button>
        <b-button v-on:click="save" variant="success" class="float-right mx-2">
          Salvar
        </b-button>
        editando processo {{ selectedId }}
      </b-row>
    </div>
  </b-container>
</template>

<script>
import Header from "@/components/Header.vue";

export default {
  name: "ProcessManagement",
  components: {
    Header,
  },
  data() {
    return {
      showAlert: false,
      alertMsg: "",
      selectedId: "",
      isEditing: false,
      isLoading: true,
      fields: [
        { key: "id", label: "Id", sortable: true, sortDirection: "desc" },
        { key: "name", label: "Nome", sortable: true, sortDirection: "desc" },
        {
          key: "status",
          label: "Status",
          sortable: true,
          sortDirection: "desc",
        },
        { key: "actions", label: "Ações" },
      ],
      items: [],
    };
  },
  methods: {
    changeToEditionMode(item) {
      this.selectedId = item.id;
      this.isEditing = true;
    },
    back() {
      this.selectedId = "";
      this.isEditing = false;
    },
    save() {
      this.alertMsg = "Salvo com sucesso";
      this.showAlert = true;
    },
    getTokenPayload() {
      const token = localStorage.token.split(".");
      return JSON.parse(atob(token[1]));
    },
    getHeaders() {
      var headers = new Headers();
      headers.append("Authorization", localStorage.token);
      headers.append("Content-Type", "application/json");
      return headers;
    },
    loadProcesses() {
      const processesUrl =
        process.env.VUE_APP_PROCESS_MANAGEMENT_MODULE_URL + "/process";

      const request = {
        method: "GET",
        headers: this.getHeaders(),
      };

      fetch(processesUrl, request)
        .then(async (response) => {
          if (!response.ok) {
            this.alertMsg = "Credenciais inválidas";
            this.showAlert = true;
            return;
          }
          const data = await response.json();
          this.items = data.processes;
          this.isLoading = false;
        })
        .catch((error) => {
          this.alertMsg =
            "Erro ao se comunicar com o servidor. Tente novamente mais tarde." +
            error;
          this.showAlert = true;
        });
    },
  },
  mounted() {
    this.loadProcesses();
  },
};
</script>