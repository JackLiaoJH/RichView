package com.lab68.richtextview.base

import android.text.Spannable
import android.text.SpannableString

/**
 *
 * @author jackson
 * @version 1.0
 * @date 2020/6/14 10:16 PM
 */
object SpanFactory {
    fun newSpannable(source: CharSequence, vararg spans: Any): Spannable {
        return SpannableString.valueOf(source).apply {
            spans.forEach {
                setSpan(it, 0, length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            }
        }
    }
}