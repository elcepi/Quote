package com.knowyourself.quote;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.os.FileUtils;
import android.util.Log;

import com.knowyourself.quote.model.Author;
import com.knowyourself.quote.model.Quotes;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private final static String TAG = MainActivity.class.toString();
    private File dir;
    private final static List<Author> authors = new ArrayList<>();
    private final static List<Quotes> quotes = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dir = new File(getApplication().getFilesDir(), "quote");

        crateDirAndCopyFile();
        fillAvailableAuthors();
        fillAvailableQuots();
    }

    private void fillAvailableQuots() {
        for (Author a:authors) {
            quotes.addAll(a.getQuotes());
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
        InputStream in = new FileInputStream(src);
        try {
            OutputStream out = new FileOutputStream(dst);
            try {
                // Transfer bytes from in to out
                byte[] buf = new byte[1024];
                int len;
                while ((len = in.read(buf)) > 0) {
                    out.write(buf, 0, len);
                }
            } finally {
                out.close();
            }
        } finally {
            in.close();
        }
    }


}