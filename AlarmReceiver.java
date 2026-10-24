package com.system.app;
import android.content.*;
public class AlarmReceiver extends BroadcastReceiver{
 @Override public void onReceive(Context c,Intent i){Sched.show(c,i.getStringExtra("t"),i.getStringExtra("b"),(int)(System.currentTimeMillis()%100000));}
}
