package com.example.homecreditapp.View.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.homecreditapp.Network.model.ProductItem;
import com.example.homecreditapp.R;

import java.util.ArrayList;
import java.util.List;

public class AdapterProduct extends RecyclerView.Adapter<AdapterProduct.productViewHolder> {
    public static final String TAG = "AdapterProduct";
    private Context contex;
    private List<ProductItem> data = new ArrayList<>();

    public AdapterProduct(Context context, List<ProductItem> productItems) {
        this.contex = context;
        this.data = productItems;
    }

    @NonNull
    @Override
    public AdapterProduct.productViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_product, parent, false);

        return new AdapterProduct.productViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterProduct.productViewHolder holder, int position) {
        Glide.with(contex)
                .load(data.get(position).getProductImage())
                .into(holder.imageProduct);
        holder.titleProduct.setText(data.get(position).getProductName());

        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(data.get(position).getLink()));
                contex.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class productViewHolder extends RecyclerView.ViewHolder {

        RelativeLayout relativeLayout;
        ImageView imageProduct;
        TextView titleProduct;

        public productViewHolder(View itemView) {
            super(itemView);
            relativeLayout = itemView.findViewById(R.id.product_bar);
            imageProduct = itemView.findViewById(R.id.image_product);
            titleProduct = itemView.findViewById(R.id.title_product);

        }
    }
}
