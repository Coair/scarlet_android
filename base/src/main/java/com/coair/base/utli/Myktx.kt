package com.coair.base.utli

import android.app.Activity
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.util.Log
import android.view.View
import androidx.activity.ComponentActivity
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelLazy
import androidx.lifecycle.ViewModelProvider
import com.blankj.utilcode.util.ActivityUtils
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.Utils
import com.coair.base.BaseApplication


typealias Action = () -> Unit

fun Boolean.byUndo(action: Action) {
    if (!this) action()
}

val stringAppend: (String, String) -> String = { x, y -> x + y }


fun <T> T.logi(): T {
    Log.i("调试看看", this.toString())
    return this
}

val bindFuncToViews: (() -> Unit) -> (() -> List<View>) -> Unit = { onClick ->
    { list ->
        list().forEach {
            it.setOnClickListener {
                onClick()
            }
        }
    }
}

/**
 * 几项常用DialogFragment设置封装
 */
fun DialogFragment.initWindow(w: Int, h: Int, outCancel: Boolean) {
    dialog?.window?.run {
        setLayout(w, h)
        setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }
    dialog?.setCanceledOnTouchOutside(outCancel)
}

inline fun <reified T> MutableLiveData<T>.edit(doSome: T.() -> Unit) {
    val oldV = this.value
    if (oldV != null) {
        doSome(oldV)
        postValue(oldV)
    } else throw NullPointerException("MutableLiveData value null")

}


operator fun <T, U> MutableLiveData<MutableMap<T, U>>.get(key: T) = value?.get(key)
fun <T, U> MutableLiveData<MutableMap<T, U>>.contains(key: T) =
    value?.contains(key) ?: false

fun <T, U> MutableLiveData<MutableMap<T, U>>.containsKey(key: T) =
    value?.containsKey(key) ?: false


fun <T, U> MutableLiveData<MutableMap<T, U>>.put(key: T, mapValue: U) =
    this.edit {
        put(key, mapValue)
    }

operator fun <T, U> MutableLiveData<MutableMap<T, U>>.set(key: T, mapValue: U) =
    this.edit {
        put(key, mapValue)
    }

fun <T, U> MutableLiveData<MutableMap<T, U>>.remove(key: T) =
    this.edit {
        remove(key)
    }

typealias DoWhat = () -> Unit

fun MotionLayout.onTransComplete(doWhat: DoWhat) {
    addTransitionListener(object : MotionLayout.TransitionListener {
        override fun onTransitionStarted(p0: MotionLayout?, p1: Int, p2: Int) {
        }

        override fun onTransitionChange(p0: MotionLayout?, p1: Int, p2: Int, p3: Float) {
        }

        override fun onTransitionCompleted(p0: MotionLayout?, p1: Int) {
            doWhat()
        }

        override fun onTransitionTrigger(p0: MotionLayout?, p1: Int, p2: Boolean, p3: Float) {
        }

    })
}

inline fun <reified VM : ViewModel>
        appViewModels(): Lazy<VM> {
    return ViewModelLazy(VM::class,
        { (Utils.getApp() as BaseApplication).viewModelStore },
        { ViewModelProvider.NewInstanceFactory() }
    )
}

inline fun <reified T :Activity> toActivity(){
    ActivityUtils.startActivity(T::class.java)
}