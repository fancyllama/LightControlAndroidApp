package com.example.ykk.lightcontrol;

import android.widget.*;
import java.util.*;
import android.content.Context;
import android.view.*;
import android.app.Activity;
import android.widget.ImageButton;

/**
 * Created by ykk on 2017-05-08.
 */

public class DeviceListAdapter extends ArrayAdapter<Device> {

    private List <Device> mDevices;
    private int mLayoutResourceId;
    private Context mContext;

    public DeviceListAdapter(Context context, int layoutResourceId, List<Device> devices) {
        super(context, layoutResourceId, devices);
        this.mLayoutResourceId = layoutResourceId;
        this.mContext = context;
        this.mDevices = devices;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        DeviceHolder holder = null;

        LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
        row = inflater.inflate(mLayoutResourceId, parent, false);

        holder = new DeviceHolder();
        holder.Device = mDevices.get(position);

        holder.onButton = (Button)row.findViewById(R.id.on_button);
        holder.onButton.setTag(holder.Device);

        holder.offButton = (Button)row.findViewById(R.id.off_button);
        holder.offButton.setTag(holder.Device);

        holder.name = (TextView)row.findViewById(R.id.device_name);

        row.setTag(holder);

        setupItem(holder);
        return row;
    }

    private void setupItem(DeviceHolder holder) {
        holder.name.setText(holder.Device.getName());
    }
    public static class DeviceHolder {
        Device Device;
        TextView name;
        Button onButton;
        Button offButton;
    }
}

