package com.app.general.balloonstore.presentation.balloons.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import com.app.general.balloonstore.BalloonListQuery
import com.app.general.balloonstore.databinding.ItemBalloonDetailsBinding
import com.app.general.balloonstore.presentation.balloons.ui.viewholders.BalloonDetailsViewHolder

class BalloonsDetailsAdapter(
    private val saveCustomMessageClickListener: (node: BalloonListQuery.Node, message: String) -> Unit
) : PagingDataAdapter<BalloonListQuery.Edge, BalloonDetailsViewHolder>(BalloonComparator) {

    override fun onBindViewHolder(holder: BalloonDetailsViewHolder, position: Int) {
        getItem(position)?.let { item -> holder.bind(item) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BalloonDetailsViewHolder {
        val binding = ItemBalloonDetailsBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return BalloonDetailsViewHolder(binding, saveCustomMessageClickListener)
    }
}