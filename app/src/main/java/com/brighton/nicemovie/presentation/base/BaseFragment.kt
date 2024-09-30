package com.syauqi.watcheez.presentation.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.facebook.shimmer.Shimmer
import com.facebook.shimmer.ShimmerDrawable

open class BaseFragment<VB: ViewBinding>(
    private val inflateMethod : (LayoutInflater, ViewGroup?, Boolean) -> VB
): Fragment() {
    var _binding : VB? = null

    // This can be accessed by the child fragments
    // Only valid between onCreateView and onDestroyView
    val binding: VB get() = _binding!!

    fun checkBindingAvalability(): Boolean{
        return _binding != null
    }

    private val shimmerBuilder = Shimmer.AlphaHighlightBuilder()
        .setDuration(1000) // how long the shimmering animation takes to do one full sweep
        .setBaseAlpha(0.7f) //the alpha of the underlying children
        .setHighlightAlpha(0.6f) // the shimmer alpha amount
        .setDirection(Shimmer.Direction.LEFT_TO_RIGHT)
        .setAutoStart(true)
        .build()

    val shimmerDrawable = ShimmerDrawable().apply {
        setShimmer(shimmerBuilder)
    }

    open fun initView(){}
    open fun observeViewModel(){}

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = inflateMethod.invoke(inflater, container, false)
        observeViewModel()
        initView()
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}