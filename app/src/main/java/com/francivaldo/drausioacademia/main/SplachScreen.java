package com.francivaldo.drausioacademia.main;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.francivaldo.drausioacademia.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplachScreen extends AppCompatActivity {
    FirebaseUser currentuser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splach_screen);
        currentuser = FirebaseAuth.getInstance().getCurrentUser();
        mostrarLogin();
    }

    private void mostrarLogin() {
        Handler handle = new Handler();
        handle.postDelayed(new Runnable() {
            @Override public void run() {
                Intent intent;
                if(currentuser == null)
                    intent = new Intent(SplachScreen.this,Login.class);
                else {
                    intent = new Intent(SplachScreen.this, Main.class);
                }
                startActivity(intent);
                finish();
            }
        }, 2000);
    }
}