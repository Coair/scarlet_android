package com.coair.base.utli

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.util.Log
import androidx.appcompat.app.AppCompatActivity

/**
 * author : zhoulei
 * desc : 监控upan插入与拔出,封装了广播，调用[register]注册，离开页面后，调用[unregister]
 * version: 1.0
 */
object UpanWatcher {

    const val TAG = "U盘"

    private var mListener: UpanListener? = null

    private var mActivity: AppCompatActivity? = null

    fun register(activity: AppCompatActivity, listener: UpanListener) {
        mActivity = activity
        mListener = listener
        val iFilter = IntentFilter()
        iFilter.addAction(Intent.ACTION_MEDIA_EJECT)
        iFilter.addAction(Intent.ACTION_MEDIA_MOUNTED)
        iFilter.addAction(Intent.ACTION_MEDIA_REMOVED)
        iFilter.addAction(Intent.ACTION_MEDIA_UNMOUNTED)
        iFilter.addDataScheme("file")
        activity.registerReceiver(mBroadcastReceiver, iFilter)
    }

    /**
     * 一定要调用，可以放在基类的onDestroy中，防止忘记导致内存泄漏
     */
    fun unregister() {
        mActivity?.unregisterReceiver(mBroadcastReceiver)
        mActivity = null
        mListener = null
    }

    private val mBroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val action = intent.action
            if (action == Intent.ACTION_MEDIA_EJECT) {
                //USB设备移除，更新UI
                mActivity?.runOnUiThread {
                    mListener?.onUpanRemove()
                }
            } else if (action == Intent.ACTION_MEDIA_MOUNTED) {
                //USB设备挂载，更新UI
                //（usb在手机上的路径）
                val usbPath = intent.dataString.also { Log.i(TAG, it ?: "") }
                mActivity?.runOnUiThread {
                    mListener?.onUpanRead(usbPath)
                }
            }
        }
    }

    /**
     * 两个方法都在UI线程执行
     */
    interface UpanListener {
        /**
         * [usbPath]为U盘的挂载路径
         *
         * 注意：[usbPath]直接访问可能会有路径上的问题
         */
        fun onUpanRead(usbPath: String?)

        /**
         * 看需要覆写
         */
        fun onUpanRemove() {
            Log.i(TAG, "u盘已经拔出")
        }
    }
}