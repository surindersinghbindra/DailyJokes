package com.regrex.dailyJokes.binding;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.regrex.dailyJokes.model.JokeCategory;

import java.util.List;

/**
 * Created by surinder on 13-Mar-17.
 */

public class RecyclerViewBinding<T> extends RecyclerView.Adapter<RecyclerViewBinding.MyViewHolder> {

    private List<T> list;
    private int viewVariable, layoutId;
    private OnClick onClick;


    public RecyclerViewBinding(List<T> list, int viewVariable, int layoutId, OnClick onClick) {
        this.list = list;
        this.viewVariable = viewVariable;
        this.layoutId = layoutId;
        this.onClick = onClick;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewDataBinding viewDataBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), layoutId, parent, false);
        return new MyViewHolder(viewDataBinding);
    }

    @Override
    public void onBindViewHolder(RecyclerViewBinding.MyViewHolder holder, int position) {
        ViewDataBinding viewDataBinding = holder.getViewDataBinding();
        viewDataBinding.setVariable(viewVariable, list.get(position));
        viewDataBinding.executePendingBindings();
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private final ViewDataBinding binding;

        private MyViewHolder(ViewDataBinding binding) {
            super(binding.getRoot());
            binding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    T item = list.get(getAdapterPosition());

                    onClick.myOnClick(((JokeCategory)item).getId());

                }
            });
            this.binding = binding;
        }

        public ViewDataBinding getViewDataBinding() {
            return binding;
        }
    }

    public interface OnClick {
        void myOnClick(int s);
    }
}
