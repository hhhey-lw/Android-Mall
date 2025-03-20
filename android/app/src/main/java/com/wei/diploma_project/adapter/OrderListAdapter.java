package com.wei.diploma_project.adapter;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.wei.diploma_project.R;
import com.wei.diploma_project.activity.OrderDetailActivity;
import com.wei.diploma_project.bean.OrderBrief;

import java.util.List;


/* 订单展示 all list => view */
public class OrderListAdapter extends RecyclerView.Adapter<OrderListAdapter.CartViewHolder> {

    private List<OrderBrief> data;
    private String[] statusName = {"", "待付款", "待发货", "待收货", "待评价", "已完成"};

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_cart, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        holder.idOrderIndex.setText(data.get(position).getOrderIndex());
        holder.idOrderStatus.setText(statusName[data.get(position).getOstatus()]);
//        if (data.get(position).getOstatus() == 5)
//            holder.idOrderStatus.setTextColor(holder.itemView.getResources().getColor(R.color.orange));
        holder.idOrderTotal.setText(String.valueOf(data.get(position).getTotal()));
        /* 适配器 */
        holder.idOrderDetailItemView.setLayoutManager(new LinearLayoutManager(holder.itemView.getContext()));
        OrderDetailListAdapter adapter = new OrderDetailListAdapter();
        adapter.setItemList(data.get(position).getItem());
        adapter.setGoodList(data.get(position).getGood());
        holder.idOrderDetailItemView.setAdapter(adapter);
        /* 内层占位视图 触发不了！ */
        holder.itemView.setOnClickListener(v -> {
            System.err.println("跳转 ！！！");
            Intent intent = new Intent(v.getContext(), OrderDetailActivity.class);
            intent.putExtra("oid", String.valueOf(data.get(position).getOid()));
            v.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        if (data == null)
            return 0;
        return data.size();
    }

    /* Item 通过布局绑定组件对象 */
    public static class CartViewHolder extends RecyclerView.ViewHolder {
        private TextView idOrderIndex;
        private TextView idOrderStatus;
        private TextView idOrderTotal;
        private RecyclerView idOrderDetailItemView;
        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            idOrderIndex = itemView.findViewById(R.id.idOrderIndex);
            idOrderStatus = itemView.findViewById(R.id.idOrderStatus);
            idOrderTotal = itemView.findViewById(R.id.idOrderTotal);
            idOrderDetailItemView = itemView.findViewById(R.id.idOrderDetailItemView);
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setData(List<OrderBrief> data) {
        this.data = data;
        notifyDataSetChanged();
    }
}
