package com.coair.base.utli.ui

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText


fun EditText.noLineBreaks(){
    val editText =this
    editText.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
        }

        override fun beforeTextChanged(
            s: CharSequence?,
            start: Int,
            count: Int,
            after: Int
        ) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            if (s.toString().contains("\n")) {
                val str: List<String> = s.toString().split("\n")
                var str1 = ""
                for (i in str.indices) {
                    str1 += str[i]
                }
                editText.setText(str1)
                editText.setSelection(start)
            }
        }
    })
}


fun EditText.noLineEndWithEnter(){
    val editText =this
    editText.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
        }

        override fun beforeTextChanged(
            s: CharSequence?,
            start: Int,
            count: Int,
            after: Int
        ) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            if (s.toString().endsWith("\n")) {
                val str1 =s.toString().slice(0..s.toString().length-2)
                editText.setText(str1)
                editText.setSelection(start)
            }
        }
    })
}

fun EditText.noStartWithBlock(){
    val editText =this
    editText.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
        }

        override fun beforeTextChanged(
            s: CharSequence?,
            start: Int,
            count: Int,
            after: Int
        ) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            if (s.toString().startsWith(" ")) {
                editText.setText("")
                editText.setSelection(start)
            }
        }
    })
}

fun EditText.noStartWith0(){
    val editText =this
    editText.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
        }

        override fun beforeTextChanged(
            s: CharSequence?,
            start: Int,
            count: Int,
            after: Int
        ) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            if (s.toString().startsWith("0")) {
                editText.setText("")
                editText.setSelection(start)
            }
        }
    })
}