package com.bawei.test.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bawei.test.bean.DataBean;
import com.bawei.test.recycleviews.R;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

/**
 * Created by asus on 2016/11/10.
 */
public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.MyViewHolder> {
    private ArrayList<DataBean.Data> mDatas;
    private Context context;

    public HomeAdapter(ArrayList<DataBean.Data> mDatas, Context context) {
        this.mDatas = mDatas;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//
        View v = View.inflate(context, R.layout.recy_item, null);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.tv.setText(mDatas.get(position).efficacy);
        ImageLoader.getInstance().displayImage(mDatas.get(position).goods_img,
                holder.iv);
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tv;
        ImageView iv;

        public MyViewHolder(View view) {
            super(view);
            tv = (TextView) view.findViewById(R.id.tv_recy);
            iv = (ImageView) view.findViewById(R.id.iv_recy);
        }
    }
}
