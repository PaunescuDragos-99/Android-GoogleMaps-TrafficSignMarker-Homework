package com.example.homeworkandroid;

import static androidx.core.content.PackageManagerCompat.LOG_TAG;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.nfc.Tag;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.homeworkandroid.databinding.ActivityMapsBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

import java.util.Map;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;
    private static final String LOG_TAG =
            MapsActivity.class.getSimpleName();
    private Marker markerSef;
    private TextView textViewAsync;
    ArrayList<MarkerModel> markerAddList;
    MyAdapter adapter;
    DatabaseReference reff;
    int count = 0;
    private retrieveData RetrieveData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        reff = FirebaseDatabase.getInstance().getReference("Marker");

        RetrieveData = new retrieveData();


        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        Toast.makeText(MapsActivity.this,"Firebase connection succes",Toast.LENGTH_LONG)
                .show();



        //RetrieveData.execute(count);



    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;
        try {
            // Customise the styling of the base map using a JSON object defined
            // in a raw resource file.
            boolean success = googleMap.setMapStyle(
                    MapStyleOptions.loadRawResourceStyle(
                            this, R.raw.mapstyle));

            if (!success) {
                Log.e("MapsActivity", "Style parsing failed.");
            }
        } catch (Resources.NotFoundException e) {
            Log.e("MapsActivity", "Can't find style. Error: ", e);
        }


        textViewAsync = findViewById(R.id.asyncTask);
        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        markerSef = mMap.addMarker(new MarkerOptions()
                .position(sydney)
                .title("Marker in Sydney")
                .draggable(true));
        markerSef.setTag(0);

        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        mMap.getUiSettings().setZoomControlsEnabled(true);
        RetrieveData.execute(count);


       reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                markerAddList = new ArrayList<>();
                if(snapshot.exists()) {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        MarkerModel markerModels = dataSnapshot.getValue(MarkerModel.class);
                        markerAddList.add(markerModels);
                        String Name = markerModels.getMarkerName();
                        String Info = markerModels.getMarkerInfo();
                        String Latitude = markerModels.getMarkerLatitude();
                        String Longitude = markerModels.getMarkerLongitude();
                        NewMarker(Name, Info, Latitude, Longitude);

                    }
                }

                //String Name = markerAddList.get(0).getMarkerName();
                //String Info = markerAddList.get(2).getMarkerInfo();
                //NewMarker(Name, Info);

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        //Setare marker pe harta, luand latitudinea si longitudinea.
        googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener(){
            public void onMapClick(LatLng point){

                Intent intent = new Intent(MapsActivity.this,ThirdActivity.class);
                String latitude = String.valueOf(point.latitude);
                String longitude = String.valueOf(point.longitude);
                intent.putExtra("Latitude",latitude);
                intent.putExtra("Longitude",longitude);
                Toast.makeText(MapsActivity.this,
                        latitude + ", " + longitude,
                        Toast.LENGTH_SHORT).show();
                startActivity(intent);
            }
        });






    }

    public void NewMarkerActivity(View view) {
        Intent intent = new Intent(this, NewMarkerActivity.class);
        startActivity(intent);

    }


    private class retrieveData extends AsyncTask<Integer,Integer, Integer> {

        private int customInt;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            customInt = 0;
        }

        @Override
        protected Integer doInBackground(Integer... integers) {
            customInt = integers[0];

            while (customInt > -1) {
                try {
                    Thread.sleep(1000);
                    customInt++;
                    publishProgress(customInt);
                } catch (InterruptedException e) {
                    Log.i(LOG_TAG, e.getMessage());
                }
            }
            return customInt;

        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            textViewAsync.setText(""+values[0]);
        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
            textViewAsync.setText(""+integer);
            count = integer;


        }
    }




    public void NewMarker (String Name, String Info,String Latitude, String Longitude) {

        Double latitude = Double.valueOf(Latitude);
        Double longitude = Double.valueOf(Longitude);
        LatLng australia = new LatLng(latitude, longitude);
        mMap.addMarker(new MarkerOptions()
                .position(australia)
                .title(Name)
                .snippet(Info)
                .draggable(true));
    }




    /**public boolean onMarkerClick(final Marker marker) {

        // Retrieve the data from the marker.
        Integer clickCount = (Integer) marker.getTag();

        // Check if a click count was set, then display the click count.
        if (clickCount != null) {
            clickCount = clickCount + 1;
            marker.setTag(clickCount);
            marker.setTitle("America");
            Toast.makeText(this,
                    marker.getTitle() +
                            " has been clicked " + clickCount + " times.",
                    Toast.LENGTH_SHORT).show();
        }

        // Return false to indicate that we have not consumed the event and that we wish
        // for the default behavior to occur (which is for the camera to move such that the
        // marker is centered and for the marker's info window to open, if it has one).
        return false;

    }*/
}

