package com.tvs.splashactivity.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

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
    public void onBindViewHolder(@NonNull final ViewHolder holder, int i) {
        holder.name.setText(mEmployeeDataList.get(i).getName());
        holder.designation.setText(mEmployeeDataList.get(i).getDesignation());
        holder.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "Itme-"+holder.getAdapterPosition(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mEmployeeDataList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.name_tv)
        TextView name;
        @BindView(R.id.designation_tv)
        TextView designation;
        @BindView(R.id.parent)
        CardView parent;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
