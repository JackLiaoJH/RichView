package com.lab68.richtextview.base

import android.text.Selection
import android.text.Spannable

/**
 *
 * @author jackson
 * @version 1.0
 * @date 2020/6/14 7:45 PM
 */
object KeyCodeDeleteHelper {
    fun onDelDown(text: Spannable): Boolean {
        val selectionStart = Selection.getSelectionStart(text)
        val selectionEnd = Selection.getSelectionEnd(text)
        text.getSpans(selectionStart, selectionEnd, DataBindingSpan::class.java)
            .firstOrNull { text.getSpanEnd(it) == selectionStart }?.run {
            return (selectionStart == selectionEnd).also {
                val spanStart = text.getSpanStart(this)
                val spanEnd = text.getSpanEnd(this)
                Selection.setSelection(text, spanStart, spanEnd)
            }
        }
        return false
    }

}