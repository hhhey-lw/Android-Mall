package com.wei.diploma_project.adapter;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.wei.diploma_project.R;
import com.wei.diploma_project.activity.OrderDetailActivity;
import com.wei.diploma_project.bean.CommentBean;
import com.wei.diploma_project.bean.OrderBrief;
import com.wei.diploma_project.util.RequestUtil;

import java.text.SimpleDateFormat;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


/* 订单展示 all list => view */
public class CommentListAdapter extends RecyclerView.Adapter<CommentListAdapter.CartViewHolder> {

    private List<CommentBean> data;

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.good_comment_item, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        System.err.println(data.get(position));
        holder.idUsername.setText(data.get(position).getUsername());
        holder.idUserRating.setRating(data.get(position).getCdegree().floatValue());
        Glide.with(holder.idUserAvatar).load(RequestUtil.BaseURL + data.get(position).getAvatar())
                .into(holder.idUserAvatar);
        holder.idCommentText.setText(data.get(position).getCcontent());
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        holder.idCommentDate.setText(format.format(data.get(position).getCcreateTime()));
        holder.idLikeNumber.setText("点赞：" + data.get(position).getClike());
    }

    @Override
    public int getItemCount() {
        if (data == null)
            return 0;
        return data.size();
    }

    /* Item 通过布局绑定组件对象 */
    public static class CartViewHolder extends RecyclerView.ViewHolder {
        private final TextView idLikeNumber;
        private final TextView idCommentDate;
        private final TextView idCommentText;
        private final TextView idUsername;
        private final RatingBar idUserRating;
        private final CircleImageView idUserAvatar;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            idLikeNumber = itemView.findViewById(R.id.idLikeNumber);
            idCommentDate = itemView.findViewById(R.id.idCommentDate);
            idCommentText = itemView.findViewById(R.id.idCommentText);
            idUserRating = itemView.findViewById(R.id.idUserRating);
            idUsername = itemView.findViewById(R.id.idUsername);
            idUserAvatar = itemView.findViewById(R.id.idUserAvatar);
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setData(List<CommentBean> data) {
        this.data = data;
        notifyDataSetChanged();
    }
}
