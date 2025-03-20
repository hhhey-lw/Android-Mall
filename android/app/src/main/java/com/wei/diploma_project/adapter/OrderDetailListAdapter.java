package com.wei.diploma_project.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.wei.diploma_project.R;
import com.wei.diploma_project.bean.GoodBean;
import com.wei.diploma_project.bean.OrderItem;
import com.wei.diploma_project.util.RequestUtil;

import java.util.List;


/* 订单详情 中 商品列表展示 */
public class OrderDetailListAdapter extends RecyclerView.Adapter<OrderDetailListAdapter.CartViewHolder> {

    List<OrderItem> itemList;
    List<GoodBean> goodList;

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_detail_item, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        holder.idGoodName.setText(goodList.get(position).getGname());
        holder.idSingleGoodPrice.setText("￥" + itemList.get(position).getGpurchasePrice());
        holder.idPurchaseNumber.setText("x" + itemList.get(position).getGpurchaseNumber());
        Glide.with(holder.idGoodImage).load(RequestUtil.BaseURL + goodList.get(position).getGimage())
                .into(holder.idGoodImage);
    }

    @Override
    public int getItemCount() {
        if (itemList == null)
            return 0;
        else
            return itemList.size();
    }

    /* Item 通过布局绑定组件对象 */
    public static class CartViewHolder extends RecyclerView.ViewHolder {

        private ImageView idGoodImage;
        private TextView idGoodName;
        private TextView idPurchaseNumber;
        private TextView idSingleGoodPrice;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            idGoodImage = itemView.findViewById(R.id.idGoodImage);
            idGoodName = itemView.findViewById(R.id.idGoodName);
            idPurchaseNumber = itemView.findViewById(R.id.idPurchaseNumber);
            idSingleGoodPrice = itemView.findViewById(R.id.idSingleGoodPrice);
        }
    }

    public void setItemList(List<OrderItem> itemList) {
        this.itemList = itemList;
    }

    public void setGoodList(List<GoodBean> goodList) {
        this.goodList = goodList;
    }
}
