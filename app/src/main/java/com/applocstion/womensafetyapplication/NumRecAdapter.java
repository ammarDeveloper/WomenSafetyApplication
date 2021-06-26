package com.applocstion.womensafetyapplication;

import android.animation.Animator;
import android.content.Context;
import android.content.DialogInterface;
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
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
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
        holder.text1.setText(phoneNum.get(position).getPhone_number().toString());

        holder.deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setMessage("Are You Sure?\nYou Want To Delete "+phoneNum.get(position).getPhone_number());
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        phoneNum.remove(phoneNum.get(position));
                        setPhoneNum(phoneNum);
                        notifyDataSetChanged();
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
        });

        holder.saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(holder.phoneValidates()){
                    holder.errorText.setVisibility(View.GONE);
                    AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                    builder.setMessage("Change "+ phoneNum.get(position).getPhone_number()+ " To "+ holder.editText.getText().toString()+" ?");
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            int id = phoneNum.get(position).getId();
                            phoneNum.set(position, new Phone_numbers(id, holder.editText.getText().toString()));
                            setPhoneNum(phoneNum);
                            holder.editText.getText().clear();
                            notifyDataSetChanged();
                        }

                    });
                    builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    builder.create();
                    builder.show();

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
            holder.relForNumAndEditBtn.setVisibility(View.GONE);
        }else {
            holder.expandedRelativeLayout.setVisibility(View.GONE);
            holder.relForNumAndEditBtn.setVisibility(View.VISIBLE);
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
        private CardView editBtn, deleteBtn, saveBtn;
        private EditText editText;
        private RelativeLayout expandedRelativeLayout;
        private RelativeLayout relForNumAndEditBtn;
        private CardView editNumCancelBtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            initViews();


            // click listerner for editBtn
            editBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    for (int i=0; i<phoneNum.size(); i++){
                        if (i == getAdapterPosition()){
                            phoneNum.get(i).setExpanded(true);
                            editText.setText(phoneNum.get(i).getPhone_number());
                        }else {
                            phoneNum.get(i).setExpanded(false);
                        }
                    }
                    notifyDataSetChanged();
                }
            });

            // Click listener for cancel editing section
            editNumCancelBtn.setOnClickListener(new View.OnClickListener() {
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

        // initializing the views
        public void initViews(){
            text1 = itemView.findViewById(R.id.numtext);
            editBtn = itemView.findViewById(R.id.editBtn);
            deleteBtn = itemView.findViewById(R.id.deleteBtn);
            editText = itemView.findViewById(R.id.editedText);
            saveBtn = itemView.findViewById(R.id.saveExpaned);
            expandedRelativeLayout = itemView.findViewById(R.id.expandR);
            errorText = itemView.findViewById(R.id.errorText);
            relForNumAndEditBtn = itemView.findViewById(R.id.relForNumAndEditBtn);
            editNumCancelBtn = itemView.findViewById(R.id.editNumCancelBtn);
        }
    }


}
