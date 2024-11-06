package com.buddy.wakemate;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

//public class MainActivity extends AppCompatActivity {
//
//    private Button setAlarmButton;
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        EdgeToEdge.enable(this);
//        setContentView(R.layout.activity_main);
//
//
//
//
//
//        //initilization
//        setAlarmButton=findViewById(R.id.setAlarmButton);
//
//        //set action
//
//        setAlarmButton.setOnClickListener(v -> {
//            Intent intent=new Intent(MainActivity.this,AlarmSettingActivity.class);
//            startActivity(intent);
//
//        });
//
//
//
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });
//    }
//



import android.os.Bundle;
import android.widget.TextView;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.LinearLayoutManager;
import android.view.View;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import android.os.Handler;
import android.content.Intent;

public class MainActivity extends AppCompatActivity {
    private TextView timeTextView;
    private TextView dateTextView;
    private RecyclerView alarmsRecyclerView;
    private Button addAlarmButton;
    private AlarmAdapter alarmAdapter;
    private ArrayList<Alarm> alarms;
    private Handler handler;
    private SimpleDateFormat timeFormat;
    private SimpleDateFormat dateFormat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeViews();
        setupTimeUpdate();
        setupAlarmsList();
        setupAddAlarmButton();
    }

    private void initializeViews() {
        timeTextView = findViewById(R.id.timeTextView);
        dateTextView = findViewById(R.id.dateTextView);
        alarmsRecyclerView = findViewById(R.id.alarmsRecyclerView);
        addAlarmButton = findViewById(R.id.addAlarmButton);

        timeFormat = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
        dateFormat = new SimpleDateFormat("EEEE, MMMM d, yyyy", Locale.getDefault());
    }

    private void setupTimeUpdate() {
        handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                Date currentTime = new Date();
                timeTextView.setText(timeFormat.format(currentTime));
                dateTextView.setText(dateFormat.format(currentTime));
                handler.postDelayed(this, 1000);
            }
        });
    }

    private void setupAlarmsList() {
        alarms = new ArrayList<>();
        alarmAdapter = new AlarmAdapter(alarms, this);
        alarmsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        alarmsRecyclerView.setAdapter(alarmAdapter);
    }

    private void setupAddAlarmButton() {
        addAlarmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SetAlarmActivity.class));
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
        }
    }
}
