package com.lab68.richtextview.weibo

import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Build
import android.text.*
import android.text.style.ImageSpan
import android.widget.EditText
import com.lab68.richtextview.User
import com.lab68.richtextview.base.Method
import com.lab68.richtextview.base.SpanFactory
import kotlin.math.min

/**
 *
 * @author jackson
 * @version 1.0
 * @date 2020/6/14 11:10 PM
 */
object QQ : Method {

    private lateinit var layout: DynamicLayout
    private val highLight = Path()
    private val highLightPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var width = 0
    private lateinit var textPaint: TextPaint

    override fun init(editText: EditText) {
        editText.text = null
        val eLayout = editText.layout
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            highLightPaint.color = editText.highlightColor
        } else {
            highLightPaint.color = Color.YELLOW
        }
        highLightPaint.style = Paint.Style.FILL
        textPaint = eLayout.paint
        width = eLayout.width
        layout = DynamicLayout(
            SpannableStringBuilder(),
            eLayout.paint,
            eLayout.width,
            eLayout.alignment,
            eLayout.spacingMultiplier,
            eLayout.spacingAdd,
            true
        )
        editText.setOnKeyListener(null)
        editText.setEditableFactory(Editable.Factory.getInstance())
    }

    override fun newSpannable(user: User): Spannable {
        return SpanFactory.newSpannable(
            "@${user.name}",
            user,
            ImageSpan(createDrawable(user.getSpannedName()))
        )
    }

    @Suppress("DEPRECATION")
    private fun createDrawable(source: CharSequence): Drawable {
        return if (::layout.isInitialized) {
            val builder = layout.text as SpannableStringBuilder
            builder.clear()
            builder.append(source)
            val want = textPaint.measureText(builder, 0, builder.length).toInt()
            if (want != layout.width) {
                layout = DynamicLayout(
                    builder,
                    layout.paint,
                    min(want, width),
                    layout.alignment,
                    layout.spacingMultiplier,
                    layout.spacingAdd,
                    true
                )
            }
            val bitmap = Bitmap.createBitmap(layout.width, layout.height, Bitmap.Config.ARGB_8888)
            val rect = Rect(0, 0, bitmap.width, bitmap.height)
            highLight.reset()
            highLight.addRect(RectF(rect), Path.Direction.CCW)
            layout.draw(Canvas(bitmap), highLight, highLightPaint, 0)
            BitmapDrawable(bitmap).apply {
                bounds = rect
            }
        } else throw IllegalAccessException()
    }
}