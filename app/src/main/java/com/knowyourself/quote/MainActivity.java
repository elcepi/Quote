package com.knowyourself.quote;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
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
import java.util.Calendar;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private final static String TAG = MainActivity.class.toString();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Quotes currentQuote = QuoteSingleton.getInstance(this).getRandomQuote();

        TextView textView = findViewById(R.id.quote);
        textView.setText(currentQuote.toShareString());

        Intent alarmIntent = new Intent(this, AlarmReceiver.class);
//        alarmIntent.putExtra(Quotes.QUOTE_ID, currentQuote.toShareString());

        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        manager.cancel(pendingIntent);

//        alarmIntent.setAction(Intent.ACTION_SEND);
//        alarmIntent.setType("text/plain");
//              sendIntent.setClassName("com.facebook.katana",
//                        "com.facebook.katana.ShareLinkActivity");

        // Set alarm at 5am
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 5);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.AM_PM, Calendar.AM);

        manager.setRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),24*60*60*1000,pendingIntent);
//        manager.setRepeating(AlarmManager.RTC_WAKEUP,Calendar.getInstance().getTimeInMillis() + 2000,24*60*60*1000,pendingIntent);
    }
}