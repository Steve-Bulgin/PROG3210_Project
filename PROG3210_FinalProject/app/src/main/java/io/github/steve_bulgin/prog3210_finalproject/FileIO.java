package io.github.steve_bulgin.prog3210_finalproject;


import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class FileIO {


    private final String URL_STRING = "http://rss.cnn.com/rss/cnn_topstories.rss";
    private final String FILENAME = "news_feed.xml";
    private Context context = null;

    public FileIO(Context context) {
        this.context = context;
    }

    public void downloadFile() {
        SharedPreferences sharedpreferences = context.getSharedPreferences("pref_file", Context.MODE_PRIVATE);
        try{
            //cnn file maker
            if (sharedpreferences.getBoolean("cnn_news", true)) {
                URL url = new URL("http://rss.cnn.com/rss/cnn_topstories.rss");

                // get the input stream
                InputStream in = url.openStream();

                // get the output stream
                FileOutputStream out =
                        context.openFileOutput("CNN", Context.MODE_PRIVATE);

                // read input and write output
                byte[] buffer = new byte[1024];
                int bytesRead = in.read(buffer);
                while (bytesRead != -1)
                {
                    out.write(buffer, 0, bytesRead);
                    bytesRead = in.read(buffer);
                }
                out.close();
                in.close();
            }
            //ctv file maker
            if (sharedpreferences.getBoolean("ctv_news", true)) {
                URL url = new URL("http://ctvnews.ca/rss/TopStories");

                // get the input stream
                InputStream in = url.openStream();

                // get the output stream
                FileOutputStream out =
                        context.openFileOutput("CTV", Context.MODE_PRIVATE);

                // read input and write output
                byte[] buffer = new byte[1024];
                int bytesRead = in.read(buffer);
                while (bytesRead != -1)
                {
                    out.write(buffer, 0, bytesRead);
                    bytesRead = in.read(buffer);
                }
                out.close();
                in.close();
            }
            //cbc file maker
            if (sharedpreferences.getBoolean("cbc_news", true)) {
                URL url = new URL("http://www.cbc.ca/cmlink/rss-topstories");

                // get the input stream
                InputStream in = url.openStream();

                // get the output stream
                FileOutputStream out =
                        context.openFileOutput("CBC", Context.MODE_PRIVATE);

                // read input and write output
                byte[] buffer = new byte[1024];
                int bytesRead = in.read(buffer);
                while (bytesRead != -1)
                {
                    out.write(buffer, 0, bytesRead);
                    bytesRead = in.read(buffer);
                }
                out.close();
                in.close();
            }
            //global file maker
            if (sharedpreferences.getBoolean("global_news", true)) {
                URL url = new URL("http://globalnews.ca/feed/");

                // get the input stream
                InputStream in = url.openStream();

                // get the output stream
                FileOutputStream out =
                        context.openFileOutput("GLOBAL", Context.MODE_PRIVATE);

                // read input and write output
                byte[] buffer = new byte[1024];
                int bytesRead = in.read(buffer);
                while (bytesRead != -1)
                {
                    out.write(buffer, 0, bytesRead);
                    bytesRead = in.read(buffer);
                }
                out.close();
                in.close();
            }

        }
        catch (IOException e) {
            Log.e("News reader", e.toString());
        }
    }

    public RSSFeed readFile() {
        try {
            // get the XML reader
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser parser = factory.newSAXParser();
            XMLReader xmlreader = parser.getXMLReader();

            // set content handler
            RSSFeedHandler theRssHandler = new RSSFeedHandler();
            xmlreader.setContentHandler(theRssHandler);

            // read the file from internal storage
            FileInputStream in = context.openFileInput("GLOBAL");

            // parse the data
            InputSource is = new InputSource(in);
            xmlreader.parse(is);

            // set the feed in the activity
            RSSFeed feed = theRssHandler.getFeed();
            feed.setSource();
            return feed;
        }
        catch (Exception e) {
            Log.e("News reader", e.toString());
            return null;
        }
    }

}
