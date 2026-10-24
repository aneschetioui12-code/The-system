package com.system.app;
import android.app.Activity;import android.content.Context;import android.os.Build;import android.os.Bundle;import android.webkit.*;
public class MainActivity extends Activity{
 WebView w;
 @Override protected void onCreate(Bundle b){super.onCreate(b);
  w=new WebView(this);setContentView(w);
  WebSettings s=w.getSettings();s.setJavaScriptEnabled(true);s.setDomStorageEnabled(true);
  w.setWebViewClient(new WebViewClient());w.setWebChromeClient(new WebChromeClient());
  w.addJavascriptInterface(new Bridge(this),"Android");
  w.loadUrl("file:///android_asset/index.html");
  if(Build.VERSION.SDK_INT>=33)requestPermissions(new String[]{"android.permission.POST_NOTIFICATIONS"},1);
 }
 @Override public void onBackPressed(){moveTaskToBack(true);}
}
class Bridge{Context c;Bridge(Context c){this.c=c;}
 @JavascriptInterface public void schedule(String j){c.getSharedPreferences("s",0).edit().putString("j",j).apply();Sched.set(c,j);}
 @JavascriptInterface public void testAlert(){Sched.show(c,"النظام","اختبار تنبيه: يعمل بنجاح",1);}
}
