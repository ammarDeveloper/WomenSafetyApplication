package com.applocstion.womensafetyapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.ContactsContract;

import androidx.annotation.Nullable;
import androidx.constraintlayout.solver.GoalRow;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class DataBaseHolder extends SQLiteOpenHelper {
    // Static variables of Required data
    public static final String PERSON_TABLE = "PERSON_TABLE";
    public static final String PERSON_ID = "PERSON_ID";
    public static final String PERSON_NAME = "PERSON_NAME";
    public static final String PHONE_NUMBERS = "PHONE_NUMBERS";
    // Constructor need to implement
    public DataBaseHolder(@Nullable Context context) {
        super(context, "person_id", null, 1);
    }

    // method which is called when the data is stored for the first time
    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableStatement = "CREATE TABLE " + PERSON_TABLE + " (" + PERSON_ID + "  INTEGER PRIMARY KEY AUTOINCREMENT, " + PERSON_NAME + " TEXT, " + PHONE_NUMBERS + " LIST)";
        db.execSQL(createTableStatement);
    }

    // Method which is called when the data is upgraded in future
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    // Adding the data
    public boolean addData(PersonsId person){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(PERSON_NAME, person.getName());
        Gson gson = new Gson();
        cv.put(PHONE_NUMBERS, gson.toJson(person.getPhone_numbers()));

        long insert = db.insert(PERSON_TABLE, null, cv);
        if(insert == -1){
            return false;
        }else{
            return true;
        }
    }

    // Getting the data
    public ArrayList<PersonsId> getData(){
        ArrayList<PersonsId> dataList = new ArrayList<>();
        String queryString = "SELECT * FROM PERSON_TABLE";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);
        if(cursor.moveToFirst()){
            do{
                int person_id = cursor.getInt(0);
                String person_name = cursor.getString(1);
                Gson gson = new Gson();
                Type type = new TypeToken<ArrayList<Phone_numbers>>(){}.getType();
                ArrayList<Phone_numbers> phone_numbers = gson.fromJson(cursor.getString(2), type);
                PersonsId personsId = new PersonsId(person_id, person_name, phone_numbers);
                dataList.add(personsId);
            }while (cursor.moveToNext());
        }
        return dataList;
    }

}