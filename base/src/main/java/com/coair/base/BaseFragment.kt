package com.coair.base

import androidx.fragment.app.Fragment
import com.coair.base.utli.event.BindEventBus
import io.reactivex.disposables.Disposable
import org.greenrobot.eventbus.EventBus


abstract class BaseFragment : Fragment() {
    val disposables = mutableListOf<Disposable>()


    override fun onStart() {
        super.onStart()
        if (this.javaClass.isAnnotationPresent(BindEventBus::class.java)) {
            EventBus.getDefault().register(this)
        }
    }

    override fun onStop() {
        super.onStop()
        if (this.javaClass.isAnnotationPresent(BindEventBus::class.java)) {
            EventBus.getDefault().unregister(this)
        }
    }

}