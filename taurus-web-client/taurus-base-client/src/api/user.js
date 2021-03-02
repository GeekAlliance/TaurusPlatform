import SERVICE_ADDRESS from "./common";

const API_URL = SERVICE_ADDRESS.BS;

// 用户信息
const API_USER_LOGIN = API_URL + "/oauth/login"; // 用户登录
const API_USER_INFO = API_URL + "/oauth/currUser"; // 获取用户信息
/** 通过模块URL查询所属行为权限 */
const API_USER_MODULE_ACTIONS = API_URL + "/oauth/action/moduleUrl";

export default {
    API_USER_LOGIN,
    API_USER_INFO,
    API_USER_MODULE_ACTIONS,
};
