package com.lucassantos.myweather.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.AnimationUtils
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

    /**
     * PT-BR: Essa função reproduz uma animação no textNameApp.
     * EN: This function plays an animation on the textNameApp.
     */
    private fun animateTextNameApp() {
        val animation = AnimationUtils.loadAnimation(this, R.anim.fade)
        mBinding.textNameApp.startAnimation(animation)
    }

    /**
     * PT-BR: Essa função abre a MainActivity depois de 1200 milis (1.2 segundos).
     * EN: This function open an MainActivity after 1200 milis (1.2 seconds).
     */
    private fun goMainActivity() {
        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, MainActivity::class.java)).also { finish() }
        }, 1200)
    }
}