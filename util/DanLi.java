package com.example.wangkuan.recyclershuaxinlianxiti.util;

import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * 1：左右
 * 2：名字
 * 3：日期
 */
public class DanLi {
    //私有无参构造
    private DanLi() {
    }

    //提供要单例的对象，为什么要static呢？因为静态方法不能访问非静态的成员，所以我们需要把成员变量静态，一般的成员都是私有的所以也就私有了
    private static OkHttpClient ok = new OkHttpClient();
    //调用该类方法的只有两种方法，一个是new DanLi();一个是把方法静态static,通过类名点getOk调用，因为我们已经把能new对象的构造函数私有了，外部访问不到，所以我们只能把提供外部访问的方法static静态

    public static OkHttpClient getOk() {
        return ok;
    }

    //异步工具类
    public static void getRespond(String url, Callback callback) {
        Request rq = new Request.Builder().url(url).build();
        getOk().newCall(rq).enqueue(callback);

    }
}
