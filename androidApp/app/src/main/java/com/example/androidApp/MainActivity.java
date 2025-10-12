package com.example.androidApp;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.androidApp.view.AuthFragment;
import com.example.androidapp.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);


        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        String api_key = sharedPref.getString("api_key", "");

        if (api_key.isEmpty()) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.auth_fragment, AuthFragment.class, null)
                    .commit();
        } else {

        }
    }

}