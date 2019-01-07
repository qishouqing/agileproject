package com.qishouqing.frame.net;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.Callback;
import com.lzy.okgo.callback.FileCallback;
import com.lzy.okgo.model.Progress;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.PostRequest;
import com.lzy.okgo.request.base.Request;
import com.qishouqing.frame.base.Config;

import java.io.File;
import java.util.List;

public class NetUtils<T> {


    public void post(String url, List<String> keys, List<String> values, final ICallback callback) {
        final PostRequest<T> request = OkGo.<T>post(Config.URL + url).tag(this);
        if (values != null) {
            for (int i = 0; i < keys.size() - 1; i++) {
                request.params(keys.get(i), values.get(i));
            }
        }
        request.execute(new Callback<T>() {
            @Override
            public void onStart(Request<T, ? extends Request> request) {
                callback.onStart();
            }

            @Override
            public void onSuccess(Response<T> response) {
                callback.onSuccess(response);
            }

            @Override
            public void onCacheSuccess(Response<T> response) {

            }

            @Override
            public void onError(Response<T> response) {
                callback.onFail(response);
            }

            @Override
            public void onFinish() {
                callback.onFinish();
            }

            @Override
            public void uploadProgress(Progress progress) {
                callback.onUpload(progress);
            }

            @Override
            public void downloadProgress(Progress progress) {
                callback.onDownload(progress);
            }

            @Override
            public T convertResponse(okhttp3.Response response) throws Throwable {
                // 如果请求成功，回调onSuccess()，该字段为convertResponse()解析数据后返回的数据。如果发生异常，回调onError()，该字段值为null
                return null;
            }
        });
    }

    /**
     * 文件上传
     * @param url
     * @param keys
     * @param values
     * @param callback
     */
    public void dealFile(String url, List<String> keys, List<File> values, final IFileCallback callback) {
        final PostRequest<File> request = OkGo.<File>post(Config.URL + url).tag(this);
        if (values != null) {
            for (int i = 0; i < keys.size() - 1; i++) {
                request.params(keys.get(i), values.get(i));
            }
        }
        request.execute(new FileCallback() {
            @Override
            public void onStart(Request<File, ? extends Request> request) {
                super.onStart(request);
                callback.onStart();
            }

            @Override
            public void onSuccess(Response<File> response) {
                callback.onSuccess(response);
            }

            @Override
            public void uploadProgress(Progress progress) {
                super.uploadProgress(progress);
                callback.onUpload(progress);
            }

            @Override
            public void onError(Response<File> response) {
                super.onError(response);
                callback.onFail(response);
            }

            @Override
            public void onFinish() {
                super.onFinish();
                callback.onFinish();
            }
        });
    }
}
