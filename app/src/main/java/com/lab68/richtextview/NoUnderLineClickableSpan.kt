package com.lab68.richtextview

import android.text.TextPaint
import android.text.style.ClickableSpan
import android.view.View

/**
 *
 * @author jackson
 * @version 1.0
 * @date 2020/6/13 8:49 PM
 */
open class NoUnderLineClickableSpan(private val color: Int) : ClickableSpan() {
    override fun onClick(widget: View) {

    }

    override fun updateDrawState(ds: TextPaint) {
//        super.updateDrawState(ds)
        ds.color = color
        ds.isUnderlineText = false
    }
}