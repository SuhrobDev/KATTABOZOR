package dev.soul.kattabozor.presentation.utils

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.widget.FrameLayout

class TouchBlockingFrameLayout : FrameLayout {

    companion object {
        private const val TAG = "TouchBlockingFrameLayout"
    }

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    var blockTouches: Boolean = false

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (blockTouches) {
            Log.v(TAG, "onTouchEvent: touch blocked, $event")
            return true
        }
        return super.onTouchEvent(event)
    }

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        if (blockTouches) {
            Log.v(TAG, "onInterceptTouchEvent: touch blocked, $ev")
            return true
        }
        return super.onInterceptTouchEvent(ev)
    }
}