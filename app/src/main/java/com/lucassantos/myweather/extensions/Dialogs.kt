package com.lucassantos.myweather.extensions

import android.app.Activity
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.lucassantos.myweather.databinding.DialogLoadingBinding

fun AppCompatActivity.getAlertDialog(activity: Activity = this, typeDialog: Int) : AlertDialog {
    val builder = AlertDialog.Builder(activity)
    val layoutInflater = LayoutInflater.from(activity)
    val dialogBinding = if (typeDialog == 0) {
        DialogLoadingBinding.inflate(layoutInflater)
    } else {
        DialogLoadingBinding.inflate(layoutInflater)
    }
    builder.setView(dialogBinding.root)
    builder.setCancelable(false)
    val alertDialog = builder.create()
    alertDialog.window?.let {
        it.setBackgroundDrawable(ColorDrawable(0))
        // TODO: Implementar a animação do dialog
    }
    return alertDialog
}