package com.app.general.balloonstore.presentation.balloons.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.general.balloonstore.R
import com.app.general.balloonstore.data.local.model.BalloonEntity
import com.app.general.balloonstore.databinding.FragmentFavoriteBalloonsBinding
import com.app.general.balloonstore.presentation.balloons.state.FavoriteBalloonsViewModel
import com.app.general.balloonstore.presentation.balloons.ui.adapters.FavoriteBalloonsAdapter
import com.app.general.balloonstore.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FavoriteBalloonsFragment : BaseFragment<FragmentFavoriteBalloonsBinding>() {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentFavoriteBalloonsBinding
        get() = FragmentFavoriteBalloonsBinding::inflate

    private val viewModel: FavoriteBalloonsViewModel by viewModels()

    private lateinit var favoriteBalloonsAdapter: FavoriteBalloonsAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListeners()
        setAdapter()
    }

    private fun setupListeners() {
        binding.toolBar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun setAdapter() {
        favoriteBalloonsAdapter = FavoriteBalloonsAdapter()

        binding.rvFavorites.layoutManager = LinearLayoutManager(context)
        binding.rvFavorites.adapter = favoriteBalloonsAdapter

        viewModel.favoriteBalloonsPager.observe(viewLifecycleOwner) { submitBalloonsData(it) }
        favoriteBalloonsAdapter.addLoadStateListener { handleLoadState(it) }
    }

    private fun submitBalloonsData(balloonsPagingData: PagingData<BalloonEntity>?) {
        lifecycleScope.launch {
            balloonsPagingData?.let {
                favoriteBalloonsAdapter.submitData(it)
            }
        }
    }

    private fun handleLoadState(loadState: CombinedLoadStates) {
        val errorState = when {
            loadState.append is LoadState.Error -> loadState.append as LoadState.Error
            loadState.prepend is LoadState.Error -> loadState.prepend as LoadState.Error
            loadState.refresh is LoadState.Error -> loadState.refresh as LoadState.Error
            else -> null
        }
        errorState?.let {
            Toast.makeText(context, R.string.something_went_wrong, Toast.LENGTH_LONG).show()
        }
    }
}