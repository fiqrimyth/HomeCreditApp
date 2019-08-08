package com.example.homecreditapp.Network.wrapper;

import com.example.homecreditapp.Network.model.ListProduct;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class WrapperProductItem {
    @SerializedName("data")
    @Expose
    private List<ListProduct> data = null;

    public List<ListProduct> getData() {
        return data;
    }

    public void setData(List<ListProduct> data) {
        this.data = data;
    }


}
