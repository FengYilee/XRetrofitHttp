package com.lfy.retrofit;

import com.lfy.libretrofit.NetProvider;
import com.lfy.libretrofit.RetrofitHelper;

import okhttp3.Headers;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Created by FengYi.Lee<fengyi.li@hotmail.com> on 2020/11/25.
 */
class HttpApi {

    private static final String BASE_URL = "https://zk.sceea.cn";

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
                            .add("Cookie","JSESSIONID=8CA9657F625BD81318350D1B434B77C5; COOKIE_SUPPORT=true; GUEST_LANGUAGE_ID=zh_CN; X-LB=2.28.29.64ee8c1c.1f90; JSESSIONID=144A7C5A3A88262B87BD15C3434AD4AD; allan=B3P5256P5622M7P52M726P5ZP5P527M7B3B39P56M7P5M7P55")
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
