package com.example.moonchildmandala

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import com.example.moonchildmandala.pieChart.ChartData
import com.example.moonchildmandala.pieChart.PieChart
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_moon_mandala.*
import kotlinx.android.synthetic.main.activity_moon_mandala.main
import kotlinx.android.synthetic.main.activity_moon_mandala.tvClickedText
import kotlin.math.cos
import kotlin.math.roundToInt
import kotlin.math.sin

class MoonMandalaActivity : AppCompatActivity() {
    private val dayModels: ArrayList<DayViewModel> = ArrayList()
    private var screenWidth: Int = 0
    private val numViews = 28
    private val circleViews: ArrayList<View> = ArrayList()
    private var lastSelectedPos: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_moon_mandala)
        val display = this.resources.displayMetrics
        screenWidth = display.widthPixels

        populateData(numViews)
        renderMoonSigns(true)
        renderMoonSigns(false)
        val params: FrameLayout.LayoutParams =
            pie_chart.layoutParams as FrameLayout.LayoutParams
        // Changes the height and width to the specified *pixels*
        // Changes the height and width to the specified *pixels*
        params.height = (screenWidth / 2).toInt()
        params.width = (screenWidth / 2).toInt()
        pie_chart.layoutParams = params

        val pieChart: PieChart = findViewById<View>(R.id.pie_chart) as PieChart
        val data: MutableList<ChartData> = java.util.ArrayList()
        data.add(ChartData("Fertile", 15F, Color.WHITE, Color.parseColor("#8C8BC0")))
        data.add(ChartData("Menstruation", 30F, Color.WHITE, Color.parseColor("#ECD9EA"))) //ARGS-> (display text, percentage)
        data.add(ChartData("Luteal", 25f, Color.WHITE, Color.parseColor("#A4C3DD")))
        data.add(ChartData("Follicular", 30F, Color.WHITE, Color.parseColor("#CABBDB")))
        pieChart.setAboutTextSize(56f)
        pieChart.setAboutTextColor(Color.WHITE)
        pieChart.setChartData(data)
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

                v.setOnClickListener {
                    val currentLayout = circleViews[i].findViewById<LinearLayout>(R.id.llMoon)
                    if (lastSelectedPos != -1) {
                        val lastLayout = circleViews[lastSelectedPos].findViewById<LinearLayout>(R.id.llMoon)
                        dayModels[lastSelectedPos].isClicked = false
                        lastLayout.background = null
                        if (dayModels[lastSelectedPos].isSelected){
                            lastLayout.background =
                                getDrawable(R.drawable.bg_item_selected)
                        }

                    }
                    dayModels[i].isClicked = true
                    currentLayout.background = null
                    currentLayout.background =
                        getDrawable(R.drawable.bg_item_selected_dotted)

                    tvClickedText.text = "Selected moon phase: ${dayModels[i].text}"
                    lastSelectedPos = i
                }
                circleViews.add(v)
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
                        isClicked = false,
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