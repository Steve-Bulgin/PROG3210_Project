package io.github.steve_bulgin.prog3210_finalproject;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Button;
import android.view.View.OnClickListener;

public class ItemActivity extends AppCompatActivity implements View.OnClickListener {

    //Variables
    private TextView lblHeadline, lblSource, lblDate, lblDescription, lblLink;
    private Button btnSaveItem;
    RSSItem story = new RSSItem();
    newsReaderDB db = new newsReaderDB(this);
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_item);

        lblHeadline = (TextView) findViewById(R.id.lblHeadline_ia);
        lblSource = (TextView) findViewById(R.id.lblSource_ia);
        lblDate = (TextView) findViewById(R.id.lblDate_ia);
        lblDescription = (TextView) findViewById(R.id.lblDescription_ia);
        lblLink = (TextView) findViewById(R.id.lblLink_ia);

        lblLink.setOnClickListener(this);

        btnSaveItem = (Button) findViewById(R.id.btnSaveItem);

       // btnSaveItem.setOnEditorActionListener(this);


        Intent intent = getIntent();
        lblHeadline.setText(intent.getStringExtra("title"));
        lblSource.setText(intent.getStringExtra("source"));
        lblDate.setText(intent.getStringExtra("pubdate"));
        lblDescription.setText(intent.getStringExtra("description"));

        //Adds a nice blue underline to the link
        SpannableString spanString = new SpannableString(intent.getStringExtra("link"));
        spanString.setSpan(new UnderlineSpan(), 0, spanString.length(), 0);
        lblLink.setText(spanString);
        btnSaveItem.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {

        Intent intent = getIntent();

        String link = intent.getStringExtra("link");
        Uri viewUri = Uri.parse(link);

        Intent viewIntent = new Intent(Intent.ACTION_VIEW, viewUri);

        Context context = getApplicationContext();
        CharSequence text = "Story saved to database";
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text,duration);
        //toast.show();

        //saves the story to the database
        switch(v.getId()){
            case R.id.btnSaveItem:
                story.setTitle(lblHeadline.getText().toString());
                story.setDescription(lblDescription.getText().toString());
                story.setLink(viewUri.toString());
                story.setSource(lblSource.getText().toString());
                db.insertStory(story);
                toast.show();
            break;
            case R.id.lblLink_ia:
                startActivity(viewIntent);
                break;
       }


    }


}
