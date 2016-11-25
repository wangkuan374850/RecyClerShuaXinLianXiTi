package com.example.wangkuan.recyclershuaxinlianxiti.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.wangkuan.recyclershuaxinlianxiti.R;
import com.example.wangkuan.recyclershuaxinlianxiti.bean.News;

import java.util.ArrayList;

/**
 * 1：左右
 * 2：名字
 * 3：日期
 */
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    private ArrayList<News.MyData> ls;
    private Context context;

    public MyAdapter(ArrayList<News.MyData> ls, Context context) {
        this.ls = ls;
        this.context = context;
    }

    //定义接口
    public interface OnItemClickLitener {
        void onItemClick(View view, int position);

        void onItemLongClick(View view, int position);
    }

    //提供外面引用的变量
    private OnItemClickLitener mOnItemClickLitener;

    //提供外面引用的方法
    public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener) {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(View.inflate(context, R.layout.item, null));
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        holder.tv1.setText(ls.get(position).content);
        holder.tv2.setText(ls.get(position).updatetime);

        // 如果设置了回调，则设置点击事件
        if (mOnItemClickLitener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = holder.getOldPosition();
                    ;
                    mOnItemClickLitener.onItemClick(holder.itemView, pos);
                }
            });

            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int pos = holder.getOldPosition();
                    ;
                    mOnItemClickLitener.onItemLongClick(holder.itemView, pos);
                    return false;
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return ls.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv1;
        TextView tv2;

        public MyViewHolder(View itemView) {
            super(itemView);
            tv1 = (TextView) itemView.findViewById(R.id.b1);
            tv2 = (TextView) itemView.findViewById(R.id.b2);
        }
    }

}
