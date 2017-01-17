package com.example.endrey.simplecrudmobileapplicationwithsqlite;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.pm.ConfigurationInfo;
import android.content.res.Configuration;
import android.database.SQLException;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText eventName;
    EditText eventOfficer;
    TextView eventListView;
    DB_Manager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.activity_main);

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        Configuration configInfo = getResources().getConfiguration();

        if(configInfo.orientation == Configuration.ORIENTATION_LANDSCAPE) {

            FragmentLandscape fragmentLandscape = new FragmentLandscape();

            fragmentTransaction.replace(android.R.id.content, fragmentLandscape);

        } else {

            FragmentPortrait fragmentPortrait= new FragmentPortrait();

            fragmentTransaction.replace(android.R.id.content, fragmentPortrait);


        }

        fragmentTransaction.commit();

        eventName = (EditText)findViewById(R.id.eventName);
        eventOfficer= (EditText) findViewById(R.id.eventOfficer);
        eventListView= (TextView) findViewById(R.id.eventListView);

        manager = new DB_Manager(this,"", null,1);


    }


    public void btn_click(View view) {
        switch(view.getId()){
            case R.id.addEvent:
                try{
                    manager.insert_Event(eventName.getText().toString(),eventOfficer.getText().toString());
                }catch (SQLException e){
                    Toast.makeText(MainActivity.this, "ALREADY EXIST", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.deleteEvent:
                manager.delete_Event(eventName.getText().toString());
                break;
            case R.id.updateEvent:
                AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                dialog.setTitle("ENTER NEW STATUS ");

                final EditText newStatus = new EditText(this);
                dialog.setView(newStatus);
                dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                            manager.update_Event(eventName.getText().toString(), newStatus.getText().toString());
                    }
                });

                dialog.show();
                break;
            case R.id.eventList:

                manager.list_AllEvents(eventListView);
                break;
        }
    }
}
