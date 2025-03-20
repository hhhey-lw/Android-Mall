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

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

/* 分类右侧商品列表 list => view */
public class ClassGoodListAdapter extends RecyclerView.Adapter<ClassGoodListAdapter.GoodViewHolder> implements View.OnClickListener {
//    private Context context;
    private List<GoodBean> localDataSet;

    /* 构造组件布局 */
    @NonNull
    @Override
    public GoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.class_good_item, parent, false);
        //  Item 点击事件
        view.setOnClickListener(this);
        return new GoodViewHolder(view);
    }

    /* 绑定item信息 */
    @Override
    public void onBindViewHolder(@NonNull GoodViewHolder holder, int position) {
        try {
            URI uri = new URI(RequestUtil.BaseURL + localDataSet.get(position).getGimage());
            /* 不需要传context进来 */
            Glide.with(holder.itemView).load(uri.toString()).into(holder.goodImg);
        } catch (URISyntaxException e) {

        }
        holder.idGoodIdHide.setText(localDataSet.get(position).getGid() + "");
        holder.goodName.setText(localDataSet.get(position).getGname());
        holder.goodPrice.setText(String.valueOf(localDataSet.get(position).getGprimalPrice()));
    }

    /* item 个数 */
    @Override
    public int getItemCount() {
        if (localDataSet == null)
            return 0;
        return localDataSet.size();
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(v.getContext(), GoodSingleShowActivity.class);
        intent.putExtra("goodId", ((TextView) v.findViewById(R.id.idGoodIdHide)).getText());
        v.getContext().startActivity(intent);
    }

    /* Item 获取布局组件对象 */
    public static class GoodViewHolder extends RecyclerView.ViewHolder {
        private final ImageView goodImg;
        private final TextView goodName;
        private final TextView goodPrice;
        // 藏Id
        private final TextView idGoodIdHide;
        public GoodViewHolder(@NonNull View itemView) {
            super(itemView);
            goodImg = itemView.findViewById(R.id.goodImg);
            goodName = itemView.findViewById(R.id.goodName);
            goodPrice = itemView.findViewById(R.id.goodPrice);
            idGoodIdHide = itemView.findViewById(R.id.idGoodIdHide);
        }
    }

    public void setLocalDataSet(List<GoodBean> localDataSet) {
        this.localDataSet = localDataSet;
    }

}
