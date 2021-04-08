package com.coair.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.blankj.utilcode.util.AdaptScreenUtils
import com.blankj.utilcode.util.ScreenUtils
import com.coair.base.utli.event.BindEventBus
import org.greenrobot.eventbus.EventBus
import android.content.res.Resources as Resources1


/**
 * 共同的基类
 */
abstract class BaseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //判断是否需要注册EventBus
        if (this.javaClass.isAnnotationPresent(BindEventBus::class.java)) {
            EventBus.getDefault().register(this)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (this.javaClass.isAnnotationPresent(BindEventBus::class.java)) {
            EventBus.getDefault().unregister(this)
        }
    }

    override fun getResources(): Resources1 {
        return if (ScreenUtils.isLandscape())
            AdaptScreenUtils.adaptHeight(super.getResources(), 1080)
        else
            AdaptScreenUtils.adaptWidth(super.getResources(), 1080)
    }

}
