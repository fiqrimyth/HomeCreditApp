package com.example.homecreditapp.View.contract;

public interface BasePresenter<V> {
    void start();
    void bind(V view);
    void unbind();
}
