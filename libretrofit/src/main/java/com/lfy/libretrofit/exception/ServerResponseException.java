package com.lfy.libretrofit.exception;

/**
 * Created by FengYi.Lee<fengyi.li@hotmail.com> on 2020/11/24.
 */
public class ServerResponseException extends Exception {

    public int code;
    public String message;

    public ServerResponseException(int code, String message) {
        this.code = code;
        this.message = message;
    }

}
