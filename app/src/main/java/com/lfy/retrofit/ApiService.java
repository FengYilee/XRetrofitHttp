package com.lfy.retrofit;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Url;

/**
 * Created by FengYi.Lee<fengyi.li@hotmail.com> on 2020/11/24.
 */
interface ApiService {
    @GET("/")
    Observable<String> getGithubApi();

    @Multipart
    @POST("/upload")
    Observable<ResponseBody> upload(@Part MultipartBody.Part part);

}
