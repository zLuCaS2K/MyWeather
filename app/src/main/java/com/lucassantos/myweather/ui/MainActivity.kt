package com.lucassantos.myweather.ui

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.lucassantos.myweather.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityMainBinding
    private lateinit var mViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        initializeViewModel()
    }

    private fun initializeViewModel() {
        mViewModel = ViewModelProvider(this, MainViewModelFactory(application)).get(MainViewModel::class.java)
        mViewModel.getWeather("-6.60667", "-39.06222")
        setObserversUI()
    }

    private fun setObserversUI() {
        mViewModel.mWeather.observe(this, {
            Log.v("TESTE", "$it")
        })
    }
}