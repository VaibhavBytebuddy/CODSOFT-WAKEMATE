package com.buddy.wakemate;


import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TimePicker;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.ArrayAdapter;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Calendar;

public class SetAlarmActivity extends AppCompatActivity {
    private TimePicker timePicker;
    private Spinner toneSpinner;
    private Button saveButton;
    private Button cancelButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_alarm);

        initializeViews();
        setupToneSpinner();
        setupButtons();
    }

    private void initializeViews() {
        timePicker = findViewById(R.id.timePicker);
        toneSpinner = findViewById(R.id.toneSpinner);
        saveButton = findViewById(R.id.saveButton);
        cancelButton = findViewById(R.id.cancelButton);

        timePicker.setIs24HourView(true);
    }

    private void setupToneSpinner() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.alarm_tones, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        toneSpinner.setAdapter(adapter);
    }

    private void setupButtons() {
        saveButton.setOnClickListener(v -> saveAlarm());
        cancelButton.setOnClickListener(v -> finish());
    }

    private void saveAlarm() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, timePicker.getHour());
        calendar.set(Calendar.MINUTE, timePicker.getMinute());
        calendar.set(Calendar.SECOND, 0);

        if (calendar.before(Calendar.getInstance())) {
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }

        int alarmId = (int) System.currentTimeMillis();

        Intent alarmIntent = new Intent(this, AlarmReceiver.class);
        alarmIntent.putExtra("ALARM_TONE", toneSpinner.getSelectedItem().toString());

        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                this, alarmId, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);

        // Save alarm to database or preferences
        DatabaseHelper db = new DatabaseHelper(this);
        db.addAlarm(new Alarm(alarmId, calendar.getTimeInMillis(),
                toneSpinner.getSelectedItem().toString(), true));

        finish();
    }
}