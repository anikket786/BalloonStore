package com.app.general.balloonstore.presentation.balloons.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.recyclerview.widget.GridLayoutManager
import com.app.general.balloonstore.BalloonListQuery
import com.app.general.balloonstore.R
import com.app.general.balloonstore.common.errors.NetworkException
import com.app.general.balloonstore.common.extensions.safeNavigate
import com.app.general.balloonstore.databinding.FragmentBalloonListBinding
import com.app.general.balloonstore.presentation.balloons.state.BalloonsViewModel
import com.app.general.balloonstore.presentation.balloons.ui.adapters.BalloonsAdapter
import com.app.general.balloonstore.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class BalloonListFragment : BaseFragment<FragmentBalloonListBinding>() {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentBalloonListBinding
        get() = FragmentBalloonListBinding::inflate

    private val viewModel: BalloonsViewModel by hiltNavGraphViewModels(R.id.balloons_nav_graph)

    private lateinit var balloonsAdapter: BalloonsAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setAdapter()
        setToolbar()
    }

    private fun setAdapter() {
        balloonsAdapter = BalloonsAdapter { position ->
            val action = BalloonListFragmentDirections
                .actionBalloonListFragmentToBalloonsDetailsFragment(position)
            findNavController().safeNavigate(action)
        }

        binding.rvBalloonList.layoutManager = GridLayoutManager(context, 2)
        binding.rvBalloonList.adapter = balloonsAdapter

        viewModel.balloonsPager.observe(viewLifecycleOwner) { submitBalloonsData(it) }
        balloonsAdapter.addLoadStateListener { handleLoadState(it) }
    }

    private fun submitBalloonsData(balloonsPagingData: PagingData<BalloonListQuery.Edge>?) {
        lifecycleScope.launch {
            balloonsPagingData?.let {
                balloonsAdapter.submitData(it)
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
            when (it.error) {
                is NetworkException -> {
                    Toast.makeText(context, R.string.internet_not_available, Toast.LENGTH_LONG)
                        .show()
                }
                else -> {
                    Toast.makeText(context, R.string.something_went_wrong, Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun setToolbar() {
        binding.toolBar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.item_menu_my_balloons -> {
                    onCustomDesignsMenuItemCLicked()
                }
            }
            true
        }
    }

    private fun onCustomDesignsMenuItemCLicked() {
        val action =
            BalloonListFragmentDirections.actionBalloonListFragmentToFavoriteBalloonsFragment()
        findNavController().safeNavigate(action)
    }
}