package com.applocstion.womensafetyapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;

public class List_of_locations extends AppCompatActivity {

    private RecyclerView list_of_locations_rec_view;
    private List_of_locations_adapter list_of_locations_adapter;
    private ArrayList<LocationDate> locationDateArrayList;
    private RelativeLayout empty_rec_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_locations);

        initViews();

        // get the data from the data base and send it to the recycler view

        DataBaseHolder dataBaseHolder = new DataBaseHolder(this);
        locationDateArrayList = dataBaseHolder.getUsersLocations();

        list_of_locations_adapter = new List_of_locations_adapter(this);
        Collections.reverse(locationDateArrayList);
        list_of_locations_adapter.setLocationDateslists(locationDateArrayList);
        list_of_locations_rec_view.setAdapter(list_of_locations_adapter);

        if(locationDateArrayList.isEmpty()){
            list_of_locations_rec_view.setVisibility(View.GONE);
            empty_rec_view.setVisibility(View.VISIBLE);
        } else {
            list_of_locations_rec_view.setVisibility(View.VISIBLE);
            empty_rec_view.setVisibility(View.GONE);
        }

        list_of_locations_rec_view.setLayoutManager(new LinearLayoutManager(this));


    }
    public void initViews(){
        list_of_locations_rec_view = findViewById(R.id.list_of_locations_rec_view);
        empty_rec_view = findViewById(R.id.empty_rec_view);
    }
}