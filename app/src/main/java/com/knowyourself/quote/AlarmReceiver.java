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
    public static int NOTIFICATION_ID = 1642;

    @Override
    public void onReceive(Context context, Intent i) {
        Quotes quote = QuoteSingleton.getInstance(context).getRandomQuote();

        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra(Intent.EXTRA_TEXT,quote);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK| Intent.FLAG_ACTIVITY_CLEAR_TASK);
        Intent[] arr = {intent};

        PendingIntent pendingIntent = PendingIntent.getActivities(context, 0, arr, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        NotificationCompat.Action action = new NotificationCompat.Action(R.drawable.baseline_change_circle_20,"Quite", pendingIntent);
        Notification notification = new NotificationCompat.Builder(context, CHANNEL_ID)
                .addAction(action)
                .build();

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.notify(NOTIFICATION_ID, notification);
    }
}
