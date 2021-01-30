package com.example.maksudi.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.maksudi.R;

public class MainActivity extends AppCompatActivity{
    Button btn_logout;
    TextView txt_username;
    String username;
    SharedPreferences sharedpreferences;
    public static final String TAG_USERNAME = "username";
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txt_username =  findViewById(R.id.txt_username);
        btn_logout   =  findViewById(R.id.btn_logout);
        ImageView imageView3 = findViewById(R.id.imageView3);
        imageView3.setOnClickListener(view -> {
            Intent i = new Intent(this,ListBarang.class);
            startActivity(i);
        });

        sharedpreferences = getSharedPreferences(LoginActivity.my_shared_preferences, Context.MODE_PRIVATE);

        username = getIntent().getStringExtra(TAG_USERNAME);

        txt_username.setText("hai : "+""+ username);

        btn_logout.setOnClickListener(v -> {
            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.putBoolean(LoginActivity.session_status, false);
            editor.putString(TAG_USERNAME, null);
            editor.apply();

            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            finish();
            startActivity(intent);
        });

    }
}