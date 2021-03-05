package com.class100.yunshixun

import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import com.class100.atropos.env.context.AtPrefs
import com.class100.hades.http.HaApiCallback
import com.class100.hades.http.HaHttpClient
import com.class100.khaos.KhAbsSdk
import com.class100.khaos.KhSdkAbility
import com.class100.khaos.KhSdkManager
import com.class100.khaos.req.KhReqStartMeeting
import com.class100.oceanides.OcActivity
import com.class100.oceanides.camera.OcCameraActivity
import com.class100.poseidon.PsWebActivity
import com.class100.yunshixun.model.request.YsxTokenRequest
import kotlinx.android.synthetic.main.activity_devops.*

class DevOpsActivity : OcActivity() {
    companion object {
        private val TAG = DevOpsActivity::javaClass.name
        private const val DEV_OPS_URL_HISTORY = "dev_ops_url_history";

        fun launch(context: Context) {
            context.startActivity(Intent(context, DevOpsActivity::class.java))
        }
    }

    override fun getContentLayout(): Int {
        return R.layout.activity_devops
    }

    override fun init() {
        val data = AtPrefs.get(DEV_OPS_URL_HISTORY, "");
        et_url.setText(data)
        btn_go.setOnClickListener {
            PsWebActivity.launch(it.context, et_url.text.toString())
            AtPrefs.put(DEV_OPS_URL_HISTORY, et_url.text.toString())
        }

        btn_camera.setOnClickListener {
            OcCameraActivity.launch(it.context)
        }

        btn_get_token.setOnClickListener {
            HaHttpClient.getInstance().enqueue(
                YsxTokenRequest("15928695284", "ZSC1988love"),
                object : HaApiCallback<String> {
                    override fun onError(code: Int, message: String?) {
                        Log.d(TAG, "error:$code, message")
                    }

                    override fun onSuccess(content: String?) {
                        Log.d(TAG, "ok:$content")
                    }
                })
        }

        btn_start_meeting.setOnClickListener {
            KhSdkManager.registerYsxSdk("13512345678")
            KhSdkManager.getInstance().load(object : KhSdkAbility.OnSdkInitializeListener {
                override fun onInitialized(sdk: KhAbsSdk) {
                    val req = KhReqStartMeeting()
                    req.topic = "Happy New Year"
                    KhSdkManager.getInstance().sdk.startMeeting(this@DevOpsActivity, req)
                }

                override fun onError() {
                    showToast("load Khaos sdk failed !")
                }
            })

        }

    }

    private fun showToast(text: CharSequence) {
        runOnUiThread {
            Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
        }
    }

}