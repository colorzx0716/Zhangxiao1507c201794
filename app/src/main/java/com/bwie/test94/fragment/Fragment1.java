package com.bwie.test94.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bwie.test94.R;
import com.bwie.test94.adapter.MyListAdapter;
import com.bwie.test94.bean.Bean;
import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.List;

import view.xlistview.XListView;

/**
 * 主页面，展示请求下来的数据
 * Created by 张肖肖 on 2017/9/4.
 */

public class Fragment1 extends Fragment implements XListView.IXListViewListener {
    private View mRootView;
    private XListView xlv;
    private MyListAdapter adapter;

    //请求数据的接口get请求
    private String url = "http://v.juhe.cn/toutiao/index?type=&key=22a108244dbb8d1f49967cd74a0c144d";
    private List<Bean.ResultBean.DataBean> data;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mRootView = View.inflate(getActivity(), R.layout.fragment1,null);
        xlv = mRootView.findViewById(R.id.xlv);
        xlv.setPullLoadEnable(true);
        xlv.setXListViewListener(this);

        x.view().inject(getActivity());
        RequestParams params = new RequestParams(url);
        params.addQueryStringParameter("username","abc");
        params.addQueryStringParameter("password","123");
        x.http().get(params, new Callback.CommonCallback<String>() {
            //请求成功
            @Override
            public void onSuccess(String result) {
                System.out.println("result = " + result);
                Gson gson = new Gson();
                Bean bean = gson.fromJson(result, Bean.class);
                Bean.ResultBean result1 = bean.getResult();
                data = result1.getData();

                //适配器
                adapter = new MyListAdapter(getActivity(),data);
                xlv.setAdapter(adapter);

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
        return mRootView;
    }

    @Override
    public void onRefresh() {
        data.addAll(data);
        adapter.notifyDataSetChanged();
        xlv.stopLoadMore();
        xlv.stopRefresh();
    }

    @Override
    public void onLoadMore() {
        data.addAll(data);
        adapter.notifyDataSetChanged();
        xlv.stopLoadMore();
        xlv.stopRefresh();
    }
}
