package com.example.homeworkandroid;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class NewMarkerActivity extends AppCompatActivity {

    public static final String EXTRA_MESSAGE = "com.example.android.homeworkandroid.extra.MESSAGE";
    public static final int TEXT_REQUEST = 1;



    private static final String TAG = "MainMarkers";
    RecyclerView recyclerView;

    ArrayList<MarkerModel> markerList;
    String Key;

    DatabaseReference reff;
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
                markerList.clear();
                for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                    MarkerModel marker = dataSnapshot.getValue(MarkerModel.class);
                    markerList.add(marker);
                    marker.setMarkerKey(dataSnapshot.getKey());



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