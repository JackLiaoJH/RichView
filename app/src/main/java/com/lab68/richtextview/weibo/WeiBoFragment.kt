package com.lab68.richtextview.weibo

import android.os.Bundle
import android.text.SpannableStringBuilder
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.lab68.richtextview.Constants
import com.lab68.richtextview.R
import com.lab68.richtextview.User
import com.lab68.richtextview.base.*
import kotlinx.android.synthetic.main.fragment_weibo.*

/**
 *
 * @author jackson
 * @version 1.0
 * @date 2020/6/14 7:49 PM
 */
class WeiBoFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_weibo, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val user = User("1", "Jackson Liao")

        Weibo.init(et_weibo)
        (et_weibo.text as SpannableStringBuilder)
            .append(Weibo.newSpannable(user))
            .append(" ")


        WeChat.init(et_weixin)
        (et_weixin.text as SpannableStringBuilder)
            .append(WeChat.newSpannable(user))
            .append(" ")

//        QQ.init(et_qq)
//        (et_qq.text as SpannableStringBuilder)
//            .append(QQ.newSpannable(user))
//            .append(" ")


        val editable = et_weibo.text
        val strings = editable.let {
            it?.getSpans(0, it.length, User::class.java)
        }?.joinToString("\n") {
            "$it, ${editable?.getSpanStart(it)}, ${editable?.getSpanEnd(it)}"
        }
        Log.e("a", strings.toString())
    }
}