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
    public static String COOKIE_VALUE = "JSESSIONID=893259DE17ED5B4B2E78CE1A6B2F7630; COOKIE_SUPPORT=true; GUEST_LANGUAGE_ID=zh_CN; X-LB=2.12.13.5cdcdb23.1f90; JSESSIONID=AA620B061077A2C4569E6EAE53D817DF; allan=65Z52P5722M7P52M726P5ZP5P527M7B3B39P56M7P5M7P55";


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
