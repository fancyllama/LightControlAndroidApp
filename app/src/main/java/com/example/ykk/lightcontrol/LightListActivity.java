package com.example.ykk.lightcontrol;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import java.util.ArrayList;


import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;
import android.view.View;


public class LightListActivity extends AppCompatActivity {

    private ListView mMainListView ;
    private DeviceListAdapter mDevAdapter;
    private HookRestClient HookClient = HookRestClient.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_light_controls_list);

        ArrayList<Device>devices = HookClient.getDevices();

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        mDevAdapter = new DeviceListAdapter(this, R.layout.device_list_item, devices);
        mMainListView = (ListView)findViewById(R.id.mainListView);
        mMainListView.setAdapter(mDevAdapter);

    }
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.action_bar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                // User chose the "Settings" item, show the app settings UI...
                return true;

            case R.id.action_logout:
                //wipe saved preferences go back to main activity
                SharedPreferences settings = getSharedPreferences("com.example.app",
                        Context.MODE_PRIVATE);

                SharedPreferences.Editor editor = settings.edit();
                editor.clear();
                editor.commit();
                Intent intent = new Intent(this, MainActivity.class);
                finish();
                startActivity(intent);
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    public void turnOnLight(View v){

        HookClient.triggerAction(Action.ON,(Device)v.getTag());
    }
    public void turnOffLight(View v){

        HookClient.triggerAction(Action.OFF,(Device)v.getTag());
    }


}

