package com.lfy.retrofit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.lfy.libretrofit.RetrofitHelper
import com.lfy.libretrofit.dialog.LoadingDialog
import com.lfy.libretrofit.dialog.LoadingDialogHelper
import com.lfy.libretrofit.dialog.LoadingDialogManager
import com.lfy.libretrofit.observer.DefaultObserver
import com.lfy.libretrofit.observer.FileUploadObserver
import com.lfy.libretrofit.request.UploadFileRequestBody
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.MultipartBody
import okhttp3.ResponseBody
import java.io.File

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        LoadingDialogManager.getInstance().setDialog(LoadingDialog(this))
        HttpApi.getApiService()
            .githubApi
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : DefaultObserver<String>(this) {
                override fun onSuccess(t: String?) {
                    println(t)
                }

            })

        upload(File(""), object : FileUploadObserver<ResponseBody>(this) {
            override fun onUpLoadSuccess(t: ResponseBody?) {
            }

            override fun onUpLoadFail(e: Throwable?) {
            }

            override fun onProgress(progress: Int) {
            }
        })
    }


    fun upload(file: File, fileUploadObserver: FileUploadObserver<ResponseBody>) {
        val uploadRequestBody = UploadFileRequestBody(file, fileUploadObserver);
        val part = MultipartBody.Part.createFormData("file", file.name, uploadRequestBody)
        HttpApi.getApiService()
            .upload(part)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(fileUploadObserver)
    }

    override fun onPause() {
        super.onPause()
        LoadingDialogHelper.getInstance().rest()
    }
}