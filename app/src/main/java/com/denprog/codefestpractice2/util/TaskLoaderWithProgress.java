package com.denprog.codefestpractice2.util;

public interface TaskLoaderWithProgress<T> extends SimpleOperationCallback<T> {
    public void onProgressUpdate(int progress);
    public void onProgressMessage(String message);
}
