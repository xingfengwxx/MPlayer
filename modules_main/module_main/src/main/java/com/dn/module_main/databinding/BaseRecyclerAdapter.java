package com.dn.module_main.databinding;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

public class BaseRecyclerAdapter<T> extends RecyclerView.Adapter<BaseRecyclerAdapter.ViewHolder> {

    private Context context;
    private LayoutInflater inflater;
    private List<T> dataList;
    private int varId;
    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @IdRes
    private int layoutId;

    public void setNewData(List<T> dataList) {
        this.dataList = dataList;
        notifyDataSetChanged();
    }

    public T getItem(int position) {
        if (position < dataList.size()) {
            return dataList.get(position);
        }
        return null;
    }

    public BaseRecyclerAdapter(Context context, @IdRes int layoutId, int varId) {
        this.context = context;
        this.layoutId = layoutId;
        this.varId = varId;
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewDataBinding binding = DataBindingUtil.inflate(inflater, layoutId, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(v, position);
                }
            }
        });

        Object object = dataList.get(position);
        ViewDataBinding binding = holder.getBinding();
        binding.setVariable(varId, object);
        binding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        if (dataList == null) return 0;
        return dataList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private ViewDataBinding binding;

        ViewHolder(ViewDataBinding binding) {
            super(binding.getRoot().getRootView());
            this.binding = binding;
        }

        public ViewDataBinding getBinding() {
            return binding;
        }
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

}