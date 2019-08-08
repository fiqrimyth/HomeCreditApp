package com.example.homecreditapp.Network.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ListProduct {
    @SerializedName("section")
    @Expose
    private String section;
    @SerializedName("items")
    @Expose
    private List<ProductItem> items = null;
    @SerializedName("section_title")
    @Expose
    private String sectionTitle;

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public List<ProductItem> getItems() {
        return items;
    }

    public void setItems(List<ProductItem> items) {
        this.items = items;
    }

    public String getSectionTitle() {
        return sectionTitle;
    }

    public void setSectionTitle(String sectionTitle) {
        this.sectionTitle = sectionTitle;
    }

}
