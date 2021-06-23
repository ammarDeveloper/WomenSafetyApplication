package com.applocstion.womensafetyapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

public class List_of_locations extends AppCompatActivity {

    private RecyclerView list_of_locations_rec_view;
    private List_of_locations_adapter list_of_locations_adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_locations);

        list_of_locations_rec_view = findViewById(R.id.list_of_locations_rec_view);
        list_of_locations_rec_view.setAdapter(list_of_locations_adapter);

        // TODO: get the data from the data base and send it to the recycler view

    }
}