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
    private var screenWidth: Int = 0
    private val numViews = 28


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val display = this.resources.displayMetrics
        screenWidth = display.widthPixels

        populateData(numViews)
        renderMoonSigns(true)
        renderMoonSigns(false)
        val params: FrameLayout.LayoutParams =
            ll_top_section.layoutParams as FrameLayout.LayoutParams
        // Changes the height and width to the specified *pixels*
        // Changes the height and width to the specified *pixels*
        params.height = (screenWidth / 1.8).toInt()
        params.width = (screenWidth / 1.8).toInt()
        ll_top_section.layoutParams = params
    }

    private fun renderMoonSigns(renderMoonSigns: Boolean){
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
            val lp = FrameLayout.LayoutParams(convertDipToPixels(54f), convertDipToPixels(54f))
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
            if (renderMoonSigns){
                v.translationX = ((screenWidth / 2.5) * cos(angleRad.toDouble()).toFloat()).toFloat()
                v.translationY = ((screenWidth / 2.5) * sin(angleRad.toDouble()).toFloat()).toFloat()
                // Set the rotation of the view.
//            v.rotation = angleDeg + 90.0f

                v.findViewById<LinearLayout>(R.id.llMoon).visibility = View.VISIBLE
                v.findViewById<ImageView>(R.id.ivStar).visibility = View.GONE
                if (dayModels[i].isSelected){
                    v.findViewById<LinearLayout>(R.id.llMoon).background =
                        getDrawable(R.drawable.bg_item_selected)
                }else{
                    v.findViewById<LinearLayout>(R.id.llMoon).background = null
                }
            }else{
                v.translationX = ((screenWidth / 3) * cos(angleRad.toDouble()).toFloat()).toFloat()
                v.translationY = ((screenWidth / 3) * sin(angleRad.toDouble()).toFloat()).toFloat()
                // Set the rotation of the view.
//            v.rotation = angleDeg + 90.0f

                v.findViewById<LinearLayout>(R.id.llMoon).visibility = View.GONE
                if (dayModels[i].isSelected){
                    v.findViewById<ImageView>(R.id.ivStar).visibility = View.VISIBLE
                }else{
                    v.findViewById<ImageView>(R.id.ivStar).visibility = View.GONE
                }
            }

            main.addView(v)
        }
    }

    private fun populateData(itemCount: Int) {
        for (i in 0 until itemCount) {
            if (i % 5 == 0) {
                dayModels.add(
                    DayViewModel(
                        i.toString(),
                        showImage = true,
                        isSelected = true,
                        isClicked = true,
                        icon = getDrawable(R.drawable.star)
                    )
                )
            } else {
                dayModels.add(
                    DayViewModel(
                        i.toString(),
                        showImage = false,
                        isSelected = false,
                        isClicked = false,
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