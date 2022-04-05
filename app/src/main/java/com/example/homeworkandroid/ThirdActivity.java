package com.example.homeworkandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ThirdActivity extends AppCompatActivity {

    public static final String EXTRA_REPLY =
            "com.example.android.twoactivities.extra.REPLY";
    private EditText mReply;


    EditText txtMarkerName, txtMarkerInfo, txtMarkerLatitude, txtMarkerLongitude;
    Button btnSave;
    DatabaseReference reff;
    MarkerModel marker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);
        txtMarkerName = findViewById(R.id.txtMarkerName);
        txtMarkerInfo = findViewById(R.id.txtMarkerInfo);
        txtMarkerLatitude = findViewById(R.id.txtMarkerLatitude);
        txtMarkerLongitude = findViewById(R.id.txtMarkerLongitude);
        Intent intent = getIntent();

       String latitude = intent.getStringExtra("Latitude");
       String longitude = intent.getStringExtra("Longitude");

       txtMarkerLatitude.setText(latitude);
       txtMarkerLongitude.setText(longitude);
        btnSave = findViewById(R.id.btnSave);
        marker = new MarkerModel();
        reff = FirebaseDatabase.getInstance().getReference().child("Marker");
        btnSave.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                marker.setMarkerName(txtMarkerName.getText().toString().trim());
                marker.setMarkerInfo(txtMarkerInfo.getText().toString().trim());
                marker.setMarkerLatitude(txtMarkerLatitude.getText().toString().trim());
                marker.setMarkerLongitude(txtMarkerLongitude.getText().toString().trim());

                reff.push().setValue(marker);
                Toast.makeText(ThirdActivity.this, "Okey", Toast.LENGTH_LONG).show();
            }
        });


    }
}
