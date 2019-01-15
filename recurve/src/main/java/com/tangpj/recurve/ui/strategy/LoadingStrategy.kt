package com.tangpj.recurve.ui.strategy

import android.app.AlertDialog
import android.app.Dialog
import android.view.LayoutInflater
import android.view.View
import androidx.databinding.ViewDataBinding


/**
 * 家在回调策略
 * 
 * @className:
 * @author: tangpengjian113
 * @createTime: 2019/1/15 12:24
 */
abstract class LoadingStrategy{

    //重试事件
    var retryCallback: (() -> Unit)? = null

    //取消事件
    var cancelCallback: (() -> Boolean)? = null

    /**
     * 加载回调事件
     *
     * @method: loadingCallback
     * @author: tangpengjian113
     * @createTime: 2019/1/15 13:06
     *
     * @return isLoading 是否加载中
     *         content 加载成功后现实的内容
     */
    abstract fun loadingCallback() : (isLoading: Boolean, content: View) -> Unit

}

abstract class ViewLoadingStrategy(private val binding: ViewDataBinding) : LoadingStrategy() {

    override fun loadingCallback(): (isLoading: Boolean, content: View) -> Unit = {isLoading, content ->
        if (isLoading){
            content.visibility = View.GONE
            binding.root.visibility = View.VISIBLE
        }else{
            content.visibility = View.VISIBLE
            binding.root.visibility = View.GONE
        }
    }

}

 class DialogLoadingStrategy(private val mDialog: Dialog ) : LoadingStrategy() {

     override fun loadingCallback(): (isLoading: Boolean, content: View) -> Unit =  { isLoading, _ ->
         if(isLoading) {
             mDialog.show()
         } else {
             mDialog.dismiss()
         }
     }
 }