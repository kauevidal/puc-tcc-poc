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
        <h3>Gestão de Normas</h3>
      </b-col>
    </b-row>

    <!--Filtro de busca -->
    <b-row class="mb-2" v-if="!isEditing">
      <b-form-input
        id="filter-input"
        v-model="filter"
        type="search"
        placeholder="Buscar normas"
      ></b-form-input>
    </b-row>

    <!--Visualização -->
    <b-row v-if="!isEditing">
      <b-table
        striped
        hover
        :items="items"
        :fields="fields"
        :busy="isLoading"
        :filter="filter"
      >
        <template #cell(inUse)="row">
          <b-form-checkbox
            id="checkbox-1"
            v-model="row.item.inUse"
            name="checkbox-1"
            :disabled="true"
          >
          </b-form-checkbox>
        </template>
        <template #cell(actions)="row">
          <b-button
            variant="info"
            size="sm"
            @click="edit(row.item)"
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
    <div v-else class="text-center">
      <b-row>
        <b-col col-12>
          <h4>Título - {{ selectedItem.title }}</h4>
        </b-col>
      </b-row>
      <b-row>
        <b-col col-12>
          <p>Palavras-chave: {{ selectedItem.keywords }}</p>
        </b-col>
      </b-row>
      <b-row>
        <b-col col-12>
          <p>Data de publicação: {{ selectedItem.publicationDate }}</p>
        </b-col>
      </b-row>
      <b-row>
        <b-col col-12>
          <b-link :href="selectedItem.url">Acessar norma</b-link>
        </b-col>
      </b-row>
      <b-row>
        <b-col col-12>
          <div v-if="selectedItem.inUse">Desabilitar uso da norma:</div>
          <div v-else>Habilitar uso da norma:</div>
          <b-form-checkbox
            id="checkbox-1"
            v-model="selectedItem.inUse"
            name="checkbox-1"
            class="mx-2"
          >
          </b-form-checkbox>
        </b-col>
      </b-row>

      <b-row class="my-2">
        <b-col col-12>
          <b-button v-on:click="back" variant="info" class="mx-2">
            Voltar
          </b-button>
          <b-button v-on:click="save" variant="success" class="mx-2">
            Salvar
          </b-button>
        </b-col>
      </b-row>
    </div>
  </b-container>
</template>
<script>
import Header from "@/components/Header.vue";

export default {
  name: "StandardsManagement",
  components: {
    Header,
  },
  data() {
    return {
      showAlert: false,
      alertMsg: "",
      isEditing: false,
      isLoading: true,
      filter: null,
      fields: [
        { key: "id", label: "Id", sortable: true, sortDirection: "asc" },
        {
          key: "title",
          label: "Título",
          sortable: true,
          sortDirection: "asc",
        },
        {
          key: "keywords",
          label: "Palavras-chave",
          sortable: true,
          sortDirection: "asc",
        },
        {
          key: "publicationDate",
          label: "Data de publicação",
          sortable: true,
          sortDirection: "asc",
        },
        {
          key: "inUse",
          label: "Em uso",
          sortable: true,
          sortDirection: "asc",
        },
        { key: "actions", label: "Ações" },
      ],
      items: [],
    };
  },
  methods: {
    edit(item) {
      this.selectedItem = item;
      this.isEditing = true;
    },
    save() {
      const processesUrl =
        process.env.VUE_APP_STANDARDS_SERVICE_URL + "/standard";

      const request = {
        method: "PUT",
        headers: this.getHeaders(),
        body: JSON.stringify(this.selectedItem),
      };

      fetch(processesUrl, request)
        .then(async (response) => {
          if (!response.ok) {
            this.alertMsg = "Credenciais inválidas";
            this.showAlert = true;
            return;
          }
          const data = await response.json();
          this.alertMsg = "Salvo com sucesso";
          this.showAlert = true;
          this.selectedItem = data;
        })
        .catch((error) => {
          this.alertMsg =
            "Erro ao se comunicar com o servidor. Tente novamente mais tarde." +
            error;
          this.showAlert = true;
        });
    },
    back() {
      this.selectedItem = {};
      this.isEditing = false;
      this.showAlert = false;
      this.loadStandards();
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
    loadStandards() {
      const processesUrl =
        process.env.VUE_APP_STANDARDS_SERVICE_URL + "/standard";

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
          this.items = data;
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
    this.loadStandards();
  },
};
</script>