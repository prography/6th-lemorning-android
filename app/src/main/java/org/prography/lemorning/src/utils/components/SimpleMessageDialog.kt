package org.prography.lemorning.src.utils.components

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.Rect
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.MotionEvent
import android.view.WindowManager
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import org.prography.lemorning.R

class SimpleMessageDialog(
  context: Context,
  private val message: String? = "",
  private val btnText: String? = "",
  private val cancelable: Boolean = true,
  private val onClick: ((Dialog) -> Unit)? = null
) : Dialog(context) {

  init {
    setCancelable(cancelable)
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.dialog_simple_message)

    findViewById<TextView>(R.id.tv_desc_dialog_simple_message).text = message
    findViewById<TextView>(R.id.tv_positive_dialog_simple_message).apply {
      text = btnText
      setOnClickListener { onClick?.invoke(this@SimpleMessageDialog) ?: dismiss() }
    }

    window?.apply {
      setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
      setGravity(Gravity.CENTER)
      setLayout(
        WindowManager.LayoutParams.MATCH_PARENT,
        WindowManager.LayoutParams.WRAP_CONTENT
      )
    }
  }

  override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
    val dialogBounds = Rect().apply { findViewById<ConstraintLayout>(R.id.cl_dialog_simple_message).getHitRect(this) }
    if (cancelable && !dialogBounds.contains(ev.x.toInt(), ev.y.toInt())) dismiss()
    return super.dispatchTouchEvent(ev)
  }
}