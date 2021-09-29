package com.knowyourself.quote;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.knowyourself.quote.model.Author;
import com.knowyourself.quote.model.Quotes;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
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
    }

    private void getRandomQuote() {
        currentQuote = quotes.get((new Random()).nextInt(quotes.size()));
    }


    private void fillAvailableQuotes() {
        for (Author a:authors) {
            try {
                quotes.addAll(a.getQuotes());
            } catch (IOException e) {
                Log.e(TAG,"Error getting quites from files", e);
            }
        }
    }

    private void fillAvailableAuthors() {
        for(String p:dir.list()) {
            authors.add(new Author(p,true));
        }
    }

    private void crateDirAndCopyFile() {
        Field[] fields = R.raw.class.getDeclaredFields();
        for (Field field : fields) {
            try {
                copy(field.getName(), dir.getAbsolutePath() + field.getName());
            } catch (IOException e) {
                Log.e(TAG,"Error Copying files", e);
            }
        }
    }

    private static void copy(String src, String dst) throws IOException {
        try (InputStream in = new FileInputStream(src)) {
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