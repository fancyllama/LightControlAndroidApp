package com.example.ykk.lightcontrol;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.content.Intent;

import org.json.JSONException;

public class MainActivity extends AppCompatActivity implements ResponseListener  {
    static HookRestClient hookClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //try to get token from shared preferences
        hookClient = HookRestClient.getInstance();
    }

    public void login(View view){
        System.out.println("attempting to login");
        EditText username = (EditText)findViewById(R.id.userNameField);
        EditText password = (EditText)findViewById(R.id.passwordField);

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
