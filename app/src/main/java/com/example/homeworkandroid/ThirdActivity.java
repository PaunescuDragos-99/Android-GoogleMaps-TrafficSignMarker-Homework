package com.example.homeworkandroid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationChannelCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ThirdActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    public static final String EXTRA_REPLY =
            "com.example.android.twoactivities.extra.REPLY";
    private EditText mReply;


    EditText txtMarkerName, txtMarkerInfo, txtMarkerLatitude, txtMarkerLongitude;
    Button btnSave;
    DatabaseReference reff;
    MarkerModel marker;
    private static final String PRIMARY_CHANNEL_ID =
            "primary_notification_channel";
    // Notification ID.
    private static final int NOTIFICATION_ID = 0;
    private NotificationManager mNotifyManager;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);
        txtMarkerName = findViewById(R.id.txtMarkerName);
        txtMarkerInfo = findViewById(R.id.txtMarkerInfo);
        txtMarkerLatitude = findViewById(R.id.txtMarkerLatitude);
        txtMarkerLongitude = findViewById(R.id.txtMarkerLongitude);
        Intent intent = getIntent();

        createNotificationChannel();
       String latitude = intent.getStringExtra("Latitude");
       String longitude = intent.getStringExtra("Longitude");

       Spinner spinner = findViewById(R.id.spinner1);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.indicatoare, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
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

                sendNotification();

                Intent intent2 = new Intent(ThirdActivity.this, NewMarkerActivity.class);
                startActivity(intent2);
            }
        });


    }
    public void createNotificationChannel() {

        // Create a notification manager object.
        mNotifyManager =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        // Notification channels are only available in OREO and higher.
        // So, add a check on SDK version.
        if (android.os.Build.VERSION.SDK_INT >=
                android.os.Build.VERSION_CODES.O) {

            // Create the NotificationChannel with all the parameters.
            NotificationChannel notificationChannel = new NotificationChannel
                    (PRIMARY_CHANNEL_ID,
                            "Mascot Notification",
                            NotificationManager.IMPORTANCE_HIGH);

            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.BLUE);
            notificationChannel.enableVibration(false);

            mNotifyManager.createNotificationChannel(notificationChannel);
        }
    }
    public void sendNotification() {

        NotificationCompat.Builder notifyBuilder = getNotificationBuilder();
        mNotifyManager.notify(NOTIFICATION_ID, notifyBuilder.build());

    }

    private NotificationCompat.Builder getNotificationBuilder() {

        // Set up the pending intent that is delivered when the notification
        // is clicked.



        // Build the notification with all of the parameters.
        NotificationCompat.Builder notifyBuilder = new NotificationCompat
                .Builder(this, PRIMARY_CHANNEL_ID)
                .setContentTitle("Marker was added")
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setDefaults(NotificationCompat.DEFAULT_ALL);
        return notifyBuilder;
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String text = adapterView.getItemAtPosition(i).toString();
        Toast.makeText(ThirdActivity.this, "NR i:"+i, Toast.LENGTH_SHORT).show();
        if(i == 0){
            marker.setMarkerIndicator("indicator_prioritate");
        }else
            if(i == 1){
            marker.setMarkerIndicator("indicator_stop");
        }else
            if(i == 2){
            marker.setMarkerIndicator("indicator_cedeaza");
        }else
            if(i == 3){
            marker.setMarkerIndicator("indicator_interzis");
        }else
            if(i == 4){
            marker.setMarkerIndicator("indicator_inainte");
        }else
            if(i == 5){
            marker.setMarkerIndicator("indicator_dreapta");
        }else
            if(i == 6){
            marker.setMarkerIndicator("indicator_stanga");
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
