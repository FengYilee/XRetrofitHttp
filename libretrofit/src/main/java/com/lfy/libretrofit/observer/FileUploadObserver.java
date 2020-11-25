package com.lfy.libretrofit.observer;

import android.content.Context;

/**
 * Created by FengYi.Lee<fengyi.li@hotmail.com> on 2020/11/24.
 */
public abstract class FileUploadObserver<T> extends DefaultObserver<T> {

    public FileUploadObserver(Context context) {
        super(context);
    }

    @Override
    public void onSuccess(T t) {

    }

    @Override
    public void onNext(T response) {
        onUpLoadSuccess(response);
    }

    @Override
    public void onError(Throwable e) {
        onUpLoadFail(e);
    }

    public void onProgressChange(long bytesWritten, long contentLength){
        onProgress((int)(bytesWritten*100/contentLength));
    }

    //上传成功的回调
    public abstract void onUpLoadSuccess(T t);

    //上传失败回调
    public abstract void onUpLoadFail(Throwable e);

    //上传进度回调
    public abstract void onProgress(int progress);

}
