package com.wei.diploma_project.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.wei.diploma_project.R;
import com.wei.diploma_project.bean.GoodBean;
import com.wei.diploma_project.util.RequestUtil;

import java.util.List;


/* 首页热销商品 list => view */
public class HomeGoodListAdapter extends BaseAdapter {

    private List<GoodBean> dataSet;
    private Context context;

    public HomeGoodListAdapter() {
    }

    public void setDataSet(List<GoodBean> dataSet) {
        this.dataSet = dataSet;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        if (dataSet != null)
            return dataSet.size();
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return dataSet.get(position);
    }

    @Override
    public long getItemId(int position) {
        return dataSet.get(position).getGid();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
//        System.err.println("****************** " + position);
        View listitemView = convertView;
        if (listitemView == null) {
            // Layout Inflater inflates each item to be displayed in GridView.
            listitemView = LayoutInflater.from(context).inflate(R.layout.home_good_item, parent, false);
        }

        GoodBean goodBean = ((GoodBean) getItem(position));
        if (goodBean != null) {
            TextView goodName = listitemView.findViewById(R.id.idGoodName);
            TextView goodPrice = listitemView.findViewById(R.id.idGoodPrice);
            ImageView goodImage = listitemView.findViewById(R.id.idGoodImage);

            goodName.setText(goodBean.getGname());
            goodPrice.setText("￥"+goodBean.getGprimalPrice());
//            goodImage.setImageResource(R.drawable.goodshow3);
            Glide.with(context).load(RequestUtil.BaseURL+goodBean.getGimage()).into(goodImage);
        }
        return listitemView;
    }
}
