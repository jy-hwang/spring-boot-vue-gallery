import Home from "@/pages/Home.vue";
import Login from "@/pages/Login.vue";
import Cart from "@/pages/Cart.vue";
import { createWebHistory } from "vue-router";
import { createRouter } from "vue-router";

const routes = [
  { path: "/", component: Home },
  { path: "/login", component: Login },
  { path: "/cart", component: Cart },
];
const router = createRouter({
  history: createWebHistory(),
  routes,
});

export default router;
