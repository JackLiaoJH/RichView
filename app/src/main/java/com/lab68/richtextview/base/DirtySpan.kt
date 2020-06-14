package com.lab68.richtextview.base

import android.text.Spannable

/**
 *
 * @author jackson
 * @version 1.0
 * @date 2020/6/14 10:11 PM
 */
interface DirtySpan {
    fun isDirty(text: Spannable): Boolean
}