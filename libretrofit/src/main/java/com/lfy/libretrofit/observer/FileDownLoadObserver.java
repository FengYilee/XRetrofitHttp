package com.lfy.libretrofit.observer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import io.reactivex.observers.DefaultObserver;
import okhttp3.ResponseBody;

/**
 * Created by FengYi.Lee<fengyi.li@hotmail.com> on 2020/11/24.
 */
public abstract class FileDownLoadObserver<T> extends DefaultObserver<T> {

    @Override
    public void onNext(T t) {
        onDownLoadSuccess(t);
    }
    @Override
    public void onError(Throwable e) {
        onDownLoadFail(e);
    }
    //可以重写，具体可由子类实现
    @Override
    public void onComplete() {
    }

    //下载成功的回调
    public abstract void onDownLoadSuccess(T t);
    //下载失败回调
    public abstract void onDownLoadFail(Throwable throwable);
    //下载进度监听
    public abstract void onProgress(int progress,long total);

    public File saveFile(ResponseBody responseBody, String destFileDir, String destFileName)  {
        InputStream is = null;
        FileOutputStream fos = null;
        byte[] buf = new byte[2048];
        int len = 0;
        int sum = 0;
        File file = new File(destFileDir);
        File outputFile = new File(file,destFileName);
        try{
            is = responseBody.byteStream();
            final long total = responseBody.contentLength();
            if (!file.exists()){
                file.mkdirs();
            }
            fos = new FileOutputStream(outputFile);
            while ((len = is.read(buf)) != -1){
                sum += len;
                fos.write(buf,0,len);
                final long finalSum = sum;
                onProgress((int) (finalSum * 100 / total),len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fos != null)
                    fos.close();
                if (is != null)
                    is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        return outputFile;

    }

}
