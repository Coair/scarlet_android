package com.coair.base.utli.serialport

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.util.Log

/**
 *@desc
 *@author chensiqi
 *@time 2019/11/22
 */

class SerialPortReceiver : BroadcastReceiver() {

    private val TAG = "SerialBroadcastReceiver"

    override fun onReceive(context: Context, intent: Intent) {
        Log.i(TAG, "enter  onReceive")
        val action = intent.action
        if (action != null && action == Intent.ACTION_SCREEN_ON) { // 屏幕开启后打开串口1
            Log.i(TAG, "recevied  ACTION_SCREEN_ON ")
//            if (boxPort == null) {
//                initSerialPort()
//            }
        }

        if (action != null && action == Intent.ACTION_SCREEN_OFF) {  // 锁屏后关闭串口
            Log.i(TAG, "recevied  ACTION_SCREEN_OFF ")
//            if (boxPort != null) {
//                closeSerialPort()
//                mReadBoxThread!!.interrupt()
//            }
        }
    }
}


//注册  锁屏广播
fun registerAction(context: Context, receiver: BroadcastReceiver) {
    Log.i("SerialPortReceiver", "register SerialPortReceiver Action")
    val filter = IntentFilter()
    filter.addAction(Intent.ACTION_SCREEN_ON)
    filter.addAction(Intent.ACTION_SCREEN_OFF)
    context.registerReceiver(receiver, filter)
}
