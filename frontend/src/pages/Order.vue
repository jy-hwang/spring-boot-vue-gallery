<template lang="">
  <div class="order">
    <div class="container">
      <main>
        <div class="py-5 text-center">
          <h1 class="h2">주문하기</h1>
          <p class="lead">
            Below is an example form built entirely with Bootstrap’s form controls. Each required form group has a validation state that can be triggered by attempting to submit the form without
            completing it.
          </p>
        </div>
        <div class="row g-5">
          <div class="col-md-5 col-lg-4 order-md-last">
            <h4 class="d-flex justify-content-between align-items-center mb-3">
              <span class="text-primary">구입 목록</span>
              <span class="badge bg-primary rounded-pill">
                {{ state.items.length }}
              </span>
            </h4>
            <ul class="list-group mb-3">
              <li class="list-group-item d-flex justify-content-between lh-sm" v-for="(i, idx) in state.items" :key="idx">
                <div>
                  <h6 class="my-0">{{ i.name }}</h6>
                </div>
                <span class="text-body-secondary">{{ lib.getNumberFormatted(i.price - (i.price * i.discountPercent) / 100) }}원</span>
              </li>
            </ul>
            <h3 class="text-center total-price">
              최종가격
              {{ lib.getNumberFormatted(computedPrice) }}
            </h3>
          </div>
          <div class="col-md-7 col-lg-8">
            <h4 class="mb-3">주문자 정보</h4>
            <div class="needs-validation" novalidate>
              <div class="row g-3">
                <div class="col-12">
                  <label for="username" class="form-label">이름</label>
                  <input type="text" class="form-control" id="username" placeholder="Username" v-model="state.form.name" />
                  <div class="invalid-feedback">Your username is required.</div>
                </div>
                <div class="col-12">
                  <label for="address" class="form-label">주소</label>
                  <input type="text" class="form-control" id="address" placeholder="1234 Main St" v-model="state.form.address" />
                  <div class="invalid-feedback">Please enter your shipping address.</div>
                </div>
              </div>
              <hr class="my-4" />
              <h4 class="mb-3">Payment</h4>
              <div class="my-3">
                <div class="form-check">
                  <input id="card" name="paymentMethod" type="radio" class="form-check-input" v-model="state.form.payment" value="card" />
                  <label class="form-check-label" for="card">신용카드</label>
                </div>
                <div class="form-check">
                  <input id="bank" name="paymentMethod" type="radio" class="form-check-input" v-model="state.form.payment" value="bank" />
                  <label class="form-check-label" for="bank">무통장 입금</label>
                </div>
              </div>
              <div class="row gy-3">
                <div class="col-md-6">
                  <label for="cardNumber" class="form-label">카드번호</label>
                  <input type="text" class="form-control" id="cardNumber" placeholder="" v-model="state.form.cardNumber" />
                </div>
              </div>
              <hr class="my-4" />
              <button class="w-100 btn btn-primary btn-lg" @click="submit()">결제하기</button>
            </div>
          </div>
        </div>
      </main>
    </div>
  </div>
</template>
<script>
import { computed, reactive } from "vue";
import lib from "@/scripts/lib";
import axios from "axios";
export default {
  setup() {
    const state = reactive({
      items: [],
      form: {
        name: "",
        address: "",
        payment: "",
        cardNumber: "",
        items: "",
      },
    });

    const load = () => {
      axios.get("/api/cart/items/").then(({ data }) => {
        console.log(data);
        state.items = data;
      });
    };

    const submit = () => {
      const args = JSON.parse(JSON.stringify(state.form));
      args.items = JSON.stringify(state.items);

      axios.post("/api/orders", args).then(() => {
        console.log("success");
      });
    };

    const computedPrice = computed(() => {
      let result = 0;

      for (let i of state.items) {
        result += i.price - (i.price * i.discountPercent) / 100;
      }

      return result;
    });
    load();
    return { lib, state, computedPrice, submit };
  },
};
</script>

<style scoped></style>
