package io.github.steve_bulgin.prog3210_finalproject;

import android.content.Context;
import android.content.Intent;
//<<<<<<< HEAD
import android.content.SharedPreferences;
//=======
import android.os.AsyncTask;
import android.support.v7.app.ActionBar;
//>>>>>>> Newsreader_components
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    //Variables
    SharedPreferences sharedpreferences;
    private static final String pref_file = "pref_file";
    private RSSFeed feed;
    private FileIO io;
    private ListView listViewNews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //android.app.ActionBar actionBar = getActionBar();
        //actionBar.setDisplayShowHomeEnabled(true);
        //actionBar.setIcon(R.mipmap.ic_launcher);

        listViewNews = (ListView) findViewById(R.id.listViewNews);
        //lstNews.setOnClickListener(this);

        io = new FileIO(getApplicationContext());
        new DownloadFeed().execute();

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

    class DownloadFeed extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            io.downloadFile();
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            Log.d("News reader", "Feed downloaded");
            new ReadFeed().execute();
        }
    }

    class ReadFeed extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            feed = io.readFile();
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            Log.d("News reader", "Feed read");

            // update the display for the activity
            MainActivity.this.updateDisplay();
        }
    }

    public void updateDisplay() {
        if (feed == null) {
            Toast.makeText(getApplicationContext(), "Feed is null", Toast.LENGTH_SHORT).show();
        }

        ArrayList<RSSItem> items = feed.getAllItems();

        // create a List of Map<String, ?> objects
        ArrayList<HashMap<String, String>> data =
                new ArrayList<HashMap<String, String>>();
        for (RSSItem item : items) {
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("source", "CNN");
            map.put("date", "2011.01.11"); //map.put("date", item.getPubDateFormatted());
            map.put("title", item.getTitle());
            data.add(map);
        }

        // create the resource, from, and to variables
        int resource = R.layout.news_list;
        String[] from = {"source","date", "title"};
        int[] to = {R.id.lblSource, R.id.lblPubDate, R.id.lblHeadline};

        // create and set the adapter
        SimpleAdapter adapter =
                new SimpleAdapter(this, data, resource, from, to);
        listViewNews.setAdapter(adapter);

        Log.d("News reader", "Feed displayed");
    }
}


