package com.coair.base.utli.ui

import android.R
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import com.blankj.utilcode.util.Utils

fun <T> Spinner.init(arr: Array<T>, onItemSelected: (position: Int) -> Unit) {
    val adapter = ArrayAdapter<T>(Utils.getApp(), R.layout.simple_list_item_1, arr)
    this.adapter = adapter
    this.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
        override fun onNothingSelected(parent: AdapterView<*>?) {

        }

        override fun onItemSelected(
            parent: AdapterView<*>?,
            view: View?,
            position: Int,
            id: Long
        ) {
            Log.i(id.toString(), arr[position].toString())
            onItemSelected(position)
        }
    }
}