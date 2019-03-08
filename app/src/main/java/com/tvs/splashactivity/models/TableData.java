package com.tvs.splashactivity.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TableData {
    @SerializedName("TABLE_DATA")
    @Expose
    private TABLE_DATA tABLEDATA;

    public TABLE_DATA getTABLEDATA() {
        return tABLEDATA;
    }

    public void setTABLEDATA(TABLE_DATA tABLEDATA) {
        this.tABLEDATA = tABLEDATA;
    }

}
