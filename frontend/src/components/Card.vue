<template>
  <div class="card shadow-sm">
    <span class="img" :style="{ backgroundImage: `url(${item.imagePath})` }"></span>
    <div class="card-body">
      <p class="card-text">
        {{ item.name }}
        <span class="discount badge bg-danger margin-left-10">{{ item.discountPercent }}%</span>
      </p>
      <div class="d-flex justify-content-between align-items-center">
        <button class="btn btn-primary" @click="addToCart(item.id)"><i class="fa fa-shopping-cart" aria-hidden="true"></i></button>
        <small class="price text-muted">{{ lib.getNumberFormatted(item.price) }}원</small>

        <small class="real text-danger">{{ lib.getNumberFormatted(item.price - (item.price * item.discountPercent) / 100) }}원</small>
      </div>
    </div>
  </div>
</template>
<script>
import lib from "@/scripts/lib";
import axios from "axios";

export default {
  name: "Card",
  props: {
    item: Object,
  },
  setup() {
    const addToCart = (itemId) => {
      axios.post(`/api/cart/items/${itemId}`).then(() => {
        console.log("success");
      });
    };

    return { lib, addToCart };
  },
};
</script>
<style scoped>
.card .img {
  display: inline-block;
  width: 100%;
  height: 250px;
  background-size: cover;
  background-position: center;
}

.card .card-body .price {
  text-decoration: line-through;
}

.margin-left-10 {
  margin-left: 10px;
}
</style>
