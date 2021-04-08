package com.coair.scarletdemo

import android.os.Bundle
import com.coair.base.BaseActivity
import com.coair.base.utli.event.BindEventBus
import com.coair.scarletdemo.databinding.ActivityMainBinding
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

@BindEventBus
class MainActivity : BaseActivity() {
    lateinit var bind: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityMainBinding.inflate(layoutInflater)
        setContentView(bind.root)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun receiveString(bind: String) {

    }
}