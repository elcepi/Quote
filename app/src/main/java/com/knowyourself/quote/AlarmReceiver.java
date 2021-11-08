package com.knowyourself.quote;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.knowyourself.quote.model.Quotes;

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String quote = intent.getStringExtra(Quotes.QUOTE_ID);
        Toast.makeText(context, "Quote" + quote, Toast.LENGTH_SHORT).show();
    }
}
