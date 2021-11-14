package com.knowyourself.quote;

import android.content.Context;
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
import java.util.Random;

public class QuoteSingleton {
    private final static String TAG = QuoteSingleton.class.toString();
    private static QuoteSingleton instance;

    private final List<Author> authors = new ArrayList<>();
    private final List<Quotes> quotes = new ArrayList<>();

    public static void initInstance(Context context)
    {
        if (instance == null)
        {
            instance = new QuoteSingleton(context);
        }
    }

    public static QuoteSingleton getInstance(Context context)
    {
        initInstance(context);
        return instance;
    }


    private QuoteSingleton(Context context)
    {
        File dir = new File(context.getExternalFilesDir(null),"quote");
        crateDirAndCopyFile(context, dir);
        fillAvailableAuthors(dir);
        fillAvailableQuotes();
    }


    private void copy(@RawRes int rawIdSrc, File dst, Context context) throws IOException {
        try (InputStream in = context.getResources().openRawResource(rawIdSrc)) {
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

    private void fillAvailableAuthors(File dir) {
        for(String p: Objects.requireNonNull(dir.list())) {
            authors.add(new Author(new File(dir, p).getAbsolutePath(),true));
        }
    }

    private void crateDirAndCopyFile(Context context, File dir) {
        if(! dir.exists()) {
            dir.mkdirs();
            Field[] fields = R.raw.class.getDeclaredFields();
            for (Field field : fields) {
                try {
                    copy((Integer)field.get(null), new File(dir.getAbsolutePath(), field.getName()),context);
                } catch (IOException | IllegalAccessException e) {
                    Log.e(TAG,"Error Copying files", e);
                }
            }
        }
    }

    public Quotes getRandomQuote()
    {
        return quotes.get((new Random()).nextInt(quotes.size()));
    }
}
