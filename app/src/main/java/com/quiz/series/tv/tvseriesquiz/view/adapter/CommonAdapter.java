package com.quiz.series.tv.tvseriesquiz.view.adapter;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.quiz.series.tv.tvseriesquiz.BR;
import com.quiz.series.tv.tvseriesquiz.view.viewmodel.SeriesViewModel;

import java.util.List;

public class CommonAdapter<ViewModel> extends RecyclerView.Adapter<CommonAdapter.ViewHolder>{

    private final List<ViewModel> items;
    private final int layout;
    private final int layoutItemID;
    private final CommonAdapterListener<ViewModel> listener;

    private View v;

    public CommonAdapter(final CommonAdapterListener<ViewModel> listener, final List<ViewModel> items, final int layout, final int layoutItemID) {
        this.listener = listener;
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

    public class ViewHolder extends RecyclerView.ViewHolder implements Listener{
        private final ViewDataBinding binding;
        private final int layoutItemID;

        public ViewHolder(final ViewDataBinding binding, final int layoutItemID) {
            super(binding.getRoot());

            this.binding = binding;
            this.layoutItemID = layoutItemID;
        }

        public void bind(Object item) {
            binding.setVariable(layoutItemID, item);
            binding.setVariable(BR.listener, this); //Line to handler onClick events
            binding.executePendingBindings();
        }

        @Override
        public void onClickListener() {
            if (listener != null) {
                final int position = getAdapterPosition();
                listener.getItem(items.get(position), itemView);
            }
        }
    }

    public void update(){
        notifyDataSetChanged();
    }

    //Interface to handler onClick events
    public interface Listener {
        void onClickListener();
    }
}
