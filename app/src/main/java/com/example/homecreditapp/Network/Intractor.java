package com.example.homecreditapp.Network;

import com.example.homecreditapp.Network.wrapper.WrapperProductItem;

import io.reactivex.Observable;

public class Intractor {
    private ApiService mService;

    public Intractor(ApiService service){
        mService = service;
    }

    public Observable<WrapperProductItem> productItem() {
        return mService.getProductItem();
    }
}
