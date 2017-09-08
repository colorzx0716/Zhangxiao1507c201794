package com.bwie.test94.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bwie.test94.R;
import com.bwie.test94.bean.Bean;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * Created by 张肖肖 on 2017/9/4.
 */

public class MyListAdapter extends BaseAdapter {
    private final Context context;
    private final List<Bean.ResultBean.DataBean> data;

    public MyListAdapter(Context context, List<Bean.ResultBean.DataBean> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int i) {
        return data.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;

        if(view == null){
            viewHolder = new ViewHolder();
            view = View.inflate(context, R.layout.xlv_item,null);
           viewHolder.title = view.findViewById(R.id.xlv_title);
            viewHolder.author = view.findViewById(R.id.xlv_author);
            viewHolder.xlv_img = view.findViewById(R.id.xlv_img);
            view.setTag(viewHolder);

        }else{
            viewHolder = (ViewHolder) view.getTag();
        }

        viewHolder.title.setText(data.get(i).getTitle());
        viewHolder.author.setText(data.get(i).getCategory());
        ImageLoader.getInstance().displayImage(data.get(i).getThumbnail_pic_s(),viewHolder.xlv_img);

        return view;
    }

    private class ViewHolder {
        public ImageView xlv_img;
        public TextView title;
        public TextView author;
    }
}
