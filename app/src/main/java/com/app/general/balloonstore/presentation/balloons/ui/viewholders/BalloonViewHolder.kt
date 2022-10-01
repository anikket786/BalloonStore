package com.app.general.balloonstore.presentation.balloons.ui.viewholders

import android.net.Uri
import androidx.recyclerview.widget.RecyclerView
import com.app.general.balloonstore.BalloonListQuery
import com.app.general.balloonstore.R
import com.app.general.balloonstore.databinding.ItemBalloonBinding
import com.app.general.balloonstore.common.utils.BALLOON_DESIGNS_BASE_URL
import com.bumptech.glide.Glide

class BalloonViewHolder(
    private val binding: ItemBalloonBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(data: BalloonListQuery.Edge, onItemClickListener: (Int) -> Unit) {
        with(data.node) {
            binding.root.setOnClickListener {
                onItemClickListener.invoke(absoluteAdapterPosition)
            }
            Glide.with(itemView)
                .load(Uri.parse("$BALLOON_DESIGNS_BASE_URL$imageUrl"))
                .into(binding.ivBalloon)

            binding.tvDesignName.text = name
            binding.tvPrice.text = String.format(
                itemView.context.getString(R.string.balloon_price),
                price
            )
        }
    }
}