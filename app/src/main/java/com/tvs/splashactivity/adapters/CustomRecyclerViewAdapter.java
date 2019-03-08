package com.tvs.splashactivity.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tvs.splashactivity.R;
import com.tvs.splashactivity.models.EmployeeData;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CustomRecyclerViewAdapter extends RecyclerView.Adapter<CustomRecyclerViewAdapter.ViewHolder> {

    private Context mContext;
    private ArrayList<EmployeeData> mEmployeeDataList;
    private ArrayList<EmployeeData> mCopyEmployeeDataList;

    public CustomRecyclerViewAdapter(Context mContext, ArrayList<EmployeeData> mEmployeeDataList) {
        this.mContext = mContext;
        this.mEmployeeDataList = mEmployeeDataList;
    }

    public void updateList(ArrayList<EmployeeData> employeeDataArrayList){
        this.mEmployeeDataList = employeeDataArrayList;
        this.mCopyEmployeeDataList = new ArrayList<>(mEmployeeDataList);
        notifyDataSetChanged();
    }
    public void filter(String text) {
        mEmployeeDataList.clear();
        if(text.isEmpty()){
            mEmployeeDataList.addAll(mCopyEmployeeDataList);
        } else{
            text = text.toLowerCase();
            for(EmployeeData item: mCopyEmployeeDataList){
                if(item.getName().toLowerCase().contains(text)){
                    mEmployeeDataList.add(item);
                }
            }
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.recycler_list_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int i) {
        holder.name.setText(mEmployeeDataList.get(i).getName());
        holder.salary.setText(mEmployeeDataList.get(i).getAmount());
        holder.date.setText(mEmployeeDataList.get(i).getDate());
        holder.place.setText(mEmployeeDataList.get(i).getPlace());
        holder.designation.setText(mEmployeeDataList.get(i).getDesignation());
        holder.code.setText(mEmployeeDataList.get(i).getCode());
    }

    @Override
    public int getItemCount() {
        return mEmployeeDataList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.name)
        TextView name;
        @BindView(R.id.designation)
        TextView designation;
        @BindView(R.id.place)
        TextView place;
        @BindView(R.id.code)
        TextView code;
        @BindView(R.id.date)
        TextView date;
        @BindView(R.id.salary)
        TextView salary;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
