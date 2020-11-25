package com.lfy.retrofit;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * Created by FengYi.Lee<fengyi.li@hotmail.com> on 2020/11/24.
 */
interface ApiService {
    @GET("/")
    Observable<String> getGithubApi();

}
