package com.lucassantos.myweather.ui

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Looper
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.android.gms.location.*
import com.google.android.material.snackbar.Snackbar
import com.lucassantos.myweather.R
import com.lucassantos.myweather.databinding.ActivityMainBinding
import com.lucassantos.myweather.extensions.getAlertDialog
import com.lucassantos.myweather.extensions.readSettingsInDataStore
import com.lucassantos.myweather.model.domain.Weather
import com.lucassantos.myweather.utils.Constants
import com.lucassantos.myweather.utils.Utils
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityMainBinding
    private lateinit var mViewModel: MainViewModel

    private lateinit var mLocationRequest: LocationRequest
    private lateinit var mLocationCallback: LocationCallback
    private lateinit var mLocationClient: FusedLocationProviderClient

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
        setupLocationRequest()
        setupLocationCallback()
        initializeLocationClient()
    }

    /**
     * PT-BR: Essa função inicializa o MainViewModel.
     * EN: This function initializes observers.
     */
    private fun initializeViewModel() {
        mViewModel = ViewModelProvider(this, MainViewModelFactory(application)).get(MainViewModel::class.java)
    }

    private fun setupLocationRequest() {
        mLocationRequest = LocationRequest.create()
        mLocationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
    }

    private fun setupLocationCallback() {
        mLocationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                locationResult.let {
                    lifecycleScope.launch {
                        val languageData = readSettingsInDataStore(Constants.PREFERENCES.LANGUAGE_DATA).first()
                        val temperatureUnit = readSettingsInDataStore(Constants.PREFERENCES.TEMPERATURE_UNIT).first()
                        mViewModel.getWeather(
                            it.lastLocation.latitude.toString(),
                            it.lastLocation.longitude.toString(),
                            languageData,
                            temperatureUnit
                        )
                    }
                }
            }
        }
    }

    private fun initializeLocationClient() {
        mLocationClient = LocationServices.getFusedLocationProviderClient(this)
    }

    private fun checkPermissionLocation() {
        if (
            ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED
            && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION)
            != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(this, arrayOf(
                android.Manifest.permission.ACCESS_FINE_LOCATION,
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            ), Constants.REQUESTS.REQUEST_CODE_LOCATION)
            return
        }
        mLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.getMainLooper())
    }

    /**
     * PT-BR: Essa função inicializa os observers.
     * EN: This function initializes observers.
     */
    private fun setObserversUI() {
        mViewModel.mWeather.observe(this, {
            if (it == null) {
                Snackbar.make(
                    mBinding.linearContainerBottom,
                    getString(R.string.snackbar_update_data),
                    Snackbar.LENGTH_INDEFINITE
                ).setAction(getString(R.string.update)) {
                    checkPermissionLocation()
                }.show()
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
            checkPermissionLocation()
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
        lifecycleScope.launch {
            with(mBinding) {
                val temperatureUnit = readSettingsInDataStore(Constants.PREFERENCES.TEMPERATURE_UNIT).first()
                this.textLocationStatus.text = weather.location
                Glide.with(this@MainActivity)
                    .asBitmap()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .load("${Constants.API.URL_ICON}${weather.weatherAPI.first().icon}@2x.png")
                    .placeholder(R.drawable.ic_downloading)
                    .error(R.drawable.ic_signal_error)
                    .into(this.imageWeatherStatus)

                /**
                 * PT-BR: Verificando qual é a unidade de temperatura.
                 * EN:
                 */
                if (temperatureUnit == Utils.getListTemperatureUnitAPI().first()) {
                    this.textTemperatureStatus.text =
                        getString(R.string.temperature_data_celsius, weather.main.temperature.toString())
                    this.textFeelsLikeTemp.text =
                        getString(R.string.temperature_data_celsius, weather.main.feels_like.toString())
                } else {
                    this.textTemperatureStatus.text =
                        getString(R.string.temperature_data_fahrenheit, weather.main.temperature.toString())
                    this.textFeelsLikeTemp.text =
                        getString(R.string.temperature_data_fahrenheit, weather.main.feels_like.toString())
                }

                this.textDescriptionStatus.text = weather.weatherAPI.first().description
                this.textMainStatus.text = weather.weatherAPI.first().main
                this.textHumidity.text =
                    getString(R.string.humidity_data, weather.main.humidity)
                this.textWind.text =
                    getString(R.string.wind_data, weather.wind.wind)
                this.textPressure.text =
                    getString(R.string.pressure_data, weather.main.pressure)
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<out String>, grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == Constants.REQUESTS.REQUEST_CODE_LOCATION && grantResults.isNotEmpty()) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(applicationContext, getString(R.string.please_wait), Toast.LENGTH_SHORT).show()
                checkPermissionLocation()
            } else {
                Toast.makeText(applicationContext, getString(R.string.error_permission), Toast.LENGTH_SHORT).show()
            }
        }
    }
}