package com.lucassantos.myweather.extensions

import android.app.Activity
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.lucassantos.myweather.R
import com.lucassantos.myweather.databinding.DialogErrorBinding
import com.lucassantos.myweather.databinding.DialogLoadingBinding
import com.lucassantos.myweather.utils.Constants

/**
 * PT-BR: Essa função retorna um AlertDialog de erro ou de carregamento. (0 para carregamento e 1 para erro).
 * EN: This function returns an error or loading AlertDialog. (0 for loading and 1 for error).
 */

fun AppCompatActivity.getAlertDialog(activity: Activity = this, dialogType: String): AlertDialog {
    val builder = AlertDialog.Builder(activity)
    val layoutInflater = LayoutInflater.from(activity)
    val dialogBinding = if (dialogType == Constants.DIALOGS.DIALOG_LOADING) {
        builder.setCancelable(false)
        DialogLoadingBinding.inflate(layoutInflater)
    } else {
        DialogErrorBinding.inflate(layoutInflater)
    }
    builder.setView(dialogBinding.root)
    val alertDialog = builder.create()
    alertDialog.window?.let {
        it.setBackgroundDrawable(ColorDrawable(0))
        it.attributes?.windowAnimations = R.style.AlertDialogSweet
    }
    return alertDialog
}