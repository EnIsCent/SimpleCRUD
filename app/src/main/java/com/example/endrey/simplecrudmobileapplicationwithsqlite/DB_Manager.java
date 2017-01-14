package com.example.endrey.simplecrudmobileapplicationwithsqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * Created by Endrey on 1/14/2017.
 */

public class DB_Manager extends SQLiteOpenHelper{

    public DB_Manager(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, "crud.db", factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("Create TABLE EVENTS(ID INTEGER PRIMARY KEY AUTOINCREMENT, EVENTNAME TEXT UNIQUE , OFFICER TEXT, STATUS TEXT);");


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS EVENTS;");
        onCreate(db);
    }

    public void insert_Event(String name, String officer){
        ContentValues contentValues= new ContentValues();
        contentValues.put("EVENTNAME", name);
        contentValues.put("OFFICER", officer);
        contentValues.put("STATUS", "On Review");
        this.getWritableDatabase().insertOrThrow("EVENTS","", contentValues);

    }

    public void delete_Event(String name){
        this.getWritableDatabase().delete("EVENTS","EVENTNAME = '"+ name+"'", null);

    }

    public void update_Event(String name, String status){
        this.getWritableDatabase().execSQL("UPDATE EVENTS SET STATUS= '"+ status+"' WHERE EVENTNAME= '"+ name+"'" );

    }

    public void list_AllEvents(TextView textView){
        Cursor cursor= this.getReadableDatabase().rawQuery("SELECT * FROM EVENTS", null);
        textView.setText("");
        while(cursor.moveToNext()){
            textView.append("\nEvent: "+cursor.getString(1)+ " \nOfficer-in-Charge: "+ cursor.getString(2)+ " \nStatus: "+ cursor.getString(3) +"\n");

        }

    }
}
