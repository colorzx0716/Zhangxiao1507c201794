package com.bwie.test94.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.bwie.test94.R;
import com.bwie.test94.bean.Catogray;

import java.util.List;

/**
 * Created by 张肖肖 on 2017/9/5.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder>{
    private final Context context;
    private final List<Catogray> list;
    private OnItemClickListener onItemClickListener;

    public MyAdapter(Context context, List<Catogray> list) {
        this.context = context;
        this.list = list;
    }

    /**
     * 创建ViewHolder，和view绑定，类似于listview中的settag的作用
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //初始化条目view
        View view = LayoutInflater.from(context).inflate(R.layout.catogry_item,null);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickListener.onItemClickListener((Integer) view.getTag(), view);
            }
        });
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyAdapter.MyViewHolder holder, int position) {
        holder.nametv.setText(list.get(position).name);
        holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView nametv;
        private CheckBox checkBox;

        public MyViewHolder(View itemView) {
            super(itemView);
            nametv = itemView.findViewById(R.id.name);
            checkBox = itemView.findViewById(R.id.checkbox);
        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClickListener(int pos, View view);
    }
}
