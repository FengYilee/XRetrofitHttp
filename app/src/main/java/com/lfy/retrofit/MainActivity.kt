package com.lfy.retrofit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.lfy.libretrofit.RetrofitHelper
import com.lfy.libretrofit.observer.DefaultObserver
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        RetrofitHelper.createApiService(ApiService::class.java,"https://api.github.com")
            .githubApi
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : DefaultObserver<String>(this){
                override fun onSuccess(t:String?) {
                    println(t)
                }

            })
    }
}