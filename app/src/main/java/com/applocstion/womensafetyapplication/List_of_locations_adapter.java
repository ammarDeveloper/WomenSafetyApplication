package com.applocstion.womensafetyapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class List_of_locations_adapter extends RecyclerView.Adapter<List_of_locations_adapter.ViewHolder>{

    private Context mContext;
    private ArrayList<LocationDate> locationDateslists;

    // Constructor
    public List_of_locations_adapter(Context mContext) {
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.design_for_list_of_locations, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.list_of_locations_address.setText(locationDateslists.get(position).getLocation().toString());
        holder.list_of_locations_date_time.setText(locationDateslists.get(position).getDate().toString());
        // TODO: get appropreate data and show it to the user
    }

    @Override
    public int getItemCount() {
        return locationDateslists.size();
    }

    // getters for the arraylist of LocationDate
    public ArrayList<LocationDate> getLocationDateslists() {
        return locationDateslists;
    }

    // setters for the arraylist of LocationDate
    public void setLocationDateslists(ArrayList<LocationDate> locationDateslists) {
        this.locationDateslists = locationDateslists;
    }

    // view holder class
    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView list_of_locations_address, list_of_locations_date_time;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            list_of_locations_address = itemView.findViewById(R.id.list_of_locations_address);
            list_of_locations_date_time = itemView.findViewById(R.id.list_of_locations_date_time);
        }
    }
}
