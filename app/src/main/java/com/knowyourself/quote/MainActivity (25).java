package com.knowyourself.quote;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContextWrapper;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.os.FileUtils;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class MainActivity extends AppCompatActivity {
    private final static String TAG = MainActivity.class.toString();

    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        crateDir();
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

    @RequiresApi(api = Build.VERSION_CODES.Q)
    private void crateDir() {
        File dir = new File(getApplication().getFilesDir(), "quote");
        Log.i(TAG,"FILE: "+getResources().getString((int) R.raw.laotzu));

        if(! dir.exists()) {
            dir.mkdirs();
            try {
                File f = new File(dir,"/foo");
                f.createNewFile();
                OutputStream o = new FileOutputStream(f);
                FileUtils.copy(getResources().openRawResource(R.raw.laotzu), o);
            } catch (IOException e) {
                Log.e(TAG,e.getLocalizedMessage(), e);
                throw new RuntimeException(e);
            }
    }

}
}