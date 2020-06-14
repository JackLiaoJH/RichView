package com.lab68.richtextview.base

import android.text.Spannable
import android.widget.EditText
import com.lab68.richtextview.User

/**
 *
 * @author jackson
 * @version 1.0
 * @date 2020/6/14 10:53 PM
 */
interface Method {
    fun init(editText: EditText)
    fun newSpannable(user: User): Spannable
}