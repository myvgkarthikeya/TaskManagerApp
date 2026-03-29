package com.example.taskmanagerapp.activities

import android.os.Bundle
import android.util.Log
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.example.taskmanagerapp.databinding.ActivityWebBinding

class WebActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "WebActivity"
        private const val DEFAULT_URL = "https://www.google.com"
    }

    private lateinit var binding: ActivityWebBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate() called")

        binding = ActivityWebBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Web Browser"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        setupWebView()
    }

    private fun setupWebView() {
        Log.d(TAG, "setupWebView() called — loading: $DEFAULT_URL")

        binding.webView.apply {
            // Configure WebView settings
            settings.apply {
                javaScriptEnabled = true
                domStorageEnabled = true
                loadWithOverviewMode = true
                useWideViewPort = true
                builtInZoomControls = true
                displayZoomControls = false
                cacheMode = WebSettings.LOAD_DEFAULT
            }

            // Keep navigation inside the WebView
            webViewClient = object : WebViewClient() {
                override fun onPageStarted(view: WebView?, url: String?, favicon: android.graphics.Bitmap?) {
                    super.onPageStarted(view, url, favicon)
                    Log.d(TAG, "WebView page started loading: $url")
                }

                override fun onPageFinished(view: WebView?, url: String?) {
                    super.onPageFinished(view, url)
                    Log.d(TAG, "WebView page finished loading: $url")
                }
            }

            webChromeClient = object : WebChromeClient() {
                override fun onProgressChanged(view: WebView?, newProgress: Int) {
                    super.onProgressChanged(view, newProgress)
                    Log.d(TAG, "WebView loading progress: $newProgress%")
                }
            }

            loadUrl(DEFAULT_URL)
            Log.d(TAG, "WebView loadUrl() called with: $DEFAULT_URL")
        }
    }

    // Handle hardware back button for WebView navigation
    override fun onBackPressed() {
        Log.d(TAG, "onBackPressed() called")
        if (binding.webView.canGoBack()) {
            binding.webView.goBack()
            Log.d(TAG, "WebView navigated back")
        } else {
            super.onBackPressed()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy() called — destroying WebView")
        binding.webView.destroy()
    }
}
