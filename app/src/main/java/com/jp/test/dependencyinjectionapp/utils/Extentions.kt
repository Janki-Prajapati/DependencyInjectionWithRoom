package com.jp.test.dependencyinjectionapp.utils

import android.content.Context
import android.view.View
import android.widget.Toast

fun Context.showToast(message:String){
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}