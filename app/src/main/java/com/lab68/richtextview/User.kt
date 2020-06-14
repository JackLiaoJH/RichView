package com.lab68.richtextview

import android.graphics.Color
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import com.lab68.richtextview.base.DataBindingSpan
import com.lab68.richtextview.base.DirtySpan

/**
 *
 * @author jackson
 * @version 1.0
 * @date 2020/6/14 10:54 PM
 */
data class User(val id: String, var name: String) : DataBindingSpan<String>,
    DirtySpan {

    fun getSpannedName(): Spannable {
        return SpannableString("@$name").apply {
            setSpan(ForegroundColorSpan(Color.CYAN), 0, length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        }
    }

    override fun isDirty(text: Spannable): Boolean {
        val spanStart = text.getSpanStart(this)
        val spanEnd = text.getSpanEnd(this)
        return spanStart >= 0 && spanEnd >= 0 && text.substring(spanStart, spanEnd) != "@$name"
    }
}