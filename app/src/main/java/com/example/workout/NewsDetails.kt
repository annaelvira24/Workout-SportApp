package com.example.workout

import android.content.Context
import android.os.Bundle
import android.webkit.WebChromeClient
import android.webkit.WebView
import androidx.appcompat.app.AppCompatActivity


class NewsDetails : AppCompatActivity(){
    private lateinit var mWebView: WebView
    private val context: Context? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.news_webview)

        val title = this.intent.extras!!.getString("title")
        val url = this.intent.extras!!.getString("url")

        setTitle(title);

        mWebView = findViewById<WebView>(R.id.wvNews);

        mWebView.webChromeClient = WebChromeClient()

        mWebView.loadUrl(url);
    }
}