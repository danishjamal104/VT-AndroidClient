package com.github.code.gambit.utility.extention

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.view.animation.AnimationUtils
import android.view.animation.DecelerateInterpolator
import android.view.inputmethod.InputMethodManager
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.github.code.gambit.R
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout

fun Window.setStatusColor(color: Int) {
    this.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
    this.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
    this.statusBarColor = color
}

fun Window.fullscreen() {
    this.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
}

fun Window.exitFullscreen() {
    this.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
}

fun View.anim(delay: Long) {
    this.animate().translationY(0f)
        .alpha(1f)
        .setInterpolator(DecelerateInterpolator())
        .setStartDelay(delay).setDuration(700).start()
}

fun View.hide() {
    if (visibility != View.GONE) {
        this.visibility = View.GONE
    }
}

fun View.show() {
    if (visibility != View.VISIBLE) {
        this.visibility = View.VISIBLE
    }
}

fun View.toggleVisibility() {
    if (isVisible) {
        visibility = View.GONE
        return
    }
    visibility = View.VISIBLE
}

fun List<View>.hideAll() {
    this.forEach { it.hide() }
}

fun List<View>.showAll() {
    this.forEach { it.show() }
}

fun View.snackbar(message: String, view: View) {
    Snackbar.make(this, message, Snackbar.LENGTH_INDEFINITE)
        .setAnchorView(view)
        .also { snackbar ->
            snackbar.setAction("OK") {
                snackbar.dismiss()
            }
                .show()
        }
}

fun View.snackbar(message: String) {
    Snackbar.make(this, message, Snackbar.LENGTH_INDEFINITE)
        .also { snackbar ->
            snackbar.setAction("OK") {
                snackbar.dismiss()
            }
                .show()
        }
}

fun RelativeLayout.bottomNavHide() {
    if (isVisible) {
        visibility = View.GONE
        animation = AnimationUtils.loadAnimation(context, R.anim.bottom_nav_hide)
    }
}

fun RelativeLayout.bottomNavShow() {
    if (!isVisible) {
        visibility = View.VISIBLE
        animation = AnimationUtils.loadAnimation(context, R.anim.bottom_nav_show)
    }
}

fun TextInputLayout.setText(text: String) {
    if (this.editText != null) {
        this.editText!!.setText(text)
    }
}

fun Fragment.hideKeyboard() {
    view?.let { activity?.hideKeyboard(it) }
}

fun Activity.hideKeyboard() {
    hideKeyboard(currentFocus ?: View(this))
}

fun Context.hideKeyboard(view: View) {
    val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}

fun Fragment.shortToast(message: String) {
    Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
}

fun Fragment.longToast(message: String) {
    Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
}