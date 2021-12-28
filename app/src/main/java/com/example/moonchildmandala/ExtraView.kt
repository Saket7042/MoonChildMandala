package com.example.moonchildmandala

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import androidx.constraintlayout.widget.ConstraintLayout
import android.view.LayoutInflater
import kotlinx.android.synthetic.main.single_item_layout.view.*

class ExtraView : ConstraintLayout {
    private var dayViewModel: DayViewModel? = null
    constructor(context: Context) : super(context) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet) : super(
        context, attrs
    ) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context, attrs, defStyleAttr
    ) {
        init(context)
    }

    fun init(context: Context) {
        LayoutInflater.from(context).inflate(R.layout.single_item_layout, this, true)

    }

    fun addData(data: DayViewModel) {
        this.dayViewModel = data
        Log.e(javaClass.simpleName, data.toString())
        tvText.text = dayViewModel?.text
        if (dayViewModel?.showImage == true) {
            ivStar.setImageDrawable(dayViewModel?.icon)
        }else{
            ivStar.visibility = GONE
        }
    }
}