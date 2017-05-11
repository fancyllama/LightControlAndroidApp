package com.example.ykk.lightcontrol;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.content.Intent;
import android.content.Context;
import android.content.SharedPreferences;

import org.json.JSONException;

public class MainActivity extends AppCompatActivity implements ResponseListener  {
    static HookRestClient hookClient;
    static SharedPreferences pref;
    static SharedPreferences.Editor edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.load_screen);

        //try to get token from shared preferences
        hookClient = HookRestClient.getInstance();
        pref = this.getSharedPreferences( "com.example.app", Context.MODE_PRIVATE);

        String uKey = "user";
        String pKey = "pass";
        String username = pref.getString(uKey, "null");
        String password = pref.getString(pKey,"null");
        if(username != "null" && password != "null"){
            System.out.println("Attempting to login with shared preferences!");
            try {
                hookClient.login(username, password, this);

            }catch(JSONException e){
                System.out.println("Failed to Login");
            }
        }else{
            setContentView(R.layout.activity_main);
        }
    }

    public void login(View view){
        System.out.println("attempting to login");
        EditText username = (EditText)findViewById(R.id.userNameField);
        EditText password = (EditText)findViewById(R.id.passwordField);

        edit = pref.edit();
        edit.putString("user", username.getText().toString());
        edit.putString("pass", password.getText().toString());
        edit.commit();

        try {
                hookClient.login(username.getText().toString(), password.getText().toString(),this);

        }catch(JSONException e){
            System.out.println("Failed to Login");
        }
    }
    public void loadListActivity(){
        Intent intent = new Intent(this, LightListActivity.class);
        startActivity(intent);
    }
    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }
}
