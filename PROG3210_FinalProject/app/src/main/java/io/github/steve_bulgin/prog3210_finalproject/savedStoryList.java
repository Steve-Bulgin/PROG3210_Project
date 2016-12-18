package io.github.steve_bulgin.prog3210_finalproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Context;
import android.content.Intent;
//<<<<<<< HEAD
import android.content.SharedPreferences;
//=======
import android.database.DatabaseUtils;
import android.os.AsyncTask;
import android.support.v7.app.ActionBar;
//>>>>>>> Newsreader_components
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class savedStoryList extends AppCompatActivity {

    SharedPreferences sharedpreferences;
    private static final String pref_file = "pref_file";
    private RSSFeed feed;
    private ArrayList<RSSFeed> feeds;
    private FileIO io;
    private ListView listViewNews;
    private HashMap items;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_story_list);
       newsReaderDB db = new newsReaderDB(this);


        listViewNews = (ListView) findViewById(R.id.listViewNews);

        io = new FileIO(getApplicationContext());

        listViewNews.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                items = (HashMap) listViewNews.getItemAtPosition(position);

                RSSItem item = feed.getItem(position);

                //Date parsing
                Date date = new Date();
                DateFormat outputFormat = new SimpleDateFormat("yyyy.MM.dd (HH:mm)");
                DateFormat inputFormat = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss Z");

                String inputText = feed.getPubDate();

                try {
                    date = inputFormat.parse(inputText);
                } catch (Exception e) {
                    Log.d("Date Parser", e.getMessage());
                }
                String dateOutput = outputFormat.format(date);

                Intent intent = new Intent(view.getContext(), ItemActivity.class);

                intent.putExtra("source", items.get("source").toString());
                intent.putExtra("pubdate", dateOutput);
                intent.putExtra("title", item.getTitle());
                intent.putExtra("description", item.getDescription());
                intent.putExtra("link", item.getLink());
                startActivity(intent);
            }
        });


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



    class ReadFeed extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            //return arraylist from readFile
            feeds = io.readFile();
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            Log.d("News reader", "Feed read");

            // update the display for the activity
            savedStoryList.this.updateDisplay();
        }
    }

    public void updateDisplay() {
        if (feeds == null) {
            Toast.makeText(getApplicationContext(), "Feed is null", Toast.LENGTH_SHORT).show();
        }
        newsReaderDB db = new newsReaderDB(this);

        ArrayList<HashMap<String, String>> data =
                new ArrayList<HashMap<String, String>>();

        for (int i = 0; i < feeds.size(); ++i) {
            feed = feeds.get(i);
            ArrayList<RSSItem> items = feed.getAllItems();

            //Date parsing
            Date date = new Date();
            DateFormat outputFormat = new SimpleDateFormat("yyyy.MM.dd (HH:mm)");
            DateFormat inputFormat = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss Z");

            String inputText = feed.getPubDate();


            try {
                date = inputFormat.parse(inputText);
            } catch (Exception e) {
                Log.d("Date Parser", e.getMessage());
            }
            String dateOutput = outputFormat.format(date);


            /*for (RSSItem item : items) {
                HashMap<String, String> map = new HashMap<String, String>();
                map.put("source", feed.getSource());
                map.put("date", dateOutput); //map.put("date", item.getPubDateFormatted());
                map.put("title", item.getTitle());
                data.add(map);
            }*/


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
