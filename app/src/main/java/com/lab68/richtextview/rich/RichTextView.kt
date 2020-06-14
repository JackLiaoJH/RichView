package com.lab68.richtextview.rich

import android.content.Context
import android.graphics.Color
import android.text.Spannable
import android.text.SpannableString
import android.text.method.LinkMovementMethod
import android.util.AttributeSet
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatTextView
import com.lab68.richtextview.NoUnderLineClickableSpan
import java.util.regex.Matcher
import java.util.regex.Pattern

/**
 *
 * @author jackson
 * @version 1.0
 * @date 2020/6/13 11:07 PM
 */
class RichTextView(context: Context, attr: AttributeSet?, defStyleAttr: Int) :
    AppCompatTextView(context, attr, defStyleAttr) {
    constructor(context: Context, attr: AttributeSet?) : this(context, attr, 0)
    constructor(context: Context) : this(context, null)



    fun setContent(content:String?) {
        content?:return
        val result = getWeiBoContent(context, content, this)
        setTextKeepState(result)
    }

    /**
     * 设置微博内容样式
     * @param context
     * @param source
     * @param textView
     * @return
     */
    fun getWeiBoContent(context: Context, source: String?, textView: TextView): SpannableString {
        val spannableString = SpannableString(source)

        //设置正则
        val pattern: Pattern = Pattern.compile(REGEX)
        val matcher: Matcher = pattern.matcher(spannableString)
        if (matcher.find()) {
            // 要实现文字的点击效果，这里需要做特殊处理
            textView.movementMethod = LinkMovementMethod.getInstance()
            // 重置正则位置
            matcher.reset()
        }
        while (matcher.find()) {
            // 根据group的括号索引，可得出具体匹配哪个正则(0代表全部，1代表第一个括号)
            val at = matcher.group(1)
            val topic = matcher.group(2)
            val emoji = matcher.group(3)
            val url = matcher.group(4)

            // 处理@符号
            if (at != null) {
                //获取匹配位置
                val start: Int = matcher.start(1)
                val end = start + at.length
                val clickableSpan = object : NoUnderLineClickableSpan(Color.BLUE) {
                    override fun onClick(widget: View) {
                        //这里需要做跳转用户的实现，先用一个Toast代替
                        Toast.makeText(context, "点击了用户：$at", Toast.LENGTH_LONG).show()
                    }
                }
                spannableString.setSpan(
                    clickableSpan,
                    start,
                    end,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )
            }


            // 处理话题##符号
            if (topic != null) {
                val start: Int = matcher.start(2)
                val end = start + topic.length
                val clickableSpan = object : NoUnderLineClickableSpan(Color.BLUE) {
                    override fun onClick(widget: View) {
                        Toast.makeText(context, "点击了话题：$topic", Toast.LENGTH_LONG).show()
                    }
                }
                spannableString.setSpan(
                    clickableSpan,
                    start,
                    end,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )
            }
            if (emoji != null) {
//                val start: Int = matcher.start(3)
//                val end = start + emoji.length
//                val ResId: Int = EmotionUtils.getImgByName(emoji)
//                var bitmap = BitmapFactory.decodeResource(context.resources, ResId)
//                if (bitmap != null) {
//                    // 获取字符的大小
//                    val size = textView.textSize.toInt()
//                    // 压缩Bitmap
//                    bitmap = Bitmap.createScaledBitmap(bitmap, size, size, true)
//                    // 设置表情
//                    val imageSpan = ImageSpan(context, bitmap)
//                    spannableString.setSpan(
//                        imageSpan,
//                        start,
//                        end,
//                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
//                    )
//                }
            }

            // 处理url地址
            if (url != null) {
                val start: Int = matcher.start(4)
                val end = start + url.length
                val clickableSpan = object : NoUnderLineClickableSpan(Color.BLUE) {
                    override fun onClick(widget: View) {
                        Toast.makeText(context, "点击了网址：$url", Toast.LENGTH_LONG).show()
                    }
                }
                spannableString.setSpan(
                    clickableSpan,
                    start,
                    end,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )
            }
        }
        return spannableString
    }

    companion object {
        // 定义正则表达式
        private const val AT = "@[\u4e00-\u9fa5\\w]+" // @人

        private const val TOPIC = "#[\u4e00-\u9fa5\\w]+#" // ##话题

        private const val EMOJI = "\\[[\u4e00-\u9fa5\\w]+\\]" // 表情

        private const val URL =
            "http://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]" // url
        private const val REGEX = "($AT)|($TOPIC)|($EMOJI)|($URL)"


        const val DEFAULT_METION_TAG = "@"
        const val DEFAULT_MENTION_PATTERN = "@[\\u4e00-\\u9fa5\\w\\-]+"
    }


}