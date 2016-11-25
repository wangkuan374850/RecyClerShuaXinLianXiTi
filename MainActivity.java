package com.example.wangkuan.recyclershuaxinlianxiti;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.Toast;

import com.example.wangkuan.recyclershuaxinlianxiti.adapter.MyAdapter;
import com.example.wangkuan.recyclershuaxinlianxiti.bean.News;
import com.example.wangkuan.recyclershuaxinlianxiti.util.DanLi;
import com.example.wangkuan.recyclershuaxinlianxiti.view.PullBaseView;
import com.example.wangkuan.recyclershuaxinlianxiti.view.PullRecyclerView;
import com.solidfire.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity implements PullBaseView.OnHeaderRefreshListener,
        PullBaseView.OnFooterRefreshListener {

    private PullRecyclerView rv;
    private ArrayList<News.MyData> ls = new ArrayList<>();
    private MyAdapter my;
    private int ye = 3;
    Handler hd = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {
                News n = (News) msg.obj;
                ls.addAll(n.result.data);
                if (my == null) {
                    my = new MyAdapter(ls, MainActivity.this);
                    rv.setAdapter(my);
                } else {
                    my.notifyDataSetChanged();
                }
                //点击事件
                my.setOnItemClickLitener(new MyAdapter.OnItemClickLitener() {
                    @Override
                    public void onItemClick(View view, int position) {

                    }

                    @Override
                    public void onItemLongClick(View view, final int position) {
                        Toast.makeText(MainActivity.this, "我被长按了哦！！！", Toast.LENGTH_SHORT).show();

//弹框
                        AlertDialog.Builder bd = new AlertDialog.Builder(MainActivity.this);
                        bd.setTitle("删除数据");
                        bd.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Toast.makeText(MainActivity.this, "确定啦！！", Toast.LENGTH_SHORT).show();
                                my.notifyItemRemoved(position);
                            }
                        });
                        bd.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Toast.makeText(MainActivity.this, "取消喽！！", Toast.LENGTH_SHORT).show();

                            }
                        });
                        bd.show();

                    }
                });

            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rv = (PullRecyclerView) findViewById(R.id.a1);
        rv.setOnHeaderRefreshListener(this);//设置下拉监听
        rv.setOnFooterRefreshListener(this);//设置上拉监听
        rv.setLayoutManager(new LinearLayoutManager(this));
        //请求数据
        qingQiu();

    }

    private void qingQiu() {
        DanLi.getRespond("http://japi.juhe.cn/joke/content/list.from?key=%20874ed931559ba07aade103eee279bb37%20&page=" + ye + "&pagesize=10&sort=asc&time=1418745237", new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String string = response.body().string();
                Gson gs = new Gson();
                News news = gs.fromJson(string, News.class);
                Message m = new Message();
                m.obj = news;
                m.what = 1;
                hd.sendMessage(m);
            }
        });
    }

    @Override
    public void onFooterRefresh(PullBaseView view) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // mDatas.add("TEXT更多");
                ye++;
                ls.clear();
                qingQiu();
                my.notifyDataSetChanged();
                rv.onFooterRefreshComplete();
            }
        }, 2000);
    }

    @Override
    public void onHeaderRefresh(PullBaseView view) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //  mDatas.add(0, "TEXT新增");
                ye = 2;
                qingQiu();
                my.notifyDataSetChanged();
                rv.onHeaderRefreshComplete();
            }
        }, 3000);
    }
}
