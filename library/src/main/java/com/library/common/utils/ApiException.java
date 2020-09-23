package com.library.common.utils;

/**
 * Creator: thymejoe@gmail.com <br>
 * Date: 2017/11/13 14:45 <br>
 */
public class ApiException extends Exception
{
    private int code;
    private String msg;
    private boolean isUnauthorized = false;

    public ApiException(String msg, int code, boolean isUnauthorized)
    {
        super(msg);
        this.code = code;
        this.msg = msg;
        this.isUnauthorized = isUnauthorized;
    }

    public int getCode()
    {
        return code;
    }

    public String getMsg()
    {
        return msg;
    }

    public void setCode(int code)
    {
        this.code = code;
    }

    public boolean isUnauthorized()
    {
        return isUnauthorized;
    }
}
