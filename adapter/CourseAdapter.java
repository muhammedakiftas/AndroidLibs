package com.sinifdefterimpro.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sinifdefterimpro.R;
import com.sinifdefterimpro.helper.ChooseCourseInfo;
import com.sinifdefterimpro.helper.ItemCourseInfo;
import com.sinifdefterimpro.helper.SimpleDividerItemDecoration;
import com.sinifdefterimpro.helper.SyllabusInfo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.MyViewHolder> implements ItemCourseAdapter.ClickListener {
    private static final String TAG = CourseAdapter.class.getSimpleName();

    private LayoutInflater inflater;
    private List<ChooseCourseInfo> data = Collections.emptyList();
    public ClickListener clickListener;
    private Context ctx;

    private ArrayList<ChooseCourseInfo> arraylist;

    String[] listArray = {"Sınıf düzeyine göre","Derse göre","Android ListView","Android ListView","Android ListView"};

    public CourseAdapter(Context context) {
        ctx = context;
        inflater = LayoutInflater.from(context);

        arraylist = new ArrayList<ChooseCourseInfo>();
    }

    public void setList(List<ChooseCourseInfo> items) {
        this.data = items;
        arraylist.clear();
        arraylist.addAll(data);
        notifyItemRangeChanged(0, items.size());
    }

    public void clear() {
        data.clear();
        notifyDataSetChanged();
    }

    public void addAll(List<ChooseCourseInfo> list) {
        data.addAll(list);
        notifyDataSetChanged();
    }

    public void setClickListener(ClickListener clickListener) {
        this.clickListener = clickListener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_list_choose_course, parent, false);
        return new MyViewHolder(view);
    }

    private ItemCourseAdapter itemCourseAdapter;
    private RecyclerView rvItemCourse;
    private List<ItemCourseInfo> itemCourseList;

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
/*
        holder.rvItemCourse.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(ctx, LinearLayoutManager.VERTICAL, false);
        holder.rvItemCourse.setLayoutManager(layoutManager);

        ItemCourseAdapter adapter = new ItemCourseAdapter(ctx);
        holder.rvItemCourse.setAdapter(adapter);
*/

        holder.tvTitle.setText(data.get(position).getTitle());


        itemCourseList = new ArrayList<>();
        //holder.rvItemCourse.setHasFixedSize(true);
        //holder.rvItemCourse.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        holder.rvItemCourse.setLayoutManager(new LinearLayoutManager(ctx));
        itemCourseAdapter = new ItemCourseAdapter(ctx);
        itemCourseAdapter.setClickListener(this);
        holder.rvItemCourse.setAdapter(itemCourseAdapter);

        for (int i = 0; i < data.get(position).getItem().length; i++) {
            itemCourseList.add(new ItemCourseInfo(data.get(position).getItem()[i],data.get(position).getItemLink()[i]));
        }

        holder.rvItemCourse.addItemDecoration(new SimpleDividerItemDecoration(ctx));

        itemCourseAdapter.setList(itemCourseList);
        itemCourseAdapter.notifyDataSetChanged();

        /*LinearLayout ll = new LinearLayout(ctx);
        ll.setOrientation(LinearLayout.VERTICAL)*/
/*
        LinearLayout.LayoutParams layoutParams = null;

        if (position==0){
            layoutParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            //holder.cvItem.setLayoutParams(new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int) ctx.getResources().getDimension(R.dimen.myCardViewHeight)));
        }else if (position==1){
            layoutParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, (int) ctx.getResources().getDimension(R.dimen.myCardViewHeight2));
            //holder.cvItem.setLayoutParams(new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int) ctx.getResources().getDimension(R.dimen.myCardViewHeight2)));
        }else{
            layoutParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, (int) ctx.getResources().getDimension(R.dimen.myCardViewHeight3));
            //holder.cvItem.setLayoutParams(new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int) ctx.getResources().getDimension(R.dimen.myCardViewHeight2)));
        }

        layoutParams.setMargins(30, 30, 30, 30);
        holder.cvItem.setLayoutParams(layoutParams);
*/
 /*
        holder.tvText1.setText(data.get(position).getCompany_visit_plan_end_date());
        holder.tvText2.setText(data.get(position).getCompany_visit_plan_start_date());
        holder.tvText3.setText(data.get(position).getEmployee_color_identifier());

       if (data.get(position).getCompany_visit_plan_end_date().equals("1")){
            holder.layout.setBackgroundColor(ctx.getResources().getColor(R.color.flatui_carrot));
        }

        holder.description.setText(data.get(position).getCompany_visit_plan_end_date());
        holder.description.setTypeface("fon");*/
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public View onCreate(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return null;
    }

    @Override
    public void itemClicked(View view, String tag) {
        if(view.getId() == R.id.cv_item_course2){
            if (clickListener != null) {
                clickListener.itemClicked(view, tag);
            }
        }
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        CardView cvItem;
        RecyclerView rvItemCourse;
        TextView tvTitle;

        MyViewHolder(View itemView) {
            super(itemView);

            cvItem = (CardView) itemView.findViewById(R.id.cv_item);
            rvItemCourse = (RecyclerView) itemView.findViewById(R.id.tv_item_course);
            tvTitle = (TextView) itemView.findViewById(R.id.tv_title);

            tvTitle.setTypeface(Typeface.createFromAsset(ctx.getAssets(), "fonts/Roboto-Bold.ttf"));

        }

    }

    public interface ClickListener {
        View onCreate(LayoutInflater inflater, ViewGroup container,
                      Bundle savedInstanceState);

        public void itemClicked(View view, String tag);

    }
}

