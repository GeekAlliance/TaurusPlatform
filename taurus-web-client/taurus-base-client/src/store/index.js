import Vue from "vue";
import Vuex from "vuex";
import global from "./global";
import User from "./modules/user/user";

Vue.use(Vuex);

export default new Vuex.Store({
    ...global,
    modules: {
        User,
    },
});
