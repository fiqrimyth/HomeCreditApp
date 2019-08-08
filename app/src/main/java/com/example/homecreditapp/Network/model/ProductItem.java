package com.example.homecreditapp.Network.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProductItem {
    @SerializedName("product_name")
    @Expose
    private String productName;
    @SerializedName("product_image")
    @Expose
    private String productImage;
    @SerializedName("link")
    @Expose
    private String link;
    @SerializedName("article_title")
    @Expose
    private String articleTitle;
    @SerializedName("article_image")
    @Expose
    private String articleImage;

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getArticleTitle() {
        return articleTitle;
    }

    public void setArticleTitle(String articleTitle) {
        this.articleTitle = articleTitle;
    }

    public String getArticleImage() {
        return articleImage;
    }

    public void setArticleImage(String articleImage) {
        this.articleImage = articleImage;
    }

}
