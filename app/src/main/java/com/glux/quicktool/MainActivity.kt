package com.glux.quicktool

import android.annotation.SuppressLint
import android.app.Activity
import android.content.res.Configuration
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient

class MainActivity : Activity() {
    private lateinit var webView: WebView

    private fun applySystemBarsTheme() {
        val isDark = (resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) == Configuration.UI_MODE_NIGHT_YES
        val barBg = if (isDark) Color.parseColor("#111827") else Color.parseColor("#FFFFFF")
        window.statusBarColor = barBg
        window.navigationBarColor = barBg
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            @Suppress("DEPRECATION")
            var flags = window.decorView.systemUiVisibility
            flags = if (isDark) {
                flags and View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR.inv() and View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR.inv()
            } else {
                flags or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR or View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
            }
            @Suppress("DEPRECATION")
            window.decorView.systemUiVisibility = flags
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        applySystemBarsTheme()

        webView = WebView(this)
        setContentView(webView)

        val settings: WebSettings = webView.settings
        settings.javaScriptEnabled = true
        settings.domStorageEnabled = true
        settings.databaseEnabled = true
        settings.allowFileAccess = true
        settings.allowContentAccess = true
        settings.useWideViewPort = true
        settings.loadWithOverviewMode = true
        settings.builtInZoomControls = false
        settings.displayZoomControls = false
        settings.cacheMode = WebSettings.LOAD_DEFAULT
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            settings.isAlgorithmicDarkeningAllowed = true
        }

        webView.webViewClient = WebViewClient()
        webView.webChromeClient = WebChromeClient()
        webView.loadUrl("file:///android_asset/index.html")
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        applySystemBarsTheme()
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        if (this::webView.isInitialized && webView.canGoBack()) {
            webView.goBack()
        } else {
            super.onBackPressed()
        }
    }
}
