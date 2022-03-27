package com.example.homeworkandroid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;

public class ThirdActivity extends AppCompatActivity {

    public static final String EXTRA_REPLY =
            "com.example.android.twoactivities.extra.REPLY";
    private EditText mReply;


    EditText txtMarkerName, txtMarkerInfo;
    Button btnSave;
    DatabaseReference reff;
    Marker marker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);
        txtMarkerName = findViewById(R.id.txtMarkerName);
        txtMarkerInfo = findViewById(R.id.txtMarkerInfo);
        btnSave = findViewById(R.id.btnSave);
        marker = new Marker();
        reff = FirebaseDatabase.getInstance().getReference().child("Marker");
        btnSave.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                marker.setMarkerName(txtMarkerName.getText().toString().trim());
                marker.setMarkerInfo(txtMarkerInfo.getText().toString().trim());

                reff.push().setValue(marker);
                Toast.makeText(ThirdActivity.this, "Okey", Toast.LENGTH_LONG).show();
            }
        });


    }
}
