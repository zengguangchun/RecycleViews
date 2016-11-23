package com.bawei.test.recycleviews;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;

import com.bawei.test.adapter.HomeAdapter;
import com.bawei.test.bean.DataBean;
import com.bawei.test.okhttputils.OkHttp;
import com.bawei.test.view.PullBaseView;
import com.bawei.test.view.PullRecyclerView;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Request;

public class MainActivity extends AppCompatActivity implements PullBaseView.OnHeaderRefreshListener,
        PullBaseView.OnFooterRefreshListener {

    private String url = "http://m.yunifang.com/yunifang/mobile/goods/getall?random=39986&encode=2092d7eb33e8ea0a7a2113f2d9886c90&category_id=17";
    private ArrayList<DataBean.Data> mDatas;
    private PullRecyclerView mRecyclerView;
    HomeAdapter mAdapter;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            ArrayList<DataBean.Data> mD = (ArrayList<DataBean.Data>) msg.obj;
            //设置设配器
            mAdapter = new HomeAdapter(mD, MainActivity.this);
            mRecyclerView.setAdapter(mAdapter);
            mAdapter.notifyDataSetChanged();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        //获得子控件
        mRecyclerView = (PullRecyclerView) findViewById(R.id.recycler);

        OkHttp.getAsync(url, new OkHttp.DataCallBack() {
            @Override
            public void requestFailure(Request request, IOException e) {
                System.out.print("数据加载失败‘’‘’‘’‘’‘’‘’‘’‘’‘’‘’‘’‘’‘’‘’‘");
            }

            @Override
            public void requestSuccess(String result) throws Exception {
                String json = result;
                Gson gson = new Gson();
                DataBean db = gson.fromJson(json, DataBean.class);
                ArrayList<DataBean.Data> mDatas = db.data;
                Message message = new Message();
                message.obj = mDatas;
                handler.sendMessage(message);
//                //设置设配器
//                mAdapter = new HomeAdapter(mDatas, MainActivity.this);
//                mRecyclerView.setAdapter(mAdapter);
            }
        });

        //设置布局管理
        //listview展示
        LinearLayoutManager mgr = new LinearLayoutManager(this);
        mgr.setOrientation(LinearLayoutManager.VERTICAL);//方向（纵、横）
        //mRecyclerView.setLayoutManager(mgr);

        //GridView展示
        GridLayoutManager mGrid = new GridLayoutManager(this, 3);//
        mRecyclerView.setLayoutManager(mGrid);

        //StaggeredGridLayoutManager展示

        // mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));


//        //设置动画
//        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setOnHeaderRefreshListener(this);//设置下拉监听
        mRecyclerView.setOnFooterRefreshListener(this);//设置上拉监听
    }


    @Override
    public void onFooterRefresh(PullBaseView view) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // mDatas.add("TEXT更多");
                mAdapter.notifyDataSetChanged();
                mRecyclerView.onFooterRefreshComplete();
            }
        }, 2000);
    }

    @Override
    public void onHeaderRefresh(PullBaseView view) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //  mDatas.add(0, "TEXT新增");
                mAdapter.notifyDataSetChanged();
                mRecyclerView.onHeaderRefreshComplete();
            }
        }, 3000);
    }
}
