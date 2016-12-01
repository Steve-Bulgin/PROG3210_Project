package io.github.steve_bulgin.prog3210_finalproject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    //Variables
    SharedPreferences sharedpreferences;
    private static final String pref_file = "pref_file";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        Toolbar maintb = (Toolbar) findViewById(R.id.main_toolbar);
//        setSupportActionBar(maintb);

        sharedpreferences = getSharedPreferences(pref_file, Context.MODE_PRIVATE);

        if (sharedpreferences.getBoolean("cnn_news", true)) {
            Toast.makeText(getApplicationContext(), "CNN ON", Toast.LENGTH_SHORT).show();
        }
        else if (!sharedpreferences.getBoolean("cnn_news", false)) {
            Toast.makeText(getApplicationContext(), "CNN OFF", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_spillover_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_settings:
                startActivity(new Intent(getApplicationContext(), SettingsActivity.class));
                return true;
            case R.id.menu_about:
                startActivity(new Intent(getApplicationContext(), AboutActivity.class));
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
