package com.lucassantos.myweather.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.lucassantos.myweather.databinding.ActivitySettingsBinding

class SettingsActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivitySettingsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        setListenerUI()
    }

    private fun setListenerUI() {
        mBinding.topAppBarSettings.setNavigationOnClickListener { finish() }
        mBinding.linearDataLanguage.setOnClickListener {
            // TODO: Mostrar dialog
        }
        mBinding.linearLanguageApp.setOnClickListener {
            // TODO: Mostrar dialog
        }
        mBinding.linearTemperatureUnit.setOnClickListener {
            // TODO: Mostrar dialog
        }
    }
}