package eu.epitech.clement.cauproject1.tools;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import eu.epitech.clement.cauproject1.view.alarm.AlarmActivity;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

public class OnAlarmReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent)
        {   //Build pending intent from calling information to display Notification
            Log.e("PASS", "RECEIVER");
            Intent intentAlarm = new Intent(context, AlarmActivity.class);
            intentAlarm.addFlags(FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intentAlarm);
        }
}

