<template lang="">
  <div class="form-signin">
    <h1 class="h3 mb-3 fw-normal">Sign in Form</h1>
    <div class="form-floating">
      <input type="email" class="form-control" id="floatingInput" placeholder="name@example.com" @keyup.enter="submit()" v-model="state.form.email" ref="emailInput" />
      <label for="floatingInput">Email address</label>
    </div>
    <div class="form-floating">
      <input type="password" class="form-control" id="floatingPassword" placeholder="Password" @keyup.enter="submit()" v-model="state.form.password" ref="passwordInput" />
      <label for="floatingPassword">Password</label>
    </div>

    <button class="w-100 btn btn-lg btn-primary" @click="submit()">Sign in</button>
    <p class="mt-5 mb-3 text-muted">&copy; 2025</p>
  </div>
</template>
<script>
import router from "@/scripts/router";
import store from "@/scripts/store";
import axios from "axios";
import { nextTick, reactive, ref } from "vue";

export default {
  setup() {
    const state = reactive({
      form: {
        email: "",
        password: "",
      },
    });

    const emailInput = ref(null);
    const passwordInput = ref(null);

    const submit = () => {
      if (state.form.email == "") {
        window.alert("이메일을 입력해주세요");
        nextTick(() => {
          if (emailInput.value) {
            emailInput.value.focus();
          }
        });
        return;
      }

      if (state.form.password == "") {
        window.alert("비밀번호를 입력해주세요");
        nextTick(() => {
          if (passwordInput.value) {
            passwordInput.value.focus();
          }
        });
        return;
      }

      axios
        .post("/api/account/login", state.form)
        .then((res) => {
          store.commit("setAccount", res.data);
          console.log(res.data);
          sessionStorage.setItem("id", res.data);
          router.push({ path: "/" });
          window.alert("로그인되었습니다.");
        })
        .catch(() => {
          window.alert("로그인 정보가 존재하지 않습니다.");
        });
    };
    return { state, submit, emailInput, passwordInput };
  },
};
</script>

<style scoped>
html,
body {
  height: 100%;
}

body {
  display: flex;
  align-items: center;
  padding-top: 40px;
  padding-bottom: 40px;
  background-color: #f5f5f5;
}

.form-signin {
  width: 100%;
  max-width: 330px;
  padding: 15px;
  margin: auto;
}

.form-signin .checkbox {
  font-weight: 400;
}

.form-signin .form-floating:focus-within {
  z-index: 2;
}

.form-signin input[type="email"] {
  margin-bottom: -1px;
  border-bottom-right-radius: 0;
  border-bottom-left-radius: 0;
}

.form-signin input[type="password"] {
  margin-bottom: 10px;
  border-top-left-radius: 0;
  border-top-right-radius: 0;
}

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
