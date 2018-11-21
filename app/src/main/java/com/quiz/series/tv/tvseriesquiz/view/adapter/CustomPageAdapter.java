package com.quiz.series.tv.tvseriesquiz.view.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.quiz.series.tv.tvseriesquiz.MyApp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CustomPageAdapter<ViewModel> extends PagerAdapter implements View.OnClickListener{

    private HashMap<Integer, View> views;

    private final List<ViewModel> items;
    private final Context context;
    private final ClickListener listener;
    private final int layout;
    private final int layoutItemID;

    private int positionCurrent;

    public CustomPageAdapter(final ClickListener listener, final int layout, final int layoutItemID) {
        this(new ArrayList<ViewModel>(), listener, layout, layoutItemID);
    }

    public CustomPageAdapter(List<ViewModel> items, ClickListener listener, final int layout, final int layoutItemID) {
        this.items = items;
        this.listener = listener;
        this.context = MyApp.getContext();
        this.views = new HashMap<>();

        this.layout = layout;
        this.layoutItemID = layoutItemID;
    }

    public void addAll(List<ViewModel> elements) {
        //this.items.clear();
        this.items.addAll(elements);
        notifyDataSetChanged();
    }

    public void clear() {
        items.clear();
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    public ViewModel get(int position) {
        return items.get(position);
    }

    public ViewModel getActive() {
        return items.get(positionCurrent);
    }

    public void setPosition(final int position){
        positionCurrent = position;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        //inflate
        final LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final ViewDataBinding binding = DataBindingUtil.inflate(inflater, layout, container, false);
        // Set your databinding up here

        //init View
        binding.setVariable(layoutItemID, items.get(position));
        binding.executePendingBindings();

        // Add viewpager_item.xml to ViewPager
        container.addView(binding.getRoot());

        //save view
        views.put(position, binding.getRoot());

        return binding.getRoot();
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((RelativeLayout)object);

        views.remove(position);
    }

    @Override
    public void onClick(View v) {

    }

    public interface ClickListener {
        void answerQuestion(boolean correct);
    }

    //We need this method because PagerAdapter has a bug and not refresh the adapter when you change its content and show viewpager in a specific position.
    public int getItemPosition(Object object){
        return POSITION_NONE;
    }
}
