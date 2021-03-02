import Cookies from "js-cookie";

export const TOKEN_KEY = "token";
export const USER_INFO = "userInfo";

export const setToken = token => {
    Cookies.set(TOKEN_KEY, token, {expires: 1});
};

export const getToken = () => {
    const token = Cookies.get(TOKEN_KEY);
    if (token) return token;
    else return false;
};

export const setUserInfo = userInfo => {
    Cookies.set(USER_INFO, userInfo, {expires: 1});
};

export const getUserInfo = () => {
    const userInfo = Cookies.get(USER_INFO);
    if (userInfo) {
        return JSON.parse(userInfo);
    } else {
        return false;
    }
};

export const removeUserInfo = () => {
    Cookies.remove(TOKEN_KEY);
    Cookies.remove(USER_INFO);
};

export default {
    TOKEN_KEY,
    USER_INFO,
    setToken,
    getToken,
    setUserInfo,
    getUserInfo,
    removeUserInfo,
};
