package com.applocstion.womensafetyapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Timer;
import java.util.TimerTask;


public class MainActivity extends AppCompatActivity {

    public static String userName = "";
    public static String num1 = "";
    public static String num2 = "";
    public static String num4 = "";

    private Timer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        timer = new Timer();

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                FileInputStream userNameFileI = null;
                FileInputStream num1fileI = null;
                FileInputStream num2fileI = null;
                FileInputStream num3fileI = null;
                try {
                    userNameFileI = openFileInput(MainActivity2.FILE_NAME1);
                    num1fileI = openFileInput(MainActivity2.FILE_NAME2);
                    num2fileI = openFileInput(MainActivity2.FILE_NAME3);
                    num3fileI = openFileInput(MainActivity2.FILE_NAME4);
                    BufferedReader br1 = new BufferedReader(new InputStreamReader(userNameFileI));
                    BufferedReader br2 = new BufferedReader(new InputStreamReader(num1fileI));
                    BufferedReader br3 = new BufferedReader(new InputStreamReader(num2fileI));
                    BufferedReader br4 = new BufferedReader(new InputStreamReader(num3fileI));
                    StringBuilder sb1 = new StringBuilder();
                    StringBuilder sb2 = new StringBuilder();
                    StringBuilder sb3 = new StringBuilder();
                    StringBuilder sb4 = new StringBuilder();

                    String textName, textNum1, textNum2, textNum3;
                    while((textName = br1.readLine()) != null){
                        sb1.append(textName).append("");
                    }
                    while((textNum1 = br2.readLine()) != null){
                        sb2.append(textNum1).append("");
                    }
                    while((textNum2 = br3.readLine()) != null){
                        sb3.append(textNum2).append("");
                    }
                    while((textNum3 = br4.readLine()) != null){
                        sb4.append(textNum3).append("");
                    }
                    userName = sb1.toString();
                    num1 = sb2.toString();
                    num2 = sb3.toString();
                    num4 = sb4.toString();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }finally {
                    if(userNameFileI != null){
                        try {
                            userNameFileI.close();
                            num1fileI.close();
                            num2fileI.close();
                            num3fileI.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                if ((!userName.equals("")) ||(!num1.equals("")) || (!num2.equals("") || (!num4.equals("")))){
                    jumpTo3();
                }else{
                    jumpTo2();
                }
                finish();
            }
        }, 1000);
    }
    public void jumpTo2(){
        Intent intent = new Intent(MainActivity.this, MainActivity2.class);
        startActivity(intent);
    }

    public void jumpTo3(){
        Intent intent = new Intent(MainActivity.this, MapsActivity.class);
        startActivity(intent);
    }
}