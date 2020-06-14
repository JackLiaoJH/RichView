package com.lab68.richtextview.weibo

import android.content.Context
import android.text.Spannable
import android.util.AttributeSet
import android.view.KeyEvent
import android.view.View
import android.widget.EditText
import androidx.appcompat.widget.AppCompatEditText
import com.lab68.richtextview.base.DataBindingSpan
import com.lab68.richtextview.base.KeyCodeDeleteHelper
import com.lab68.richtextview.base.NoCopySpanEditableFactory
import com.lab68.richtextview.base.SelectionSpanWatcher

/**
 *
 * @author jackson
 * @version 1.0
 * @date 2020/6/14 7:46 PM
 */
class WeiBoEditText(context: Context, attrs: AttributeSet?, defStyleAttr: Int) :
    AppCompatEditText(context, attrs, defStyleAttr), View.OnKeyListener {
    constructor(context: Context, attrs: AttributeSet?) : this(
        context,
        attrs,
        androidx.appcompat.R.attr.editTextStyle
    )

    constructor(context: Context) : this(context, null)

    init {
        setEditableFactory(NoCopySpanEditableFactory(SelectionSpanWatcher(DataBindingSpan::class)))
        setOnKeyListener(this)
    }

    override fun onKey(v: View?, keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_DEL && event?.action == KeyEvent.ACTION_DOWN) {
            return KeyCodeDeleteHelper.onDelDown((v as EditText).text)
        }
        return false
    }
}