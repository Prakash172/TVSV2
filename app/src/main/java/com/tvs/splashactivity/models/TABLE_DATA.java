package com.tvs.splashactivity.models;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TABLE_DATA {

    @SerializedName("data")
    @Expose
    private List<List<Object>> data = null;

    public List<List<Object>> getData() {
        return data;
    }

    public void setData(List<List<Object>> data) {
        this.data = data;
    }

}