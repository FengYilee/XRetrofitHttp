package com.lfy.retrofit;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Url;

/**
 * Created by FengYi.Lee<fengyi.li@hotmail.com> on 2020/11/24.
 */
interface ApiService {
    @GET("/RegExam/login/AuthImageServlet")
    Observable<ResponseBody> getAuthImage();

    @Multipart
    @POST("/upload")
    Observable<ResponseBody> upload(@Part MultipartBody.Part part);

    @POST("/RegExam/switchPage?resourceId=reg")
    Observable<String> qiangZw(@Body RequestBody body);

    @POST("/RegExam/elogin?resourceId=login")
    @Headers({
            "Content-Type:application/x-www-form-urlencoded; charset=UTF-8",
            "Origin: https://zk.sceea.cn",
            "Referer: https://zk.sceea.cn/",
            "Accept: application/json, text/javascript, */*; q=0.01",
            "Accept-Language: zh-CN,zh-TW;q=0.9,zh;q=0.8,en-US;q=0.7,en;q=0.6",
            "Accept-Encoding: gzip, deflate, br",
            "Connection: keep-alive",
            "Sec-Fetch-Dest: empty",
            "Sec-Fetch-Mode: cors",
            "Sec-Fetch-Site: same-origin",
            "User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/88.0.4324.182 Safari/537.36" ,
            "X-Requested-With: XMLHttpRequest",
            "Cookie: JSESSIONID=B47AE3A792671B3F268B13AD7EFD4FE9; COOKIE_SUPPORT=true; GUEST_LANGUAGE_ID=zh_CN; X-LB=2.12.13.5cdcdb23.1f90; JSESSIONID=66A59762FE404F1F2F02B929A6773934"
    })
    Observable<String> login(@Body RequestBody body);



}
