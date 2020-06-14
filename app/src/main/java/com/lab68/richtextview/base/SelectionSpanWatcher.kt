package com.lab68.richtextview.base

import android.text.Selection
import android.text.SpanWatcher
import android.text.Spannable
import kotlin.math.abs
import kotlin.reflect.KClass

/**
 *
 * @author jackson
 * @version 1.0
 * @date 2020/6/14 7:39 PM
 */
class SelectionSpanWatcher<T : Any>(private val kClass: KClass<T>) : SpanWatcherAdapter() {

    private var selStart = 0
    private var selEnd = 0

    override fun onSpanChanged(
        text: Spannable,
        what: Any,
        ostart: Int,
        oend: Int,
        nstart: Int,
        nend: Int
    ) {
        if (what === Selection.SELECTION_END && selEnd != nstart) {
            selEnd = nstart
            text.getSpans(nstart, nend, kClass.java).firstOrNull().run {
                val spanStart = text.getSpanStart(this)
                val spanEnd = text.getSpanEnd(this)
                val index =
                    if (abs(selEnd - spanEnd) > abs(selEnd - spanStart)) spanStart else spanEnd
                if (index != -1)
                    Selection.setSelection(text, Selection.getSelectionStart(text), index)
            }
        }

        if (what === Selection.SELECTION_START && selStart != nstart) {
            selStart = nstart
            text.getSpans(nstart, nend, kClass.java).firstOrNull().run {
                val spanStart = text.getSpanStart(this)
                val spanEnd = text.getSpanEnd(this)
                val index =
                    if (abs(selStart - spanEnd) > abs(selStart - spanStart)) spanStart else spanEnd
                if (index != -1)
                    Selection.setSelection(text, index, Selection.getSelectionEnd(text))
            }
        }
    }
}