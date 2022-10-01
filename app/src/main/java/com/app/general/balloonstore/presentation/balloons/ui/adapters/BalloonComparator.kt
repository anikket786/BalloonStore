package com.app.general.balloonstore.presentation.balloons.ui.adapters

import androidx.recyclerview.widget.DiffUtil
import com.app.general.balloonstore.BalloonListQuery

object BalloonComparator : DiffUtil.ItemCallback<BalloonListQuery.Edge>() {
    override fun areItemsTheSame(
        oldItem: BalloonListQuery.Edge,
        newItem: BalloonListQuery.Edge
    ): Boolean {
        return oldItem.node.id == newItem.node.id
    }

    override fun areContentsTheSame(
        oldItem: BalloonListQuery.Edge,
        newItem: BalloonListQuery.Edge,
    ): Boolean {
        return oldItem == newItem
    }
}