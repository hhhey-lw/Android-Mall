package com.wei.diploma_project.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.wei.diploma_project.R;
import com.wei.diploma_project.bean.GoodCategoryBeanExtend;
import com.wei.diploma_project.fragment.tabBar.ClassFragment;

import java.util.List;

/* 分类左侧分类名称 */
public class ClassMenuListAdapter extends RecyclerView.Adapter<ClassMenuListAdapter.LeftClassHolder> {
    // 回调 数据
    private ClassFragment parent;

    /* 本地数据集 */
    private List<GoodCategoryBeanExtend> localDataSet;
    private Integer currentPosition;

    public ClassMenuListAdapter(ClassFragment parent) {
        this.parent = parent;
    }

    /* 填充Item布局 */
    @NonNull
    @Override
    public LeftClassHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.class_menu_item, parent, false);
        return new LeftClassHolder(view);
    }
    /* Item组件绑定数据  */
    @Override
    public void onBindViewHolder(@NonNull LeftClassHolder holder, int position) {
        holder.getTitleView().setText(localDataSet.get(position).getGcategory_name());
        setSelected(holder, position);
    }

    /* 设置选中 */
    @SuppressLint("NotifyDataSetChanged")
    private void setSelected(LeftClassHolder holder, int position) {
        if (position == currentPosition)
            holder.getTitleView().setSelected(true);
        else
            holder.getTitleView().setSelected(false);
        holder.getTitleView().setOnClickListener(v -> {
            /* 先改本地 再回传点击位置 */
            currentPosition = position;
            notifyDataSetChanged();
            // 回调 更新右侧商品信息
            parent.setPositionToUpdateView(position);
        });
    }

    /* 提供渲染项数 */
    @Override
    public int getItemCount() {
        if (localDataSet == null)
            return 0;
        return localDataSet.size();
    }

    /* Item 模板 */
    public static class LeftClassHolder extends RecyclerView.ViewHolder {
        /* 定义 组件 */
        private final TextView titleView;
        /* 初始化 组件 */
        public LeftClassHolder(@NonNull View itemView) {
            super(itemView);
            titleView = itemView.findViewById(R.id.leftClassItem);
        }
        /* 获取 组件 */
        public TextView getTitleView() {
            return titleView;
        }
    }

    /* 数据集 */
    public void setLocalDataSet(List<GoodCategoryBeanExtend> localDataSet) {
        if (currentPosition == null)
            currentPosition = 0;
        this.localDataSet = localDataSet;
    }
}
