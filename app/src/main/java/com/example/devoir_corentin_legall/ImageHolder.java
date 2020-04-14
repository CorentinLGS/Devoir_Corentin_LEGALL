package com.example.devoir_corentin_legall;

import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

public class ImageHolder extends RecyclerView.ViewHolder{

    public ImageView image;

    ImageHolder(View view) {
        super(view);

        image = view.findViewById(R.id.image);
    }
}
