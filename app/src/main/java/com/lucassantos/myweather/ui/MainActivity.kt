package com.lucassantos.myweather.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.lucassantos.myweather.databinding.ActivityMainBinding
import com.lucassantos.myweather.extensions.getAlertDialog
import com.lucassantos.myweather.model.domain.Weather

class MainActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityMainBinding
    private lateinit var mViewModel: MainViewModel
    private val mAlertDialogLoading by lazy {
        getAlertDialog(this, 0)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        initializeViewModel()
        setObserversUI()
        setListennersUI()
    }

    private fun initializeViewModel() {
        mViewModel = ViewModelProvider(this, MainViewModelFactory(application))
            .get(MainViewModel::class.java)
        mViewModel.getWeather("-6.60667", "-39.06222")
    }

    private fun setObserversUI() {
        mViewModel.mWeather.observe(this, {
            setDataInUI(it)
        })
        mViewModel.isViewLoading.observe(this, {
            if (it) {
                mAlertDialogLoading.show()
            } else {
                mAlertDialogLoading.dismiss()
            }
        })
    }

    private fun setListennersUI() {
        mBinding.imageButtonRefresh.setOnClickListener {
            mViewModel.getWeather("-6.60667", "-39.06222")
        }
    }

    private fun setDataInUI(weather: Weather) {
        mBinding.apply {
            this.textLocationStatus.text = weather.location
            this.textTemperatureStatus.text = weather.main.temperature.toString()
            this.textDescriptionStatus.text = weather.weatherAPI.first().description
            this.textMainStatus.text = weather.weatherAPI.first().main

            this.textFeelsLikeTemp.text = weather.main.feels_like.toString()
            this.textHumidity.text = weather.main.humidity
            this.textWind.text = weather.wind.wind.toString()
            this.textPressure.text = "${weather.main.pressure} hPa"
        }
    }
}