package com.recurve.adapter.creator

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

class RecurveViewHolder<Binding: ViewDataBinding>(val binding: Binding)
    : RecyclerView.ViewHolder(binding.root)