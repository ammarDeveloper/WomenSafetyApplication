package com.applocstion.womensafetyapplication;

import android.content.Context;
import android.provider.Settings;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
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
        holder.list_of_locations_address.setText(locationDateslists.get(position).getLocation());
        holder.list_of_locations_date_time.setText(locationDateslists.get(position).getDatetime());

//        holder.card_of_saved_locations.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                return false;
//            }
//        });

        final long[] lastclicktime = {0};
        holder.card_of_saved_locations.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long clicketime = System.currentTimeMillis();
                if (clicketime - lastclicktime[0] < 300){
                    locationDateslists.remove(position);
                    setLocationDateslists(locationDateslists);
                    lastclicktime[0] = 0;
                    notifyDataSetChanged();
                } else {
                    Toast.makeText(mContext, "Double Tap To Remove", Toast.LENGTH_SHORT).show();
                }
                lastclicktime[0] = clicketime;
            }
        });
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
        private CardView card_of_saved_locations;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            card_of_saved_locations = itemView.findViewById(R.id.card_of_saved_locations);
            list_of_locations_address = itemView.findViewById(R.id.list_of_locations_address);
            list_of_locations_date_time = itemView.findViewById(R.id.list_of_locations_date_time);
        }
    }
}
