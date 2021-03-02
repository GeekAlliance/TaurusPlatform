// states
const state = {
    // 全屏标记
    isFullScreen: false,
    // 内容区大小
    clientRect: {
        width: 0,
        height: 0,
    },
};
// actions
const actions = {};

// mutations
const mutations = {
    /**
     * 切换全屏
     * @param {*} state 内部参数，不需要传递
     */
    switchFullScreen(state) {
        state.isFullScreen = !state.isFullScreen;
    },

    /**
     * 设置内容区大小
     * @param {*} state  内部参数，不需要传递
     * @param {object} rect 内容区宽、高
     */
    setClientRect(state, {width, height}) {
        state.clientRect = {width, height};
    },
};

// getters
const getters = {};

export default {
    state,
    getters,
    actions,
    mutations,
};
