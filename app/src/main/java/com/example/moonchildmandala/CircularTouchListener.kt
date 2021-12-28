package com.example.moonchildmandala

import android.view.View.OnTouchListener
import android.view.MotionEvent
import android.view.View
import android.view.animation.ScaleAnimation
import android.view.animation.OvershootInterpolator
import kotlin.math.sqrt

/**
 * Created by j.h. on 2017/4/22.
 *
 *
 * handle touch event of my circular ListView
 */
class CircularTouchListener : OnTouchListener {
    interface CircularItemClickListener {
        fun onItemClick(view: View?, index: Int)
    }

    private var itemClickListener: CircularItemClickListener? = null
    private var init_x = 0f
    private var init_y = 0f
    private var cur_x = 0f
    private var cur_y = 0f
    private var move_x = 0f
    private var move_y = 0f
    fun setItemClickListener(listener: CircularItemClickListener?) {
        itemClickListener = listener
    }

    override fun onTouch(v: View, event: MotionEvent): Boolean {
        val circularView = v as CircularListView
        val minClickDistance = 30.0f
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                cur_x = event.x
                cur_y = event.y
                init_x = event.x
                init_y = event.y
                cur_x = event.x
                cur_y = event.y
                move_x = init_x - cur_x
                move_y = init_y - cur_y
                return true
            }
            MotionEvent.ACTION_MOVE -> {
                cur_x = event.x
                cur_y = event.y
                move_x = init_x - cur_x
                move_y = init_y - cur_y
                return true
            }
            MotionEvent.ACTION_UP -> {
                // it is an click action if move distance < min distance
                val moveDistance = sqrt((move_x * move_x + move_y * move_y).toDouble())
                    .toFloat()
                if (moveDistance < minClickDistance) {
                    var i = 0
                    if (circularView.itemViewList != null) {
                        while (i < circularView.itemViewList!!.size) {
                            val view = circularView.itemViewList?.get(i)
                            if (view != null) {
                                if (isTouchInsideView(cur_x, cur_y, view)) {
                                    itemClickListener!!.onItemClick(view, i)

                                    // set click animation
                                    val animation = ScaleAnimation(
                                        0.5f, 1.0f, 0.5f, 1.0f,
                                        ScaleAnimation.RELATIVE_TO_SELF, 0.5f,
                                        ScaleAnimation.RELATIVE_TO_SELF, 0.5f
                                    )
                                    animation.duration = 300
                                    animation.interpolator = OvershootInterpolator()
                                    view.startAnimation(animation)
                                    break
                                }
                            }
                            i++
                        }
                    }
                }
                return true
            }
        }
        return false
    }

    /**
     *
     * @param x touch position of x
     * @param y touch position of y
     * @param view the view that you want to know if we touch inside it
     * @return true or false whether we are actually touch the view
     */
    private fun isTouchInsideView(x: Float, y: Float, view: View): Boolean {
        val left = view.x
        val top = view.y
        val wid = view.width.toFloat()
        val h = view.height.toFloat()
        return x > left && x < left + wid && y > top && y < top + h
    }
}