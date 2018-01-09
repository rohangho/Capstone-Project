package com.example.android.contactshare;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.widget.Button;
import android.widget.ImageView;

import java.util.ArrayList;

public class Subject extends AppCompatActivity {


    RecyclerView recyclerView;
    ArrayList<CustomClassSub> subject_name = new ArrayList<CustomClassSub>();
    SubjectAdapter adapt;
    ImageView sub_img;
    Button Logout;
    RecyclerView.LayoutManager layoutManager;
    int[] image_id = {R.drawable.ds, R.drawable.co, R.drawable.ethics, R.drawable.environment, R.drawable.digital, R.drawable.p};
    String[] name;
    static SharedPreferences sp;
    Uri imageuri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_subject);

        name = getResources().getStringArray(R.array.Subject);
        int count = 0;

        Logout = (Button) findViewById(R.id.LogOut);
        Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent myintent = new Intent(getApplicationContext(), ToLogIn.class);
                startActivity(myintent);

            }
        });

        for (String Name : name) {
            CustomClassSub custom = new CustomClassSub(image_id[count], Name);
            count++;
            subject_name.add(custom);
        }
        recyclerView = (RecyclerView) findViewById(R.id.subject_selection);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        adapt = new SubjectAdapter(subject_name, this);
        recyclerView.setAdapter(adapt);
        Logout = (Button) findViewById(R.id.LogOut);
        Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myintent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(myintent);
            }
        });
        animateViewsIn();

    }

    private void animateViewsIn() {
        ViewGroup root = (ViewGroup) findViewById(R.id.root);
        int count = root.getChildCount();
        float offset = getResources().getDimensionPixelSize(R.dimen.offset_y);
        Interpolator interpolator =
                AnimationUtils.loadInterpolator(this, android.R.interpolator.linear_out_slow_in);
        for (int i = 0; i < count; i++) {
            View view = root.getChildAt(i);
            view.setVisibility(View.VISIBLE);
            view.setTranslationY(offset);
            view.setAlpha(0.85f);
            // then animate back to natural position
            view.animate()
                    .translationY(0f)
                    .alpha(1f)
                    .setInterpolator(interpolator)
                    .setDuration(1500L)
                    .start();
            // increase the offset distance for the next view
            offset *= 1.5f;
        }

    }

    public  void changeImage(View v)
    {
        opengallery();
    }

    public void opengallery()
    {
        Intent gallery=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery,22);

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode,
                                 Intent resultData) {

        super.onActivityResult(requestCode,resultCode,resultData);
        if(resultCode==RESULT_OK && requestCode==22)
        {
            imageuri=resultData.getData();
           String[] projection={MediaStore.Images.Media.DATA};
            Cursor cursor=getContentResolver().query(imageuri,projection,null,null,null);
            cursor.moveToFirst();
            int column=cursor.getColumnIndex(projection[0]);
            String filepath=cursor.getString(column);
            cursor.close();
            Bitmap selectedImage= BitmapFactory.decodeFile(filepath);
            sub_img=(ImageView)findViewById(R.id.subject_image);
            sub_img.setImageBitmap(selectedImage);
        }

    }
}
