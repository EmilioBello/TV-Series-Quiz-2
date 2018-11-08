package com.quiz.series.tv.tvseriesquiz.view.adapter;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

public class CommonAdapter<In> extends RecyclerView.Adapter<CommonAdapter.ViewHolder>{

    private final List<In> items;
    private final int layout;
    private final int layoutItemID;

    public CommonAdapter(final List<In> items, final int layout, final int layoutItemID) {
        this.items = items;
        this.layout = layout;
        this.layoutItemID = layoutItemID;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        final ViewDataBinding binding = DataBindingUtil.inflate(inflater, layout, parent, false);
        return new ViewHolder(binding, layoutItemID);
    }

    @Override
    public void onBindViewHolder(final CommonAdapter.ViewHolder holder, final int position) {
        final Object item = items.get(position);
        holder.bind(item);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final ViewDataBinding binding;
        private final int layoutItemID;

        public ViewHolder(final ViewDataBinding binding, final int layoutItemID) {
            super(binding.getRoot());

            this.binding = binding;
            this.layoutItemID = layoutItemID;
        }

        public void bind(Object item) {
            binding.setVariable(layoutItemID, item);
            binding.executePendingBindings();
        }
    }

    public void update(){
        notifyDataSetChanged();
    }
}
