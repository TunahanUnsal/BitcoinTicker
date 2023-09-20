package com.example.project.util

import android.app.AlertDialog
import android.content.Context

object UiUtil {

    fun customAlertDialog(context: Context,message:String){
        val builder = AlertDialog.Builder(context)

        with(builder)
        {
            setMessage(message)
            setPositiveButton("OK") { dialog, _ ->
                dialog.dismiss()
            }
            show()
        }
    }
}