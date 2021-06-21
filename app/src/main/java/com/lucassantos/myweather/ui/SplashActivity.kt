package com.lucassantos.myweather.ui

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.lucassantos.myweather.R
import com.lucassantos.myweather.databinding.ActivitySplashBinding

class SplashActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        animateTextNameApp()
        goMainActivity()
    }

    private fun animateTextNameApp() {
        val animation = AnimationUtils.loadAnimation(this, R.anim.fade)
        mBinding.textNameApp.startAnimation(animation)
    }

    private fun goMainActivity() {
        Handler(Looper.getMainLooper()).postDelayed({
            Toast.makeText(this, "Teste", Toast.LENGTH_SHORT).show()
        }, 1200)
    }
}