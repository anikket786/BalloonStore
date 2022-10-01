package com.app.general.balloonstore.presentation.balloons.ui

import android.os.Bundle
import android.view.LayoutInflater
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.app.general.balloonstore.R
import com.app.general.balloonstore.databinding.ActivityBalloonsBinding
import com.app.general.balloonstore.presentation.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BalloonsActivity : BaseActivity<ActivityBalloonsBinding>() {

    override val bindingInflater: (LayoutInflater) -> ActivityBalloonsBinding
        get() = ActivityBalloonsBinding::inflate

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
    }
}