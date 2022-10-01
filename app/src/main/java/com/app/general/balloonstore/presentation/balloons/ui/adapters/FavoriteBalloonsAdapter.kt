package com.app.general.balloonstore.presentation.balloons.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.app.general.balloonstore.data.local.model.BalloonEntity
import com.app.general.balloonstore.databinding.ItemFavoriteBalloonBinding
import com.app.general.balloonstore.presentation.balloons.ui.viewholders.FavoriteBalloonViewHolder

class FavoriteBalloonsAdapter :
    PagingDataAdapter<BalloonEntity, FavoriteBalloonViewHolder>(Comparator) {

    override fun onBindViewHolder(holder: FavoriteBalloonViewHolder, position: Int) {
        getItem(position)?.let { item -> holder.bind(item) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteBalloonViewHolder {
        val binding = ItemFavoriteBalloonBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return FavoriteBalloonViewHolder(binding)
    }

    object Comparator : DiffUtil.ItemCallback<BalloonEntity>() {
        override fun areItemsTheSame(oldItem: BalloonEntity, newItem: BalloonEntity): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: BalloonEntity,
            newItem: BalloonEntity,
        ): Boolean {
            return oldItem == newItem
        }
    }
}