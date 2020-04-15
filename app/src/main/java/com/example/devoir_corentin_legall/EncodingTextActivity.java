package com.example.devoir_corentin_legall;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

public class EncodingTextActivity extends AppCompatActivity {

    String image_path;
    ImageView image_view;
    TextView text_view;
    Button button;
    Coder coder;
    String decoded_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_encoding);

        image_view = findViewById(R.id.encodeImage);
        text_view = findViewById(R.id.texte);
        button = findViewById(R.id.encodeButton);

        button.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    coder.encode(text_view.getText().toString());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        image_path = getIntent().getStringExtra("imagePath");

        coder = new Coder(image_path);
        try {
            decoded_text = coder.decode();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(decoded_text.length()>0) {
            text_view.setText(decoded_text);
        }
        image_view.setImageBitmap(BitmapFactory.decodeFile(image_path));
    }
}
