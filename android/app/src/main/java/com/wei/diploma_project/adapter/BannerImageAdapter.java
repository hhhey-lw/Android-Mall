package com.wei.diploma_project.adapter;

import android.annotation.SuppressLint;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.wei.diploma_project.bean.BannerBean;
import com.wei.diploma_project.util.RequestUtil;
import com.youth.banner.adapter.BannerAdapter;

import java.util.List;

/**
 * Banner轮播图，图片
 */
public class BannerImageAdapter extends BannerAdapter<BannerBean, BannerImageAdapter.ImageHolder> {

    public BannerImageAdapter() {
        super(null);
    }

    public BannerImageAdapter(List<BannerBean> mDatas) {
        //设置数据，也可以调用banner提供的方法,或者自己在adapter中实现
        super(mDatas);
    }

    //更新数据
    @SuppressLint("NotifyDataSetChanged")
    public void updateData(List<BannerBean> data) {
        //这里的代码自己发挥，比如如下的写法等等
        mDatas.clear();
        mDatas.addAll(data);
        notifyDataSetChanged();
    }


    //创建ViewHolder，可以用viewType这个字段来区分不同的ViewHolder
    @Override
    public ImageHolder onCreateHolder(ViewGroup parent, int viewType) {
        ImageView imageView = new ImageView(parent.getContext());
        //注意，必须设置为match_parent，这个是viewpager2强制要求的
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        imageView.setLayoutParams(params);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        return new ImageHolder(imageView);
    }

    @Override
    public void onBindView(ImageHolder holder, BannerBean data, int position, int size) {
        Glide.with(holder.imageView).load(RequestUtil.BaseURL + data.getbImageurl()).into(holder.imageView);
    }

    /* 布局组件赋予成员对象 */
    static class ImageHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;

        public ImageHolder(@NonNull View view) {
            super(view);
            this.imageView = (ImageView) view;
        }
    }

}
