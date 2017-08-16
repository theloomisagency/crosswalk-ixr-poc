package seandmcgee.com.ixrwebviewpoc;

import android.app.Activity;
import android.os.Bundle;

import org.xwalk.core.XWalkPreferences;
import org.xwalk.core.XWalkView;
import android.view.View;
import android.webkit.WebView;
import android.app.Dialog;
import android.widget.TextView;
import android.webkit.WebViewClient;
import android.widget.Button;

public class MainActivity extends Activity {
    private XWalkView xWalkWebView;
    WebView web;
    Button adLink;
    Dialog web_dialog;
    TextView AdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        xWalkWebView=(XWalkView)findViewById(R.id.xwalkWebView);
        xWalkWebView.load("https://codepen.io/chrisgannon/full/NPwjGE", null);

        // turn on debugging
        XWalkPreferences.setValue(XWalkPreferences.REMOTE_DEBUGGING, true);
        AdView =(TextView)findViewById(R.id.AdView);
        adLink = (Button)findViewById(R.id.defaultAd);
        adLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                web_dialog = new Dialog(MainActivity.this);
                web_dialog.setContentView(R.layout.web_layout);
                web = (WebView) web_dialog.findViewById(R.id.webview);
                web.getSettings().setJavaScriptEnabled(true);

                web.loadUrl("http://www.google.com");

                web.setWebViewClient(new WebViewClient() {
                    @Override
                    public boolean shouldOverrideUrlLoading(WebView view, String url) {
                        view.loadUrl(url);
                        return true;
                    }

                });
                web_dialog.show();
                web_dialog.setTitle("Some Advertisement");
                web_dialog.setCancelable(true);
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (xWalkWebView != null) {
            xWalkWebView.pauseTimers();
            xWalkWebView.onHide();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (xWalkWebView != null) {
            xWalkWebView.resumeTimers();
            xWalkWebView.onShow();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (xWalkWebView != null) {
            xWalkWebView.onDestroy();
        }
    }
}
