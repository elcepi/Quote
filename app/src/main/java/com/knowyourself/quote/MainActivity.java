package com.knowyourself.quote;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.RawRes;
import androidx.appcompat.app.AppCompatActivity;

import com.knowyourself.quote.model.Author;
import com.knowyourself.quote.model.Quotes;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private final static String TAG = MainActivity.class.toString();
    private File dir;
    private final List<Author> authors = new ArrayList<>();
    private final List<Quotes> quotes = new ArrayList<>();
    private static Quotes currentQuote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dir = new File(getApplication().getFilesDir(), "quote");

        crateDirAndCopyFile();
        fillAvailableAuthors();
        fillAvailableQuotes();

        getRandomQuote();

        TextView textView = findViewById(R.id.quote);
        textView.setText(currentQuote.toShareString());

        ImageButton im= (ImageButton) findViewById(R.id.share);
        im.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v){
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, currentQuote.toShareString());
                sendIntent.setType("text/plain");
//              sendIntent.setClassName("com.facebook.katana",
//                        "com.facebook.katana.ShareLinkActivity");
                Intent shareIntent = Intent.createChooser(sendIntent, null);
                startActivityForResult(shareIntent, 0);
            }
        });

    }

    private void getRandomQuote() {
        currentQuote = quotes.get((new Random()).nextInt(quotes.size()));
    }


    private void fillAvailableQuotes() {
        for (Author a:authors) {
            try {
                if(a.isSelected()) {
                    quotes.addAll(a.getQuotes());
                }
            } catch (IOException e) {
                Log.e(TAG,"Error getting quites from files", e);
            }
        }
    }

    private void fillAvailableAuthors() {
        for(String p: Objects.requireNonNull(dir.list())) {
            authors.add(new Author(new File(dir, p).getAbsolutePath(),true));
        }
    }

    private void crateDirAndCopyFile() {
        if(! dir.exists()) {
            dir.mkdirs();
            Field[] fields = R.raw.class.getDeclaredFields();
            for (Field field : fields) {
                try {
                    copy((Integer)field.get(null), new File(dir.getAbsolutePath(), field.getName()));
                } catch (IOException | IllegalAccessException e) {
                    Log.e(TAG,"Error Copying files", e);
                }
            }
        }
    }

    private void copy(@RawRes int rawIdSrc, File dst) throws IOException {
        try (InputStream in = getResources().openRawResource(rawIdSrc)) {
            try (OutputStream out = new FileOutputStream(dst)) {
                // Transfer bytes from in to out
                byte[] buf = new byte[1024];
                int len;
                while ((len = in.read(buf)) > 0) {
                    out.write(buf, 0, len);
                }
            }
        }
    }
}