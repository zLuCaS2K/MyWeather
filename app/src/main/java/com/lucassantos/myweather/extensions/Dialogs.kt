package com.lucassantos.myweather.extensions

import android.app.Activity
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.lucassantos.myweather.databinding.DialogErrorBinding
import com.lucassantos.myweather.databinding.DialogLoadingBinding

/**
 * PT-BR: Esse metódo retorna um AlertDialog de erro ou de carregamento. (0 para carregamento e 1 para erro).
 * EN: This method returns an error or loading AlertDialog. (0 for loading and 1 for error).
 */

fun AppCompatActivity.getAlertDialog(activity: Activity = this, typeDialog: Int) : AlertDialog {
    val builder = AlertDialog.Builder(activity)
    val layoutInflater = LayoutInflater.from(activity)
    val dialogBinding = if (typeDialog == 0) {
        builder.setCancelable(false)
        DialogLoadingBinding.inflate(layoutInflater)
    } else {
        builder.setCancelable(true)
        DialogErrorBinding.inflate(layoutInflater)
    }
    builder.setView(dialogBinding.root)
    val alertDialog = builder.create()
    alertDialog.window?.let {
        it.setBackgroundDrawable(ColorDrawable(0))
        // TODO: Implementar a animação do dialog
    }
    return alertDialog
}