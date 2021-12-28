package com.example.moonchildmandala

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.view.LayoutInflater
import android.view.View
import java.util.ArrayList
import com.example.moonchildmandala.pieChart.ChartData
import com.example.moonchildmandala.pieChart.PieChart

class
ExampleActivity : AppCompatActivity() {
    private var adapter: CircularItemAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_example)

        // simple text item with numbers 0 ~ 9
        val itemTitles = ArrayList<String>()
        for (i in 0..30) {
            itemTitles.add(i.toString())
        }

        val pieChart: PieChart = findViewById<View>(R.id.pie_chart) as PieChart
        val data: MutableList<ChartData> = ArrayList()

        data.add(ChartData("Fertile", 15F, Color.WHITE, Color.BLUE))
        data.add(ChartData("Follicular", 25F, Color.WHITE, Color.GREEN))
        data.add(ChartData("Luteal", 30F, Color.WHITE, Color.MAGENTA))
        data.add(ChartData("Menstruation", 30F, Color.WHITE, Color.CYAN)) //ARGS-> (display text, percentage)
        pieChart.setChartData(data)


        // usage sample
        val circularListView = findViewById<View>(R.id.my_circular_list) as CircularListView
        val textView = findViewById<View>(R.id.tvClickedText) as TextView
        adapter = CircularItemAdapter(layoutInflater, itemTitles)
        circularListView.setAdapter(adapter)
        circularListView.setRadius(400f)
        circularListView.setOnItemClickListener(object : CircularTouchListener.CircularItemClickListener {
            override fun onItemClick(view: View?, index: Int) {
                textView.text = "Circle $index Clicked"

            }
        })
    }

    // you should extends CircularAdapter to add your custom item
    private inner class CircularItemAdapter(private val mInflater: LayoutInflater, private val mItems: ArrayList<String>) :
        CircularAdapter() {
        private val mItemViews: ArrayList<View> = ArrayList()
        override val count: Int
            get() = mItemViews.size

        override val allViews: ArrayList<View>
            get() = mItemViews


        override fun getItemAt(i: Int): View {
            return mItemViews[i]
        }

        override fun removeItemAt(i: Int) {
            if (mItemViews.size > 0) {
                mItemViews.removeAt(i)
                notifyItemChange()
            }
        }

        override fun addItem(view: View) {
            mItemViews.add(view)
            notifyItemChange()
        }

        init {
            for (s in mItems) {
                val view = mInflater.inflate(R.layout.view_circular_item, null)
                val itemView = view.findViewById<View>(R.id.bt_item) as TextView
                itemView.text = s
                mItemViews.add(view)
            }
        }
    }
}