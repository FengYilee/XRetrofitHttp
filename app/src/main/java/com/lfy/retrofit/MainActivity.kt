package com.lfy.retrofit

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.*
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
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.collections.HashMap

class MainActivity : AppCompatActivity() {

    private val FORM_CONTENT_TYPE = "Content-Type:application/x-www-form-urlencoded; charset=utf-8"
    private val FORM_CONTENT_JSON_TYPE = "Content-Type:application/json; charset=utf-8"
    val json = "[{\"zy_bm\": \"W120102\",\"kc_bm\": \"02134\"},{\"zy_bm\": \"W120102\",\"kc_bm\": \"02139\"},{\"zy_bm\": \"W120102\",\"kc_bm\": \"02136\"},{\"zy_bm\": \"W120102\",\"kc_bm\": \"02129\"}]"
    var mDisposable : CompositeDisposable ?= null
    val sdf = SimpleDateFormat("yy/MM/dd HH:mm:ss")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mDisposable = CompositeDisposable()

        LoadingDialogManager.getInstance().setDialog(LoadingDialog(this))
        val tvResponseContent = findViewById<TextView>(R.id.textView)
        val btnGetAuth = findViewById<Button>(R.id.btn_get_auth)
        val ivAuth = findViewById<ImageView>(R.id.iv_auth)
        val btnLogin = findViewById<Button>(R.id.btn_login)

        val etAccount = findViewById<EditText>(R.id.et_account)
        val etPassword = findViewById<EditText>(R.id.et_password)
        val etAuth = findViewById<EditText>(R.id.et_auth)

        val etSfzh = findViewById<EditText>(R.id.et_sfzh)
        val etZkzh = findViewById<EditText>(R.id.et_zkzh)
        val etAreaCode =findViewById<EditText>(R.id.et_area_code)
        val etAreaName =findViewById<EditText>(R.id.et_area_name)


        btnLogin.setOnClickListener {
            val account = etAccount.text
            val password = etPassword.text
            val code = etAuth.text
            if (TextUtils.isEmpty(account) || TextUtils.isEmpty(password)){
                Toast.makeText(this,"账号或密码不能为空",Toast.LENGTH_LONG).show()
            }
            if (TextUtils.isEmpty(code)){
                Toast.makeText(this,"验证码不能为空",Toast.LENGTH_LONG).show()
            }


            val loginJson = "data:{name:${account},code:${code},pwd:${password}}"
            val body = RequestBody.create(MediaType.parse(FORM_CONTENT_TYPE), loginJson)
            HttpApi.getApiService().login(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : DefaultObserver<String>(this){
                    override fun onSuccess(response: String) {
                        HttpApi.COOKIE_VALUE = response
                        tvResponseContent.text = response
                    }

                })
        }

        btnGetAuth.setOnClickListener {

            HttpApi.getApiService().authImage
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .flatMap {
                    responseBody->
                    val inputStream = responseBody.byteStream()
                    val bitmap = BitmapFactory.decodeStream(inputStream)
                    return@flatMap Observable.just(bitmap)
                }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : DefaultObserver<Bitmap>(this){
                    override fun onSuccess(bitmap: Bitmap) {
                        ivAuth.setImageBitmap(bitmap)
                    }

                })
        }


        val paramMap = HashMap<String,String>()

        paramMap.put("courseJson", json)

        val sb = StringBuffer()
        //设置表单参数
        //设置表单参数
        for (key in paramMap.keys) {
            sb.append(key + "=" + paramMap[key] + "&")
        }

        val body = RequestBody.create(MediaType.parse(FORM_CONTENT_TYPE), sb.toString());

        findViewById<Button>(R.id.button).setOnClickListener {

            if (TextUtils.isEmpty(etSfzh.text.toString())){
                Toast.makeText(this,"身份证号不能为空",Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            if (TextUtils.isEmpty(etZkzh.text.toString())){
                Toast.makeText(this,"准考证不能为空",Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            if (TextUtils.isEmpty(etAreaCode.text.toString())){
                Toast.makeText(this,"地区编码号不能为空",Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            if (TextUtils.isEmpty(etAreaName.text.toString())){
                Toast.makeText(this,"地区名称不能为空",Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }


            paramMap.put("sfzh", etSfzh.text.toString())  //身份证号
            paramMap.put("zkzh",  etZkzh.text.toString())        //准考证号
            paramMap.put("kslb",  etSfzh.text.toString())
            paramMap.put("mainIds",  etAreaCode.text.toString())
            paramMap.put("qxname",  etAreaName.text.toString())
            paramMap.put("xx_bm",  etAreaCode.text.toString())


            HttpApi.getApiService().qiangZw(body)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .flatMap {
                    Thread.sleep(1000)
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
                        val sb = StringBuffer()
                        sb.append("ServerResponse:$t \n")
                        sb.append("UpdateTime：${sdf.format(Date())}")
                        tvResponseContent.text = sb.toString()
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