package com.system.app;
import android.content.*;
public class BootReceiver extends BroadcastReceiver{
 @Override public void onReceive(Context c,Intent i){String j=c.getSharedPreferences("s",0).getString("j",null);if(j!=null)Sched.set(c,j);}
}
