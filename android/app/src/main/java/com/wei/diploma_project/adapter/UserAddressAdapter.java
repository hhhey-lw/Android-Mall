package com.wei.diploma_project.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.wei.diploma_project.R;
import com.wei.diploma_project.activity.UserAddressActivity;
import com.wei.diploma_project.activity.UserAddressEditActivity;
import com.wei.diploma_project.api.AddressApi;
import com.wei.diploma_project.bean.AddressBean;
import com.wei.diploma_project.util.RequestUtil;
import com.wei.diploma_project.util.Result;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/* 分类左侧分类名称 */
public class UserAddressAdapter extends RecyclerView.Adapter<UserAddressAdapter.UserAddressHolder> {
    // 回调 数据
    private UserAddressActivity parent;

    private boolean showUseBtn = false;

    /* 本地数据集 */
    private List<AddressBean> localDataSet;

    public UserAddressAdapter(UserAddressActivity parent) {
        this.parent = parent;
    }

    /* 填充Item布局 */
    @NonNull
    @Override
    public UserAddressHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_address_item, parent, false);
        return new UserAddressHolder(view);
    }
    /* Item组件绑定数据  */
    @Override
    public void onBindViewHolder(@NonNull UserAddressHolder holder, int position) {
        holder.idUserName.setText(localDataSet.get(position).getUsername());
        holder.idPhone.setText(localDataSet.get(position).getPhone());
        holder.idDefaultAddr.setChecked(localDataSet.get(position).getAddrDefualt() == 1);

        holder.idDefaultAddr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(v.getContext()).setTitle("确定更改默认地址吗？")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                RequestUtil.getRetrofit().create(AddressApi.class)
                                        .setDefaultAddr(localDataSet.get(holder.getAdapterPosition()).getUid(),
                                                localDataSet.get(holder.getAdapterPosition()).getAddrId()).enqueue(new Callback<ResponseBody>() {
                                            @Override
                                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                                                Toast.makeText(parent, "更改成功！", Toast.LENGTH_SHORT).show();
                                                parent.initInfo();
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
                                // ...
                            }
                        })
                        .show();
            }
        });

        holder.idAddress.setText(String.format("%s%s%s%s",
                localDataSet.get(position).getAddrProvince(),
                localDataSet.get(position).getAddrCity(),
                localDataSet.get(position).getAddrDistrict(),
                localDataSet.get(position).getAddrDetail()));

        holder.idDelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(parent).setTitle("确定删除吗？")
                        .setPositiveButton("删除", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                RequestUtil.getRetrofit().create(AddressApi.class).delAddress(localDataSet.get(holder.getAdapterPosition()).getAddrId()).enqueue(
                                        new Callback<ResponseBody>() {
                                            @Override
                                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                                try {
                                                    /* 拿到Result的JSON串 取出其中数据data */
                                                    String json = new String(response.body().bytes());
                                                    JSONObject jsonObject = new JSONObject(json);
                                                    System.err.println(jsonObject);
                                                    if (jsonObject.getString("code").equals("200")) {
                                                        Toast.makeText(parent, "删除成功", Toast.LENGTH_SHORT).show();
                                                        parent.initInfo();
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
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // ...
                            }
                        })
                        .show();
            }
        });

        holder.idEditBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(parent, UserAddressEditActivity.class);
                intent.putExtra("action", "update");
                intent.putExtra("addr_id", localDataSet.get(holder.getAdapterPosition()).getAddrId() + "");
                holder.itemView.getContext().startActivity(intent);
            }
        });

        holder.idUseBtn.setVisibility(showUseBtn ? View.VISIBLE : View.GONE);
        if (showUseBtn)
            holder.idUseBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.putExtra("addr_id", localDataSet.get(holder.getAdapterPosition()).getAddrId());
                    parent.setResult(Activity.RESULT_OK, intent);
                    parent.finish();
                }
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
    public static class UserAddressHolder extends RecyclerView.ViewHolder {
        /* 定义 组件 */
        private final TextView idUserName;
        private final TextView idPhone;
        private final TextView idAddress;
        private final RadioButton idDefaultAddr;
        private final TextView idDelBtn;
        private final TextView idEditBtn;
        private final TextView idUseBtn;
        /* 初始化 组件 */
        public UserAddressHolder(@NonNull View itemView) {
            super(itemView);
            idUserName = itemView.findViewById(R.id.idUserName);
            idPhone = itemView.findViewById(R.id.idPhone);
            idAddress = itemView.findViewById(R.id.idAddress);
            idDefaultAddr = itemView.findViewById(R.id.idDefaultAddr);
            idDelBtn = itemView.findViewById(R.id.idDelBtn);
            idEditBtn = itemView.findViewById(R.id.idEditBtn);
            idUseBtn = itemView.findViewById(R.id.idUseBtn);
        }
        /* 获取 组件 */
    }

    /* 数据集 */
    public void setLocalDataSet(List<AddressBean> localDataSet) {
        this.localDataSet = localDataSet;
    }

    public void setShowUseBtn(boolean showUseBtn) {
        this.showUseBtn = showUseBtn;
        notifyDataSetChanged();
    }
}
