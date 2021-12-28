package com.example.moonchildmandala

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_weight_scale.*

class WeightScaleActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weight_scale)
        gv_1.setValue(0f, 200f, 0f, 0.1f, 10)

        gv_1.setOnValueChangedListener {
            tvMeasurement.text = "$it lbs \n ${it*0.453592} Kg"
        }
    }
}