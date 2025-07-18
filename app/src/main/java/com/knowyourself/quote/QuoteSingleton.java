package com.knowyourself.quote;

import android.util.Log;

import androidx.annotation.RawRes;

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

public class QuoteSingleton {
    private final static String TAG = QuoteSingleton.class.toString();
    private static QuoteSingleton instance;

    private final List<Author> authors = new ArrayList<>();
    private final List<Quotes> quotes = new ArrayList<>();

    private File dir;


    //  TODO Android add context
    public static void initInstance()
    {
        if (instance == null)
        {
            // Create the instance
            instance = new QuoteSingleton();
        }
    }

    public static QuoteSingleton getInstance()
    {
        // Return the instance
        return instance;
    }


    private QuoteSingleton()
    {
        crateDirAndCopyFile();
        fillAvailableAuthors();
        fillAvailableQuotes();
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

    public void customSingletonMethod()
    {
        // Custom method
    }
}
