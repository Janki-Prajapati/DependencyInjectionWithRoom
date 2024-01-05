package com.jp.test.dependencyinjectionapp.utils

import android.content.Context
import android.content.DialogInterface
import android.icu.util.Calendar
import androidx.appcompat.app.AlertDialog
import com.jp.test.dependencyinjectionapp.interfaces.AlertDialogClickListeners

object Utils {

    fun getCurrentDate(calendar: Calendar): String {
        val formattedDayMonth = getFormattedDayAndMonth(
            calendar.get(Calendar.DAY_OF_MONTH),
            calendar.get(Calendar.MONTH) + 1
        )
        return "$formattedDayMonth-${
            calendar.get(
                Calendar.YEAR
            )
        }"
    }

    fun getFormattedDayAndMonth(dayOfMonth: Int, monthOfYear: Int): String {

        var day = ""
        var month = ""
        month = if (monthOfYear < 10) {
            "0$monthOfYear"
        } else {
            "$monthOfYear"
        }
        day = if (dayOfMonth < 10) {
            "0$dayOfMonth"
        } else {
            "$dayOfMonth"
        }

        return "$day-$month"
    }

    fun showAlertDialog(
        context: Context,
        title: String,
        message: String,
        positiveBtnText: String,
        negativeBtnText: String,
        fromFlag: String,
        listeners: AlertDialogClickListeners
    ) {
        AlertDialog.Builder(context).setTitle(title).setMessage(message).setPositiveButton(
            positiveBtnText
        ) { dialog, which ->
            listeners.positiveClick(fromFlag)
            dialog.dismiss()
        }.setNegativeButton(negativeBtnText) { dialog, which ->
            listeners.negativeClick(fromFlag)
            dialog.dismiss()
        }.setIcon(android.R.drawable.ic_dialog_alert)
            .show()
    }

}