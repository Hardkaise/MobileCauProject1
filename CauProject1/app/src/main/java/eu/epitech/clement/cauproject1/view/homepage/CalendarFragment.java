package eu.epitech.clement.cauproject1.view.homepage;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import eu.epitech.clement.cauproject1.R;
import eu.epitech.clement.cauproject1.tools.StepFragment;
import eu.epitech.clement.cauproject1.tools.Storage;

/**
 * A simple {@link Fragment} subclass.
 */
public class CalendarFragment extends StepFragment {
    public static final int INFORMATION_MEMO = 0;
    public static final int INFORMATION_ALARM = 1;
    @BindView(R.id.calendar_alarm_info) TextView alarm;

    private boolean stop = false;

    public CalendarFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_calendar, container, false);
        ButterKnife.bind(this, root);
        displayAlarmSetted();
        return root;
    }

    private void displayAlarmSetted() {
        long time = Storage.Session.with(getContext()).getAlarm();
        if (time == -1)
            alarm.setText(getString(R.string.alarm_set_to, getString(R.string.na)));
        else {
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(time);
            alarm.setText(getString(R.string.alarm_set_to, String.format("%02d:%02d", calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE))));
        }
    }


    @OnClick(R.id.calendar_memo)
    public void onClickMemo() {
        if (!stop)
            sendMessageToActivity(INFORMATION_MEMO);
        stop = true;
    }

    @OnClick(R.id.calendar_alarm)
    public void onClickAlarm() {
        if (!stop)

            sendMessageToActivity(INFORMATION_ALARM);
        stop = true;
    }

    @Override
    public boolean onBackPressed() {
        stepDone();
        return super.onBackPressed();
    }
}
