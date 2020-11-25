package com.lfy.libretrofit;

import okhttp3.CookieJar;
import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Created by FengYi.Lee<fengyi.li@hotmail.com> on 2020/11/24.
 */
public class NetProvider {

    public NetProvider() {
    }

    public boolean configIsRetryOnConnectionFailure() {
        return true;
    }

    public Headers configCommonHeaders() {
        return null;
    }

    public Interceptor[] configNetworkInterceptors() {
        return null;
    }

    public Interceptor[] configInterceptors() {
        return null;
    }


    public CookieJar configCookie() {
        return null;
    }

    public HttpLoggingInterceptor.Level configLogLevel() {
        return HttpLoggingInterceptor.Level.BODY;
    }

    public boolean log(String message) {
        return false;
    }

    public long configConnectTimeoutMills() {
        return 10000L;
    }

    public long configReadTimeoutMills() {
        return 2000000L;
    }


}
