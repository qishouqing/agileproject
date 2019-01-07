package com.qishouqing.frame.net;

import com.lzy.okgo.model.Progress;

/**
 * 文本类
 *
 * @param <T>
 */
public interface ICallback<T> {
    void onStart();

    void onSuccess(T t);

    void onFail(T t);

    void onFinish();

    void onUpload(Progress progress);

    void onDownload(Progress progress);
}
