<template lang="">
  <div class="cart">카트페이지</div>
</template>
<script>
import axios from "axios";
import { reactive } from "vue";

export default {
  setup() {
    axios.get("/api/cart/items/").then(({ data }) => {
      console.log(data);
    });

    const state = reactive({});

    return { state };
  },
};
</script>

<style scoped></style>
