package com.coair.base.utli.ui

import com.coair.base.OneOffLiveData
import com.coair.base.postOneOff

object TipDialog {
    val tipDialogLiveData = OneOffLiveData<Message>()
    fun success(s: String? = null) {
        tipDialogLiveData.postOneOff(
            Message.Success(s)
        )
    }

    fun loading(s: String? = null) {
        tipDialogLiveData.postOneOff(
            Message.Loading(s)
        )
    }

    fun info(s: String? = null) {
        tipDialogLiveData.postOneOff(
            Message.Info(s)
        )
    }

    fun fail(s: String? = null) {
        tipDialogLiveData.postOneOff(
            Message.Fail(s)
        )
    }

    fun dismiss() {
        tipDialogLiveData.postOneOff(
            Message.Dismiss()
        )
    }


    sealed class Message(val msg: String?) {
        class Loading(msg: String? = null) : Message(msg)
        class Success(msg: String? = null) : Message(msg)
        class Info(msg: String? = null) : Message(msg)
        class Fail(msg: String? = null) : Message(msg)
        class Dismiss() : Message(null)
    }
}