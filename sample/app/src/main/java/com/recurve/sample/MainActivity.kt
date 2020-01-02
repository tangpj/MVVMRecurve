package com.recurve.sample

import android.os.Bundle
import com.recurve.core.ui.acitivty.RecurveActivity
import com.recurve.sample.databinding.CollasingTestBinding
import com.recurve.sample.databinding.ContentTestBinding

class MainActivity: RecurveActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        @Suppress("UNUSED_VARIABLE") val binding = initContentBinding<ContentTestBinding>(R.layout.content_test)
        appbar {
            scrollEnable = true
            scrollFlags = "scroll|exitUntilCollapsed"
            collapsingToolbar {
                contentScrimColor = R.color.colorAccent
                collapsingView { inflater, collapsingToolbarLayout ->
                    val content = CollasingTestBinding.inflate(inflater, collapsingToolbarLayout, false)
                    content.root
                }
            }
        }
    }
}