<template>
  <Header />

  <RouterView />

  <Footer />
</template>

<script>
import Footer from "./components/Footer.vue";
import Header from "./components/Header.vue";
import store from "./scripts/store";

export default {
  name: "App",
  components: {
    Footer,
    Header,
  },
  setup() {
    const id = sessionStorage.getItem("id");

    if (id) {
      store.commit("setAccount", id);
    }
  },
};
</script>

<style>
.bd-placeholder-img {
  font-size: 1.125rem;
  text-anchor: middle;
  -webkit-user-select: none;
  -moz-user-select: none;
  user-select: none;
}

@media (min-width: 768px) {
  .bd-placeholder-img-lg {
    font-size: 3.5rem;
  }
}
</style>
