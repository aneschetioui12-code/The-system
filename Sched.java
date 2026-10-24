package com.system.app;
import android.app.*;import android.content.*;import android.os.Build;import org.json.*;
public class Sched{
 static final String CH="sys";
 static int fl(){return PendingIntent.FLAG_UPDATE_CURRENT|(Build.VERSION.SDK_INT>=23?PendingIntent.FLAG_IMMUTABLE:0);}
 static void show(Context c,String t,String b,int id){
  NotificationManager nm=(NotificationManager)c.getSystemService(Context.NOTIFICATION_SERVICE);
  Notification.Builder n;
  if(Build.VERSION.SDK_INT>=26){nm.createNotificationChannel(new NotificationChannel(CH,"النظام",NotificationManager.IMPORTANCE_HIGH));n=new Notification.Builder(c,CH);}
  else{n=new Notification.Builder(c);n.setPriority(Notification.PRIORITY_HIGH);}
  PendingIntent pi=PendingIntent.getActivity(c,0,new Intent(c,MainActivity.class),fl());
  n.setSmallIcon(android.R.drawable.ic_dialog_info).setContentTitle(t).setContentText(b).setStyle(new Notification.BigTextStyle().bigText(b)).setContentIntent(pi).setAutoCancel(true).setDefaults(Notification.DEFAULT_ALL);
  nm.notify(id,n.build());
 }
 static PendingIntent pi(Context c,int id,String t,String b){Intent i=new Intent(c,AlarmReceiver.class);i.putExtra("t",t);i.putExtra("b",b);return PendingIntent.getBroadcast(c,id,i,fl());}
 static void set(Context c,String json){
  AlarmManager am=(AlarmManager)c.getSystemService(Context.ALARM_SERVICE);
  try{SharedPreferences p=c.getSharedPreferences("s",0);int old=p.getInt("n",0);
   for(int i=0;i<old;i++)am.cancel(pi(c,i,"",""));
   JSONArray a=new JSONArray(json);int n=0;
   for(int i=0;i<a.length()&&n<60;i++){JSONObject o=a.getJSONObject(i);long at=o.getLong("at");if(at<=System.currentTimeMillis())continue;
    PendingIntent x=pi(c,n,o.getString("title"),o.getString("text"));
    if(Build.VERSION.SDK_INT>=23)am.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP,at,x);else am.setExact(AlarmManager.RTC_WAKEUP,at,x);
    n++;}
   p.edit().putInt("n",n).apply();}catch(Exception e){}
 }
}
