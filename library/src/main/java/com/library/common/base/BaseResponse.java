package com.library.common.base;


import com.google.gson.annotations.SerializedName;

/**
 * Description: 服务器返回数据格式 <br>
 * <p>
 * Creator: thymejoe@gmail.com <br>
 * Date: 2017/8/7 15:28 <br>
 */
public class BaseResponse<T>  {
    @SerializedName(value = "data",alternate = {"artists", "songs"})
    private T data;
    private int code;
    private String msg;


    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }


    /**
     * 请求是否成功
     *
     * @return
     */
    public boolean isSuccess() {
        return code == 200;
    }

    public boolean isUnauthorized() {
        return code == 401;
    }
}
