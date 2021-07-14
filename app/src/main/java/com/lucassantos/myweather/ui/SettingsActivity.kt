package com.lucassantos.myweather.ui

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.lucassantos.myweather.R
import com.lucassantos.myweather.databinding.ActivitySettingsBinding
import com.lucassantos.myweather.databinding.DialogSettingsBinding
import com.lucassantos.myweather.utils.Constants
import com.lucassantos.myweather.utils.Utils

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
            showDialogSettings(Constants.DIALOGS.DATA_LANGUAGE)
        }
        mBinding.linearLanguageApp.setOnClickListener {
            showDialogSettings(Constants.DIALOGS.LANGUAGE_APP)
        }
        mBinding.linearTemperatureUnit.setOnClickListener {
            showDialogSettings(Constants.DIALOGS.TEMPERATURE_UNIT)
        }
    }

    private fun showDialogSettings(dialogType: String) {
        val builder = AlertDialog.Builder(this)
        val layoutInflater = LayoutInflater.from(this)
        val dialogBinding = DialogSettingsBinding.inflate(layoutInflater)
        val alertDialog = builder.create()
        val adapter: ArrayAdapter<String>
        when (dialogType) {
            Constants.DIALOGS.DATA_LANGUAGE -> {
                adapter = ArrayAdapter(this, R.layout.list_item, Utils.getListDataLanguage())
                dialogBinding.textTitleDialogSettings.text = getString(R.string.data_language)
                dialogBinding.btnSaveSettings.setOnClickListener {
                    saveDataLanguage()
                    alertDialog.dismiss()
                }
            }
            Constants.DIALOGS.LANGUAGE_APP -> {
                adapter = ArrayAdapter(this, R.layout.list_item, Utils.getListLanguageApp())
                dialogBinding.textTitleDialogSettings.text = getString(R.string.app_language)
                dialogBinding.btnSaveSettings.setOnClickListener {
                    saveLanguageApp()
                    alertDialog.dismiss()
                }
            }
            Constants.DIALOGS.TEMPERATURE_UNIT -> {
                adapter = ArrayAdapter(this, R.layout.list_item, Utils.getListTemperatureUnit())
                dialogBinding.textTitleDialogSettings.text = getString(R.string.temperature_unit)
                dialogBinding.btnSaveSettings.setOnClickListener {
                    saveTemperatureUnit()
                    alertDialog.dismiss()
                }
            }
            else -> {
                throw IllegalArgumentException("Tipo de dialog não encontrado!")
            }
        }

        dialogBinding.spinnerDialogSettings.adapter = adapter
        alertDialog.setView(dialogBinding.root)
        alertDialog.window?.let {
            it.setBackgroundDrawable(ColorDrawable(0))
            it.attributes?.windowAnimations = R.style.AlertDialogSweet
        }
        alertDialog.show()
    }

    private fun saveDataLanguage() {
        // TODO: Implementar
    }

    private fun saveLanguageApp() {
        // TODO: Implementar
    }

    private fun saveTemperatureUnit() {
        // TODO: Implementar
    }
}