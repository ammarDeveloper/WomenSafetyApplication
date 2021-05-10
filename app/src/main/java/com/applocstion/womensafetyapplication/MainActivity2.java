package com.applocstion.womensafetyapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class MainActivity2 extends AppCompatActivity {

    public static final String FILE_NAME1 = "username.txt";
    public static final String FILE_NAME2 = "phone1.txt";
    public static final String FILE_NAME3 = "phone2.txt";
    public static final String FILE_NAME4 = "phone3.txt";
    private String TAG;
    private Button btnRegister;

    public static String userNameIn2, num1In2, num2In2, num3In2;

    private EditText editUserName, editPhone1, editPhone2, editPhone3;
    private TextView textWarning1, textwarning2, textWaring3, textWarning4;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.SEND_SMS, Manifest.permission.INTERNET}, PackageManager.PERMISSION_GRANTED);
        initView();

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initRegister();
            }
        });


    }

    private void initView() {
        Log.d(TAG, "initView: started");
        editUserName = findViewById(R.id.editName1);
        editPhone1 = findViewById(R.id.editNum1);
        editPhone2 = findViewById(R.id.editNum2);
        editPhone3 = findViewById(R.id.editNum3);
        textWaring3 = findViewById(R.id.waringText1);
        textWaring3 = findViewById(R.id.warningText2);
        textWaring3 = findViewById(R.id.warningTExt3);
        textWaring3 = findViewById(R.id.warningText4);
        btnRegister = findViewById(R.id.button);
    }

    private void initRegister() {
        Log.d(TAG, "initRegister: started");

        if (validatesData()) {
            if (phoneValidates()){
                String textUserName = editUserName.getText().toString();
                String textPhoneNum1 = editPhone1.getText().toString();
                String textPhoneNum2 = editPhone2.getText().toString();
                String textPhoneNum3 = editPhone3.getText().toString();

                FileOutputStream fosUserName = null;
                FileOutputStream fosPhone1 = null;
                FileOutputStream fosPhone2 = null;
                FileOutputStream fosPhone3 = null;

                try {
                    fosUserName = openFileOutput(FILE_NAME1, MODE_PRIVATE);
                    fosPhone1 = openFileOutput(FILE_NAME2, MODE_PRIVATE);
                    fosPhone2 = openFileOutput(FILE_NAME3, MODE_PRIVATE);
                    fosPhone3 = openFileOutput(FILE_NAME4, MODE_PRIVATE);
                    fosUserName.write(textUserName.getBytes());
                    fosPhone1.write(textPhoneNum1.getBytes());
                    fosPhone2.write(textPhoneNum2.getBytes());
                    fosPhone3.write(textPhoneNum3.getBytes());

                    editUserName.getText().clear();
                    editPhone1.getText().clear();
                    editPhone2.getText().clear();
                    editPhone3.getText().clear();


                    FileInputStream userNameFileI = null;
                    FileInputStream num1fileI = null;
                    FileInputStream num2fileI = null;
                    FileInputStream num3fileI = null;

                    userNameFileI = openFileInput(FILE_NAME1);
                    num1fileI = openFileInput(FILE_NAME2);
                    num2fileI = openFileInput(FILE_NAME3);
                    num3fileI = openFileInput(FILE_NAME4);

                    BufferedReader br1 = new BufferedReader(new InputStreamReader(userNameFileI));
                    BufferedReader br2 = new BufferedReader(new InputStreamReader(num1fileI));
                    BufferedReader br3 = new BufferedReader(new InputStreamReader(num2fileI));
                    BufferedReader br4 = new BufferedReader(new InputStreamReader(num3fileI));
                    StringBuilder sb1 = new StringBuilder();
                    StringBuilder sb2 = new StringBuilder();
                    StringBuilder sb3 = new StringBuilder();
                    StringBuilder sb4 = new StringBuilder();

                    String textName, textNum1, textNum2, textNum3;
                    while ((textName = br1.readLine()) != null) {
                        sb1.append(textName).append("");
                    }
                    while ((textNum1 = br2.readLine()) != null) {
                        sb2.append(textNum1).append("");
                    }
                    while ((textNum2 = br3.readLine()) != null) {
                        sb3.append(textNum2).append("");
                    }
                    while ((textNum3 = br4.readLine()) != null) {
                        sb4.append(textNum3).append("");
                    }

                    userNameIn2 = sb1.toString();
                    num1In2 = sb2.toString();
                    num2In2 = sb3.toString();
                    num3In2 = sb4.toString();


                    Intent intent = new Intent(MainActivity2.this, MapsActivity.class);
                    startActivity(intent);
                    finish();

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if ((fosUserName != null) || (fosPhone1 != null) || (fosPhone2 != null) || (fosPhone3 != null)) {
                        try {
                            fosUserName.close();
                            fosPhone1.close();
                            fosPhone2.close();
                            fosPhone3.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }else{
                Toast.makeText(this, "Invalid Number", Toast.LENGTH_SHORT).show();
            }


//            String textUserName = editUserName.getText().toString();
//            String textPhoneNum1 = editPhone1.getText().toString();
//            String textPhoneNum2 = editPhone2.getText().toString();
//            String textPhoneNum3 = editPhone3.getText().toString();
//
//            FileOutputStream fosUserName = null;
//            FileOutputStream fosPhone1 = null;
//            FileOutputStream fosPhone2 = null;
//            FileOutputStream fosPhone3 = null;
//
//            try {
//                fosUserName = openFileOutput(FILE_NAME1, MODE_PRIVATE);
//                fosPhone1 = openFileOutput(FILE_NAME2, MODE_PRIVATE);
//                fosPhone2 = openFileOutput(FILE_NAME3, MODE_PRIVATE);
//                fosPhone3 = openFileOutput(FILE_NAME4, MODE_PRIVATE);
//                fosUserName.write(textUserName.getBytes());
//                fosPhone1.write(textPhoneNum1.getBytes());
//                fosPhone2.write(textPhoneNum2.getBytes());
//                fosPhone3.write(textPhoneNum3.getBytes());
//
//                editUserName.getText().clear();
//                editPhone1.getText().clear();
//                editPhone2.getText().clear();
//                editPhone3.getText().clear();
//
//
//                FileInputStream userNameFileI = null;
//                FileInputStream num1fileI = null;
//                FileInputStream num2fileI = null;
//                FileInputStream num3fileI = null;
//
//                userNameFileI = openFileInput(FILE_NAME1);
//                num1fileI = openFileInput(FILE_NAME2);
//                num2fileI = openFileInput(FILE_NAME3);
//                num3fileI = openFileInput(FILE_NAME4);
//
//                BufferedReader br1 = new BufferedReader(new InputStreamReader(userNameFileI));
//                BufferedReader br2 = new BufferedReader(new InputStreamReader(num1fileI));
//                BufferedReader br3 = new BufferedReader(new InputStreamReader(num2fileI));
//                BufferedReader br4 = new BufferedReader(new InputStreamReader(num3fileI));
//                StringBuilder sb1 = new StringBuilder();
//                StringBuilder sb2 = new StringBuilder();
//                StringBuilder sb3 = new StringBuilder();
//                StringBuilder sb4 = new StringBuilder();
//
//                String textName, textNum1, textNum2, textNum3;
//                while ((textName = br1.readLine()) != null) {
//                    sb1.append(textName).append("");
//                }
//                while ((textNum1 = br2.readLine()) != null) {
//                    sb2.append(textNum1).append("");
//                }
//                while ((textNum2 = br3.readLine()) != null) {
//                    sb3.append(textNum2).append("");
//                }
//                while ((textNum3 = br4.readLine()) != null) {
//                    sb4.append(textNum3).append("");
//                }
//
//                userNameIn2 = sb1.toString();
//                num1In2 = sb2.toString();
//                num2In2 = sb3.toString();
//                num3In2 = sb4.toString();
//
//
//                Intent intent = new Intent(MainActivity2.this, MapsActivity.class);
//                startActivity(intent);
//                finish();
//
//            } catch (FileNotFoundException e) {
//                e.printStackTrace();
//            } catch (IOException e) {
//                e.printStackTrace();
//            } finally {
//                if ((fosUserName != null) || (fosPhone1 != null) || (fosPhone2 != null) || (fosPhone3 != null)) {
//                    try {
//                        fosUserName.close();
//                        fosPhone1.close();
//                        fosPhone2.close();
//                        fosPhone3.close();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }

        } else {
            Toast.makeText(this, "Enter The Required Data", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean validatesData() {
        Log.d(TAG, "validatesData: started");
        if (editUserName.getText().toString().equals("")) {
            return false;
        }

        if (editPhone1.getText().toString().equals("")) {
            return false;
        }

        if (editPhone2.getText().toString().equals("")) {
            return false;
        }

        if (editPhone3.getText().toString().equals("")) {
            return false;
        }
        return true;
    }

    public boolean phoneValidates(){
        if (editPhone1.getText().toString().length() != 10 || editPhone2.getText().toString().length() != 10 || editPhone3.getText().toString().length() != 10){
            return false;
        }

        return true;
    }

}