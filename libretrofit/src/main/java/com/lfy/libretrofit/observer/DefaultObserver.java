package com.lfy.libretrofit.observer;

import android.content.Context;
import android.widget.Toast;

import com.google.gson.JsonParseException;
import com.jakewharton.retrofit2.adapter.rxjava2.HttpException;
import com.lfy.libretrofit.exception.ServerResponseException;

import org.json.JSONException;

import java.io.InterruptedIOException;
import java.net.ConnectException;
import java.net.UnknownHostException;
import java.text.ParseException;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by FengYi.Lee<fengyi.li@hotmail.com> on 2020/11/24.
 */
public abstract class DefaultObserver<T> implements Observer<T> {

    protected Context mContext;

    public DefaultObserver(Context context){
        this.mContext = context;
    }

    @Override
    public void onSubscribe(Disposable d) {
    }

    @Override
    public void onNext(T response) {
        onSuccess(response);
        onFinish();
    }

    @Override
    public void onError(Throwable e) {
        if (e instanceof HttpException) {     //   HTTP错误
            onException(ExceptionReason.BAD_NETWORK);
        } else if (e instanceof ConnectException
                || e instanceof UnknownHostException) {   //   连接错误
            onException(ExceptionReason.CONNECT_ERROR);
        } else if (e instanceof InterruptedIOException) {   //  连接超时
            onException(ExceptionReason.CONNECT_TIMEOUT);
        } else if (e instanceof JsonParseException
                || e instanceof JSONException
                || e instanceof ParseException
                || e instanceof IllegalStateException) {   //  解析错误
            onException(ExceptionReason.PARSE_ERROR);
        }else if(e instanceof ServerResponseException){
            onFail(e.getMessage());
        } else {
            onException(ExceptionReason.UNKNOWN_ERROR);
        }
    }

    @Override
    public void onComplete() {

    }

    public abstract void onSuccess(T t);

    public void onFinish(){}

    /**
     * 服务器返回数据，但响应码不为200
     *
     */
    public void onFail(String message) {
        Toast.makeText(mContext,message,Toast.LENGTH_LONG).show();
    }

    /**
     * 请求异常
     *
     * @param reason
     */
    public void onException(ExceptionReason reason) {
        switch (reason) {
            case CONNECT_ERROR:
                Toast.makeText(mContext,"网络开了点小差,请检查您的网络。",Toast.LENGTH_LONG).show();
                break;

            case CONNECT_TIMEOUT:
                Toast.makeText(mContext,"与服务连接超时。",Toast.LENGTH_LONG).show();
                break;

            case BAD_NETWORK:
                Toast.makeText(mContext,"请求错误,请检查是否404。",Toast.LENGTH_LONG).show();
                break;
            case PARSE_ERROR:
                Toast.makeText(mContext,"解析错误,请检查json串是否正确",Toast.LENGTH_LONG).show();
                break;
            case UNKNOWN_ERROR:
            default:
                Toast.makeText(mContext,"未知网络错误。",Toast.LENGTH_LONG).show();
                break;
        }
    }

    /**
     * 请求网络失败原因
     */
    public enum ExceptionReason {
        /**
         * 解析数据失败
         */
        PARSE_ERROR,
        /**
         * 网络问题
         */
        BAD_NETWORK,
        /**
         * 连接错误
         */
        CONNECT_ERROR,
        /**
         * 连接超时
         */
        CONNECT_TIMEOUT,
        /**
         * 未知错误
         */
        UNKNOWN_ERROR,
    }
}
