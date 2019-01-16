package com.tangpj.recurve.ui.strategy

import android.app.Dialog
import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer


/**
 * 加载回调策略
 * 
 * @className: LoadingStrategy
 * @author: tangpengjian113
 * @createTime: 2019/1/15 12:24
 *
 * @param lifecycleOwner 监听生命周期
 * @param content 组建的布局内容（加载完后要显示的布局）
 */
abstract class LoadingStrategy(
        private val lifecycleOwner: LifecycleOwner,
        private val content: View){

    //重试事件
    var retryCallback: (() -> Unit)? = null

    //取消事件
    var cancelCallback: (() -> Boolean)? = null

    //Loading回调LiveData
    open var loadingLiveData: LiveData<Boolean>? = null

}

abstract class ViewLoadingStrategy(
        val lifecycleOwner: LifecycleOwner,
        val content: View,
        private val loadingViewBinding: ViewDataBinding
        ) : LoadingStrategy(lifecycleOwner,content) {

    override var loadingLiveData: LiveData<Boolean>?
        get() = super.loadingLiveData
        set(value) {
            value?.observe(lifecycleOwner, Observer {
                if(it){
                    content.visibility = View.GONE
                    loadingViewBinding.root.visibility = View.VISIBLE
                }else{
                    content.visibility = View.VISIBLE
                    loadingViewBinding.root.visibility = View.GONE
                }
            })
        }
}

class DialogLoadingStrategy(
        val lifecycleOwner: LifecycleOwner,
        val content: View,
        private val mDialog: Dialog)
     : LoadingStrategy(lifecycleOwner,content) {

    override var loadingLiveData: LiveData<Boolean>?
        get() = super.loadingLiveData
        set(value) {
            value?.observe(lifecycleOwner, Observer {
                if (it){
                    mDialog.show()
                }else{
                    mDialog.dismiss()
                }
            })
        }

}