package com.denprog.codefestpractice2.util;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AsyncRunner {
    public static ExecutorService executorService = Executors.newSingleThreadExecutor();
    public static Handler handler = new Handler(Looper.getMainLooper());
    public static <Params, Result, Progress> void runAsync(AsyncStructure<Params, Result, Progress> asyncStructure, Params params) {
        asyncStructure.onPreExecute();
        executorService.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    Result result = asyncStructure.onAsync(params);
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            asyncStructure.onFinish(result);
                        }
                    });
                } catch (Exception e) {

                }
            }
        });
    }

    public static interface AsyncStructure<Params, Result, Progress> {
        Result onAsync(Params params);
        void onFinish(Result result);
        void onProgressUpdate(Progress progress);
        void onPreExecute();
        void onFail();
    }
}
