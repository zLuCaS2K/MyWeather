package com.lucassantos.myweather.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.lucassantos.myweather.R
import com.lucassantos.myweather.databinding.ActivitySplashBinding
import com.lucassantos.myweather.extensions.readSettingsInDataStore
import com.lucassantos.myweather.extensions.saveSettingsInDataStore
import com.lucassantos.myweather.utils.Constants
import com.lucassantos.myweather.utils.Utils
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class SplashActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        animateTextNameApp()
        verifyIfIsFirstRunApp()
        goMainActivityDelayed()
    }

    /**
     * PT-BR: Reproduz uma animação no textNameApp.
     * EN: Play animation on the textNameApp.
     */
    private fun animateTextNameApp() {
        val animation = AnimationUtils.loadAnimation(this, R.anim.fade)
        mBinding.textNameApp.startAnimation(animation)
    }

    /**
     * PT-BR: Verificando se é a primeira vez que o app está sendo executado.
     * EN: Verify if is first running application.
     */
    private fun verifyIfIsFirstRunApp() {
        lifecycleScope.launch {
            val languageData = readSettingsInDataStore(Constants.PREFERENCES.LANGUAGE_DATA).first()
            if (languageData == Constants.PREFERENCES.NO_PREFERENCES) {
                saveSettingsDefaultInDataStore()
            }
        }
    }

    /**
     * PT-BR: Salvando o idioma dos dados e unidade de temperatura do retorno da API.
     * EN: Saving the data language and API return temperature unit.
     */
    private fun saveSettingsDefaultInDataStore() {
        saveSettingsInDataStore(
            Constants.PREFERENCES.LANGUAGE_DATA,
            Utils.getListDataLanguage().first()
        )
        saveSettingsInDataStore(
            Constants.PREFERENCES.TEMPERATURE_UNIT,
            Utils.getListTemperatureUnitAPI().first()
        )
    }

    /**
     * PT-BR: Iniciando a MainActivity com 1.2 segundos de atraso.
     * EN: Starting MainActivity with 1.2 seconds delayed.
     */
    private fun goMainActivityDelayed() {
        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, MainActivity::class.java)).also { finish() }
        }, 1200)
    }
}