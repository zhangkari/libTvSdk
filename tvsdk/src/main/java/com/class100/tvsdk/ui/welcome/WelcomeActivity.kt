package com.class100.tvsdk.ui.welcome

import android.view.KeyEvent
import androidx.viewpager.widget.ViewPager
import com.class100.oceanides.OcActivity
import com.class100.tvsdk.R
import com.class100.tvsdk.exts.gotoWifiSetting
import com.class100.tvsdk.exts.isWifiConnected

open class WelcomeActivity : OcActivity() {
    private val viewPager = lazy {
        val vp = findViewById<ViewPager>(R.id.vp_welcome)
        vp.isEnabled = false
        vp
    }

    private val adapter = lazy {
        val adapter = WelcomePagerAdapter(supportFragmentManager)
        adapter.addFragment(PrepareBatteryFragment())
        adapter.addFragment(ConnectControllerFragment())
        adapter
    }

    override fun getContentLayout(): Int {
        return R.layout.ts_activity_welcome
    }

    override fun init() {
        super.init()
        initViewPager()
    }

    private fun initViewPager() {
        viewPager.value.adapter = adapter.value
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        when (keyCode) {
            KeyEvent.KEYCODE_DPAD_CENTER,
            KeyEvent.KEYCODE_DPAD_RIGHT,
            KeyEvent.KEYCODE_DPAD_DOWN -> {
                val current = viewPager.value.currentItem
                if (current + 1 < viewPager.value.childCount) {
                    viewPager.value.currentItem = current + 1
                } else {
                    arriveLastGuidePage()
                }
                return true
            }
        }
        return super.onKeyDown(keyCode, event)
    }

    protected open fun arriveLastGuidePage() {
        if (!isWifiConnected()) {
            gotoWifiSetting()
        }
        finish()
    }
}