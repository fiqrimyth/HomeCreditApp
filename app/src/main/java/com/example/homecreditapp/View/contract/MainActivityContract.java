package com.example.homecreditapp.View.contract;

import com.example.homecreditapp.Network.model.ListProduct;

import java.util.List;

public interface MainActivityContract {

    interface View extends BaseView<Presenter> {

        void onLoadProduct();

        void loadingData();

        void loadDone();

        void onLoadArticle();

    }
    interface Presenter extends BasePresenter<View> {

        void loadProduct();

        List<ListProduct> listProduct();

    }
}
