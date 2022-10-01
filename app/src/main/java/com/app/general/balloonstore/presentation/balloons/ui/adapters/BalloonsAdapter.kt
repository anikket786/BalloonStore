package com.app.general.balloonstore.presentation.balloons.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import com.app.general.balloonstore.BalloonListQuery
import com.app.general.balloonstore.databinding.ItemBalloonBinding
import com.app.general.balloonstore.presentation.balloons.ui.viewholders.BalloonViewHolder

class BalloonsAdapter(
    private val onItemClickListener: (Int) -> Unit
) : PagingDataAdapter<BalloonListQuery.Edge, BalloonViewHolder>(BalloonComparator) {

    override fun onBindViewHolder(holder: BalloonViewHolder, position: Int) {
        getItem(position)?.let { item -> holder.bind(item, onItemClickListener) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BalloonViewHolder {
        val binding = ItemBalloonBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BalloonViewHolder(binding)
    }
}