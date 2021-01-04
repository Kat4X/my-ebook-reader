package com.kat4x.myebookreader.utils

import android.content.Context
import android.content.res.Resources
import android.widget.Toast

fun Int.toDp(): Int = (this / Resources.getSystem().displayMetrics.density).toInt()
fun Int.toPx(): Int = (this * Resources.getSystem().displayMetrics.density).toInt()

val Int.dp: Int
    get() = (this / Resources.getSystem().displayMetrics.density).toInt()
val Int.px: Int
    get() = (this * Resources.getSystem().displayMetrics.density).toInt()

fun Context.makeToast(text: String, isProlonged: Boolean = false) {
    Toast.makeText(this, text, if (isProlonged) Toast.LENGTH_LONG else Toast.LENGTH_SHORT).show()
}