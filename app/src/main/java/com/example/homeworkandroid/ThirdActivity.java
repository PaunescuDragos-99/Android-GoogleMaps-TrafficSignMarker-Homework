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

}
