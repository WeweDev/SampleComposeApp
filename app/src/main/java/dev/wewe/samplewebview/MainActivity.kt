package dev.wewe.samplewebview

import android.graphics.Bitmap
import android.os.Bundle
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import dev.wewe.samplewebview.ui.theme.SampleWebViewTheme

class MainActivity : ComponentActivity() {
    val URL = "https://google.com"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SampleWebViewTheme {
                var webView: WebView? = null
                var backEnabled by remember { mutableStateOf(false) }
                Column {
                    AndroidView(
                        factory = { context ->
                            WebView(context).apply {
                                settings.loadWithOverviewMode = true
                                settings.javaScriptEnabled = true
                                webViewClient = object : WebViewClient() {
                                    override fun shouldOverrideUrlLoading(
                                        view: WebView?,
                                        request: WebResourceRequest?,
                                    ): Boolean {
                                        if (request?.url.toString().startsWith("url_to_redirect")) {
                                            // Handle action on specific url
                                            return true
                                        }
                                        return super.shouldOverrideUrlLoading(view, request)
                                    }

                                    override fun onPageStarted(
                                        view: WebView,
                                        url: String?,
                                        favicon: Bitmap?,
                                    ) {
                                        super.onPageStarted(view, url, favicon)
                                        backEnabled = view.canGoBack()
                                    }
                                }

                            }

                        },
                        update = {
                            webView = it
                            it.loadUrl(URL)
                        }
                    )
                }

                BackHandler(enabled = backEnabled) {
                    webView?.goBack()
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Column {
        Text(
            text = "Hello $name!",
            modifier = modifier
        )
        Text(
            text = "Hello $name!",
            modifier = modifier
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    SampleWebViewTheme {
        Greeting("ios")
    }
}