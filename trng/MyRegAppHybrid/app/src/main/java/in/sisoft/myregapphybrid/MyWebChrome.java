package in.sisoft.myregapphybrid;

import android.content.Context;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.Toast;

/**
 * Created by Dell on 20-09-2016.
 */
public class MyWebChrome extends WebChromeClient {

    Context mContext;
    MyWebChrome(Context ctx)
    {
        mContext = ctx ;
    }
    @Override
    public boolean onJsAlert(WebView view, String url, String message, JsResult result) {

        super.onJsAlert(view, url, message, result);
        Toast.makeText(mContext,"Android:"+url+":"+message,Toast.LENGTH_LONG).show() ;
        return false;
    }
}
