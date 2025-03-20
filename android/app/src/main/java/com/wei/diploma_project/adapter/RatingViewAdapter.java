package com.wei.diploma_project.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.wei.diploma_project.R;
import com.wei.diploma_project.activity.GoodRatingActivity;
import com.wei.diploma_project.api.CommentApi;
import com.wei.diploma_project.bean.CommentBean;
import com.wei.diploma_project.bean.GoodBean;
import com.wei.diploma_project.bean.OrderItem;
import com.wei.diploma_project.util.RequestUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/* 订单展示 all list => view */
public class RatingViewAdapter extends RecyclerView.Adapter<RatingViewAdapter.CartViewHolder> {

    private int oid;
    private int uid;
    private List<OrderItem> itemList;
    private List<GoodBean> goodList;
    private Map<Integer, Integer> ratingMap = new HashMap<>(); // position => rating

    private GoodRatingActivity goodRatingActivity;

    public RatingViewAdapter() {
    }

    public RatingViewAdapter(GoodRatingActivity goodRatingActivity) {
        this.goodRatingActivity = goodRatingActivity;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.good_rating_item, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        holder.idGoodName.setText(goodList.get(position).getGname());
        Glide.with(holder.itemView).load(RequestUtil.BaseURL + goodList.get(position).getGimage())
                .into(holder.idGoodImage);
        if (itemList.get(position).getOratingStatus() != null &&itemList.get(position).getOratingStatus() == 1) {
            holder.idCommentText.setText("您已评价过该商品了！");
            holder.idCommentText.setEnabled(false);
            holder.idRatingBtn.setRating(Float.valueOf("5.0"));
            holder.idRatingBtn.setEnabled(false);
            holder.idSubmitBtn.setEnabled(false);
        }
        // 星星数
        holder.idRatingBtn.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                if (ratingMap.containsKey(holder.getAdapterPosition())) {
                    ratingMap.replace(holder.getAdapterPosition(), (int)rating);
                }else {
                    ratingMap.put(holder.getAdapterPosition(), (int)rating);
                }
                System.err.println(ratingMap.get(holder.getAdapterPosition()));
            }
        });

        // 提交
        holder.idSubmitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "Rating : " + ratingMap.get(holder.getAdapterPosition())
                        + ", Text : " + holder.idCommentText.getText().toString().trim(), Toast.LENGTH_SHORT).show();

                CommentBean commentBean = new CommentBean();

                commentBean.setCcontent(holder.idCommentText.getText().toString().trim());
                commentBean.setClike(0);
                commentBean.setCdegree(ratingMap.get(holder.getAdapterPosition()));
                commentBean.setGid(goodList.get(holder.getAdapterPosition()).getGid());
                commentBean.setUid(uid);

                RequestUtil.getRetrofit().create(CommentApi.class).addComment(commentBean, oid, itemList.get(holder.getAdapterPosition()).getOitemId()).enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        /* 转json */
                        try {
                            byte[] bytes = response.body().bytes();
                            String json = new String(bytes);
                            JSONObject jsonObject = new JSONObject(json);

                            if (jsonObject.getString("code").equals("200")) {
                                Toast.makeText(v.getContext(), "评论成功！", Toast.LENGTH_SHORT).show();
                                goodRatingActivity.initInfo();
                            }
                        } catch (JSONException | IOException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        t.printStackTrace();
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        if (itemList == null)
            return 0;
        return itemList.size();
    }

    /* Item 通过布局绑定组件对象 */
    public static class CartViewHolder extends RecyclerView.ViewHolder {
        private final RatingBar idRatingBtn;
        private final EditText idCommentText;
        private final Button idSubmitBtn;
        private final CircleImageView idGoodImage;
        private final TextView idGoodName;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            idRatingBtn = itemView.findViewById(R.id.idRatingBtn);
            idCommentText = itemView.findViewById(R.id.idCommentText);
            idSubmitBtn = itemView.findViewById(R.id.idSubmitBtn);
            idGoodImage = itemView.findViewById(R.id.idGoodImage);
            idGoodName = itemView.findViewById(R.id.idGoodName);
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setData(int oid, int uid, List<OrderItem> itemList, List<GoodBean> goodList) {
        this.oid = oid;
        this.uid = uid;
        this.itemList = itemList;
        this.goodList = goodList;
        notifyDataSetChanged();
    }
}
