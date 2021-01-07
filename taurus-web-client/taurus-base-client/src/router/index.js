import Vue from 'vue';
import Router from 'vue-router';

Vue.use(Router);

export default new Router({
    routes: [
        {
            path: '/',
            redirect: '/dashboard'
        },
        {
            path: '/',
            component: () => import('../components/common/Home.vue'),
            meta: {title: '自述文件'},
            children: [
                {
                    path: '/dashboard',
                    component: () => import('../views/dash-board/Dashboard.vue'),
                    meta: {title: '系统首页'}
                },
            ]
        },
        {
            path: '/login',
            component: () => import('../views/login/Login.vue'),
            meta: {title: '登录'}
        }
    ]
});
