package com.coair.base

import android.annotation.SuppressLint
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.blankj.utilcode.util.*
import com.coair.base.ProjectFrameConfig.logPath
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.File


/**
 * 通用的启动页面
 * 一闪而过的白屏，存在的主要目的是申请权限
 */
abstract class FirstActivity : AppCompatActivity() {

    override fun onStart() {
        super.onStart()
        callPermission()

        lifecycleScope.launchWhenStarted {

        }
    }

    /**
     * 权限列表
     */
    private fun callPermission() {
        permissions()
            .rationale { _, shouldRequest -> showRationaleDialog(shouldRequest) }
            .callback(object : PermissionUtils.FullCallback {
                @SuppressLint("MissingPermission")
                override fun onGranted(permissionsGranted: List<String>) {
                    LogUtils.d(permissionsGranted)
                    GlobalScope.launch {
                        if (ProjectFrameConfig.newDirs) {
                            ProjectFrameConfig.dirs.forEach {
                                FileUtils.createOrExistsDir(it)
                            }
                        }

                        if (ProjectFrameConfig.hasLog) {
                            val root = ProjectFrameConfig.rootPath
                            FileUtils.createOrExistsDir(root)
                            FileUtils.createOrExistsDir(logPath)

                            LogUtils.getConfig()
                                .setSaveDays(30)
                                .setDir(logPath)
                                .setLogHeadSwitch(false)
                                .setBorderSwitch(false)
                                .setLog2FileSwitch(true)

                            CrashUtils.init(logPath + File.separator + "crash")
                        }
                    }
                    afterCallPermission()
                    finish()
                }

                override fun onDenied(
                    permissionsDeniedForever: List<String>,
                    permissionsDenied: List<String>
                ) {
                    if (permissionsDeniedForever.isNotEmpty()) {
                        showOpenAppSettingDialog()
                    }
                    LogUtils.d(permissionsDeniedForever, permissionsDenied)
                }
            })
            .theme { activity -> ScreenUtils.setFullScreen(activity) }
            .request()
    }

    abstract fun permissions(): PermissionUtils

    abstract fun afterCallPermission()
}

fun showRationaleDialog(shouldRequest: PermissionUtils.OnRationaleListener.ShouldRequest) {
    val topActivity = ActivityUtils.getTopActivity() ?: return
    AlertDialog.Builder(topActivity)
        .setMessage("您已经拒绝我们申请授权，请同意授权，否则功能将无法正常使用!")
        .setPositiveButton("同意") { _, _ -> shouldRequest.again(true) }
        .setNegativeButton("拒绝") { _, _ -> shouldRequest.again(false) }.setCancelable(false)
        .create()
        .show()
}

fun showOpenAppSettingDialog() {
    val topActivity = ActivityUtils.getTopActivity() ?: return
    AlertDialog.Builder(topActivity)
        .setMessage("我们需要一些您拒绝或系统申请失败的权限，请手动在设置页面授权，否则功能不能正常使用!")
        .setPositiveButton("同意") { _, _ -> PermissionUtils.launchAppDetailsSettings() }
        .setNegativeButton("拒绝") { _, _ -> }.setCancelable(false)
        .create()
        .show()
}

