package com.knowyourself.quote;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.os.FileUtils;
import android.util.Log;

import com.knowyourself.quote.model.Author;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private final static String TAG = MainActivity.class.toString();
    private File dir;
    private final static List<Author> authors = new ArrayList<Author>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dir = new File(getApplication().getFilesDir(), "quote");

        crateDirAndCopyFile();
        fillAvailableAuthors();

/*
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(
                    new InputStreamReader(getAssets().open("filename.txt"), "UTF-8"));

            // do reading, usually loop until end of file reading
            String mLine;
            while ((mLine = reader.readLine()) != null) {
               Log.e("TMP", mLine);
            }
        } catch (IOException e) {
            //log the exception
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    //log the exception
                }
            }

 */
        }

    private void fillAvailableAuthors() {
        for(String p:dir.list()) {
            authors.add(new Author(p,true));
        }
    }

    private void crateDirAndCopyFile() {
        if(! dir.exists()) {
            Log.i("TAG", "Creating dir:" + dir);
            dir.mkdirs();
            try {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    Field[] fields = R.raw.class.getDeclaredFields();
                    for (Field field : fields) {
                        copyFile((field.getInt(field)));
                    }
                } else {
                    Log.i(TAG, "Coping file not supported");
                }
            } catch (IOException | IllegalAccessException e) {
                Log.e(TAG,e.getLocalizedMessage(), e);
                throw new RuntimeException(e);
            }
       }

    }

    @SuppressLint("NewApi")
    private void copyFile(final int id) throws IOException {
        File src = new File(getResources().getString(id));
        Log.i(TAG, "Coping file from: " + src);
        File f = new File(dir,src.getName());
        f.createNewFile();
        FileUtils.copy(getResources().openRawResource(id), new FileOutputStream(f));
    }
}