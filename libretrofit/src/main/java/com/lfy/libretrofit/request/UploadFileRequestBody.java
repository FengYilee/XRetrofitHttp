package com.lfy.libretrofit.request;

import com.lfy.libretrofit.observer.FileUploadObserver;

import java.io.File;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSink;
import okio.ForwardingSink;
import okio.Okio;
import okio.Sink;

/**
 * Created by FengYi.Lee<fengyi.li@hotmail.com> on 2020/11/24.
 */
public class UploadFileRequestBody extends RequestBody {

    private RequestBody mRequestBody;
    private FileUploadObserver<ResponseBody> fileUploadObserver;
    private BufferedSink bufferedSink = null;

    public UploadFileRequestBody(File file, FileUploadObserver<ResponseBody> fileUploadObserver) {
        this.mRequestBody = RequestBody.create(MediaType.parse("application/octet-stream"),file);
        this.fileUploadObserver = fileUploadObserver;
    }

    @Override
    public long contentLength() throws IOException {
        return mRequestBody.contentLength();
    }

    @Override
    public MediaType contentType() {
        return mRequestBody.contentType();
    }

    @Override
    public void writeTo(BufferedSink sink) throws IOException {
        if (bufferedSink == null){
            CountingSink countingSink = new CountingSink(sink);
            bufferedSink = Okio.buffer(countingSink);
        }
        //写入
        mRequestBody.writeTo(bufferedSink);
        //刷新
        //必须调用flush，否则最后一部分数据可能不会被写入
        bufferedSink.flush();
    }

    protected final class CountingSink extends ForwardingSink{

        //当前写入字节数
        long currentSize = 0L;
        //总字节长度，避免多次调用contentLength()方法
        long totalSize = 0L;

        public CountingSink(Sink delegate) {
            super(delegate);
        }

        @Override
        public void write(Buffer source, long byteCount) throws IOException {
            super.write(source, byteCount);
            if (totalSize == 0){
                //获得contentLength的值，后续不再调用
                totalSize = contentLength();
            }
            //增加当前写入的字节数
            currentSize += byteCount;
            //当前上传的百分比进度
            int progress = (int) (currentSize * 100 / totalSize);
            if (fileUploadObserver != null) {
                fileUploadObserver.onProgressChange(progress, contentLength());
            }
        }

    }
}
