package com.app.general.balloonstore.presentation.balloons.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.paging.PagingData
import com.app.general.balloonstore.BalloonListQuery
import com.app.general.balloonstore.R
import com.app.general.balloonstore.databinding.FragmentBalloonsDetailsBinding
import com.app.general.balloonstore.presentation.balloons.state.BalloonsViewModel
import com.app.general.balloonstore.presentation.balloons.ui.adapters.BalloonsDetailsAdapter
import com.app.general.balloonstore.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Runnable
import kotlinx.coroutines.launch

@AndroidEntryPoint
class BalloonsDetailsFragment : BaseFragment<FragmentBalloonsDetailsBinding>() {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentBalloonsDetailsBinding
        get() = FragmentBalloonsDetailsBinding::inflate

    private val viewModel: BalloonsViewModel by hiltNavGraphViewModels(R.id.balloons_nav_graph)

    private lateinit var balloonsAdapter: BalloonsDetailsAdapter

    private val args: BalloonsDetailsFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setBalloonsViewPager()
    }

    private fun setBalloonsViewPager() {
        balloonsAdapter = BalloonsDetailsAdapter(viewModel::saveBalloonWithCustomMessage)
        binding.viewPager.adapter = balloonsAdapter
        binding.viewPager.offscreenPageLimit = 1
        binding.viewPager.post(Runnable { binding.viewPager.setCurrentItem(args.position, false) })
        viewModel.balloonsPager.observe(viewLifecycleOwner) { submitBalloonsData(it) }
    }

    private fun submitBalloonsData(balloonsPagingData: PagingData<BalloonListQuery.Edge>?) {
        lifecycleScope.launch {
            balloonsPagingData?.let {
                balloonsAdapter.submitData(it)
            }
        }
    }
}