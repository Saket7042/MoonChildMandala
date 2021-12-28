package com.example.moonchildmandala

import android.os.Bundle
import android.view.Gravity
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.math.roundToInt
import android.util.DisplayMetrics
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import kotlin.math.cos
import kotlin.math.sin


class MainActivity : AppCompatActivity() {
    private val dayModels: ArrayList<DayViewModel> = ArrayList()
    private val circleViews: ArrayList<View> = ArrayList()
    private var lastSelectedPos: Int = -1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val display = this.resources.displayMetrics
        val width = display.widthPixels
        val height = display.heightPixels

        val numViews = 28
        populateData(numViews)
        for (i in dayModels.indices) {
            // Create some quick TextViews that can be placed.
            val v = ExtraView(this)
            v.addData(dayModels[i])
            // Set a text and center it in each view.
//            v.text = "View $i"
//            v.gravity = Gravity.CENTER
//            v.setBackgroundColor(-0x10000)
            // Force the views to a nice size (150x100 px) that fits my display.
            // This should of course be done in a display size independent way.
            val lp = FrameLayout.LayoutParams(convertDipToPixels(60f), convertDipToPixels(80f))
            // Place all views in the center of the layout. We'll transform them
            // away from there in the code below.
            lp.gravity = Gravity.CENTER
            // Set layout params on view.
            v.layoutParams = lp

            // Calculate the angle of the current view. Adjust by 90 degrees to
            // get View 0 at the top. We need the angle in degrees and radians.
            val angleDeg = i * 360.0f / numViews - 90.0f
            val angleRad = (angleDeg * Math.PI / 180.0f).toFloat()
            // Calculate the position of the view, offset from center (300 px from
            // center). Again, this should be done in a display size independent way.
            v.translationX = ((width / 2.5) * cos(angleRad.toDouble()).toFloat()).toFloat()
            v.translationY = ((width / 2.5) * sin(angleRad.toDouble()).toFloat()).toFloat()
            // Set the rotation of the view.
            v.rotation = angleDeg + 90.0f
            v.setOnClickListener {
                val currentLayout = circleViews[i].findViewById<LinearLayout>(R.id.llMoon)
                if (lastSelectedPos != -1) {
                    val lastLayout = circleViews[lastSelectedPos].findViewById<LinearLayout>(R.id.llMoon)
                    dayModels[lastSelectedPos].isSelected = false
                    lastLayout.background = null

                }
                dayModels[i].isSelected = true
                currentLayout.background =
                    getDrawable(R.drawable.bg_item_selected)

                tvClickedText.text = "Selected moon phase: ${dayModels[i].text}"
                lastSelectedPos = i
            }
            main.addView(v)
            circleViews.add(v)
        }
        val params: FrameLayout.LayoutParams =
            ll_top_section.layoutParams as FrameLayout.LayoutParams
        // Changes the height and width to the specified *pixels*
        // Changes the height and width to the specified *pixels*
        params.height = (width / 1.8).toInt()
        params.width = (width / 1.8).toInt()
        ll_top_section.layoutParams = params
    }

    private fun populateData(itemCount: Int) {
        for (i in 0 until itemCount) {
            if (i % 2 == 0) {
                dayModels.add(
                    DayViewModel(
                        i.toString(),
                        showImage = true,
                        isSelected = false,
                        icon = getDrawable(R.drawable.star)
                    )
                )
            } else {
                dayModels.add(
                    DayViewModel(
                        i.toString(),
                        showImage = false,
                        isSelected = false,
                        icon = getDrawable(R.drawable.star)
                    )
                )
            }
        }
    }

    private fun convertDipToPixels(dips: Float): Int {
        return ((dips * resources.displayMetrics.density + 0.5f).roundToInt())
    }
}