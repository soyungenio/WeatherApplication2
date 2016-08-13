package com.example.marat.someapplication;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

/**
 * Created by Marat on 23.07.2016.
 */
public class SQL {

    FeedReaderDbHelper mDbHelper;
    FeedReaderContract.FeedEntry FE;
    ProgressDialog progDialog;

    public SQL(Context context,ProgressDialog progDialog){
        mDbHelper = new FeedReaderDbHelper(context);
        this.progDialog = progDialog;
    }

    public void writeDb(DataOfCity data){
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(FE.COLUMN_NAME_CITY, data.getName());
        values.put(FE.COLUMN_NAME_COUN, data.getCountry());
        values.put(FE.COLUMN_NAME_SKY, data.getSky());
        values.put(FE.COLUMN_NAME_TEMPERATURE, data.getTemp());
        values.put(FE.COLUMN_NAME_PRESSURE, data.getPress());
        values.put(FE.COLUMN_NAME_HUMIDITY, data.getHum());
        values.put(FE.COLUMN_NAME_WINDSPEED, data.getSpeed());
        values.put(FE.COLUMN_NAME_TIME, data.getTime());

        System.out.println("TAG we puts the values:"+data.getName());
        // Insert the new row,
        System.out.println("Tag: get id of row"+db.insert(
                FE.TABLE_NAME,
                null,
                values));
        db.close();
    }
    public void first(){
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(FE.COLUMN_NAME_CITY, "true");

        System.out.println("Tag: first starting "+db.insert(
                FE.TABLE_NAME,
                null,
                values));
        db.close();
    }
    public  void updateCity(DataOfCity data){
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        /*String query = "INSERT OR REPLACE INTO "+ FE.TABLE_NAME+" ("+FE.COLUMN_NAME_CITY+","+FE.COLUMN_NAME_COUN+"" +
                ","+FE.COLUMN_NAME_SKY+","+FE.COLUMN_NAME_TEMPERATURE+","+FE.COLUMN_NAME_PRESSURE+","+FE.COLUMN_NAME_HUMIDITY+","
                +FE.COLUMN_NAME_WINDSPEED+","+FE.COLUMN_NAME_TIME+" ) VALUES "+"('"+ data.getName() +"','" + data.getCountry()+"','" +
                data.getSky() +"','"+data.getTemp()+"','"+data.getPress()+"','"+data.getHum()+"','"+data.getSpeed()+"','"+data.getTime()+"')";
        db.execSQL(query, null);*/
        /*String query = "INSERT OR REPLACE INTO "+ FE.TABLE_NAME+" ("+FE.COLUMN_NAME_CITY+","+FE.COLUMN_NAME_COUN+"" +
        ","+FE.COLUMN_NAME_SKY+","+FE.COLUMN_NAME_TEMPERATURE+","+FE.COLUMN_NAME_PRESSURE+","+FE.COLUMN_NAME_HUMIDITY+","
                +FE.COLUMN_NAME_WINDSPEED+","+FE.COLUMN_NAME_TIME+" )"+" VALUES (?,?,?,?,?,?,?,?)";*/
        String query1 = "SELECT "+FE.COLUMN_NAME_CITY+" FROM "+ FE.TABLE_NAME+" WHERE "+FE.COLUMN_NAME_CITY+" = ?";
        Cursor cursor = db.rawQuery(query1,new String[]{data.getName()});

        if(cursor.getCount()==0){
            writeDb(data);
            System.out.println("Город для записи");
        }else {
            String query2 = "UPDATE " + FE.TABLE_NAME + " SET " + FE.COLUMN_NAME_COUN + "=?" + "," +
                    FE.COLUMN_NAME_SKY + "=?" + "," +
                    FE.COLUMN_NAME_TEMPERATURE + "=?" + "," +
                    FE.COLUMN_NAME_PRESSURE + "=?" + "," +
                    FE.COLUMN_NAME_HUMIDITY + "=?" + "," +
                    FE.COLUMN_NAME_WINDSPEED + "=?" + "," +
                    FE.COLUMN_NAME_TIME + "=? " +
                    "WHERE " + FE.COLUMN_NAME_CITY + "='" + data.getName() + "'";
            db.execSQL(query2,
                    new String[]{data.getCountry(), data.getSky(), data.getTemp(), data.getPress(), data.getHum(), data.getSpeed(), data.getTime()});
        }
        /*String query = "INSERT OR REPLACE INTO "+ FE.TABLE_NAME+" VALUES (?,?,?,?,?,?,?,?)";
        db.execSQL(query,
                new String[]{data.getName(),data.getCountry(),data.getSky(),data.getTemp(),data.getPress(),data.getHum(),data.getSpeed(),data.getTime()});*/
        db.close();
    }
    public void deleteCity(String city){
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        String query = "DELETE FROM "+ FE.TABLE_NAME+" WHERE "+FE.COLUMN_NAME_CITY+" = ?";
        db.execSQL(query, new String[]{city});
    }
    public String[][] readForList(){
        String selectQuery = "SELECT "+FE.COLUMN_NAME_CITY+","+FE.COLUMN_NAME_TEMPERATURE+" FROM " + FE.TABLE_NAME;
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        String[][] nameTemp = new String[2][cursor.getCount()-1];
        int count = 0;
        cursor.moveToFirst();
        if (cursor.moveToNext()) {
            do {
                nameTemp[0][count] = cursor.getString(0);
                nameTemp[1][count] = cursor.getString(1);
                count++;
            } while (cursor.moveToNext());
        }
        return nameTemp;
    }
    public ArrayList<DataOfCity> getAllContacts() {
        // Select All Query
        String selectQuery = "SELECT  * FROM " + FE.TABLE_NAME;

        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        ArrayList<DataOfCity> arrayOfCities = new ArrayList<DataOfCity>();
        ArrayList<String> data = new ArrayList<String>();
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                for(int i=1;i<10;i++)
                    data.add(cursor.getString(i));

                DataOfCity city = new DataOfCity();
                city.putCity(data);
                arrayOfCities.add(city);
            } while (cursor.moveToNext());
        }
        // return contact list
        return arrayOfCities;
    }
    public boolean checkFirstStarting(){
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT  * FROM "+FE.TABLE_NAME,null);
        System.out.println(cursor.getCount());
        boolean check;
        if(cursor.getCount()==0) {
            check=true;
        }else{
            check=false;
        }
        return check;
    }
    public Cursor getCityByName(String cityName){
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT  * FROM "+FE.TABLE_NAME+" where "+FE.COLUMN_NAME_CITY+" = ?",new String[]{cityName});
        cursor.moveToFirst();

        return cursor;
    }
   /* public String getCityById(int num) {
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT  * FROM "+FE.TABLE_NAME+" where "+FE._ID+" = ?",new String[]{String.valueOf(num)});
        cursor.moveToFirst();

        return cursor.getString(1);
    }*/
}
