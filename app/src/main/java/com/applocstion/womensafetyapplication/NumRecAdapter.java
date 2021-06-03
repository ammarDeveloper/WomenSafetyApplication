package com.applocstion.womensafetyapplication;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class NumRecAdapter extends RecyclerView.Adapter<NumRecAdapter.ViewHolder> {

    private ArrayList<Phone_numbers> phoneNum = new ArrayList<>();
    private Context mContext;

    // Constructor
    public NumRecAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.design_for_numbers, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.text1.setText(phoneNum.get(position).getPhone_numbers().toString());

        holder.deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phoneNum.remove(phoneNum.get(position));
                setPhoneNum(phoneNum);
                notifyDataSetChanged();
            }
        });

        holder.saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(holder.phoneValidates()){
                    holder.errorText.setVisibility(View.GONE);
                    int id = phoneNum.get(position).getId();
                    phoneNum.set(position, new Phone_numbers(id, holder.editText.getText().toString()));
                    setPhoneNum(phoneNum);
                    notifyDataSetChanged();
                }else if(holder.emptyPhone()){
                    holder.errorText.setText("Section Empty");
                    holder.errorText.setVisibility(View.VISIBLE);
                }else{
                    holder.errorText.setText("Invalid Number");
                    holder.errorText.setVisibility(View.VISIBLE);
                }

            }
        });

        if (phoneNum.get(position).isExpanded()){
            holder.expandedRelativeLayout.setVisibility(View.VISIBLE);
        }else {
            holder.expandedRelativeLayout.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return phoneNum.size();
    }

    // Getters and Setters for the Phone number arraylists
    public ArrayList<Phone_numbers> getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(ArrayList<Phone_numbers> phoneNum) {
        this.phoneNum = phoneNum;
        notifyDataSetChanged();
    }

    // ViewHolder class
    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView text1, errorText;
        private ImageView editBtn, deleteBtn, saveBtn;
        private EditText editText;
        private RelativeLayout expandedRelativeLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            text1 = itemView.findViewById(R.id.numtext);
            editBtn = itemView.findViewById(R.id.editBtn);
            deleteBtn = itemView.findViewById(R.id.deleteBtn);
            editText = itemView.findViewById(R.id.editedText);
            saveBtn = itemView.findViewById(R.id.saveExpaned);
            expandedRelativeLayout = itemView.findViewById(R.id.expandR);
            errorText = itemView.findViewById(R.id.errorText);

            editBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    phoneNum.get(getAdapterPosition()).setExpanded(!phoneNum.get(getAdapterPosition()).isExpanded());
                    notifyDataSetChanged();
                }
            });

        }

        // Checks if the Phone number slot is empty
        public boolean emptyPhone(){
            if (editText.getText().toString().equals("")){
                return true;
            }
            return false;
        }

        // Checks if the phone number is valid
        public boolean phoneValidates(){
            if (editText.getText().toString().length() != 10){
                return false;
            }
            return true;
        }
    }
}
