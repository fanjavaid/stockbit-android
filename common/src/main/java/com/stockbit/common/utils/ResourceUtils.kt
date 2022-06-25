package com.stockbit.common.utils

import android.content.Context
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat

object ResourceUtils {

    fun getColor(context: Context, @ColorRes color: Int): Int {
        return ContextCompat.getColor(context, color)
    }
}