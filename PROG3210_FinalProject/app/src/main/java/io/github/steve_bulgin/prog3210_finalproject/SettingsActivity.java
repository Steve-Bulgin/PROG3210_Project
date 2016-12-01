/* FileName: SettingsActivity.java
 * Purpose: Activity for the settings view
 * Revision History
 * 		Steven Bulgin, 2016.11.30: Created
 */

package io.github.steve_bulgin.prog3210_finalproject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

public class SettingsActivity extends AppCompatActivity {

    //Variables
    private static final String pref_file = "pref_file";

    SharedPreferences sharedpreferences;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_settings);

        sharedpreferences = getSharedPreferences(pref_file, Context.MODE_PRIVATE);

        Switch ctv = (Switch) findViewById(R.id.switch_ctv);

        if (sharedpreferences.getBoolean("ctv_news", true)) {
            ctv.setChecked(true);
        }
        else if (sharedpreferences.getBoolean("ctv_news", false)) {
            ctv.setChecked(false);
        }

        ctv.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                SharedPreferences.Editor editor = sharedpreferences.edit();

                if (isChecked) {
                    editor.putBoolean("ctv_news", true);
                }
                else if (!isChecked) {
                    editor.putBoolean("ctv_news", false);
                }

                editor.commit();
            }
        });

        Switch cbc = (Switch) findViewById(R.id.switch_cbc);

        if (sharedpreferences.getBoolean("cbc_news", true)) {
            cbc.setChecked(true);
        }
        else if (sharedpreferences.getBoolean("cbc_news", false)) {
            cbc.setChecked(false);
        }

        cbc.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                SharedPreferences.Editor editor = sharedpreferences.edit();

                if (isChecked) {
                    editor.putBoolean("cbc_news", true);
                }
                else if (!isChecked) {
                    editor.putBoolean("cbc_news", false);
                }

                editor.commit();
            }
        });

        Switch global = (Switch) findViewById(R.id.switch_global);

        if (sharedpreferences.getBoolean("global_news", true)) {
            global.setChecked(true);
        }
        else if (sharedpreferences.getBoolean("global_news", false)) {
            global.setChecked(false);
        }

        global.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                SharedPreferences.Editor editor = sharedpreferences.edit();

                if (isChecked) {
                    editor.putBoolean("global_news", true);
                }
                else if (!isChecked) {
                    editor.putBoolean("global_news", false);
                }

                editor.commit();
            }
        });

        Switch cnn = (Switch) findViewById(R.id.switch_cnn);

        if (sharedpreferences.getBoolean("cnn_news", true)) {
            cnn.setChecked(true);
        }
        else if (sharedpreferences.getBoolean("cnn_news", false)) {
            cnn.setChecked(false);
        }

        cnn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Toast.makeText(getApplicationContext(), isChecked ? "ON":"OFF", Toast.LENGTH_SHORT).show();

                SharedPreferences.Editor editor = sharedpreferences.edit();

                if (isChecked) {
                    editor.putBoolean("cnn_news", true);
                }
                else if (!isChecked) {
                    editor.putBoolean("cnn_news", false);
                }

                editor.commit();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.settings_spillover_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_main:
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                return true;
            case R.id.menu_about:
                startActivity(new Intent(getApplicationContext(), AboutActivity.class));
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
