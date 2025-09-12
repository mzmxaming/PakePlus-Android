package com.app.pakeplus

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.os.Bundle
import android.view.GestureDetector
import android.view.MotionEvent
import android.webkit.WebChromeClient
import android.webkit.WebResourceError
导入 android.webkit.WebResourceRequest
导入 android.webkit.WebView
导入 android.webkit.WebViewClient
导入 androidx.activity.enableEdgeToEdge
// 导入 android.view.Menu
// 导入 android.view.WindowInsets
// 导入 com.google.android.material.snackbar.Snackbar
// 导入 com.google.android.material.navigation.NavigationView
// 导入 androidx.navigation.findNavController
// 导入 androidx.navigation.ui.AppBarConfiguration
// 导入 androidx.navigation.ui.navigateUp
// 导入 androidx.navigation.ui.setupActionBarWithNavController
// 导入 androidx.navigation.ui.setupWithNavController
// 导入 androidx.drawerlayout.widget.DrawerLayout
// 导入 com.app.pakeplus.databinding.ActivityMainBinding
导入 androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GestureDetectorCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

//    private lateinit var appBarConfiguration: AppBarConfiguration
//    private lateinit var binding: ActivityMainBinding

    private lateinit var webView: WebView
    private lateinit var gestureDetector: GestureDetectorCompat

    @SuppressLint("SetJavaScriptEnabled", "ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContentView(R.layout.single_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.ConstraintLayout)) { view, insets ->
            val systemBar = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.setPadding(
                0, systemBar.top, 0, 0
            )
            insets
        }

        webView = findViewById<WebView>(R.id.webview)

        webView.settings.apply {
            javaScriptEnabled = true       // 启用JS
            domStorageEnabled = true       // 启用DOM存储（Vue 需要）
            允许文件访问 = 真         // 允许文件访问
            setSupportMultipleWindows(真)
        }

        // webView.settings.userAgentString = ""

        webView.settings.loadWithOverviewMode = 真
        webView.settings.setSupportZoom(false)

        // 清除缓存
        webView.clearCache(真)

        // 注入js
        webView.webViewClient = MyWebViewClient()

        // 获取网页加载进度
        webView.webChromeClient = MyChromeClient()

        // 设置手势检测器
        手势检测器 =
            GestureDetectorCompat(这个, 对象 : GestureDetector.SimpleOnGestureListener() {
                覆盖 乐趣 在滑动时(
                    e1: 事件动作?
                    e2: 按摩事件,
                    速度X：浮点数，
                    速度Y：浮点数
                ): 布尔 {
                    如果 (e1 == 无) 返回 假

                    值 差X = e2.x - e1.x
                    val diffY = e2.y - e1.y

                    // 仅处理水平滑动
                    如果 (Math.abs(diffX) > Math.abs(diffY)) {
                        如果 (Math.abs(diffX) > 100 && Math.abs(velocityX) > 100) {
                            如果 (diffX > 0) {
                                // 向右滑动 - 返回
                                如果 (webView可以返回()) {
                                    webView.goBack()
                                    返回 真
                                }
                            } 否则 {
                                // 向左滑动 - 向前
                                如果 (webView可以向前导航()) {
                                    webView.goForward()
                                    返回 真
                                }
                            }
                        }
                    }
                    返回 假
                }
            })

        // 为WebView设置触摸监听器
        webView.setOnTouchListener { _, event ->
            手势检测器.处理触摸事件(事件)
            假
        }

          webView.loadUrl("http://www.mxcgm.top:9999/ai/gwen.html")
//        webView.loadUrl("file:///android_asset/index.html")

//        binding = ActivityMainBinding.inflate(layoutInflater)
//        setContentView(R.layout.single_main)

//        setSupportActionBar(binding.appBarMain.toolbar)

//        binding.appBarMain.fab.setOnClickListener { view ->
//            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                .setAction("Action", null)
//                .setAnchorView(R.id.fab).show()
//        }

//        val drawerLayout: DrawerLayout = binding.drawerLayout
//        val navView: NavigationView = binding.navView
//        val navController = findNavController(R.id.nav_host_fragment_content_main)

        // 将每个菜单ID作为一组ID传递，因为每个
        // 菜单应被视为顶级目的地。
//        appBarConfiguration = AppBarConfiguration(
//            setOf(
//                R.id导航主页, R.id导航图库, R.id导航幻灯片
//            ), 抽屉布局
//        )
//        setupActionBarWithNavController(navController, appBarConfiguration)
//        navView.setupWithNavController(navController)
    }


    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack()
        } else {
            super.onBackPressed()
        }
    }

//    override fun onCreateOptionsMenu(menu: Menu): Boolean {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        menuInflater.inflate(R.menu.main, menu)
//        return true
//    }

//    override fun onSupportNavigateUp(): Boolean {
//        val navController = findNavController(R.id.nav_host_fragment_content_main)
//        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
//    }

    inner class MyWebViewClient : WebViewClient() {

        // vConsole debug
        private var debug = false

        @Deprecated("Deprecated in Java", ReplaceWith("false"))
        override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
            return false
        }

        override fun doUpdateVisitedHistory(view: WebView?, url: String?, isReload: Boolean) {
            super.doUpdateVisitedHistory(view, url, isReload)
        }

        override fun onReceivedError(
            view: WebView?,
            request: WebResourceRequest?,
            error: WebResourceError?
        ) {
            super.onReceivedError(view, request, error)
            println("webView onReceivedError: ${error?.description}")
        }

        override fun onPageFinished(view: WebView?, url: String?) {
            super.onPageFinished(view, url)
        }

        override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
            super.onPageStarted(view, url, favicon)
            if (debug) {
                // vConsole
                val vConsole = assets.open("vConsole.js").bufferedReader().use { it.readText() }
                val openDebug = """var vConsole = new window.VConsole()"""
                view?.evaluateJavascript(vConsole + openDebug, null)
            }
            // inject js
            val injectJs = assets.open("custom.js").bufferedReader().use { it.readText() }
            view?.evaluateJavascript(injectJs, null)
        }
    }

    inner class MyChromeClient : WebChromeClient() {
        override fun onProgressChanged(view: WebView?, newProgress: Int) {
            super.onProgressChanged(view, newProgress)
            val url = view?.url
            println("wev view url:$url")
        }
    }
}
