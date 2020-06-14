package com.lab68.richtextview.base

import android.text.Spannable

/**
 *
 * @author jackson
 * @version 1.0
 * @date 2020/6/14 10:09 PM
 */
class DirtySpanWatcher(private val removePredicate: (Any) -> Boolean):SpanWatcherAdapter() {
    override fun onSpanChanged(
        text: Spannable,
        what: Any,
        ostart: Int,
        oend: Int,
        nstart: Int,
        nend: Int
    ) {
        super.onSpanChanged(text, what, ostart, oend, nstart, nend)
        if (what is DirtySpan && what.isDirty(text)) {
            val spanStart = text.getSpanStart(what)
            val spanEnd = text.getSpanEnd(what)
            text.getSpans(spanStart, spanEnd, Any::class.java).filter {
                removePredicate.invoke(it)
            }.forEach {
                text.removeSpan(it)
            }
        }
    }
}