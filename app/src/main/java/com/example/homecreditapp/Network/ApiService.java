package com.example.homecreditapp.Network;

import com.example.homecreditapp.Network.wrapper.WrapperProductItem;


import io.reactivex.Observable;
import retrofit2.http.GET;

public interface ApiService {

    @GET("/home")
    Observable<WrapperProductItem> getProductItem();

}
