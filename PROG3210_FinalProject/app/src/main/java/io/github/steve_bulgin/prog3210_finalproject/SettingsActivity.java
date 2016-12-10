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

        Switch bbc = (Switch) findViewById(R.id.switch_bbc);

        if (sharedpreferences.getBoolean("bbc_news", true)) {
            bbc.setChecked(true);
        }
        else if (sharedpreferences.getBoolean("bbc_news", false)) {
            bbc.setChecked(false);
        }

        bbc.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                SharedPreferences.Editor editor = sharedpreferences.edit();

                if (isChecked) {
                    editor.putBoolean("bbc_news", true);
                }
                else if (!isChecked) {
                    editor.putBoolean("bbc_news", false);
                }

                editor.commit();
            }
        });

        Switch cbs = (Switch) findViewById(R.id.switch_cbs);

        if (sharedpreferences.getBoolean("cbs_news", true)) {
            cbs.setChecked(true);
        }
        else if (sharedpreferences.getBoolean("cbs_news", false)) {
            cbs.setChecked(false);
        }

        cbs.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                SharedPreferences.Editor editor = sharedpreferences.edit();

                if (isChecked) {
                    editor.putBoolean("cbs_news", true);
                }
                else if (!isChecked) {
                    editor.putBoolean("cbs_news", false);
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
