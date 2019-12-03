package com.example.xydt;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class Main3Activity extends AppCompatActivity {
    WebView webView;
    String string;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
//        string=getIntent() .getStringExtra("data");
        webView = (WebView) findViewById(R.id.webview);
        //访问网页
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("https://baike.baidu.com");
        //系统默认会通过手机浏览器打开网页，为了能够直接通过WebView显示网页，则必须设置
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                //使用WebView加载显示url
                view.loadUrl(url);

                // 返回true
                 return true;
                 }
                 });

    }
}
