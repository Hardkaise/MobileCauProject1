package eu.epitech.clement.cauproject1.view.alarm;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import eu.epitech.clement.cauproject1.R;
import eu.epitech.clement.cauproject1.tools.Storage;

public class AlarmActivity extends AppCompatActivity {
    @BindView(R.id.alarm_clock) TextView clock;
    private  MediaPlayer mMediaPlayer;
    private CountDownTimer newtimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);
        ButterKnife.bind(this);
        mMediaPlayer = MediaPlayer.create(this, R.raw.ringtone);
        mMediaPlayer.setVolume(100, 100);
        mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mMediaPlayer.setLooping(true);
        mMediaPlayer.start();
         newtimer = new CountDownTimer(1000000000, 1000) {
            public void onTick(long millisUntilFinished) {
                Calendar c = Calendar.getInstance();
                clock.setText(String.format("%02d:%02d:%02d", c.get(Calendar.HOUR), c.get(Calendar.MINUTE), c.get(Calendar.SECOND)));
            }
            public void onFinish() { }
        };
        newtimer.start();
    }

    @OnClick(R.id.alarm_stop)
    public void onClickStop() {
        mMediaPlayer.stop();
        newtimer.cancel();
        Storage.Session.with(getBaseContext()).setAlarm(-1);
        Storage.Session.with(getBaseContext()).setStateAlarm(false);
        finish();
    }
}
