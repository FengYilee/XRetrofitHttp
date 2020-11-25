package com.lfy.libretrofit;

/**
 * Created by FengYi.Lee<fengyi.li@hotmail.com> on 2020/11/24.
 */
public class RetrofitHelper<T> {

    public static <T>T createApiService(Class<T> clazz,String baseUrl){
        return RetrofitManager.getInstance().getRetrofit(baseUrl).create(clazz);
    }

    public static <T>T createApiService(Class<T> clazz,String baseUrl,NetProvider netProvider){
        return RetrofitManager.getInstance().getRetrofit(baseUrl, netProvider).create(clazz);
    }

}
