package com.stockbit.common.utils

import java.text.DecimalFormat

object NumberUtils {
    private val decimalSeparator = DecimalFormat("##,###")

    fun Long.formatThousand(): String = decimalSeparator.format(this)
}
