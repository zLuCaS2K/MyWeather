package com.lucassantos.myweather.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.android.material.snackbar.Snackbar
import com.lucassantos.myweather.R
import com.lucassantos.myweather.databinding.ActivityMainBinding
import com.lucassantos.myweather.extensions.getAlertDialog
import com.lucassantos.myweather.model.domain.Weather
import com.lucassantos.myweather.utils.Constants

class MainActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityMainBinding
    private lateinit var mViewModel: MainViewModel

    private val mAlertDialogLoading: AlertDialog by lazy {
        getAlertDialog(this, 0)
    }
    private val mAlertDialogError: AlertDialog by lazy {
        getAlertDialog(this, 1)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        initializeViewModel()
        setObserversUI()
        setListennersUI()
    }

    /**
     * PT-BR: Essa função inicializa o MainViewModel.
     * EN: This function initializes observers.
     */
    private fun initializeViewModel() {
        mViewModel = ViewModelProvider(this, MainViewModelFactory(application)).get(MainViewModel::class.java)
    }

    /**
     * PT-BR: Essa função inicializa os observers.
     * EN: This function initializes observers.
     */
    private fun setObserversUI() {
        mViewModel.mWeather.observe(this, {
            if (it == null) {
                Snackbar.make(mBinding.textPressure, "Sem dados salvo localmente", Snackbar.LENGTH_SHORT).show()
            } else {
                setDataInUI(it)
            }
        })
        mViewModel.isViewLoading.observe(this, {
            if (it) mAlertDialogLoading.show() else mAlertDialogLoading.dismiss()
        })
        mViewModel.anErrorOccurred.observe(this, {
            if (it) mAlertDialogError.show() else mAlertDialogError.dismiss()
        })
    }

    private fun setListennersUI() {
        mBinding.imageButtonRefresh.setOnClickListener {
            mViewModel.getWeather("-6.60667", "-39.06222")
        }
        mBinding.imageButtonSettings.setOnClickListener {
            startActivity(Intent(this, SettingsActivity::class.java))
        }
    }

    /**
     * PT-BR: Essa função insere os dados na UI.
     * EN: This function enters data into the UI.
     */
    private fun setDataInUI(weather: Weather) {
        mBinding.apply {
            this.textLocationStatus.text = weather.location
            Glide.with(this@MainActivity)
                .asBitmap()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .load("${Constants.API.URL_ICON}${weather.weatherAPI.first().icon}@2x.png")
                .placeholder(R.drawable.ic_downloading)
                .error(R.drawable.ic_signal_error)
                .into(this.imageWeatherStatus)
            this.textTemperatureStatus.text =
                getString(R.string.temperature_data, weather.main.temperature.toString())
            this.textDescriptionStatus.text = weather.weatherAPI.first().description
            this.textMainStatus.text = weather.weatherAPI.first().main

            this.textFeelsLikeTemp.text =
                getString(R.string.temperature_data, weather.main.feels_like.toString())
            this.textHumidity.text =
                getString(R.string.humidity_data, weather.main.humidity)
            this.textWind.text =
                getString(R.string.wind_data, weather.wind.wind)
            this.textPressure.text =
                getString(R.string.pressure_data, weather.main.pressure)
        }
    }
}