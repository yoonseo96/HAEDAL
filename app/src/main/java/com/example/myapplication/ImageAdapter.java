package com.example.myapplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.List;
import java.util.concurrent.Executors;


public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ViewHolder> {

    private Context context;
    private List<ImageItem> imageItemList;
    public ImageAdapter(Context context,List<ImageItem> imageItemList){
        this.context=context;
        this.imageItemList=imageItemList;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.item_image,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ImageItem item=imageItemList.get(position);
        LoadImage(item.getImageUrl(),holder.imageview);
    }
    @Override
    public int getItemCount() {
        return imageItemList.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageview;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageview=itemView.findViewById(R.id.imageView);
        }
    }

    private void LoadImage(String urlStr, ImageView imageView) {
        Glide.with(imageView.getContext())
                .load(urlStr)
                .into(imageView);
    }
}
