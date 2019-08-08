package com.example.homecreditapp;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.homecreditapp.View.adapter.AdapterArticle;
import com.example.homecreditapp.View.adapter.AdapterProduct;
import com.example.homecreditapp.View.contract.MainActivityContract;
import com.example.homecreditapp.View.presenter.MainActivityPresenter;

public class MainActivity extends AppCompatActivity implements MainActivityContract.View {

    private MainActivityContract.Presenter mpresenter;

    private AdapterProduct adapterProduct;
    private AdapterArticle adapterArticle;
    private RecyclerView rvProduct;
    private RecyclerView rvArticle;
    private ProgressDialog progressDialog;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mpresenter = initPresenter();
        mpresenter.bind(this);
        mpresenter.start();
        progressDialog = new ProgressDialog(this);

        mpresenter.loadProduct();
        textView = findViewById(R.id.title_article);
        textView.setVisibility(View.GONE);

        rvProduct = findViewById(R.id.rvProduct);
        rvProduct.setHasFixedSize(true);
        rvProduct.setLayoutManager(new GridLayoutManager(this, 3, GridLayoutManager.VERTICAL, false));

        rvArticle = findViewById(R.id.rvArticle);
        rvArticle.setHasFixedSize(true);
        rvArticle.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

    }

    @Override
    public void onLoadProduct() {
        adapterProduct = new AdapterProduct(this, mpresenter.listProduct().get(0).getItems());
        rvProduct.setAdapter(adapterProduct);
    }

    @Override
    public void onLoadArticle() {
        textView.setVisibility(View.VISIBLE);
        adapterArticle = new AdapterArticle(this, mpresenter.listProduct().get(1).getItems());
        rvArticle.setAdapter(adapterArticle);
    }

    @Override
    public void loadingData() {
        progressDialog.setMessage("loading");
        progressDialog.show();
    }

    @Override
    public void loadDone() {
        progressDialog.dismiss();
    }

    @Override
    public MainActivityContract.Presenter initPresenter() {
        return new MainActivityPresenter(this);
    }
}
