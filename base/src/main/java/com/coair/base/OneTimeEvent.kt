package com.coair.base

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer


/**
 * 一次性的事件
 */
class OneTimeEvent<T>(private val event: T) {
    private var flag = true

    /**
     * [func]只会生效一次,
     * [call]和[get]只会成功调用一次
     */
    fun call(func: (T) -> Unit) {
        if (flag) {
            flag = false
            func(event)
        }
    }

    /**
     * 只会得到一次值,
     * [call]和[get]只会成功调用一次
     */
    fun get(): T? = if (flag) {
        flag = false
        event
    } else {
        null
    }
}

/**
 * 返回一个一次性事件的封装对象
 */
fun <T> T.oneOff() = OneTimeEvent(this)

/**
 * 发射一次一次性事件
 */
fun <T> MutableLiveData<OneTimeEvent<T>>.postOneOff(someOneOff: T) {
    this.postValue(OneTimeEvent(someOneOff))
}


class OneOffLiveData<T> : MutableLiveData<OneTimeEvent<T>>() {
    /**
     * 替代掉LiveData的observe方法
     */
    fun observeOneOff(owner: LifecycleOwner, func: (T?) -> Unit) {
        observe(owner, Observer {
            it?.call(func)
        })
    }
}