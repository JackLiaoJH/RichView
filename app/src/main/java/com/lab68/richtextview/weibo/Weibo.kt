package com.lab68.richtextview.weibo

import android.text.Spannable
import android.view.KeyEvent
import android.widget.EditText
import com.lab68.richtextview.User
import com.lab68.richtextview.base.*

/**
 *
 * @author jackson
 * @version 1.0
 * @date 2020/6/14 10:53 PM
 */
object Weibo : Method {
    override fun init(editText: EditText) {
        editText.text = null
        editText.setEditableFactory(NoCopySpanEditableFactory(SelectionSpanWatcher(DataBindingSpan::class)))
        editText.setOnKeyListener { v, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_DEL && event?.action == KeyEvent.ACTION_DOWN) {
                return@setOnKeyListener KeyCodeDeleteHelper.onDelDown((v as EditText).text)
            }
            return@setOnKeyListener false
        }
    }

    override fun newSpannable(user: User): Spannable {
        return SpanFactory.newSpannable(user.getSpannedName(), user)
    }

}