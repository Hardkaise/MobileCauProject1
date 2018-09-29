package eu.epitech.clement.cauproject1.tools;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.Calendar;
import java.util.Date;

public class Alarm {
    private Context context;
    private AlarmManager alarmMgr;
    private PendingIntent sender;

    public Alarm(Context context) {
        this.context = context;
    }

    public void setAlarm(long time) {
        alarmMgr = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, OnAlarmReceiver.class);
        sender = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmMgr.set(AlarmManager.RTC_WAKEUP, time, sender);
        Log.e("pass", "here");
    }

    public void cancelAlarm() {
        alarmMgr = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent myIntent = new Intent(context, OnAlarmReceiver.class);
        sender = PendingIntent.getBroadcast(context, 0, myIntent, 0);
        alarmMgr.cancel(sender);
    }
}
