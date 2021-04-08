package com.coair.base.utli

import com.blankj.utilcode.util.ConvertUtils
import com.blankj.utilcode.util.DeviceUtils
import com.blankj.utilcode.util.ScreenUtils

val deviceDescription = {
    val screenWidth = ScreenUtils.getScreenWidth()
    val screenHeight = ScreenUtils.getScreenHeight()
    "dip:${ScreenUtils.getScreenDensityDpi()} 屏幕密度:${ScreenUtils.getScreenDensity()}\n" +
            "屏幕的宽度（单位：px）：${screenWidth}  屏幕的高度（单位：px）：${screenHeight}\n" +
            "屏幕的宽度（单位：dp）：${ConvertUtils.px2dp(screenWidth.toFloat())}  屏幕的高度（单位：dp）：${
                ConvertUtils.px2dp(
                    screenHeight.toFloat()
                )
            }\n" +
            "设备名称:${DeviceUtils.getModel()}"
}