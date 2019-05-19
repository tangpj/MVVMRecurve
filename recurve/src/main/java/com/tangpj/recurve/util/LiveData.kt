package com.tangpj.recurve.util

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.Transformations

fun <X, Y>Transformations.singelSwitchMap(
        source: LiveData<X> ,
        switchMapFunction: (X) -> LiveData<Y>) : LiveData<Y>{
    val result = MediatorLiveData<Y>()
    result.addSource(source, object : Observer<X> {
        var mSource: LiveData<Y>? = null

        override fun onChanged(x: X) {
            val newLiveData = switchMapFunction.invoke(x)
            if (mSource === newLiveData) {
                return
            }
            mSource?.let {
                result.removeSource(it)
            }
        }
    })
    return result
}