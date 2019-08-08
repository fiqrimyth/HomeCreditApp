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

public class AdapterArticle extends RecyclerView.Adapter<AdapterArticle.articleViewHolder> {
    public static final String TAG = "AdapterArticle";
    private Context contex;
    private List<ProductItem> data = new ArrayList<>();

    public AdapterArticle(Context context, List<ProductItem> productItems) {
        this.contex = context;
        this.data = productItems;
    }

    @NonNull
    @Override
    public AdapterArticle.articleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_article, parent, false);

        return new AdapterArticle.articleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterArticle.articleViewHolder holder, int position) {
        Glide.with(contex)
                .load(data.get(position).getArticleImage())
                .into(holder.imageArticle);
        holder.titleArticle.setText(data.get(position).getArticleTitle());

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

    public static class articleViewHolder extends RecyclerView.ViewHolder {

        RelativeLayout relativeLayout;
        ImageView imageArticle;
        TextView titleArticle;

        public articleViewHolder(View itemView) {
            super(itemView);
            relativeLayout = itemView.findViewById(R.id.article_bar);
            imageArticle = itemView.findViewById(R.id.image_article);
            titleArticle = itemView.findViewById(R.id.title_article);

        }
    }
}

