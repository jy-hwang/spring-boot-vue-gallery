<template lang="">
  <div class="cart">
    <div class="container">
      <ul>
        <li v-for="(i, idx) in state.items" :key="idx">
          <img :src="i.imagePath" />
          <span class="name">{{ i.name }}</span>
          <span class="price">{{ lib.getNumberFormatted(i.price - (i.price * i.discountPercent) / 100) }}원</span>
          <i class="fa fa-trash" @click="remove(i.id)"></i>
        </li>
      </ul>
      <router-link to="/order" class="btn btn-primary">구입하기</router-link>
    </div>
  </div>
</template>
<script>
import axios from "axios";
import { reactive } from "vue";
import lib from "@/scripts/lib";
export default {
  setup() {
    const load = () => {
      axios.get("/api/cart/items/").then(({ data }) => {
        state.items = data;
      });
    };
    const state = reactive({
      items: [],
    });

    const remove = (itemId) => {
      axios.delete(`/api/cart/items/${itemId}`).then(({ data }) => {
        load();
      });
    };

    load();
    return { lib, state, remove };
  },
};
</script>

<style scoped>
.cart ul {
  list-style: none;
  margin: 0;
  padding: 0;
}
.cart ul li {
  border: 1px solid #eee;
  margin-top: 25px;
  margin-bottom: 25px;
}

.cart ul li img {
  width: 150px;
  height: 150px;
}

.cart ul li .name,
.cart ul li .price {
  margin-left: 25px;
}

.cart ul li i {
  float: right;
  font-size: 20px;
  margin-top: 65px;
  margin-right: 50px;
}

.cart .btn {
  width: 300px;
  display: block;
  margin: 0 auto;
  padding: 30px 50px;
  font-size: 20px;
}
</style>
