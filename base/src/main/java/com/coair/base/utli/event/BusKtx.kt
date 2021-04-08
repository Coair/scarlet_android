package com.coair.base.utli.event

import org.greenrobot.eventbus.EventBus

val bus by lazy {
    EventBus.getDefault()
}

inline fun<reified T> T.post(){
    bus.post(this)
}