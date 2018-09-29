package eu.epitech.clement.cauproject1.view.alarm;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import eu.epitech.clement.cauproject1.R;
import eu.epitech.clement.cauproject1.tools.Alarm;
import eu.epitech.clement.cauproject1.tools.Storage;
import eu.epitech.clement.cauproject1.view.homepage.CalendarFragment;
import eu.epitech.clement.cauproject1.view.homepage.HomepageActivity;

public class AlarmPickerActivity extends AppCompatActivity {
    @BindView(R.id.alarm_info)  TextView info;
    private static long time = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_picker);
        ButterKnife.bind(this);
        long time = Storage.Session.with(getBaseContext()).getAlarm();
        if (time == -1)
            info.setText(getString(R.string.alarm_set_to, getString(R.string.na)));
        else {
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(time);
            info.setText(getString(R.string.alarm_set_to, String.format("%02d:%02d", calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE))));
        }
    }



    public void setNewAlarm() {
        final Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

       TimePickerDialog timePickerDialog = new TimePickerDialog(AlarmPickerActivity.this, new TimePickerDialog.OnTimeSetListener() {
           @Override
           public void onTimeSet(TimePicker timePicker, int i, int i1) {
               calendar.set(Calendar.HOUR_OF_DAY, i);
               calendar.set(Calendar.MINUTE, i1);
               calendar.set(Calendar.SECOND, 0);
               time = calendar.getTimeInMillis();
               info.setText(getString(R.string.alarm_set_to, String.format("%02d:%02d", i, i1)));
           }
       }, hour, minute, true);
       timePickerDialog.show();
    }

    @OnClick(R.id.alarm_set_new)
    public void onClickNew() {
        setNewAlarm();
    }

    @OnClick(R.id.alarm_accept)
    public void onClickAccept() {
        if (time > new Date().getTime()) {
            Alarm alarm = new Alarm(getBaseContext());
            alarm.setAlarm(time);
            Storage.Session.with(getBaseContext()).setAlarm(time);
            Storage.Session.with(getBaseContext()).setStateAlarm(true);
            startActivity(new Intent(this, HomepageActivity.class));
            finish();
        }
        else {
            AlertDialog.Builder dialog = new AlertDialog.Builder(AlarmPickerActivity.this)
                    .setCancelable(false)
                    .setTitle(R.string.error)
                    .setMessage(R.string.cannot_set_past_time)
                    .setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) { }
                    });
            dialog.create().show();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        startActivity(new Intent(this, HomepageActivity.class));
    }



    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {

        }
    }

}
