package com.coair.base.utli.ui

import android.widget.Switch

/**
 * 用来忽略代码导致的状态改变
 */
var ignoreChange = false
fun Switch.on() {
    ignoreChange = true
    isChecked = true
    ignoreChange = false
}

fun Switch.off() {
    ignoreChange = true
    isChecked = false
    ignoreChange = false
}

fun Switch.onChange(onChange: (Boolean) -> Unit) {
    setOnCheckedChangeListener { _, isChecked ->
        if (!ignoreChange) {
            onChange(isChecked)
        }
    }
}