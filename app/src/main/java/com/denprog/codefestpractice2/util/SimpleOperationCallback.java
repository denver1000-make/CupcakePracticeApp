package com.denprog.codefestpractice2.util;

public interface SimpleOperationCallback<T> {
    void onLoading();
    void onFinished(T data);
    void onError(String message);
}
