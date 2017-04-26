package in.sisoft.myregapphybrid;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    WebView wv1 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        wv1 = (WebView) findViewById(R.id.webView);
        WebSettings wvs = wv1.getSettings();
        wvs.setJavaScriptEnabled(true);
        wv1.addJavascriptInterface(new WebAppInterface(this),"Android");
        wv1.loadUrl("file:///android_res/raw/androidjs.html");


        wv1.setWebViewClient(new MyWebViewClient(this));
      //  wv1.loadUrl("http://www.google.com");
         wv1.setWebChromeClient(new MyWebChrome(this));
    }

    public void backPressed(View v)
    {
        if ( wv1.canGoBack()) {
            wv1.goBack();
                    }
        else
        {
            Toast.makeText(this,"No History", Toast.LENGTH_LONG).show();
        }

    }
}
