package com.app.general.balloonstore.presentation.base

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding

/**
 * Abstract activity class containing all the common code across activities
 */
abstract class BaseActivity<VB : ViewBinding> : AppCompatActivity() {
    abstract val bindingInflater : (LayoutInflater) -> VB

    private var _binding : ViewBinding? = null
    @Suppress("UNCHECKED_CAST")
    protected val binding : VB
        get() = _binding as VB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = bindingInflater.invoke(layoutInflater)
        setContentView(requireNotNull(_binding).root)
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}