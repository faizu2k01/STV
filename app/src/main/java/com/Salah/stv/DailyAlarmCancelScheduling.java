package com.Salah.stv;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class DailyAlarmCancelScheduling extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Salah_Vibration_Activity sb = new Salah_Vibration_Activity();
        sb.cancelAlarm();
    }
}
