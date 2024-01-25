package com.bmarpc.acpsiam.batteryells.otherclasses

import android.content.Context
import android.content.SharedPreferences
import android.content.res.Configuration
import android.content.res.Resources
import com.bmarpc.acpsiam.batteryells.R
import java.util.Locale

object LanguageHelper {

    fun setLocale(context: Context, languageCode: String) {
        val locale = Locale(languageCode)
        Locale.setDefault(locale)

        val resources: Resources = context.resources
        val config: Configuration = Configuration(resources.configuration)
        config.setLocale(locale)
        resources.updateConfiguration(config, resources.displayMetrics)

        val sharedPreferences: SharedPreferences = context.getSharedPreferences(
            context.getString(R.string.SHARED_PREFERENCES_APP_PROCESS),
            Context.MODE_PRIVATE
        )
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.putString(context.getString(R.string.CURRENT_LOCALE), languageCode)
        editor.apply()
    }

    fun getCurrentLocalePref(context: Context): String {
        val sharedPreferences: SharedPreferences = context.getSharedPreferences(
            context.getString(R.string.SHARED_PREFERENCES_APP_PROCESS),
            Context.MODE_PRIVATE
        )
        return sharedPreferences.getString(context.getString(R.string.CURRENT_LOCALE), "en") ?: "en"
    }
}
