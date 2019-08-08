package com.example.homecreditapp.View.presenter;

import android.content.Context;
import android.widget.Toast;

import com.example.homecreditapp.Network.ApiServiceBuilder;
import com.example.homecreditapp.Network.Intractor;
import com.example.homecreditapp.Network.model.ListProduct;
import com.example.homecreditapp.Network.wrapper.WrapperProductItem;
import com.example.homecreditapp.View.contract.MainActivityContract;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class MainActivityPresenter implements MainActivityContract.Presenter {
    public static final String TAG = "MainActivityPresenter";

    private MainActivityContract.View mView;
    private Intractor mInteractor;
    private CompositeDisposable compositeDisposable;
    private Context mContext;

    private List<ListProduct> mlistview;

    public MainActivityPresenter(Context context) {
        mContext = context;
        compositeDisposable = new CompositeDisposable();
        mlistview = new ArrayList<>();
        if (mInteractor == null) {
            mInteractor = new Intractor(new ApiServiceBuilder().provideApiService());
        }
    }

    @Override
    public void loadProduct() {
        mView.loadingData();
        compositeDisposable.add(mInteractor.productItem()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<WrapperProductItem>() {
                    @Override
                    public void onNext(WrapperProductItem wrapperProductItem) {
                        if (wrapperProductItem.getData() != null) {
                            mlistview = wrapperProductItem.getData();
                            mView.onLoadProduct();
                            mView.onLoadArticle();
                            mView.loadDone();
                        } else {
                            Toast.makeText(mContext, "Failed to get data", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                }));
    }

    @Override
    public List<ListProduct> listProduct() {
        return mlistview;
    }

    @Override
    public void start() {

    }

    @Override
    public void bind(MainActivityContract.View view) {
        mView = view;
    }

    @Override
    public void unbind() {

    }
}
