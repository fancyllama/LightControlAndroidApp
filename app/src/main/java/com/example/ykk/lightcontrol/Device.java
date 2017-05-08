package com.example.ykk.lightcontrol;

import java.io.Serializable;

/**
 * Created by ykk on 2017-05-05.
 */

public class Device implements Serializable{
    private String mDeviceId;
    private String mDeviceName;

    public Device(String name, String id){
        mDeviceName = name;
        mDeviceId = id;
    }
    public String getName(){
        return mDeviceName;

    }
    public String getId(){
        return mDeviceId;

    }
}


