package com.knowyourself.quote;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.knowyourself.quote.model.Quotes;

import java.util.Calendar;
import java.util.UUID;

public class AlarmReceiver extends BroadcastReceiver {
    public static String CHANNEL_ID = "1642";

    @Override
    public void onReceive(Context context, Intent i) {
        String quote = QuoteSingleton.getInstance(context).getRandomQuote().toShareString();

        Intent intent = new Intent();
        intent.putExtra(Intent.EXTRA_TEXT,quote);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        NotificationCompat.Action action = new NotificationCompat.Action(R.drawable.baseline_change_circle_20,"Quite", pendingIntent);

        Notification notification = new NotificationCompat.Builder(context, CHANNEL_ID)
                .addAction(action)
                .build();

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.notify(Math.toIntExact(Calendar.getInstance().getTimeInMillis()), notification);
    }
}
