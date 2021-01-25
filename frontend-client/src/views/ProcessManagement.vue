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
          <h6>Resumo</h6>
        </b-col>
      </b-row>
      <b-row>
        <b-col col-12>
          <p>Nome do Processo: {{ selectedItem.name }}</p>
        </b-col>
      </b-row>
      <b-row>
        <b-col col-12>
          <p>
            Status do Processo: {{ parseProcessStatus(selectedItem.status) }}
          </p>
        </b-col>
      </b-row>
      <b-row>
        <b-col col-12>
          <p>Estoque Insumos: {{ selectedItem.material_status }}%</p>
        </b-col>
      </b-row>
      <b-row>
        <b-col col-12>
          <p>Capacidade de produção: {{ selectedItem.capacity }} peças/hora</p>
        </b-col>
      </b-row>
      <hr />

      <b-row>
        <b-col col-12>
          <h6 class="text-left">Pedidos:</h6>
          <b-card-group deck>
            <b-card
              v-for="order in selectedItem.order"
              :key="order.id"
              style="max-width: 20rem"
              :sub-title="order.id.toString()"
              class="my-1"
            >
              <b-card-text>Comprador: {{ order.buyer }}</b-card-text>
              <b-card-text>Quantidade: {{ order.quantity }}</b-card-text>
              <b-card-text>
                Prazo de entrega: {{ order.due_date }}
              </b-card-text>
            </b-card>
          </b-card-group>
        </b-col>
      </b-row>
      <hr />

      <b-row>
        <b-col col-12>
          <h6 class="text-left">Atividades:</h6>
          <b-card-group deck>
            <b-card
              v-for="activity in activities"
              :key="activity.id"
              style="max-width: 20rem"
              :sub-title="activity.name.toString()"
              class="my-1"
            >
              <b-card-text> Id: {{ activity.id }} </b-card-text>
              <b-card-text> Status: {{ activity.status }}</b-card-text>
            </b-card>
          </b-card-group>
        </b-col>
      </b-row>
      <hr />

      <b-row>
        <b-col col-12>
          <h6 class="text-left">Pendências de compliance:</h6>
          <b-card
            v-for="requirement in requirements"
            :key="requirement.id"
            style="max-width: 40rem"
            :sub-title="requirement.title.toString()"
            class="my-2"
          >
            <b-card-text> Id: {{ requirement.id }} </b-card-text>
            <b-card-text>
              Descrição: {{ requirement.description }}
            </b-card-text>
            <b-card-text> Status: {{ requirement.status }} </b-card-text>
            <b-card-text> Norma: {{ requirement.standardId }} </b-card-text>
          </b-card>
        </b-col>
      </b-row>
      <hr />

      <b-row class="my-2">
        <b-col col-12>
          <b-button v-on:click="back" variant="info" class="mx-2 float-left">
            Voltar
          </b-button>
        </b-col>
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
      selectedItem: {},
      isEditing: false,
      isLoading: true,
      fields: [
        { key: "id", label: "Id", sortable: true, sortDirection: "desc" },
        { key: "name", label: "Nome", sortable: true, sortDirection: "desc" },
        { key: "actions", label: "Ações" },
      ],
      items: [],
      activities: [],
      requirements: [],
    };
  },
  methods: {
    edit(item) {
      this.selectedItem = item;
      this.loadActivities();
      this.loadRequirements();
      this.isEditing = true;
    },
    back() {
      this.selectedItem = {};
      this.activities = [];
      this.isEditing = false;
    },
    parseProcessStatus(status) {
      if (status === "operational") {
        return "Operacional";
      } else {
        return "parado por Problemas";
      }
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
        process.env.VUE_APP_PROCESS_MANAGEMENT_URL + "/process";

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
    loadActivities() {
      const processesUrl =
        process.env.VUE_APP_PROCESS_MANAGEMENT_URL +
        "/activity?processId=" +
        this.selectedItem.id;

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
          this.activities = data;
        })
        .catch((error) => {
          this.alertMsg =
            "Erro ao se comunicar com o servidor. Tente novamente mais tarde." +
            error;
          this.showAlert = true;
        });
    },
    loadRequirements() {
      const requirementsUrl =
        process.env.VUE_APP_CONSULTANCY_SERVICE_URL +
        "/requirement?processId=" +
        this.selectedItem.id;

      const request = {
        method: "GET",
        headers: this.getHeaders(),
      };

      fetch(requirementsUrl, request)
        .then(async (response) => {
          if (!response.ok) {
            this.alertMsg = "Credenciais inválidas";
            this.showAlert = true;
            return;
          }
          const data = await response.json();
          this.requirements = data;
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