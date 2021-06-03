package com.applocstion.womensafetyapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class MainActivity2 extends AppCompatActivity {

    public static String userNameIn2, num1In2, num2In2, num3In2;
    private String TAG;
    private Button btnRegister, addBtn;
    private EditText editUserName, editPhone1;
    private TextView textWarning1, textwarning2;
    private RecyclerView recyclerView;
    private NumRecAdapter adapter;
    private ArrayList<Phone_numbers> phone_numbers_list = new ArrayList<>();
    private ConstraintLayout constraintLayout;
    private PersonsId personsId;
    private DataBaseHolder dataBaseHolder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.SEND_SMS, Manifest.permission.INTERNET}, PackageManager.PERMISSION_GRANTED);



        initView();  // initializing the views in the function


        // Use to add the number in the list of numbers
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (phoneValidates()){
                    textwarning2.setVisibility(View.GONE);
                    Phone_numbers phone_numbers = new Phone_numbers(-1, editPhone1.getText().toString());
                    phone_numbers_list.add(phone_numbers);
//                PersonsId personsId = new PersonsId(phone_numbers.getId(), editUserName.getText().toString(), phone_numbers);
                    adapter.setPhoneNum(phone_numbers_list);
                    recyclerView.setAdapter(adapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity2.this));
                }else if(emptyPhone()){
                    textwarning2.setText("Section Empty");
                    textwarning2.setVisibility(View.VISIBLE);
                }else {
                    textwarning2.setText("Invalid Number");
                    textwarning2.setVisibility(View.VISIBLE);
                }

            }
        });

        // Registration button
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initRegister();
            }
        });


    }

    // initializing the view elements of our activity
    private void initView() {
        Log.d(TAG, "initView: started");
        editUserName = findViewById(R.id.editName1);
        editPhone1 = findViewById(R.id.editNum1);
        textWarning1 = findViewById(R.id.waringText1);
        textwarning2 = findViewById(R.id.warningText2);
        btnRegister = findViewById(R.id.button);
        addBtn = findViewById(R.id.addPhoneBtn);
        recyclerView = findViewById(R.id.phoneRecView);
        adapter = new NumRecAdapter(MainActivity2.this);
        constraintLayout = findViewById(R.id.parent);
    }

    // initializing the Registration
    private void initRegister() {
        Log.d(TAG, "initRegister: started");

        if (validatesData()) {
            textWarning1.setVisibility(View.GONE);
            personsId = new PersonsId(-1, editUserName.getText().toString(), phone_numbers_list);
            dataBaseHolder = new DataBaseHolder(this);
            boolean b = dataBaseHolder.addData(personsId);
            if (b){
                Intent intent = new Intent(this, MapsActivity.class);
                startActivity(intent);
            }
        }
    }

    // Checks for the name and the atleast 3 numbers
    private boolean validatesData() {
        Log.d(TAG, "validatesData: started");
        int count;
        count = 0;
        if (editUserName.getText().toString().equals("")) {
            textWarning1.setVisibility(View.VISIBLE);
            textWarning1.setText("Enter Your Name");
            count = count + 1;
        }else if(adapter.getPhoneNum().size() < 3){
            Snackbar snackbar = Snackbar
                    .make(constraintLayout, "Required Aleast 3 Phone Numbers", Snackbar.LENGTH_LONG)
                    .setAction("Got it", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                        }
                    });
            textWarning1.setVisibility(View.GONE);
            snackbar.setActionTextColor(Color.YELLOW);
            snackbar.show();
            count = count + 1;
        }

        if (count == 2){
            return false;
        }
        return true;
    }

    // Checks if the Phone number slot is empty
    public boolean emptyPhone(){
        Log.d(TAG, "emptyPhone: Started");
        if (editPhone1.getText().toString().equals("")){
            return true;
        }
        return false;
    }

    // Checks if the phone number is valid
    public boolean phoneValidates(){
        Log.d(TAG, "phoneValidates: Started");
        if (editPhone1.getText().toString().length() != 10){
            return false;
        }
        return true;
    }

}