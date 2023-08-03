package com.example.aisleassignment.moudles.core.utils

import android.content.Context
import android.util.Patterns
import android.widget.Toast

object Utils {

    fun isValidMobile(phone: String): Boolean {
        return Patterns.PHONE.matcher(phone).matches()
    }

    fun showToast(context: Context, text: String) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
    }
}