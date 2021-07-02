package com.applocstion.womensafetyapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.ContactsContract;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.constraintlayout.solver.GoalRow;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;

public class DataBaseHolder extends SQLiteOpenHelper {

    // Static variables of Required storing in persons id tabel
    public static final String PERSON_TABLE = "PERSON_TABLE";
    public static final String PERSON_ID = "PERSON_ID";
    public static final String PERSON_NAME = "PERSON_NAME";
    public static final String PHONE_NUMBERS = "PHONE_NUMBERS";


    // Static variables Required to store in locationsdate table
    private static final String LOCATIONS_TABLE = "LOCATIONS_TABLE";
    private static final String LOCATION_ID = "LOCATION_ID";
    private static final String STATE = "STATE";
    private static final String LOCATION = "LOCATION";
    private static final String DATE = "DATE";

    // Constructor need to implement
    public DataBaseHolder(@Nullable Context context) {
        super(context, "person_id", null, 1);
    }

    // method which is called when the data is stored for the first time
    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableStatement1 = "CREATE TABLE " + PERSON_TABLE + " (" + PERSON_ID + "  INTEGER PRIMARY KEY AUTOINCREMENT, " + PERSON_NAME + " TEXT, " + PHONE_NUMBERS + " LIST)";
        db.execSQL(createTableStatement1);

        String createTableStatement2 = "CREATE TABLE " + LOCATIONS_TABLE + " (" + LOCATION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + STATE + " TEXT, "+ LOCATION + " TEXT, " + DATE + " TEXT)";
        db.execSQL(createTableStatement2);
    }

    // Method which is called when the data is upgraded in future
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    // Adding the data of persons id
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

    // Getting the data of persons id
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

    // Updating the data of persons id
    public int updateData(PersonsId personsId){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(PERSON_NAME, personsId.getName());
        Gson gson = new Gson();
        cv.put(PHONE_NUMBERS, gson.toJson(personsId.getPhone_numbers()));
        int update = db.update(PERSON_TABLE, cv, PERSON_ID + "=?", new String[]{String.valueOf(personsId.getId())});
        return update;
    }

    // Deleting the data of persons id
    public boolean deleteData(PersonsId personsId){
        SQLiteDatabase db = this.getWritableDatabase();
        String queryString = "DELETE FROM "+PERSON_TABLE+" WHERE "+PERSON_ID+"="+personsId.getId();
        Cursor cursor = db.rawQuery(queryString, null);
        if(cursor.moveToNext()){
            return true;
        } else{
            return false;
        }
    }

    // Adding the data of users locations
    public boolean addUsersLocations(LocationDate locationDate){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(STATE, locationDate.getState());
        cv.put(LOCATION, locationDate.getLocation());
        cv.put(DATE, locationDate.getDatetime());

        long insert = db.insert(LOCATIONS_TABLE, null, cv);

        if (insert == -1){
            return false;
        } else {
            return true;
        }
    }

    // Updating the data of users locations
    public int updateUsersLocations(LocationDate locationDate){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(STATE, locationDate.getState());
        cv.put(LOCATION, locationDate.getLocation());
        cv.put(DATE, locationDate.getDatetime());

        int update = db.update(LOCATIONS_TABLE, cv, LOCATION_ID, new String[]{String.valueOf(locationDate.getLocation_id())});
        return update;

    }

    // getting the data of users Locations
    public ArrayList<LocationDate> getUsersLocations(){
        ArrayList<LocationDate> locationDateArrayList = new ArrayList<>();
        String queryString = "SELECT * FROM LOCATIONS_TABLE";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);
        if (cursor.moveToFirst()){
            do{
                int location_id = cursor.getInt(0);
                String state = cursor.getString(1);
                String location = cursor.getString(2);
                String datetime = cursor.getString(3);
                LocationDate locationDate = new LocationDate(location_id, state,  location, datetime);
                locationDateArrayList.add(locationDate);
            }while (cursor.moveToNext());
        }
        return locationDateArrayList;
    }

    // deleting the data of users locations
    public boolean deleteUsersLocations(LocationDate locationDate){
        SQLiteDatabase db = this.getWritableDatabase();
        String queryString = "DELETE FROM "+ LOCATIONS_TABLE +" WHERE "+LOCATION_ID+"="+locationDate.getLocation_id();
        Cursor cursor = db.rawQuery(queryString, null);
        if (cursor.moveToNext()){
            return true;
        }else {
            return false;
        }
    }
}
