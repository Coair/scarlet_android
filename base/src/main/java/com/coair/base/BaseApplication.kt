package com.coair.base

import android.app.Application
import androidx.lifecycle.*
import com.blankj.utilcode.util.*
import com.tencent.mmkv.MMKV
import java.io.File

open class BaseApplication : Application() , ViewModelStoreOwner {
    private val appViewModelStore: ViewModelStore by lazy {
        ViewModelStore()
    }

    override fun getViewModelStore(): ViewModelStore {
        return appViewModelStore
    }

    companion object {
        val application by lazy { this }
    }

    override fun onCreate() {
        super.onCreate()
        Utils.init(this)

        val screenWidth = ScreenUtils.getScreenWidth()
        val screenHeight = ScreenUtils.getScreenHeight()

        LogUtils.i(
            "dip:${ScreenUtils.getScreenDensityDpi()} 屏幕密度:${ScreenUtils.getScreenDensity()}\n" +
                    "屏幕的宽度（单位：px）：${screenWidth}  屏幕的高度（单位：px）：${screenHeight}\n" +
                    "屏幕的宽度（单位：dp）：${ConvertUtils.px2dp(screenWidth.toFloat())}  屏幕的高度（单位：dp）：${
                        ConvertUtils.px2dp(
                            screenHeight.toFloat()
                        )
                    }\n" +
                    "设备名称:${DeviceUtils.getModel()}"
        )
    }
}

object ProjectFrameConfig {
    var hasLog = false
    var newDirs = false
    private var projectName = "grid_terminal"
    val dirs = mutableListOf<String>()

    fun init(projectName: String = "demo", block: ProjectFrameConfig.() -> Unit) {
        this.projectName = projectName
        this.block()
    }

    fun standardPath(dirName: String): String {
        newDirs = true
        val path = rootPath + File.separator + dirName
        dirs.add(path)
        return path
    }

    val rootPath by lazy {
        PathUtils.getExternalStoragePath() + File.separator + "dp" + File.separator + projectName
    }

    val logPath by lazy {
        rootPath + File.separator + "log"
    }
}
/**
*目标navigationId
*/
const val TARGET="target"
/**
 * 是否需要登录
*/
const val NEED_LOGIN="needLogin"
/**
*是否需要认证
*/
const val NEED_IDENTIFY="needIdentify"

const val NEED_FLOAT_BACK="needFloatBack"

