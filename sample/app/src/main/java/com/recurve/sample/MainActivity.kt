package com.recurve.sample

import android.content.Intent
import android.os.Bundle
import com.recurve.core.ui.acitivty.RecurveActivity
import com.recurve.sample.databinding.ContentMainBinding
import com.recurve.sample.databinding.ContentTestBinding
import com.recurve.sample.paging.PagedActivity

class MainActivity: RecurveActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = initContentBinding<ContentMainBinding>(R.layout.content_main)
        appbar {
            scrollEnable = true
            scrollFlags = "scroll|exitUntilCollapsed"

        }
        binding.btnPagingRoom.setOnClickListener {
            startActivity(Intent(this@MainActivity, PagedActivity::class.java ))
        }

        binding.btnRecurve.setOnClickListener {
            startActivity(Intent(this@MainActivity, RecurveDemoActivity::class.java))
        }
    }
}