package com.example.demoapp_sample1;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private WebView webView;
    private View rootView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        webView = findViewById(R.id.webview);
      // To handle page navigation
        webView.getSettings().setJavaScriptEnabled(true); // Enable JavaScript
webView.loadUrl("https://youtu.be/V9-Kw8MlZSo?si=5c9ep83M3fw5MInU");
        webView.setWebViewClient(new WebViewClient());
        //rotation code
        webView.setWebChromeClient(new WebChromeClient() {
            public void onShowCustomView(View view, WebChromeClient.CustomViewCallback callback) {
                super.onShowCustomView(view, callback);
                if (view instanceof android.widget.FrameLayout) {
                    MainActivity.this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                    webView.setVisibility(View.GONE);
                    setContentView(view);
                }
            }


            public void onHideCustomView() {
                super.onHideCustomView();
                MainActivity.this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                webView.setVisibility(View.VISIBLE);
            }
        });

        // Get the root view of your layout
        rootView = getWindow().getDecorView();

        // Set a touch listener on the root view
        rootView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // Hide the system UI when the user touches the screen
                hideSystemUI();
                return false;
            }
        });

        // Initially, hide the system UI
        hideSystemUI();



    }

    // Method to hide the system UI (status bar and navigation bar)
    private void hideSystemUI() {
        rootView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN |
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
    }

    // Handle back button press to navigate back in WebView
    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed();
        }
    }
}