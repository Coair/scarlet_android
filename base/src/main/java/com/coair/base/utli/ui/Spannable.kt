package com.coair.base.utli.ui

import android.text.SpannableString
import android.text.Spanned
import android.text.style.CharacterStyle

fun CharacterStyle.creat(
    string: CharSequence,
    start: Int = 0,
    end: Int = string.length,
    flags: Int = Spanned.SPAN_INCLUSIVE_EXCLUSIVE
) = SpannableString(string).also {
    it.setSpan(
        this,
        start,
        end,
        flags
    )
}

