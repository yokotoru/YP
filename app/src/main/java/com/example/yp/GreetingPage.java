package com.example.yp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class GreetingPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_greeting_page);
    }
    public void Log(View v) {
        Intent intent = new Intent(GreetingPage.this, vhod.class);
        startActivity(intent);
    }
}