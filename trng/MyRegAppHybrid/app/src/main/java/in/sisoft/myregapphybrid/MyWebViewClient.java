package in.sisoft.myregapphybrid;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;


public class MyWebViewClient extends WebViewClient {

    Context ctx ;
    MyWebViewClient(Context c1)
    {
        this.ctx = c1 ;
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        super.shouldOverrideUrlLoading(view, url);
        Log.d("MyWebViewClient:URL",Uri.parse(url).getHost() );
        if (Uri.parse(url).getHost().equals("www.sisoft.in")) {
            return false;
        }
        else {
            // Otherwise, the link is not for a page on my site, so launch another Activity that handles URLs       
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            ctx.startActivity(intent);
            return true;
        }
    }
}

