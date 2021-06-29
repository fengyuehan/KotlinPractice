package com.example.webview

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.FrameLayout
import androidx.core.view.isVisible
import com.example.common.Constants
import com.example.common.base.BaseActivity
import com.example.webview.databinding.ActivityWebviewBinding


class WebviewActivity :BaseActivity<ActivityWebviewBinding>() {
    private lateinit var mWebView: WebView

    override fun initData(savedInstanceState: Bundle?) {
        val url = intent.extras?.getString(Constants.KEY_WEBVIEW_PATH)
        val title = intent.extras?.getString(Constants.KEY_WEBVIEW_TITLE)
        initToolBar(title)
        initWebView(url)
    }

    private fun initWebView(url: String?) {
        mWebView = WebView(this)
        val layoutParams:FrameLayout.LayoutParams = FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT)
        mWebView.layoutParams = layoutParams
        mBinding?.flWebviewContain?.addView(mWebView)
        val webSettings = mWebView.settings
        webSettings.domStorageEnabled = true
        webSettings.useWideViewPort = true
        webSettings.loadWithOverviewMode  = true
        webSettings.javaScriptEnabled = true

        mWebView.webViewClient = object :WebViewClient(){
            override fun shouldOverrideUrlLoading(
                view: WebView?,
                request: WebResourceRequest?
            ): Boolean {
                if (url == null){
                    return false
                }
                try {
                    if (url.startsWith("weixin://")||url.startsWith("jianshu://")){
                        val  intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                        startActivity(intent)
                        return true
                    }
                }catch (e:Exception){
                    return false
                }
                view?.loadUrl(url)
                return true
            }
        }

        mWebView.webChromeClient = object :WebChromeClient(){
            override fun onProgressChanged(view: WebView?, newProgress: Int) {
                mBinding?.progressBar?.isVisible = newProgress != 100
                mBinding?.progressBar?.progress  = newProgress
            }
        }

        mWebView.loadUrl(url?:"")
    }

    private fun initToolBar(title: String?) {
        mBinding?.run {
            setToolbarBackIcon(toolbarLayout.ivBack, R.drawable.ic_back_clear)
            title?.let {
                setToolbarTitle(toolbarLayout.tvTitle,it)
            }
            toolbarLayout.ivBack.setOnClickListener {
                finish()
            }
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_webview
    }

    override fun onDestroy() {
        mWebView.loadDataWithBaseURL(null,"","text/html", "utf-8", null)
        mWebView.clearHistory()
        (mWebView.parent as ViewGroup).removeView(mWebView)
        mWebView.destroy()
        super.onDestroy()
    }
}