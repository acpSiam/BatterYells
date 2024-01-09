package com.bmarpc.acpsiam.batteryells.otherclasses

import android.app.Activity
import android.content.Context
import android.util.AttributeSet
import androidx.preference.Preference
import androidx.preference.PreferenceViewHolder
import com.bmarpc.acpsiam.batteryells.R
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup

class LanguagePreference(context: Context, attrs: AttributeSet) : Preference(context, attrs) {

    init {
        widgetLayoutResource = R.layout.extra_preference_screen_language_layout
    }

    override fun onBindViewHolder(holder: PreferenceViewHolder) {
        super.onBindViewHolder(holder)

        // Setting Language Preference
        val enLanguageChip: Chip = holder.itemView.findViewById(R.id.preference_screen_en_language_pref_chip_id)
        val bnLanguageChip: Chip = holder.itemView.findViewById(R.id.preference_screen_bn_language_pref_chip_id)
        val languageChipGroup: ChipGroup = holder.itemView.findViewById(R.id.preference_screen_language_chip_group_id)

        if (LanguageHelper.getCurrentLocalePref(context) == "en") {
            enLanguageChip.isSelected = true
            bnLanguageChip.isSelected = false
            languageChipGroup.check(enLanguageChip.id)
        } else {
            enLanguageChip.isSelected = false
            bnLanguageChip.isSelected = true
            languageChipGroup.check(bnLanguageChip.id)
        }

        enLanguageChip.setOnCheckedChangeListener { _, isChecked ->
            LanguageHelper.setLocale(context, "en")
            MyMethods.restartActivity(context as Activity) // Restart the activity to apply the new locale effect
        }

        bnLanguageChip.setOnCheckedChangeListener { _, isChecked ->
            LanguageHelper.setLocale(context, "bn")
            MyMethods.restartActivity(context as Activity) // Restart the activity to apply the new locale effect
        }
    }
}
