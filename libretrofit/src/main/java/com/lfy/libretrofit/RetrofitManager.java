package com.lfy.libretrofit;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by FengYi.Lee<fengyi.li@hotmail.com> on 2020/11/24.
 */
class RetrofitManager {

    private static RetrofitManager mInstance;

    private RetrofitManager(){}

    public static RetrofitManager getInstance(){
        if (mInstance == null){
            synchronized (RetrofitManager.class){
                mInstance = new RetrofitManager();
            }
        }
        return mInstance;
    }

    public Retrofit getRetrofit(String baseUrl){
        return this.getRetrofit(baseUrl,getOkHttpClient(new NetProvider()),false);
    }

    public Retrofit getRetrofit(String baseUrl,NetProvider netProvider){
        return this.getRetrofit(baseUrl,this.getOkHttpClient(netProvider),false);
    }

    public Retrofit getRetrofit(String baseUrl,NetProvider netProvider,boolean closeJson){
        return this.getRetrofit(baseUrl,this.getOkHttpClient(netProvider),closeJson);
    }

    public Retrofit getRetrofit(String baseUrl, OkHttpClient okHttpClient,boolean closeJson){
        Retrofit.Builder builder = new Retrofit.Builder();
        builder.baseUrl(baseUrl)
                .client(okHttpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create());
        if (!closeJson){
            Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").serializeNulls().create();
            builder.addConverterFactory(GsonConverterFactory.create(gson));
        }
        return builder.build();
    }

    public OkHttpClient getOkHttpClient(final NetProvider provider){
        final OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(provider.configConnectTimeoutMills(), TimeUnit.MILLISECONDS);
        builder.readTimeout(provider.configReadTimeoutMills(),TimeUnit.MILLISECONDS);
        builder.writeTimeout(provider.configReadTimeoutMills(),TimeUnit.MILLISECONDS);

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                Log.d("OkHttp",message);
            }
        });
        loggingInterceptor.setLevel(provider.configLogLevel());
        builder.addInterceptor(loggingInterceptor);

        if (provider.configInterceptors() != null){
            for (Interceptor interceptor : provider.configInterceptors()) {
                builder.addInterceptor(interceptor);
            }
        }
        if (provider.configCommonHeaders() != null){
            builder.addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request.Builder builderHeader = chain.request().newBuilder();
                    Headers commonHeaders = provider.configCommonHeaders();
                    for (int i = 0; i < commonHeaders.size(); i++) {
                        builderHeader.addHeader(commonHeaders.name(i),commonHeaders.value(i));
                    }
                    Request request = builderHeader.build();
                    return chain.proceed(request);
                }
            });
        }
        return builder.build();
    }

}
