import Vue from "vue";
import App from "./App.vue";
import Index from "./components/Index";
import Signup from "./components/Signup";
import Login from "./components/Login";
import MyInfo from "./components/member/MyInfo";
import BoardList from "./components/board/BoardList";
import Board from "./components/board/BoardForm";
import BoardForm from "./components/board/BoardForm";
import BoardFormById from "./components/board/BoardForm";
import BoardDetail from "./components/board/BoardDetail";

import ElementUI from "element-ui";
import VueRouter from "vue-router";
import axios from "axios";

import "element-ui/lib/theme-chalk/index.css";

Vue.use(ElementUI);
Vue.use(VueRouter);
Vue.prototype.$axios = axios;

const router = new VueRouter({
  mode: "history",
  routes: [
    {
      path: "/",
      component: Index,
      props: true,
      children: [
        { path: "/signup", component: Signup },
        { path: "/login", component: Login },
        { path: "/myinfo/:memberId", name: "myinfo", component: MyInfo },
        { path: "/boardlist", component: BoardList },
        { path: "/boarddetail/:boardId", name: "boarddetail", component: BoardDetail },
        { 
          path: "/boardform",
          component: Board,
          children: [
            { path: "", component: BoardForm },
            { path: ":boardId", component: BoardFormById, props: true }
          ]
        }
      ]
    }
  ]
})

new Vue({
  el: "#app",
  render: h => h(App),
  router
})