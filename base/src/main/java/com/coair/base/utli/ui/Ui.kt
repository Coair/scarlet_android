package com.coair.base.utli.ui

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.view.View
import android.view.WindowManager
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.viewbinding.ViewBinding
import com.blankj.utilcode.util.ToastUtils
import com.coair.base.R
import com.google.android.material.textfield.TextInputEditText
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty


fun setVisible(v: View, isVisible: Boolean) {
    v.visibility = if (isVisible) View.VISIBLE else View.GONE
}

fun View.visible(isVisible: Boolean) {
    if (visibility == View.GONE && isVisible) {
        visibility = View.VISIBLE
    } else if (visibility == View.VISIBLE && !isVisible) {
        visibility = View.GONE
    }
}

fun View.gone() {
    visibility = View.GONE
}

fun View.invisible() {
    visibility = View.INVISIBLE
}


fun View.visible() {
    visibility = View.VISIBLE
}


inline fun <reified T: Activity> Activity.starActivity(){
    startActivity(Intent(this,T::class.java))
}


/**
 *@desc 认证密码
 *@author rick
 *@time 2019/9/26
 */
fun showVerifyDialog(activity: FragmentActivity, action: () -> Unit) {
    val view = activity.layoutInflater.inflate(R.layout.item_edittext_info_v2, null)
    val etPwd = view.findViewById<TextInputEditText>(R.id.etPwd)
    val build = AlertDialog.Builder(activity).setTitle("请输入管理密码")
        .apply {
            setView(view)
            setPositiveButton("确定") { dialog, which ->
                if (etPwd.text.toString().trim() == "1234") {
                    dialog.dismiss()
                    action()
                } else {
                    ToastUtils.showLong("密码错误！")
                }
            }
            setNegativeButton("取消") { dialog, which ->

            }
        }

    build.create().apply {
        window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE)
        show()
        etPwd.requestFocus()
    }
}


inline fun <reified VB : ViewBinding> Fragment.bindView() =
    FragmentBindingDelegate(VB::class.java)

class FragmentBindingDelegate<VB : ViewBinding>(
    private val clazz: Class<VB>
) : ReadOnlyProperty<Fragment, VB> {

    private var isInitialized = false
    private var _binding: VB? = null
    private val binding: VB get() = _binding!!

    override fun getValue(thisRef: Fragment, property: KProperty<*>): VB {
        if (!isInitialized) {
            thisRef.viewLifecycleOwner.lifecycle.addObserver(object : LifecycleObserver {
                @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
                fun onDestroyView() {
                    _binding = null
                }
            })
            _binding = clazz.getMethod("bind", View::class.java)
                .invoke(null, thisRef.requireView()) as VB
            isInitialized = true
        }
        return binding
    }
}