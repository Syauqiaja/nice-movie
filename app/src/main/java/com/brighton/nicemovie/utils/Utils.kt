package com.brighton.nicemovie.utils

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Bitmap
import android.view.View
import com.hoko.blur.HokoBlur
import com.brighton.nicemovie.utils.enums.ImageSize

fun String.asRemoteImagePath(imageSize: ImageSize): String{
    return "https://image.tmdb.org/t/p/${imageSize.value}/$this"
}

fun Bitmap.blurAtRuntime(context: Context, radius: Int = 5): Bitmap{
    return HokoBlur.with(context)
        .mode(HokoBlur.MODE_GAUSSIAN)
        .radius(radius)
        .blur(this)
}

fun View.scaleViewOneShot(scale: Float, duration: Long) {
    val animator = ValueAnimator.ofFloat(scaleX, scale)
    animator.addUpdateListener { valueAnimator ->
        val animatedValue = valueAnimator.animatedValue as Float
        scaleX = animatedValue
        scaleY = animatedValue
    }
    animator.duration = duration // Duration of the animation in milliseconds
    animator.repeatMode = ValueAnimator.REVERSE
    animator.repeatCount = 1
    animator.start()
}