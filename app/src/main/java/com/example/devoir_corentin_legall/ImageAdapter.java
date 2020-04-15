package com.example.devoir_corentin_legall;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.util.Log;
import android.util.Size;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class ImageAdapter extends RecyclerView.Adapter<ImageHolder> {

    private ArrayList<ImageContent> images;
    private Context context;


    public ImageAdapter(ArrayList<ImageContent> paths, Context context) {
        this.images = paths;
        this.context = context;
    }

    @Override
    public ImageHolder onCreateViewHolder(@NonNull ViewGroup container, int position) {
        LayoutInflater inflater = LayoutInflater.from(container.getContext());
        View cell = inflater.inflate(R.layout.image_holder, container, false);
        return new ImageHolder(cell);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onBindViewHolder(@NonNull final ImageHolder holder, final int position) {
        Bitmap bm = MediaStore.Images.Thumbnails.getThumbnail(context.getContentResolver(), images.get(position).id, MediaStore.Images.Thumbnails.MICRO_KIND, null);
        holder.image.setImageBitmap(bm);
        holder.image.setOnTouchListener(new View.OnTouchListener() {
            private GestureDetector gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onDoubleTap(MotionEvent event) {
                    Intent nextActivity = new Intent(context,EncodingTextActivity.class);
                    nextActivity.putExtra("imagePath", images.get(position).path);
                    context.startActivity(nextActivity);
                    return true;
                }


            });

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                gestureDetector.onTouchEvent(event);
                return true;
            }
        });

    }

    @Override
    public int getItemCount() {
        return images.size();
    }

}
