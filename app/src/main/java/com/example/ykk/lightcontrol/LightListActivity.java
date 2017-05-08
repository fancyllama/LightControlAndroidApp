package com.example.ykk.lightcontrol;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import java.util.ArrayList;


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

        mDevAdapter = new DeviceListAdapter(this, R.layout.device_list_item, devices);
        mMainListView = (ListView)findViewById(R.id.mainListView);
        mMainListView.setAdapter(mDevAdapter);

    }

    public void turnOnLight(View v){

        HookClient.triggerAction(Action.ON,(Device)v.getTag());
    }
    public void turnOffLight(View v){

        HookClient.triggerAction(Action.OFF,(Device)v.getTag());
    }
}

