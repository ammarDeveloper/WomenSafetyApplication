package com.applocstion.womensafetyapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.transition.Slide;
import androidx.transition.Transition;
import androidx.transition.TransitionManager;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Typeface;
import android.graphics.pdf.PdfDocument;
import android.hardware.Camera;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import java.io.File;
import java.io.FileOutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private static final String TAG = "MapsActivity";
    private static final int REQUEST_VIDEO_CAPTURE = 1;
    private static final int PERMISSION_STORAGE = 100;
    private static final int PAGE_WIDTH = 1080;
    private static final int PAGE_HEIGHT = 2010;
    private String datetime;
    private double log, lat;

    private GoogleMap mMap;
    public static final int PERMISSIONS_SEND_MESSAGE = 98;
    private static final int PERMISSIONS_FINE_LOCATION = 99;
    private LatLng latLng;
    private CardView cardViewConnect, cardViewEdit, cardExpand, addToCardNum, cardCancel,saveBtn, CancelNameEditFOrCard, SaveBtnForNameInCard, imageBtnForEditingName, errorMessageCard, relocateCardBtn, list_of_settings, editing_side_bar, editingPageNameIconCardView, last_ten_location_card, add_to_saved_location_card, edit_saved_details_card_view, my_location_card_view, settings_card_view, about_card_view, my_location_container_card_view, send_WA_message_card, send_location_pdf_btn;
    private TextView cardWarningNumber, cardWarningName, cardTextName, my_location_body;
    private RelativeLayout cardTextNameHide, relForNameAndEditBtn;
    private String userName, lineAddress;
    private ArrayList<Phone_numbers> Phone_numbers, phone_numbers_list;
    private RecyclerView cardRecyclerView;
    private NumRecAdapter adapter;
    private ConstraintLayout parent;
    private EditText cardEditNumber, cardEditName;
    private Bitmap bitmap, scaleBitmap;


    //Location Request is a config file for all Settings related to FusedLocationProvider
    private LocationRequest locationRequest;

    // Google's API for location services. the majority of the app functions using this class.
    private FusedLocationProviderClient fusedLocationProviderClient;

    // invaked when location changed
    private LocationCallback locationCallback;

    private CircleOptions circleOptions = new CircleOptions();
    private MarkerOptions markerOptions = new MarkerOptions();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Do You Want To Exit?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.create();
        builder.show();
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

        locationRequest = new LocationRequest();

        locationRequest.setInterval(1000 * 2);

        locationRequest.setFastestInterval(1000 * 1);

        locationRequest.setSmallestDisplacement(10);

        locationRequest.setPriority(locationRequest.PRIORITY_HIGH_ACCURACY);


        mMap = googleMap;

        initViews();

        updateGps();

        //event is called when update interval is met
        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(@NonNull LocationResult locationResult) {
                super.onLocationResult(locationResult);
                mMap.clear();
                mMap.getUiSettings().setAllGesturesEnabled(false);
                updateGps();
            }
        };


        callBackRelocation();
    }



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSIONS_FINE_LOCATION:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    updateGps();
                } else {
                    Snackbar snackbar = Snackbar
                            .make(parent, "Please Provide The Location Permission", Snackbar.LENGTH_LONG)
                            .setAction("Got it", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                }
                            });
                    snackbar.show();
                }
                break;
            case PERMISSIONS_SEND_MESSAGE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    sendMessage();
                } else {
                    Snackbar snackbar = Snackbar
                            .make(parent, "Please Provide The Sms Permission", Snackbar.LENGTH_LONG)
                            .setAction("Got it", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                }
                            });
                    snackbar.show();
                }
                break;
            case PERMISSION_STORAGE:
                if(grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED){
                    sendLocationsPdf();
                } else {
                    Snackbar snackbar = Snackbar
                            .make(parent, "Please Provide Storage Permission", Snackbar.LENGTH_LONG)
                            .setAction("Got it", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                }
                            });
                    snackbar.show();
                }
            default:
                break;
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_VIDEO_CAPTURE && resultCode == RESULT_OK) {
            Uri uri = data.getData();
            sendWAMessage(uri);
        }
    }



    // UI elements
    public void UIElements() {
        // to expand the card view to edit the details
        cardViewEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fetchAndFill();
                expandCard();
            }
        });

        // cancelling the card view
        cardCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeCard();
            }
        });

        // Add Number number button inside card view
        addToCardNum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (phoneValidates()) {
                    cardWarningNumber.setVisibility(View.GONE);
                    Phone_numbers phone_numbers = new Phone_numbers(-1, cardEditNumber.getText().toString());
                    phone_numbers_list.add(phone_numbers);
                    adapter.setPhoneNum(phone_numbers_list);
                    cardRecyclerView.setAdapter(adapter);
                    cardRecyclerView.setLayoutManager(new LinearLayoutManager(MapsActivity.this));
                    cardEditNumber.getText().clear();
                } else if (emptyPhone()) {
                    cardWarningNumber.setText("Section Empty");
                    cardWarningNumber.setVisibility(View.VISIBLE);
                } else {
                    cardWarningNumber.setText("Invalid Number");
                    cardWarningNumber.setVisibility(View.VISIBLE);
                }
            }
        });

        // Save Btn inside Card view
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initUpdate();
            }
        });

        // Expanding the Edit section for name of the user
        imageBtnForEditingName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cardEditName.setText(cardTextName.getText().toString());
                cardTextNameHide.setVisibility(View.VISIBLE);
                relForNameAndEditBtn.setVisibility(View.GONE);
            }
        });

        // canceling the Edit name section
        CancelNameEditFOrCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cardTextNameHide.setVisibility(View.GONE);
                relForNameAndEditBtn.setVisibility(View.VISIBLE);
            }
        });

        // Save Btn for name
        SaveBtnForNameInCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cardTextName.setText(cardEditName.getText().toString());
                cardTextNameHide.setVisibility(View.GONE);
                relForNameAndEditBtn.setVisibility(View.VISIBLE);
            }
        });

        // Button to send text message
        cardViewConnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callBackRelocation();
                DataBaseHolder dataBaseHolder = new DataBaseHolder(MapsActivity.this);
                userName = dataBaseHolder.getData().get(0).getName();
                Phone_numbers = dataBaseHolder.getData().get(0).getPhone_numbers();
                if (ActivityCompat.checkSelfPermission(MapsActivity.this, Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED) {
                    sendMessage();
                } else {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        requestPermissions(new String[]{Manifest.permission.SEND_SMS}, PERMISSIONS_SEND_MESSAGE);
                    }
                }
            }
        });

        // Button to send images on whatsapp
        send_WA_message_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callBackRelocation();
                DataBaseHolder dataBaseHolder = new DataBaseHolder(MapsActivity.this);
                userName = dataBaseHolder.getData().get(0).getName();
                Phone_numbers = dataBaseHolder.getData().get(0).getPhone_numbers();
                if(checkCameraHardware(MapsActivity.this)){
                    Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
                    try{
                        intent.putExtra(MediaStore.EXTRA_DURATION_LIMIT, 10);
                        startActivityForResult(intent, REQUEST_VIDEO_CAPTURE);

                    }catch (Exception e){
                        Toast.makeText(MapsActivity.this, "Something went wrong, Your Android version doesn't support this feature", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        // Button to send saved location pdf
        send_location_pdf_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if((ActivityCompat.checkSelfPermission(MapsActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) && (ActivityCompat.checkSelfPermission(MapsActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)){
                    sendLocationsPdf();
                } else {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_STORAGE);
                    }
                }
            }
        });

        // Button for relocating the user
        relocateCardBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callBackRelocation();
            }
        });

        // Button for Expanding editing side bar
        list_of_settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                open_editing_slide_bar();
            }
        });

        // event listener on map to close all the cards
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(@NonNull LatLng latLng) {
                if (cardExpand.getVisibility() == View.VISIBLE){
                    closeCard();
                } else if(editing_side_bar.getVisibility() == View.VISIBLE){
                    close_editing_slide_bar();
                    enableBackGround();
                } else{
                    close_my_loction();
                    enableBackGround();
                }
            }
        });

        // event listener on marker
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(@NonNull Marker marker) {
                show_my_location();
                my_location_body.setText(lineAddress);
                disableBackGround();
                return false;
            }
        });

        // empty click
        editingPageNameIconCardView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                return true;
            }
        });

        // empty click
        cardExpand.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                return true;
            }
        });

        // empty click
        my_location_container_card_view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                return true;
            }
        });

        // click to show last ten locations
        last_ten_location_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MapsActivity.this, List_of_locations.class);
                startActivity(intent);
            }
        });

        // click for adding the location to the saved locations
        add_to_saved_location_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Date date = new Date();
                datetime = DateFormat.getDateTimeInstance().format(date);
                LocationDate locationDate = new LocationDate(-1, lineAddress, datetime);
                DataBaseHolder dataBaseHolder = new DataBaseHolder(MapsActivity.this);
                boolean A = dataBaseHolder.addUsersLocations(locationDate);
                if (A){
                    Toast.makeText(MapsActivity.this, "Location Added", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MapsActivity.this, "Something Went Wrong, Try Again", Toast.LENGTH_SHORT).show();
                }

            }
        });

        // click for editing saved detials
        edit_saved_details_card_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editing_side_bar.setVisibility(View.GONE);
                fetchAndFill();
                expandCard();
            }
        });

        // click to display my location
        my_location_card_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editing_side_bar.setVisibility(View.GONE);
                show_my_location();
                my_location_body.setText(lineAddress);
                disableBackGround();
            }
        });

        // click for settings
        settings_card_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MapsActivity.this, SettingsActivity.class);
                startActivity(intent);
            }
        });

        // click for about section
        about_card_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: creating an about section
            }
        });
    }

    // initializes the Views
    public void initViews() {
        cardViewConnect = findViewById(R.id.changeCard);
        cardViewEdit = findViewById(R.id.cardEdit);
        cardExpand = findViewById(R.id.cardExpand);
        cardCancel = findViewById(R.id.cardCancel);
        errorMessageCard = findViewById(R.id.errorMessageCard);
        parent = findViewById(R.id.mapParent);
        cardRecyclerView = findViewById(R.id.cardRecView);
        adapter = new NumRecAdapter(this);
        addToCardNum = findViewById(R.id.cardAddBtn);
        cardEditNumber = findViewById(R.id.cardEditNumber);
        cardWarningNumber = findViewById(R.id.cardWarningNumber);
        cardEditName = findViewById(R.id.cardEditName);
        cardWarningName = findViewById(R.id.cardWarningName);
        saveBtn = findViewById(R.id.saveCard);
        phone_numbers_list = new ArrayList<>();
        cardTextName = findViewById(R.id.cardTextName);
        imageBtnForEditingName = findViewById(R.id.imageBtnForEditingName);
        cardTextNameHide = findViewById(R.id.cardTextNameHide);
        relForNameAndEditBtn = findViewById(R.id.relForNameAndEditBtn);
        CancelNameEditFOrCard = findViewById(R.id.CancelBtnForNameEditInCard);
        SaveBtnForNameInCard = findViewById(R.id.SaveBtnForNameInCard);
        relocateCardBtn = findViewById(R.id.reLocateCardBtn);
        list_of_settings = findViewById(R.id.list_of_settings);
        editing_side_bar = findViewById(R.id.editing_side_bar);
        editingPageNameIconCardView = findViewById(R.id.editingPageNameIconCardView);
        last_ten_location_card = findViewById(R.id.last_ten_location_card);
        add_to_saved_location_card = findViewById(R.id.add_to_saved_location_card);
        edit_saved_details_card_view = findViewById(R.id.edit_Saved_details_card_view);
        my_location_card_view = findViewById(R.id.my_location_card_view);
        settings_card_view = findViewById(R.id.settings_card_view);
        about_card_view = findViewById(R.id.about_card_view);
        my_location_body = findViewById(R.id.My_location_body);
        my_location_container_card_view = findViewById(R.id.my_location_container_card_view);
        send_WA_message_card = findViewById(R.id.send_WA_messag_card);
        send_location_pdf_btn = findViewById(R.id.send_location_pdf_btn);
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.pdf_top_banar);
        scaleBitmap = Bitmap.createScaledBitmap(bitmap, 1080, 446, false);
    }

    // updating the gps
    public void updateGps() {
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(MapsActivity.this);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            fusedLocationProviderClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    updateLocation(location);
                }
            });
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSIONS_FINE_LOCATION);
            }
        }
    }

    // updates the location
    public void updateLocation(Location location) {
        try {
            latLng = new LatLng(location.getLatitude(), location.getLongitude());
            log = location.getLongitude();
            lat = location.getLatitude();
            UIElements();
            checkNetwork();
            Transition transition = new Slide(Gravity.TOP);
            transition.setDuration(600);
            transition.addTarget(R.id.errorMessageCard);
            errorMessageCard.setVisibility(View.GONE);
            mMap.getUiSettings().setAllGesturesEnabled(true);
            mMap.addCircle(circleOptions.center(latLng).radius(1000).strokeColor(Color.rgb(218, 226, 240)).fillColor(Color.TRANSPARENT));
            mMap.addMarker(markerOptions.position(latLng));
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15f));

            Geocoder geocoder = new Geocoder(MapsActivity.this);
            try {
                ArrayList<Address> addresses = (ArrayList<Address>) geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                lineAddress = addresses.get(0).getAddressLine(0);
            } catch (Exception e) {
                lineAddress = String.valueOf(location.getLatitude()) + String.valueOf(location.getLongitude());
            }
        } catch (Exception e) {
            mMap.getUiSettings().setAllGesturesEnabled(false);
            Transition transition = new Slide(Gravity.BOTTOM);
            transition.setDuration(600);
            transition.addTarget(R.id.errorMessageCard);
            errorMessageCard.setVisibility(View.VISIBLE);
        }
    }

    // Sending messages
    public void sendMessage() {
        String locationlink = "http://maps.google.com/maps?saddr="+lat+","+log;
        String message = "Hi this is " + userName + " check out my location below,  i am at " + lineAddress+" From women safety app with map";
        SmsManager smsManager = SmsManager.getDefault();
        ArrayList<String> listOfMessages = smsManager.divideMessage(message);
        for (Phone_numbers s : Phone_numbers) {
            smsManager.sendMultipartTextMessage(s.getPhone_number(), null, listOfMessages, null, null);
            smsManager.sendTextMessage(s.getPhone_number(), null, locationlink, null, null);
        }
    }

    // sending messages on whatapp
    private void sendWAMessage(Uri uri) {
        String locationlink = "http://maps.google.com/maps?saddr="+lat+","+log+" check out my location, i am at "+lineAddress+" From women safety app with map";
//        sendMessage();
        Intent whatsappintent = new Intent(Intent.ACTION_SEND);
        whatsappintent.setPackage("com.whatsapp");
        if(isAppInstalled(whatsappintent.getPackage())){
            whatsappintent.putExtra(Intent.EXTRA_TEXT, locationlink);
            whatsappintent.setType("text/plain");
            whatsappintent.putExtra(Intent.EXTRA_STREAM, uri);
            whatsappintent.setType("video/*");
            startActivity(whatsappintent);
            Toast.makeText(this, "message sent", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "WhatsApp is not installed", Toast.LENGTH_SHORT).show();
        }

    }

    // initializing the Update
    private void initUpdate() {
        Log.d(TAG, "initRegister: started");

        if (validatesData()) {
            cardWarningName.setVisibility(View.GONE);
            cardWarningNumber.setVisibility(View.GONE);
            DataBaseHolder dataBaseHolder = new DataBaseHolder(this);
            PersonsId personsId = new PersonsId(dataBaseHolder.getData().get(0).getId(), cardTextName.getText().toString(), adapter.getPhoneNum());
            int b = dataBaseHolder.updateData(personsId);
            if(b == -1){
                Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show();
            } else{
                Toast.makeText(this, "Data Set Changed", Toast.LENGTH_SHORT).show();
            }
            cardEditName.getText().clear();
            cardEditNumber.getText().clear();
            closeCard();
        }
    }

    // Checks for the name and the atleast 3 numbers
    private boolean validatesData() {
        Log.d(TAG, "validatesData: started");
        int count;
        count = 0;

        if (adapter.getPhoneNum().size() < 3) {
            Snackbar snackbar = Snackbar
                    .make(parent, "Required Aleast 3 Phone Numbers", Snackbar.LENGTH_LONG)
                    .setAction("Got it", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                        }
                    });
            cardWarningName.setVisibility(View.GONE);
            snackbar.setActionTextColor(Color.YELLOW);
            snackbar.show();
            count = count + 1;
        }

        if (count > 0) {
            return false;
        }
        return true;
    }

    // Checks if the Phone number slot is empty
    public boolean emptyPhone() {
        Log.d(TAG, "emptyPhone: Started");
        if (cardEditNumber.getText().toString().equals("")) {
            return true;
        }
        return false;
    }

    // Checks if the phone number is valid
    public boolean phoneValidates() {

        Log.d(TAG, "phoneValidates: Started");
        if (cardEditNumber.getText().toString().length() != 10) {
            return false;
        }
        return true;
    }

    // Card Expanding
    public void expandCard() {
        Transition transition = new Slide(Gravity.TOP);
        transition.setDuration(300);
        transition.addTarget(R.id.cardExpand);
        TransitionManager.beginDelayedTransition(parent, transition);
        cardExpand.setVisibility(View.VISIBLE);
        disableBackGround();
    }

    // card Closing
    public void closeCard() {
        Transition transition = new Slide(Gravity.TOP);
        transition.setDuration(300);
        transition.addTarget(R.id.cardExpand);
        TransitionManager.beginDelayedTransition(parent, transition);
        cardExpand.setVisibility(View.GONE);
        enableBackGround();
    }

    // Checking Network Connections
    public void checkNetwork() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(this.CONNECTIVITY_SERVICE);
        if ((connectivityManager.getNetworkInfo(connectivityManager.TYPE_MOBILE).getState() != NetworkInfo.State.CONNECTED) || !(connectivityManager.getNetworkInfo(connectivityManager.TYPE_WIFI).getState() != NetworkInfo.State.CONNECTED)) {
            Snackbar snackbar = Snackbar.make(parent, "Could'nt Load The Map, Please Check Your Connection", Snackbar.LENGTH_LONG)
                    .setAction("Got It", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                        }
                    });
            snackbar.show();
        }
    }

    // Method which call back the relocation
    public void callBackRelocation() {
        if (ActivityCompat.checkSelfPermission(MapsActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(MapsActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, null);
    }

    // fetching the data from database and filling the edit details section
    public void fetchAndFill(){
        DataBaseHolder dataBaseHolder = new DataBaseHolder(MapsActivity.this);
        cardTextName.setText(dataBaseHolder.getData().get(0).getName());
        phone_numbers_list = dataBaseHolder.getData().get(0).getPhone_numbers();
        adapter.setPhoneNum(phone_numbers_list);
        cardRecyclerView.setAdapter(adapter);
        cardRecyclerView.setLayoutManager(new LinearLayoutManager(MapsActivity.this));
    }

    // enabling the buttons
    public void enableBackGround(){
        mMap.getUiSettings().setAllGesturesEnabled(true);
        cardViewConnect.setEnabled(true);
        cardViewEdit.setEnabled(true);
        relocateCardBtn.setEnabled(true);
        list_of_settings.setEnabled(true);
        send_WA_message_card.setEnabled(true);
    }

    // disabling the buttons
    public void disableBackGround(){
        mMap.getUiSettings().setAllGesturesEnabled(false);
        cardViewConnect.setEnabled(false);
        cardViewEdit.setEnabled(false);
        relocateCardBtn.setEnabled(false);
        list_of_settings.setEnabled(false);
        send_WA_message_card.setEnabled(false);
    }

    // show my location
    public void show_my_location(){
        Transition transition = new Slide(Gravity.BOTTOM);
        transition.setDuration(200);
        transition.addTarget(R.id.my_location_container_card_view);
        TransitionManager.beginDelayedTransition(parent, transition);
        my_location_container_card_view.setVisibility(View.VISIBLE);
    }

    // close my location
    public void close_my_loction(){
        Transition transition = new Slide(Gravity.BOTTOM);
        transition.setDuration(200);
        transition.addTarget(R.id.my_location_container_card_view);
        TransitionManager.beginDelayedTransition(parent, transition);
        my_location_container_card_view.setVisibility(View.GONE);
    }

    // opening edit slide bar
    public void open_editing_slide_bar(){
        Transition transition = new Slide(Gravity.LEFT);
        transition.setDuration(500);
        transition.addTarget(R.id.editing_side_bar);
        TransitionManager.beginDelayedTransition(parent, transition);
        editing_side_bar.setVisibility(View.VISIBLE);
        disableBackGround();
    }

    // close edit slide bar
    public void close_editing_slide_bar(){
        Transition transition = new Slide(Gravity.LEFT);
        transition.setDuration(200);
        transition.addTarget(R.id.editing_side_bar);
        TransitionManager.beginDelayedTransition(parent, transition);
        editing_side_bar.setVisibility(View.GONE);
        enableBackGround();
    }

    // checking if the mobile has the camera
    public boolean checkCameraHardware(Context context){
        if (context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)){
            return true;
        }else{
            return false;
        }
    }

    // checking if whatsapp is installed or not
    private boolean isAppInstalled(String packageName) {
        PackageManager pm = getPackageManager();
        boolean app_installed;
        try {
            pm.getPackageInfo(packageName, PackageManager.GET_ACTIVITIES);
            app_installed = true;
        }
        catch (PackageManager.NameNotFoundException e) {
            app_installed = false;
        }
        return app_installed;
    }

    // sending location pdf
    public void sendLocationsPdf(){
        // todo: sening pdf to whatsapp
        DataBaseHolder dataBaseHolder = new DataBaseHolder(MapsActivity.this);
        if (dataBaseHolder.getUsersLocations().isEmpty()){
            Toast.makeText(MapsActivity.this, "No Locations added please add the locations to send", Toast.LENGTH_SHORT).show();
        } else {
            PdfDocument pdfDocument = new PdfDocument();
            Paint myPaint = new Paint();
            PdfDocument.PageInfo myPageInfo1 = new PdfDocument.PageInfo.Builder(PAGE_WIDTH, PAGE_HEIGHT, 1).create();
            PdfDocument.Page myPage1 = pdfDocument.startPage(myPageInfo1);
            Canvas canvas = myPage1.getCanvas();

            canvas.drawBitmap(scaleBitmap, 0, 0, myPaint);

            myPaint.setStyle(Paint.Style.STROKE);
            myPaint.setStrokeWidth(4);
            canvas.drawRect(20, 476, PAGE_WIDTH-20, 556, myPaint);

            myPaint.setTextAlign(Paint.Align.LEFT);
            myPaint.setStyle(Paint.Style.FILL);
            myPaint.setTypeface(Typeface.DEFAULT_BOLD);
            myPaint.setTextSize(27f);
            myPaint.setColor(Color.rgb(161, 158, 157));
            canvas.drawText("SI.NO: ", 40, 526, myPaint);
            canvas.drawText("Locations Visited", 200, 526, myPaint);
            canvas.drawText("Date & Time", 900, 526, myPaint);

            canvas.drawLine(180, 486, 180, 546, myPaint);
            canvas.drawLine(880, 486, 880, 546, myPaint);

            pdfDocument.finishPage(myPage1);

            File filePath = Environment.getExternalStorageDirectory();
            Date date = new Date();
            datetime = DateFormat.getDateTimeInstance().format(date);

            File file = new File(filePath, "Locations-at-"+datetime+".pdf");

            try{
                pdfDocument.writeTo(new FileOutputStream(file));
                Toast.makeText(this, "Pdf Saved", Toast.LENGTH_SHORT).show();
            }catch(Exception e){
                Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
            }



            if(file.exists()) {
                Intent whatsappintent = new Intent(Intent.ACTION_SEND);
                whatsappintent.setPackage("com.whatsapp");
                if (isAppInstalled(whatsappintent.getPackage())) {
                    Uri uri = Uri.fromFile(file);

                    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
                        uri = FileProvider.getUriForFile(this, BuildConfig.APPLICATION_ID+".provider", file);
                    }

                    whatsappintent.putExtra(Intent.EXTRA_STREAM, uri);
                    whatsappintent.setType("application/pdf");
                    startActivity(whatsappintent);
                    Toast.makeText(this, "message sent", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "WhatsApp is not installed", Toast.LENGTH_SHORT).show();
                }
            }else {
                Toast.makeText(this, "no such file", Toast.LENGTH_SHORT).show();
            }
        }
    }
}