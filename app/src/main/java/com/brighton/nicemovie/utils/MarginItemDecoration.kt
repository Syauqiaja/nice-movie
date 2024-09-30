package com.brighton.nicemovie.utils

import android.content.Context
import android.graphics.Rect
import android.util.TypedValue
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class MarginItemDecoration(context: Context, private val margin: Int) : RecyclerView.ItemDecoration() {

    private val marginInPx: Int = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        margin.toFloat(),
        context.resources.displayMetrics
    ).toInt()

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        outRect.set(marginInPx, marginInPx, marginInPx, marginInPx)
    }
}