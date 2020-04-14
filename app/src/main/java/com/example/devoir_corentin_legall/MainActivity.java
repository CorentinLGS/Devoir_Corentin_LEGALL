package com.example.devoir_corentin_legall;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.media.Image;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<ImageContent> images = new ArrayList<ImageContent>();
    private RecyclerView recycler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED) {
            Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
            String[] projection = {MediaStore.Images.ImageColumns.DATA, MediaStore.Images.ImageColumns._ID};
            String titleOrder = MediaStore.Images.Media.DEFAULT_SORT_ORDER;

            Cursor cursor = getApplicationContext().getContentResolver().query(uri, projection, null, null , titleOrder);

            while(cursor.moveToNext())
            {
                ImageContent image = new ImageContent(cursor.getString(0), cursor.getInt(1));
                images.add(image);
            }

            recycler = findViewById(R.id.recycler);
            recycler.setLayoutManager( new GridLayoutManager(this, 2));
            recycler.hasFixedSize();
            recycler.setAdapter( new ImageAdapter(images, getBaseContext()));
        }
    }
}
