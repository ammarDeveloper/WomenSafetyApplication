package com.applocstion.womensafetyapplication;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private LocationManager locationManager;
    private LocationListener locationListener;
    private final long MIN_TIME = 1000;
    private final long MIN_DIST = 5;
    private LatLng latLng;
    private Button btnDanger;
    private Button addBtn;
    private String userName;
    private String Phone1;
    private String Phone2;
    private String Phone3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

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




        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        btnDanger = findViewById(R.id.btnEmregency);
        addBtn = findViewById(R.id.addBtn);


        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {
                try{
                    latLng = new LatLng(location.getLatitude(), location.getLongitude());
                    mMap.addMarker(new MarkerOptions().position(latLng));
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                    btnDanger.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            if (MainActivity.userName.equals("") || MainActivity.num1.equals("") || MainActivity.num2.equals("") || MainActivity.num4.equals("")){
                                userName = MainActivity2.userNameIn2;
                                Phone1 = MainActivity2.num1In2;
                                Phone2 = MainActivity2.num2In2;
                                Phone3 = MainActivity2.num3In2;
                            }else{
                                userName = MainActivity.userName;
                                Phone1 = MainActivity.num1;
                                Phone2 = MainActivity.num2;
                                Phone3 = MainActivity.num4;
                            }

                            String myLatitudeLocation = String.valueOf(location.getLatitude());
                            String myLogitudeLocation = String.valueOf(location.getLongitude());
                            String message = "Hi this is "+userName+" i am at the Loction: \n"+"Latitude = "+myLatitudeLocation+" Longitude = "+myLogitudeLocation+"\n I AM IN EMERGENCY RIGHT NOW PLEASE TRY TO HELP ME! ";
                            SmsManager smsManager = SmsManager.getDefault();
                            smsManager.sendTextMessage(Phone1, null, message, null, null);
                            smsManager.sendTextMessage(Phone2, null, message, null, null);
                            smsManager.sendTextMessage(Phone3, null, message, null, null);

                        }
                    });

                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        };

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        try{
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MIN_TIME, MIN_DIST, locationListener);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,MIN_TIME, MIN_DIST,locationListener);

        }catch (SecurityException e){
            e.printStackTrace();
        }

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MapsActivity.this, MainActivity2.class);
                startActivity(intent);
            }
        });



    }
}