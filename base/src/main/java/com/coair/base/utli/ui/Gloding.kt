//package com.coair.base.utli.ui
//
//import android.annotation.SuppressLint
//import android.content.Context
//import android.view.Gravity
//import android.view.LayoutInflater
//import android.view.View
//import android.widget.ImageView
//import android.widget.LinearLayout
//import android.widget.TextView
//import com.billy.android.loading.Gloading.*
//import com.blankj.utilcode.util.NetworkUtils
//import com.coair.base.R
//
//
//class GlobalAdapter : Adapter {
//    override fun getView(
//        holder: Holder,
//        convertView: View,
//        status: Int
//    ): View {
//        //convertView为可重用的布局
//        //Holder中缓存了各状态下对应的View
//        //	如果status对应的View为null，则convertView为上一个状态的View
//        //	如果上一个状态的View也为null，则convertView为null
//        var loadingStatusView: GlobalLoadingStatusView? = null
//        //reuse the old view, if possible
//        //reuse the old view, if possible
//        if (convertView != null && convertView is GlobalLoadingStatusView) {
//            loadingStatusView = convertView
//        }
//        if (loadingStatusView == null) {
//            loadingStatusView = GlobalLoadingStatusView(holder.context, holder.retryTask)
//        }
//        loadingStatusView.setStatus(status)
//        val data: Any = holder.getData()
//        //show or not show msg view
//        //show or not show msg view
//        val hideMsgView: Boolean = "hide_loading_status_msg" == data
//        loadingStatusView.setMsgViewVisibility(!hideMsgView)
//        return loadingStatusView
//    }
//}
//
//class GlobalLoadingStatusView(context: Context?, retryTask: Runnable?) :
//    LinearLayout(context), View.OnClickListener {
//    private val mTextView: TextView
//    private val mRetryTask: Runnable?
//    private val mImageView: ImageView
//    fun setMsgViewVisibility(visible: Boolean) {
//        mTextView.setVisibility(if (visible) VISIBLE else GONE)
//    }
//
//    @SuppressLint("MissingPermission")
//    fun setStatus(status: Int) {
//        var show = true
//        var onClickListener: View.OnClickListener? = null
//        var image: Int = R.drawable.loading
//        var str: Int = R.string.str_none
//        when (status) {
//            STATUS_LOAD_SUCCESS -> show = false
//            STATUS_LOADING -> str = R.string.loading
//            STATUS_LOAD_FAILED -> {
//                str = R.string.load_failed
//                image = R.drawable.icon_failed
//                val networkConn: Boolean = NetworkUtils.isConnected()
//                if (networkConn != null && !networkConn) {
//                    str = R.string.load_failed_no_network
//                    image = R.drawable.icon_no_wifi
//                }
//                onClickListener = this
//            }
//            STATUS_EMPTY_DATA -> {
//                str = R.string.empty
//                image = R.drawable.icon_empty
//            }
//            else -> {
//            }
//        }
//        mImageView.setImageResource(image)
//        setOnClickListener(onClickListener)
//        mTextView.setText(str)
//        setVisibility(if (show) View.VISIBLE else View.GONE)
//    }
//
//    override fun onClick(v: View?) {
//        mRetryTask?.run()
//    }
//
//    init {
//        setOrientation(VERTICAL)
//        setGravity(Gravity.CENTER_HORIZONTAL)
//        LayoutInflater.from(context).inflate(R.layout.view_global_loading_status, this, true)
//        mImageView = findViewById(R.id.image)
//        mTextView = findViewById(R.id.text)
//        mRetryTask = retryTask
//        setBackgroundColor(-0xf0f10)
//    }
//}