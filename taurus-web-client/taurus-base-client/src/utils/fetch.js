import "whatwg-fetch";
import {removeUserInfo, getToken} from "./token";

require("isomorphic-fetch");
let modalDestoryed = true;

// 处理get请求，传入参数对象拼接
let formatUrl = obj => {
    let params = Object.values(obj).reduce((a, b, i) => `${a}${Object.keys(obj)[i]}=${encodeURI(b)}&`, "?");
    return params.substring(0, params.length - 1);
};

let Fetch = (url, option = {}) => {
    option.headers = option.headers || {};
    const token = getToken();
    if (token != null && token != undefined && token != "" && token != "undefined") {
        option.headers["Authorization"] = token;
    }
    const m = (option.method || "").toLocaleLowerCase();
    // get query format
    if (m == "get") {
        if (option.query) {
            url = url + formatUrl(option.query);
        }
    }
    // 对非get类请求头和请求体做处理
    if (m === "post" || m === "put" || m === "delete") {
        option.headers["Content-Type"] = "application/json";
        // formData上传文件时，不能有headers，这样fetch会自动添加正确的content-type;body不能JSON.stringify
        let isFormData = option.headers["formData"];
        if (isFormData) {
            delete option.headers["Content-Type"];
            delete option.headers["formData"];
        }

        if (isFormData) {
            //option.body = option.body;
        } else if (option.body instanceof Array || option.body instanceof Object) {
            option.body = JSON.stringify(option.body);
        } else {
            option.body = '"' + option.body + '"';
        }
        // option.body = qs.stringify(option.body) //根据后台要求，如果有时候是java请求会用qs转
    }
    return new Promise(resolve => {
        function handleResponse(response) {
            let contentType = response.headers.get("content-type");
            if (contentType.includes("application/json")) {
                return handleJSONResponse(response);
            } else if (contentType.includes("text/html") || contentType.includes("text/plain;charset=UTF-8")) {
                return handleTextResponse(response);
            } else if (contentType.includes("application/octet-stream;charset=UTF-8")) {
                return handleBlobResponse(response);
            } else {
                throw new Error(`Sorry, content-type ${contentType} not supported`);
            }
        }

        function handleJSONResponse(response) {
            return response.json().then(json => {
                if (response.ok) {
                    if (parseInt(json.code) && (parseInt(json.code) <= 1000 || parseInt(json.code) == 1100) && parseInt(json.code) != 1) {
                        return Promise.reject(
                            Object.assign({}, json, {
                                status: parseInt(json.code),
                                statusText: json.message,
                            }),
                        );
                    } else if (json.code && json.code == 10020005) {
                        if (modalDestoryed) {
                            let modal = Modal.info();
                            modalDestoryed = false;
                            modal.update({
                                title: "token已过期，请点击确定按钮重新登录",
                                okText: "确定",
                                onOk: () => {
                                    //router.push({ name: "login" });
                                    removeUserInfo();
                                    modal.destroy();
                                    modalDestoryed = true;
                                },
                            });
                        }
                    } else {
                        return json;
                    }
                } else {
                    return Promise.reject(
                        Object.assign({}, json, {
                            status: response.status,
                            statusText: response.statusText,
                        }),
                    );
                }
            });
        }

        function handleTextResponse(response) {
            return response.text().then(text => {
                if (response.ok) {
                    return text;
                } else {
                    return Promise.reject({
                        status: response.status,
                        statusText: response.statusText,
                        err: text,
                    });
                }
            });
        }

        function handleBlobResponse(response) {
            return response.blob().then(blob => {
                if (response.ok) {
                    return blob;
                } else {
                    return Promise.reject({
                        status: response.status,
                        statusText: response.statusText,
                        err: blob,
                    });
                }
            });
        }

        fetch(url, option)
            .then(handleResponse)
            .then(response => {
                //console.log("请求跟踪:");
                //console.log(response);
                if (response.status === 500) {
                    //CookiesCookies.set("token", "");
                }
                // response.status = status;
                // if (response.code >'200') {
                //   console.log('400')
                // if (response.state == 8888) {
                //   // 登陆超时返回状态吗
                //   Fetch.overTime && Fetch.overTime(response)
                // }
                // if (response.state == 6666) {
                //   // 没有权限返回状态码
                // }
                // }
                resolve(response);
            })
            .catch(error => {
                console.log(error);
                if (error.status == undefined) error.status = 404;
                if (!error.statusText) error.statusText = error.message;
                if (error.status <= 1000) {
                    error.statusText = "服务器请求错误，请稍后再试或者咨询管理员";
                    if (error.status == 404) error.statusText = "服务器无法连接或者您请求的页面地址不对";
                    if (error.status == 500) error.statusText = "服务器处理发生错误，请咨询管理员";
                }
                // AsConfirm.error({
                //   height: 100,
                //   title: "请求响应异常",
                //   body: error.statusText + "（" + error.status + "）",
                //   onOk: function() {
                //     if (
                //       error.status == 1100 || //用户未登录或者登录已经过期
                //       error.status == 404 || //服务器无法连接
                //       error.status == 500 //服务器抛出异常
                //     ) {
                //       if (vm.$editDialog && vm.$editDialog.instance) {
                //         vm.$editDialog.instance.$destroy();
                //         vm.$editDialog.instance.$el.remove();
                //       }
                //       loginOut();
                //     }
                //   }
                // });
                // router.push({
                //     name: "login",
                // });
                resolve({
                    code: error.status,
                    message: error.statusText,
                });
            });
    });
};

export default Fetch;
