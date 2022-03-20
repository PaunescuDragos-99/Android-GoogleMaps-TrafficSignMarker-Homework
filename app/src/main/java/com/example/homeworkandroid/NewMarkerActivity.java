package com.example.homeworkandroid;

import static androidx.core.content.PackageManagerCompat.LOG_TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.internal.Constants;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;


public class NewMarkerActivity extends AppCompatActivity {

    public static final String EXTRA_MESSAGE = "com.example.android.homeworkandroid.extra.MESSAGE";
    private EditText mMessageEditText;
    public static final int TEXT_REQUEST = 1;
    private TextView mReplyHeadTextView;
    private TextView mReplyTextView;


    private static final String TAG = "MainMarkers";
    RecyclerView recyclerView;
    ArrayList<Marker> markerList;
    String Key;

    DatabaseReference reff;
    DatabaseReference reff2;
    MyAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_marker);

        recyclerView = findViewById(R.id.markerList);
        reff = FirebaseDatabase.getInstance().getReference("Marker");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));



        markerList = new ArrayList<>();
        adapter = new MyAdapter(this,markerList);
        recyclerView.setAdapter(adapter);


        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                    Marker marker = dataSnapshot.getValue(Marker.class);


                    markerList.add(marker);
                    reff2 = FirebaseDatabase.getInstance().getReference().child("Marker").push();
                    String key = reff2.getKey();
                    Log.d("Post Key" , key);
                }
                adapter.notifyDataSetChanged();
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }





    public void ThirdActivity(View view) {
        Intent intent = new Intent(this, ThirdActivity.class);
        startActivity(intent);
    }

}