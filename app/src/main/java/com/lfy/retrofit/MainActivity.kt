package com.lfy.retrofit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.lfy.libretrofit.RetrofitHelper
import com.lfy.libretrofit.dialog.LoadingDialog
import com.lfy.libretrofit.dialog.LoadingDialogHelper
import com.lfy.libretrofit.dialog.LoadingDialogManager
import com.lfy.libretrofit.observer.DefaultObserver
import com.lfy.libretrofit.observer.FileUploadObserver
import com.lfy.libretrofit.request.UploadFileRequestBody
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import java.io.File
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    private val FORM_CONTENT_TYPE = "Content-Type:application/x-www-form-urlencoded; charset=utf-8"
    val json = "[{\"zy_bm\": \"W120102\",\"kc_bm\": \"02139\"},{\"zy_bm\": \"W120102\",\"kc_bm\": \"02129\"}]"
    var mDisposable : CompositeDisposable ?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mDisposable = CompositeDisposable()

        LoadingDialogManager.getInstance().setDialog(LoadingDialog(this))
        val tvResponseContent = findViewById<TextView>(R.id.textView)

        val paramMap = HashMap<String,String>()
        paramMap.put("sfzh", "510106198807211416")
        paramMap.put("zkzh", "010821335254")
        paramMap.put("kslb", "1")
        paramMap.put("mainIds", "0106")
        paramMap.put("qxname", "金牛区")
        paramMap.put("xx_bm", "0108")
        paramMap.put("courseJson", json)

        val sb = StringBuffer()
        //设置表单参数
        //设置表单参数
        for (key in paramMap.keys) {
            sb.append(key + "=" + paramMap[key] + "&")
        }

        val body = RequestBody.create(MediaType.parse(FORM_CONTENT_TYPE), sb.toString());

        findViewById<Button>(R.id.button).setOnClickListener {
            HttpApi.getApiService().qiangZw(body)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .flatMap {
                    if (it.indexOf("容量不足") == -1){
//                        return@flatMap Observable.()
                    }
                    return@flatMap Observable.just(it)
                }
                .repeat(1000)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : DefaultObserver<String>(this,false){
                    override fun onSuccess(t: String?) {
                        tvResponseContent.text = t
                    }

                    override fun onComplete() {
                        super.onComplete()

                    }

                })




        }




//        upload(File(""), object : FileUploadObserver<ResponseBody>(this) {
//            override fun onUpLoadSuccess(t: ResponseBody?) {
//            }
//
//            override fun onUpLoadFail(e: Throwable?) {
//            }
//
//            override fun onProgress(progress: Int) {
//            }
//        })
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