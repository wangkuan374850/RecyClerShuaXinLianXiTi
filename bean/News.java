package com.example.wangkuan.recyclershuaxinlianxiti.bean;

import java.util.ArrayList;

/**
 * autour: 王广宽
 * date: 2016/11/24 9:11
 * update: 2016/11/24
 * explain:bean  包
 */
public class News {

    public MyResult result;

    public class MyResult {
        public ArrayList<MyData> data;
    }

    public class MyData {
        public String content;
        public String updatetime;
    }
}
