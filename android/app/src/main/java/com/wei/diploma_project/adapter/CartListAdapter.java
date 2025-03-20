package com.wei.diploma_project.adapter;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.wei.diploma_project.R;
import com.wei.diploma_project.api.CartApi;
import com.wei.diploma_project.bean.CartBean;
import com.wei.diploma_project.fragment.tabBar.CartFragment;
import com.wei.diploma_project.util.RequestUtil;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/* 购物车卡片 List => view */
public class CartListAdapter extends RecyclerView.Adapter<CartListAdapter.CartViewHolder> {
    private CartFragment c;

    /* 存选中的 购物车cart id */
    private Set<Integer> selectedCart = new HashSet<>();

    private List<CartBean> localDataSet;

    /* 加载视图布局 */
    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_good_item, parent, false);
        return new CartViewHolder(view);
    }

    /* 赋值与绑定事件 */
    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        holder.goodName.setText(localDataSet.get(position).getGood().getGname());
        holder.goodPrice.setText("￥" + localDataSet.get(position).getGood().getGprimalPrice());
        holder.goodNumber.setText("x" + localDataSet.get(position).getGpurchaseNumber());
        Glide.with(holder.goodImg).load(RequestUtil.BaseURL + localDataSet.get(position).getGood().getGimage()).into(holder.goodImg);
        // 存储选中的 cart id
        holder.selectBtn.setOnClickListener(v -> {
            if (holder.selectBtn.isSelected()) {
                selectedCart.remove(localDataSet.get(position).getCid());
                holder.selectBtn.setSelected(false);
            } else {
                holder.selectBtn.setSelected(true);
                selectedCart.add(localDataSet.get(position).getCid());
            }
            c.updateCartTotal();
        });
        /* 修改 */
        holder.idAddCartItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.err.println("click add : " + localDataSet.get(holder.getAdapterPosition()).getCid());
                RequestUtil.getRetrofit().create(CartApi.class)
                        .updateGoodNumber(localDataSet.get(holder.getAdapterPosition()).getCid(), localDataSet.get(holder.getAdapterPosition()).getGpurchaseNumber() + 1)
                        .enqueue(new Callback<ResponseBody>() {
                            @Override
                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                c.getCartInfo();
                            }

                            @Override
                            public void onFailure(Call<ResponseBody> call, Throwable t) {
                                t.printStackTrace();
                            }
                        });
            }
        });
        holder.idSubCartItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.err.println("click sub : " + localDataSet.get(holder.getAdapterPosition()).getCid());
                if (localDataSet.get(holder.getAdapterPosition()).getGpurchaseNumber() == 1)
                    return;
                RequestUtil.getRetrofit().create(CartApi.class)
                        .updateGoodNumber(localDataSet.get(holder.getAdapterPosition()).getCid(), localDataSet.get(holder.getAdapterPosition()).getGpurchaseNumber() - 1)
                        .enqueue(new Callback<ResponseBody>() {
                            @Override
                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                c.getCartInfo();
                            }

                            @Override
                            public void onFailure(Call<ResponseBody> call, Throwable t) {
                                t.printStackTrace();
                            }
                        });
            }
        });
        // Delete
        holder.idDelCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(v.getContext()).setTitle("确定删除吗？")
                        .setPositiveButton("删除", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                RequestUtil.getRetrofit().create(CartApi.class).deleteCart(localDataSet.get(holder.getAdapterPosition()).getCid())
                                        .enqueue(new Callback<ResponseBody>() {
                                            @Override
                                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                                // ...
                                                c.onStart();
                                            }

                                            @Override
                                            public void onFailure(Call<ResponseBody> call, Throwable t) {
                                                t.printStackTrace();
                                            }
                                        });
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .show();

            }
        });
    }

    @Override
    public int getItemCount() {
        if (localDataSet != null)
            return localDataSet.size();
        return 0;
    }

    /* Item 通过布局绑定组件对象 */
    public static class CartViewHolder extends RecyclerView.ViewHolder {
        private final CheckBox selectBtn;
        private final ImageView goodImg;
        private final TextView goodName;
        private final TextView goodPrice;
        private final TextView goodNumber;
        private final TextView idDelCart;
        private final Button idAddCartItem;
        private final Button idSubCartItem;
        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            selectBtn = itemView.findViewById(R.id.selectBtn);
            goodImg = itemView.findViewById(R.id.goodImg);
            goodName = itemView.findViewById(R.id.goodName);
            goodPrice = itemView.findViewById(R.id.goodPrice);
            goodNumber = itemView.findViewById(R.id.goodNumber);
            idAddCartItem = itemView.findViewById(R.id.idAddCartItem);
            idSubCartItem = itemView.findViewById(R.id.idSubCartItem);
            idDelCart = itemView.findViewById(R.id.idDelCart);
        }
    }

    public void setLocalDataSet(List<CartBean> localDataSet) {
        this.localDataSet = localDataSet;
    }

    public Set<Integer> getSelectedCart() {
        return selectedCart;
    }

    public void clearSelectedCart() {
        if (selectedCart != null)
            selectedCart.clear();
    }


    public void setParent(CartFragment c) {
        this.c = c;
    }
}
