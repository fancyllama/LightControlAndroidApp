package com.example.ykk.lightcontrol;

/**
 * Created by ykk on 2017-05-05.
 */
import org.json.*;
import com.loopj.android.http.*;
import org.json.JSONObject;
import cz.msebera.android.httpclient.Header;
import java.util.*;

enum Action{
    ON, OFF
}

public final class HookRestClient {

    private static final HookRestClient INSTANCE = new HookRestClient();

    private String mToken;
    private ArrayList<Device> mDeviceList;
    private Boolean mHasDevice;
    private Boolean mHasToken;

    private HookRestClient(){
        mHasDevice = false;
        mHasToken = false;
    }

    public static HookRestClient getInstance() {
        return INSTANCE;
    }

    public void login(String userName, String passWord, final ResponseListener listener) throws JSONException {
        HashMap<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("username", userName);
        paramMap.put("password", passWord);
        RequestParams params = new RequestParams(paramMap);
        HookRestApi.post("user/login/", params, new JsonHttpResponseHandler() {

            public void onSuccess(int statusCode, Header[] headers, JSONObject response){
                // If the response is JSONObject instead of expected JSONArray
                try {
                    String token = response.getJSONObject("data").getString("token");
                    //System.out.println("token was " + token);

                    mToken = token;
                    mHasToken = true;

                    _getDevices(listener);

                }catch(JSONException e){
                    System.out.println("LOGIN ERROR! " + e.getMessage());
                }
            }
            public void onFailure(int statusCode, Header[] headers, JSONObject errorResponse)
            {
                System.out.println("LOGIN FAILURE! ");
            }
        });

    }

    public void triggerAction(Action action, Device device){
        HookRestApi.get("device/trigger/"+device.getId()+"/"+ action.toString() + "/?token=" + mToken,
                        null,
                        new JsonHttpResponseHandler(){
            public void onSuccess(int statusCode, Header[] headers, JSONObject response){
                //TODO: show message that light was successfully turned on
            }
            public void onFailure(int statusCode, Header[] headers, JSONObject response, Action action, Device device){
                //System.out.println("failed to turn " + action.toString() + device.getName());
                //TODO: show message that light was not turned on
            }

        });
    }

    public ArrayList<Device> getDevices() {
        return mDeviceList;
    }

    private void _getDevices(final ResponseListener listener) {
        HookRestApi.get("device/?token=" + mToken, null, new JsonHttpResponseHandler(){

            public void onSuccess(int statusCode, Header[] headers, JSONObject response){
                // If the response is JSONObject instead of expected JSONArray
                //System.out.println("Getting Devices");
                try {
                    mDeviceList = new ArrayList<Device>();
                    JSONArray devices = response.getJSONArray("data").getJSONArray(0);

                    for(int i=0; i<devices.length(); i++)
                    {
                        JSONObject device = devices.getJSONObject(i);
                        String name = device.getString("device_name");
                        String id = device.getString("device_id");
                        mDeviceList.add(new Device(name,id));
                    }
                    mHasDevice = true;
                }catch(JSONException e){
                    System.out.println("GETTING DEVICES FAILED! " + e.getMessage());
                }

                listener.loadListActivity();
            }
            public boolean onFailure(int statusCode, Header[] headers, JSONObject errorResponse)
            {
                System.out.println("GET DEVICE FAILURE! ");
                return false;
            }
        });
    }

}
