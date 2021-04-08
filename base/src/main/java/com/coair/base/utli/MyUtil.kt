package com.coair.base.utli

import android.view.View
import androidx.annotation.ColorRes
import com.blankj.utilcode.util.ColorUtils
import com.blankj.utilcode.util.ScreenUtils
import com.blankj.utilcode.util.StringUtils
import com.blankj.utilcode.util.Utils
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

//该文件放一些扩展方法,小框架,公用的实用的

/**
 * 获取屏幕像素大小信息
 */
fun getScreenMsg() =
    "屏幕的宽度:${ScreenUtils.getScreenWidth()}\n屏幕的高度:${ScreenUtils.getScreenHeight()}\n"


fun ByteArray.toHexString(): String =
    this.map {
        it.toUByte().toString(16).let { hexString ->
            if (hexString.length == 1) {
                "0$hexString"
            } else hexString
        }
    }.joinToString(" ")



fun List<Byte>.toHexString(): String =
    this.map {
        it.toUByte().toString(16).let { hexString ->
            if (hexString.length == 1) {
                "0$hexString"
            } else hexString
        }
    }.joinToString(" ")

fun Byte.toHexString(): String =
    this.toUByte().toString(16).let { hexString ->
        if (hexString.length == 1) {
            "0$hexString"
        } else hexString
    }


fun Byte.to2String(): String =
    this.toUByte().toString().let { string ->
        if (string.length == 1) {
            "0$string"
        } else string
    }

