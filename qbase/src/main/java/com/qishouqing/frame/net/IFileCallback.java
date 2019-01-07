package com.qishouqing.frame.net;

import com.lzy.okgo.model.Progress;
import com.lzy.okgo.model.Response;

import java.io.File;

/**
 * 文件类
 */
public interface IFileCallback {
    void onStart();

    void onSuccess(Response<File> response);

    void onFail(Response<File> response);

    void onUpload(Progress progress);

    void onFinish();
}
