package com.buddy.wakemate;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent alarmActivityIntent = new Intent(context, AlarmRingActivity.class);
        alarmActivityIntent.putExtra("ALARM_TONE", intent.getStringExtra("ALARM_TONE"));
        alarmActivityIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(alarmActivityIntent);
    }
}