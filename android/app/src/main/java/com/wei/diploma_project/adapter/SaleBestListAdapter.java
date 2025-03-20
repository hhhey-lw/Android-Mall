package com.wei.diploma_project.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.wei.diploma_project.R;
import com.wei.diploma_project.activity.GoodSingleShowActivity;
import com.wei.diploma_project.bean.GoodBean;
import com.wei.diploma_project.util.RequestUtil;

import java.util.List;

/* 某大类商品 list => view */
public class SaleBestListAdapter extends RecyclerView.Adapter<SaleBestListAdapter.GoodViewHolder> {

    private List<GoodBean> localDataSet;

    /* 构造组件布局 */
    @NonNull
    @Override
    public GoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.good_salebest_item, parent, false);
        return new GoodViewHolder(view);
    }

    /* 绑定item信息 */
    @Override
    public void onBindViewHolder(@NonNull GoodViewHolder holder, int position) {
        holder.goodName.setText(localDataSet.get(position).getGname());
        holder.goodIndex.setText(String.valueOf(position + 1));
        Glide.with(holder.goodImg.getContext()).load(RequestUtil.BaseURL + localDataSet.get(position).getGimage()).into(holder.goodImg);
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), GoodSingleShowActivity.class);
            intent.putExtra("goodId", localDataSet.get(position).getGid() + "");
            v.getContext().startActivity(intent);
        });
    }

    /* item 个数 */
    @Override
    public int getItemCount() {
        if (localDataSet == null)
            return 0;
        return localDataSet.size();
    }

    /* Item 获取组件 */
    public static class GoodViewHolder extends RecyclerView.ViewHolder {
        private final TextView goodIndex;
        private final ImageView goodImg;
        private final TextView goodName;
        public GoodViewHolder(@NonNull View itemView) {
            super(itemView);
            goodImg = itemView.findViewById(R.id.goodImg);
            goodName = itemView.findViewById(R.id.goodName);
            goodIndex = itemView.findViewById(R.id.goodIndex);
        }
    }

    public void setLocalDataSet(List<GoodBean> localDataSet) {
        this.localDataSet = localDataSet;
    }
}
