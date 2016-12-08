package io.github.steve_bulgin.prog3210_finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

public class ItemActivity extends AppCompatActivity {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_item);

        Intent intent = getIntent();

        Toast.makeText(getApplicationContext(), intent.getStringExtra("title"), Toast.LENGTH_SHORT).show();
    }

}
