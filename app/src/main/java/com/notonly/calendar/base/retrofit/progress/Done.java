package com.notonly.calendar.base.retrofit.progress;

/**
 * 完成状态 - 进度状态
 *
 * @author a_liYa
 * @date 16/8/6 19:32.
 */
public enum Done {

    START_UP(Type.REQUEST, State.START),
    PROCESS_UP(Type.REQUEST, State.PROCESS),
    FINISH_UP(Type.REQUEST, State.FINISH),

    START_DOWN(Type.RESPONSE, State.START),
    PROCESS_DOWN(Type.RESPONSE, State.PROCESS),
    FINISH_DOWN(Type.RESPONSE, State.FINISH);

    /**
     * 类型 : 上传或下载
     */
    private Type type;
    /**
     * 状态 : 开始、进行中或结束
     */
    private State state;


    Done(Type type, State state) {
        this.type = type;
        this.state = state;
    }

    public State getState() {
        return state;
    }

    public Type getType() {
        return type;
    }

    public boolean isUpload() {
        return type == Type.REQUEST;
    }

    public boolean isDownload() {
        return type == Type.RESPONSE;
    }


    @Override
    public String toString() {
        String str = "";
        if (type == Type.RESPONSE) {
            str += "下载 + ";
            switch (state) {
                case START:
                    str += "开始";
                    break;
                case PROCESS:
                    str += "进行中";
                    break;
                case FINISH:
                    str += "结束";
                    break;
            }
        }
        return str;
    }

    /**
     * 请求类型 : 上传还是下载
     *
     * @author a_liYa
     * @date 16/8/6 下午7:36.
     */
    public enum Type {
        /**
         * 上传
         */
        REQUEST,

        /**
         * 下载
         */
        RESPONSE
    }

    /**
     * 状态 : 开始、进行中、结束
     */
    public enum State {
        /**
         * 开始
         */
        START,
        /**
         * 进行中
         */
        PROCESS,
        /**
         * 结束
         */
        FINISH
    }
}
