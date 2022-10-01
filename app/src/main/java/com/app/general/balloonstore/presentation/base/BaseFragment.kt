package com.app.general.balloonstore.presentation.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

/**
 * Abstract fragment class containing all the common code across fragments
 */
abstract class BaseFragment<VB : ViewBinding> : Fragment() {
    abstract val bindingInflater : (LayoutInflater, ViewGroup?, Boolean) -> VB

    private var _binding : ViewBinding? = null
    @Suppress("UNCHECKED_CAST")
    protected val binding : VB
        get() = _binding as VB

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        _binding = bindingInflater.invoke(inflater, container, false)
        return requireNotNull(_binding).root
    }
}