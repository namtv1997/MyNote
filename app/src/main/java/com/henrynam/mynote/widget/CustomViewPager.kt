package com.henrynam.mynote.widget

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.viewpager.widget.ViewPager

class CustomViewPager(context: Context, attrs: AttributeSet) : ViewPager(context, attrs) {

    private var initialX: Float = 0.0f
    private var direction: SwipeDirection = SwipeDirection.All

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (isSwipeAllowed(event!!)) {
            return super.onTouchEvent(event)
        }
        return false
    }

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        if (isSwipeAllowed(ev!!)) {
            return super.onInterceptTouchEvent(ev)
        }
        return false
    }

    private fun isSwipeAllowed(event: MotionEvent): Boolean {
        if (direction == SwipeDirection.All) {
            return true
        }

        if (direction == SwipeDirection.None) {
            return false
        }

        if (event.action == MotionEvent.ACTION_DOWN) {
            initialX = event.x
            return true
        }

        if (event.action == MotionEvent.ACTION_MOVE) {
            try {
                val diffX = (event.x - initialX)
                if (diffX > 0 && direction == SwipeDirection.Right) {
                    // swipe from left to right detected
                    return false
                } else if (diffX < 0 && direction == SwipeDirection.Left) {
                    // swipe from right to left detected
                    return false
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return true
        }

        return true
    }

    fun setSwipeDirection(direction: SwipeDirection) {
        this.direction = direction
    }

    enum class SwipeDirection(val type: Int) {
        None(0),
        All(1),
        Left(2),
        Right(3)
    }
}