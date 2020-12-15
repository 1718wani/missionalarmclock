package com.example.ikuya.missionalertclock.ui.setting

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import androidx.annotation.RequiresApi
import androidx.preference.PreferenceFragmentCompat
import com.example.ikuya.missionalertclock.R

class SettingsFragment : PreferenceFragmentCompat() {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
        val intent = Intent(Settings.ACTION_CHANNEL_NOTIFICATION_SETTINGS).apply {
            putExtra(Settings.EXTRA_APP_PACKAGE, "com.example.ikuya.missionalertclock")


        }
//        putExtra(Settings.EXTRA_CHANNEL_ID, .getId())

        startActivity(intent)
    }

}