package com.lab68.richtextview.rich

import android.content.Context
import android.graphics.Color
import android.text.Spannable
import android.text.TextUtils
import android.text.style.BackgroundColorSpan
import android.text.style.ForegroundColorSpan
import android.util.AttributeSet
import android.util.Log
import android.view.KeyEvent
import android.view.View
import androidx.appcompat.widget.AppCompatEditText
import com.lab68.richtextview.R


/**
 *
 * @author jackson
 * @version 1.0
 * @date 2020/6/13 11:57 PM
 */
class RichEditText(context: Context, attrs: AttributeSet?, defStyleAttr: Int) :
    AppCompatEditText(context, attrs, defStyleAttr), View.OnKeyListener {
    constructor(context: Context, attrs: AttributeSet?) : this(
        context,
        attrs,
        androidx.appcompat.R.attr.editTextStyle
    )

    constructor(context: Context) : this(context, null)

    companion object {
        private val TAG = RichEditText::class.java.simpleName
        private val FOREGROUND_COLOR = Color.parseColor("#FF8C00")
        private val BACKGROUND_COLOR = Color.parseColor("#FFDEAD")
    }

    private val mRichList = arrayListOf<RichVo>()

    private var mForegroundColor = FOREGROUND_COLOR
    private var mBackgroundColor = BACKGROUND_COLOR


    init {

        val a = context.obtainStyledAttributes(attrs, R.styleable.RichEditText)
        mBackgroundColor =
            a.getColor(R.styleable.RichEditText_rich_background_color, BACKGROUND_COLOR)
        mForegroundColor =
            a.getColor(R.styleable.RichEditText_rich_foreground_color, FOREGROUND_COLOR)
        a.recycle()

//        addTextChangedListener {
//            doAfterTextChanged {
//                refreshEditTextUi(it.toString())
//            }
//        }

        setOnKeyListener(this)
    }

    override fun onSelectionChanged(selStart: Int, selEnd: Int) {
        super.onSelectionChanged(selStart, selEnd)
        if (mRichList == null || mRichList.isEmpty()) return

        //监听光标的位置,若光标处于话题内容中间则移动光标到话题结束位置
        var start: Int
        var end: Int
        var tempRichText: String
        mRichList.forEach {
            tempRichText = it.text
            start = text.toString().indexOf(tempRichText)
            end = start + tempRichText.length
            if (start != -1 && selStart > start && selStart <= end) {
                // 移到末尾
                setSelection(end)
            }
        }
    }

    fun setRich(richVo: RichVo?) {
        richVo ?: return

        val startRule = richVo.startRule
        val endRule = richVo.endRule
        val text = richVo.text
        if (TextUtils.isEmpty(startRule) || TextUtils.isEmpty(text)) return

        val newRichText = startRule + text + endRule
        richVo.text = newRichText
        mRichList.add(richVo)

        // insert content
        val editable = getText()

        if (selectionStart >= 0) {
            val start = selectionStart
            val end = selectionStart + newRichText.length

            editable?.insert(selectionStart, newRichText)
            editable?.insert(selectionStart, " ")
            setSelection(selectionStart)

            val colorSpan = ForegroundColorSpan(mForegroundColor)
            editable?.setSpan(colorSpan, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        }
    }

    fun getRichList(): MutableList<RichVo> {
        val richList = arrayListOf<RichVo>()
        mRichList.forEach {
            it.text = it.text
                .replace(it.startRule, "")
                .replace(it.endRule, "")
            richList.add(it)
        }
        return richList
    }

    fun onClear() {
        if (mRichList.isNotEmpty()) mRichList.clear()
    }

    private fun refreshEditTextUi(content: String?) {
        if (mRichList.isEmpty()) return
        if (TextUtils.isEmpty(content)) {
            if (mRichList.isNotEmpty()) mRichList.clear()
            return
        }

        var start: Int
        var tempRichText: String
        mRichList.forEach {
            tempRichText = it.text
            start = content!!.indexOf(tempRichText)
            if (start != -1) {
                val colorSpan = ForegroundColorSpan(mForegroundColor)
                text?.setSpan(
                    colorSpan,
                    start,
                    start + tempRichText.length,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )
            }
        }
    }

    override fun onKey(v: View?, keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_DEL && event?.action == KeyEvent.ACTION_DOWN) {
            Log.e(TAG, ">>>>>>>>$selectionStart === $selectionEnd")
            // 如果光标起始和结束不在同一位置,删除文本
            if (selectionStart != selectionEnd) {
                // 查询文本是否属于目标对象,若是移除列表数据
                val targetText = text.toString().substring(selectionStart, selectionEnd)
                mRichList.forEach o@{
                    if (TextUtils.equals(targetText, it.text)) {
                        mRichList.remove(it)
                        return false
                    }
                }
            }

            var lastPos = 0
            var end: Int
            var tempRichText: String
            mRichList.forEach {
                tempRichText = it.text
                lastPos = text.toString().indexOf(tempRichText, lastPos)
                if (lastPos != -1) {
                    end = lastPos + tempRichText.length
                    if (selectionStart != 0 && selectionStart > lastPos && selectionStart <= end) {
                        // 选中目标文本
                        setSelection(lastPos, end)
                        Log.e(TAG, "$selectionStart === $selectionEnd")
                        text?.setSpan(
                            BackgroundColorSpan(mBackgroundColor),
                            lastPos,
                            end,
                            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                        )
                        return true
                    }

                }
                lastPos += tempRichText.length
            }
        }

        return false
    }
}