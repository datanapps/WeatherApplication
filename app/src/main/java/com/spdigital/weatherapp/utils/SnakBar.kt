package com.spdigital.weatherapp.utils


import android.app.Activity
import android.content.Context
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar


object SnakBar {

    /**
     * This method is used to display debug log message
     */


    fun show(activity: Activity?, msg: String) {
        if (activity != null) {

            Snackbar.make(activity.findViewById(android.R.id.content), msg, Snackbar.LENGTH_SHORT)
                .show()


        }

    }


    /*
     * if you are passing context from some where then it will be show toast because snackbar can show only for activit
     * */
    fun show(activity: Context?, snackBarMsg: String) {

        if (activity != null) {
            if (activity is Activity) {
                show(activity, snackBarMsg)
            } else {
                Toast.makeText(activity, validateString(snackBarMsg), Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun validateString(msg: String?): String {
        return msg ?: "null"
    }

}
