import Vue from "vue";
import {Fetch} from "../../../utils/fetch";
import user from "../../../api/user";
import {setToken, setUserInfo} from "../../../utils/token";

Vue.prototype.Fetch = Fetch;

// states
const state = {};
// actions
const actions = {
    //用户登录
    userLogin({commit}, loginInfo) {
        console.log(loginInfo);
        let {username, password} = loginInfo;
        let obj = {
            username: username,
            password: password,
        };
        return new Promise(resolve => {
            Fetch(user.API_USER_LOGIN, {
                method: "post",
                body: obj,
            }).then(res => {
                resolve(res);
                if (res.code == 1) {
                    commit("MU_USER_TOKEN", res);
                    commit("MU_REM_PSD", loginInfo);
                }
            });
        });
    },

    // 登录后获取用户信息
    getUserInfo({commit}) {
        return new Promise(resolve => {
            Fetch(user.API_USER_INFO, {
                method: "get",
            }).then(res => {
                resolve(res);
                if (res.code == 1) {
                    commit("MU_USER_INFO", res);
                }
            });
        });
    },
};

// mutations
const mutations = {
    MU_MONEY_LIST(state, res) {
        state.moneyListData = res;
    },
    MU_USER_LIST(state, res) {
        state.getUserListData = res.data.list;
        state.pager = {
            pageNo: res.data.pageNum,
            pageSize: res.data.pageSize,
            totalPages: res.data.pages,
            totalRows: res.data.total, //总数据
        };
    },
    MU_USER_TOKEN(state, res) {
        console.log(res);
        let token = res.data.tokenType + " " + res.data.accessToken;
        setToken(token);
    },
    MU_USER_INFO(state, res) {
        state.userInfo = res.data;
        setUserInfo(res.data);
    },

    MU_FIND_USER(state, res) {
        state.getSingleUertData = res.data;
        localStorage.setItem("user", JSON.stringify(state.getSingleUertData));
    },
};

// getters
const getters = {
    checkoutStatus: state => state.checkoutStatus,
};

export default {
    namespaced: true,
    state,
    getters,
    actions,
    mutations,
};
