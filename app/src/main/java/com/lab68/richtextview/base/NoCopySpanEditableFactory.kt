package com.lab68.richtextview.base

import android.text.Editable
import android.text.NoCopySpan
import android.text.SpannableStringBuilder
import android.text.Spanned

/**
 *
 * @author jackson
 * @version 1.0
 * @date 2020/6/14 9:10 PM
 */
class NoCopySpanEditableFactory(private vararg val spans: NoCopySpan) : Editable.Factory() {
    override fun newEditable(source: CharSequence): Editable {
        return SpannableStringBuilder.valueOf(source).apply {
            spans.forEach {
                setSpan(it, 0, source.length, Spanned.SPAN_INCLUSIVE_INCLUSIVE)
            }
        }

    }
}