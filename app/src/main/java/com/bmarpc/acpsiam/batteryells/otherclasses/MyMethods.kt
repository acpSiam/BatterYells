package com.bmarpc.acpsiam.batteryells.otherclasses

import android.app.Activity
import android.content.Intent

class MyMethods {
    companion object {
        fun restartActivity(activity: Activity) {
            val intent = Intent(activity, activity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            activity.startActivity(intent)
            activity.finish()
        }
    }
}
