package com.buddy.wakemate;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class AlarmAdapter extends RecyclerView.Adapter<AlarmAdapter.ViewHolder> {
    private ArrayList<Alarm> alarms;
    private Context context;
    private SimpleDateFormat timeFormat;

    public AlarmAdapter(ArrayList<Alarm> alarms, Context context) {
        this.alarms = alarms;
        this.context = context;
        this.timeFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.alarm_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Alarm alarm = alarms.get(position);
        holder.timeTextView.setText(timeFormat.format(new Date(alarm.getTimeInMillis())));
        holder.toneTextView.setText(alarm.getTone());
        holder.enableSwitch.setChecked(alarm.isEnabled());

        holder.enableSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            alarm.setEnabled(isChecked);
            // Update alarm status in database
            // Update alarm manager status
        });
    }

    @Override
    public int getItemCount() {
        return alarms.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView timeTextView;
        public TextView toneTextView;
        public Switch enableSwitch;

        public ViewHolder(View view) {
            super(view);
            timeTextView = view.findViewById(R.id.alarmTimeTextView);
            toneTextView = view.findViewById(R.id.alarmToneTextView);
            enableSwitch = view.findViewById(R.id.alarmEnableSwitch);
        }
    }
}