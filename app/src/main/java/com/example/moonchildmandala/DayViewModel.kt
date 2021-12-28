package com.example.moonchildmandala

import android.graphics.drawable.Drawable

data class DayViewModel(
    val text: String,
    val showImage: Boolean,
    var isSelected: Boolean,
    val icon: Drawable?
)
