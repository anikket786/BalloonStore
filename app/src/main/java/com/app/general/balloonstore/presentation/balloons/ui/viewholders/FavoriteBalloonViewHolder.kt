package com.app.general.balloonstore.presentation.balloons.ui.viewholders

import android.net.Uri
import androidx.recyclerview.widget.RecyclerView
import com.app.general.balloonstore.R
import com.app.general.balloonstore.common.utils.BALLOON_DESIGNS_BASE_URL
import com.app.general.balloonstore.data.local.model.BalloonEntity
import com.app.general.balloonstore.databinding.ItemFavoriteBalloonBinding
import com.bumptech.glide.Glide

class FavoriteBalloonViewHolder(
    private val binding: ItemFavoriteBalloonBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(data: BalloonEntity) {
        with(data) {
            Glide.with(itemView)
                .load(Uri.parse("$BALLOON_DESIGNS_BASE_URL$imageUrl"))
                .into(binding.ivBalloon)

            binding.tvDesignName.text = name
            binding.tvPrice.text = String.format(
                itemView.context.getString(R.string.balloon_price),
                price
            )
            binding.tvCustomMessage.text = customMessage
        }
    }
}