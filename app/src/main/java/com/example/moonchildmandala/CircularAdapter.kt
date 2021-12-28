package com.example.moonchildmandala

import android.view.View
import java.util.ArrayList

/**
 * Created by jh on 2017/4/15.
 *
 * using this class to initialize your items
 * you need to override getCount() and getItemAt() to get it work
 */
abstract class CircularAdapter {
    interface CircularItemChangeListener {
        fun onCircularItemChange()
    }

    /**
     * get item count
     * @return numbers of item
     */
    abstract val count: Int

    /**
     * get all custom views, you should put all views into an ArrayList
     * @return a list of views
     */
    abstract val allViews: ArrayList<View>

    /**
     * get item at index i
     * @param i index of item
     * @return view at position i
     */
    abstract fun getItemAt(i: Int): View?

    /**
     * remove move an item from the list
     * @param i index of item to be removed
     */
    abstract fun removeItemAt(i: Int)

    /**
     * add an item into the list from last
     */
    abstract fun addItem(view: View)

    /**
     * need to notify parent view when item has been changed
     */
    fun notifyItemChange() {
        circularItemChangeListener?.onCircularItemChange()
    }

    private var circularItemChangeListener: CircularItemChangeListener? = null
    fun setOnItemChangeListener(listener: CircularItemChangeListener?) {
        circularItemChangeListener = listener
    }
}