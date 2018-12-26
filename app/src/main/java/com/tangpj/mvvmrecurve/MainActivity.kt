package com.tangpj.mvvmrecurve

import android.os.Bundle
import com.tangpj.mvvmrecurve.databinding.CollasingTestBinding
import com.tangpj.mvvmrecurve.databinding.ContentTestBinding
import com.tangpj.recurve.ui.acitivty.RecurveActivity

class MainActivity: RecurveActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = initContentBinding<ContentTestBinding>(R.layout.content_test)
        appbar {
            scrollEnable = false
            toolBar {
                title = "测试测试"
            }
        }
    }
}