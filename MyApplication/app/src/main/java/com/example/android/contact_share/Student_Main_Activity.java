package com.example.android.contact_share;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class Student_Main_Activity extends AppCompatActivity {


    Button abc;
    ArrayList<CustomClass_forStudent> mylist;


    Parcelable mListState;
    int b;
    String data;
    RecyclerView namedisplayer;
    StudentAdapter studentAdapter;
    String rollconcat;
    RecyclerView.LayoutManager layoutManager;
    String nameconcat;
    Button save;
    int a;
    public static ArrayList<CustomClass_forStudent> myUpdatedList;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    String currentDateTimeString = DateFormat.getDateTimeInstance()
            .format(new Date());

    static final int READ_REQUEST_CODE = 22;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student__main_);

        save = (Button) findViewById(R.id.save);

        abc = (Button) findViewById(R.id.file_selector);
        if (savedInstanceState == null) {
            abc.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.M)
                @Override
                public void onClick(View v) {
                    showFile();
                }//end of else part
            });//end of onclick
        }
        else
        {


            int d=savedInstanceState.getInt("number");
            namedisplayer = (RecyclerView) findViewById(R.id.rec);
            layoutManager = new LinearLayoutManager(this);
            namedisplayer.setLayoutManager(layoutManager);
            namedisplayer.setHasFixedSize(true);
            mylist=savedInstanceState.getParcelableArrayList("LIST_STATE");
            studentAdapter = new StudentAdapter( mylist, this, d);
            namedisplayer.setAdapter(studentAdapter);

            abc.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.M)
                @Override
                public void onClick(View v) {
                    showFile();
                }//end of else part
            });//en

            animateViewsIn();


        }



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


    public void SaveMe(View view) throws IOException {

        myUpdatedList = new ArrayList<>();
        int k = 0;
        //Log.e("hiiiiii",Boolean.toString(studentAdapter.tracker[2]));
        if (data == null)
            Toast.makeText(getApplicationContext(), "Seems Empty", Toast.LENGTH_LONG).show();
        else {
            List<String> list = new ArrayList<String>(Arrays.asList(data.split(",")));
            if (list.isEmpty())
                Toast.makeText(getApplicationContext(), "Seems Empty", Toast.LENGTH_LONG).show();
            for (int i = 0; i < list.size() - 2; i = i + 3) {
                CustomClass_forStudent addition1 = new CustomClass_forStudent(list.get(i), list.get(i + 1), Boolean.toString(studentAdapter.tracker[k]));
                myUpdatedList.add(addition1);
                k++;

            }
            //
            if (Build.VERSION.SDK_INT >= 23) {
                if (checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE)
                        == PackageManager.PERMISSION_GRANTED) {
                    write_file(myUpdatedList);
                } else {
                    ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, 1);

                }
            } else
                write_file(myUpdatedList);
        }
    }

    public void write_file(ArrayList<CustomClass_forStudent> myUpdatedList) {
        String Filename = currentDateTimeString;
        FileOutputStream outputStream = null;
        try {
            outputStream = openFileOutput(Filename, Context.MODE_PRIVATE);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        for (int j = 0; j < myUpdatedList.size(); j++) {
            try {

                outputStream.write(myUpdatedList.get(j).getRoll().getBytes());
                outputStream.write(",".getBytes());
                outputStream.write(myUpdatedList.get(j).getName().getBytes());
                outputStream.write(",".getBytes());
                outputStream.write(myUpdatedList.get(j).getBool().getBytes());
                outputStream.write(",".getBytes());
                outputStream.write("\n".getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        StorageReference storageReference = storage.getReference();
        StorageReference attendSheet = storageReference.child(currentDateTimeString);
        StorageReference attendSheetReference = storageReference.child("data/data/com.example.android.contact_share/files/" + currentDateTimeString);

        Toast.makeText(getApplicationContext(), "Hang On", Toast.LENGTH_LONG).show();
        putStream(attendSheet);


    }

    public void putStream(StorageReference attendSheet) {
        try {
            InputStream stream = new FileInputStream(new File("data/data/com.example.android.contact_share/files/" + currentDateTimeString));
            UploadTask uploadTask = attendSheet.putStream(stream);
            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                    Uri downloadUrl = taskSnapshot.getDownloadUrl();
                    Toast.makeText(getApplicationContext(), "Successful", Toast.LENGTH_LONG).show();
                }
            });


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }


    public void ClearEveryThing(View view) {
        mylist = new ArrayList<CustomClass_forStudent>();
        List<String> list = new ArrayList<String>(Arrays.asList(data.split(",")));
        studentAdapter = new StudentAdapter(mylist, this, (list.size() / 3));
        namedisplayer.setAdapter(studentAdapter);
        data = null;

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void showFile() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                //  Toast.makeText(this,"hiiiiiiiii",Toast.LENGTH_LONG).show();
                getFile();

            } else {
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, 1);

            }
        } else {
            // Toast.makeText(this,"hiiiiiiiii",Toast.LENGTH_LONG).show();
            getFile();

        }

    }

    private void getFile() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("text/*");
        startActivityForResult(intent, READ_REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode,
                                 Intent resultData) {

        if (requestCode == READ_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            Uri uri = null;
            if (resultData != null) {
                uri = resultData.getData();
                String message = null;
                try {
                    message = readTextFromUri(uri);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                //ContentResolver resolver = this.getContentResolver();
                // Cursor cursor=resolver.query(uri,null,null,null,null);
                data = message.toString();

                addtoArraylist_Using1function(data);

            }
        }

    }


    //different function in which the list is seperated using only one function

    public void addtoArraylist_Using1function(String q) {
        mylist = new ArrayList<CustomClass_forStudent>();
        List<String> list = new ArrayList<String>(Arrays.asList(q.split(",")));
        for (int i = 0; i < list.size() - 2; i = i + 3) {
            CustomClass_forStudent addition = new CustomClass_forStudent(list.get(i), list.get(i + 1), list.get(i + 2));
            mylist.add(addition);

        }


        namedisplayer = (RecyclerView) findViewById(R.id.rec);
        layoutManager = new LinearLayoutManager(this);
        namedisplayer.setLayoutManager(layoutManager);
        namedisplayer.setHasFixedSize(true);
        studentAdapter = new StudentAdapter(mylist, this, (list.size() / 3));
        a=list.size()/3;
        namedisplayer.setAdapter(studentAdapter);
        animateViewsIn();

    }

    private String readTextFromUri(Uri uri) throws IOException {
        InputStream inputStream = getContentResolver().openInputStream(uri);
        BufferedReader reader = new BufferedReader(new InputStreamReader(
                inputStream));
        StringBuilder stringBuilder = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            stringBuilder.append(line);
        }
        inputStream.close();
        return stringBuilder.toString();
    }

    protected void onSaveInstanceState(Bundle state) {
        super.onSaveInstanceState(state);

        // Save list state
        if(layoutManager!=null) {
            mListState = layoutManager.onSaveInstanceState();
            state.putInt("number",a);
            state.putParcelableArrayList("LIST_STATE", mylist);
        }
    }
    protected void onRestoreInstanceState(Bundle state) {
        super.onRestoreInstanceState(state);

        // Retrieve list state and list/item positions
        if(state != null) {
            mylist = state.getParcelableArrayList("LIST_STATE");
            a=state.getInt("number");
            ;

        }
    }
    protected void onResume() {
        super.onResume();

        if (mListState != null) {

            layoutManager.onRestoreInstanceState(mListState);
        }
    }

}
