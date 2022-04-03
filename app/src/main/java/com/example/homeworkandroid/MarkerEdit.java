package com.example.homeworkandroid;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.homeworkandroid.databinding.ActivityNewMarkerBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.HashMap;

public class MarkerEdit extends AppCompatActivity {

        ActivityNewMarkerBinding binding;
        EditText editMarkerName;
        EditText editMarkerInfo;
        Button btnEdit;
        String Key;
        String display_MarkerName = null;
        String display_MarkerInfo = null;

        @Override
        protected void onCreate(Bundle savedInstanceState)

        {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.marker_edit);
            editMarkerName = findViewById(R.id.editMarkerNameText);
            editMarkerInfo = findViewById(R.id.editMarkerInfoText);
            btnEdit = findViewById(R.id.editBtn);



            Intent intent = getIntent();
            Key = intent.getStringExtra("EDIT");

            DatabaseReference reff = FirebaseDatabase.getInstance().getReference().child("Marker").child(Key);
            HashMap map = new HashMap ();



            btnEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    map.put("markerName",editMarkerName.getText().toString().trim());
                    map.put("markerInfo",editMarkerInfo.getText().toString().trim());
                    String markerNametxt = String.valueOf(editMarkerName.getText());
                   reff.updateChildren(map);
                    Toast.makeText(MarkerEdit.this,
                            "Marker name:"+ markerNametxt,
                            Toast.LENGTH_SHORT).show();
                    Intent intent2 = new Intent(MarkerEdit.this,NewMarkerActivity.class);
                    startActivity(intent2);
                }
            });
        }




}



