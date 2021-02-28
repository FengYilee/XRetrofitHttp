package com.lfy.retrofit;

import com.lfy.libretrofit.NetProvider;
import com.lfy.libretrofit.RetrofitHelper;

import java.io.IOException;

import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Created by FengYi.Lee<fengyi.li@hotmail.com> on 2020/11/25.
 */
class HttpApi {

    private static final String BASE_URL = "https://zk.sceea.cn";
    public static String COOKIE_VALUE = "";


    public static ApiService apiService = null;
    public static NetProvider netProvider = null;

    public static ApiService getApiService(){
        if (apiService == null){
            apiService = RetrofitHelper.createApiService(ApiService.class,BASE_URL,getProvider(),true);
        }
        return apiService;
    }

    private static NetProvider getProvider(){
        if (netProvider == null){
            netProvider = new NetProvider(){
                @Override
                public Headers configCommonHeaders() {
                    return new Headers.Builder()
                            .add("Content-Type","application/x-www-form-urlencoded;charset=utf-8")
                            .add("Cookie",COOKIE_VALUE)
                            .build();
                }
                @Override
                public HttpLoggingInterceptor.Level configLogLevel() {
                    return HttpLoggingInterceptor.Level.BODY;
                }
            };
        }
        return netProvider;
    }



}
