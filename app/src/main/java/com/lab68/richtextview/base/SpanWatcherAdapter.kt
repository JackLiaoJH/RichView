package com.lab68.richtextview.base

import android.text.SpanWatcher
import android.text.Spannable

/**
 *
 * @author jackson
 * @version 1.0
 * @date 2020/6/14 10:07 PM
 */
open class SpanWatcherAdapter: SpanWatcher {
    override fun onSpanChanged(
        text: Spannable,
        what: Any,
        ostart: Int,
        oend: Int,
        nstart: Int,
        nend: Int
    ) {

    }

    override fun onSpanRemoved(text: Spannable, what: Any, start: Int, end: Int) {

    }

    override fun onSpanAdded(text: Spannable, what: Any, start: Int, end: Int) {

    }
}