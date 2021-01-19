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
        <h4>Consultoria</h4>
      </b-col>
    </b-row>
  </b-container>
</template>
<script>
import Header from "../components/Header.vue";

export default {
  name: "Consultancy",
  components: {
    Header,
  },
  data() {
    return {
      showAlert: false,
      alertMsg: "",
    };
  },
  methods: {},
};
</script>