package com.example.homeworkandroid;

import static androidx.core.content.PackageManagerCompat.LOG_TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class NewMarkerActivity extends AppCompatActivity {

    public static final String EXTRA_MESSAGE = "com.example.android.homeworkandroid.extra.MESSAGE";
    private EditText mMessageEditText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_marker);
        mMessageEditText = findViewById(R.id.editTextMain);


        }
    public void ThirdActivity(View view) {
        Intent intent = new Intent(this, ThirdActivity.class);
        String message = mMessageEditText.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);

    }
}