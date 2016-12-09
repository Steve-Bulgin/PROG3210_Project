package io.github.steve_bulgin.prog3210_finalproject;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class ItemActivity extends AppCompatActivity implements View.OnClickListener {

    //Variables
    private TextView lblHeadline, lblSource, lblDate, lblDescription, lblLink;


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


        Intent intent = getIntent();
        lblHeadline.setText(intent.getStringExtra("title"));
        lblSource.setText(intent.getStringExtra("source"));
        lblDate.setText(intent.getStringExtra("pubdate"));
        lblDescription.setText(intent.getStringExtra("description"));

        //Adds a nice blue underline to the link
        SpannableString spanString = new SpannableString(intent.getStringExtra("link"));
        spanString.setSpan(new UnderlineSpan(), 0, spanString.length(), 0);
        lblLink.setText(spanString);

    }


    @Override
    public void onClick(View v) {

        Intent intent = getIntent();

        String link = intent.getStringExtra("link");
        Uri viewUri = Uri.parse(link);

        Intent viewIntent = new Intent(Intent.ACTION_VIEW, viewUri);
        startActivity(viewIntent);
    }
}
