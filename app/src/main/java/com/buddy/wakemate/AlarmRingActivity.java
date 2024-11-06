package com.buddy.wakemate;



import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Handler;

public class AlarmRingActivity extends AppCompatActivity {
    private MediaPlayer mediaPlayer;
    private Button dismissButton;
    private Button snoozeButton;
    private static final int SNOOZE_DELAY = 5 * 60 * 1000; // 5 minutes

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_ring);

        initializeViews();
        startAlarm();
        setupButtons();
    }

    private void initializeViews() {
        dismissButton = findViewById(R.id.dismissButton);
        snoozeButton = findViewById(R.id.snoozeButton);
    }

    private void startAlarm() {
        Uri alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        mediaPlayer = MediaPlayer.create(this, alarmUri);
        mediaPlayer.setLooping(true);
        mediaPlayer.start();
    }

    private void setupButtons() {
        dismissButton.setOnClickListener(v -> dismissAlarm());
        snoozeButton.setOnClickListener(v -> snoozeAlarm());
    }

    private void dismissAlarm() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
        }
        finish();
    }

    private void snoozeAlarm() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
        }

        // Schedule a new alarm for 5 minutes later
        // Implementation similar to SetAlarmActivity's saveAlarm()

        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.release();
        }
    }
}