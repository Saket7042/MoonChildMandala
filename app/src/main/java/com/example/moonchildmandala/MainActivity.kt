package com.example.moonchildmandala

import android.os.Bundle
import android.view.Gravity
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val numViews = 31
        for (i in 0 until numViews) {
            // Create some quick TextViews that can be placed.
            val v = ExtraView(this)
            // Set a text and center it in each view.
//            v.text = "View $i"
//            v.gravity = Gravity.CENTER
//            v.setBackgroundColor(-0x10000)
            // Force the views to a nice size (150x100 px) that fits my display.
            // This should of course be done in a display size independent way.
            val lp = FrameLayout.LayoutParams(100, 150)
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
            v.translationX = 280 * Math.cos(angleRad.toDouble()).toFloat()
            v.translationY = 280 * Math.sin(angleRad.toDouble()).toFloat()
            // Set the rotation of the view.
            v.rotation = angleDeg + 90.0f
            main.addView(v)
        }
    }
}