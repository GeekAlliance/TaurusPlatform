import Vue from 'vue'
import App from './App.vue'
import router from './router';
import ElementUI from 'element-ui';
import VueI18n from 'vue-i18n';
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
new Vue({
    router,
    render: h => h(App)
}).$mount('#app');
