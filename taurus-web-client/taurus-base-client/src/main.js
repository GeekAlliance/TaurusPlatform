import Vue from 'vue'
import App from './App.vue'
import router from './router';
import store from "./store";
import ElementUI from 'element-ui';
import VueI18n from 'vue-i18n';
import language from "./lang"
import 'element-ui/lib/theme-chalk/index.css'; // 默认主题
Vue.config.productionTip = false;
Vue.use(VueI18n);
Vue.use(ElementUI, {
    size: 'small'
});
//使用钩子函数对路由进行权限跳转
router.beforeEach((to, from, next) => {
    next();
});
// Vue语言切换
const i18n = new VueI18n({
    locale: localStorage.getItem("locale") || "zh-cn", // 语言表示，通过切换local来切换本地语言
    messages: language,
});
window.i18n = i18n;
new Vue({
    i18n,
    store,
    router,
    render: h => h(App)
}).$mount('#app');
