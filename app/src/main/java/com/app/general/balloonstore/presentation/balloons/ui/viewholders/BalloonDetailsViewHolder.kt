package com.app.general.balloonstore.presentation.balloons.ui.viewholders

import android.net.Uri
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.app.general.balloonstore.BalloonListQuery
import com.app.general.balloonstore.R
import com.app.general.balloonstore.common.extensions.hideKeyboard
import com.app.general.balloonstore.common.extensions.onTextChanged
import com.app.general.balloonstore.common.utils.BALLOON_DESIGNS_BASE_URL
import com.app.general.balloonstore.common.utils.formatDateTime
import com.app.general.balloonstore.databinding.ItemBalloonDetailsBinding
import com.bumptech.glide.Glide

class BalloonDetailsViewHolder(
    private val binding: ItemBalloonDetailsBinding,
    private val saveCustomMessageClickListener: (node: BalloonListQuery.Node, message: String) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(data: BalloonListQuery.Edge) {
        with(data.node) {
            Glide.with(itemView)
                .load(Uri.parse("$BALLOON_DESIGNS_BASE_URL$imageUrl"))
                .into(binding.ivBalloon)

            binding.tvDesignName.text = name
            binding.tvDescription.text = description
            binding.tvPrice.text = String.format(
                itemView.context.getString(R.string.balloon_price),
                price
            )

            val timeStamp = availableSince as? String
            timeStamp?.let {
                val date = formatDateTime(it, "dd MMM yyyy")
                binding.tvAvailableSince.text = String.format(
                    itemView.context.getString(R.string.available_since),
                    date ?: "NA"
                )
                binding.tvAvailableSince.visibility = View.VISIBLE
            }

            binding.etCustomMessage.onTextChanged {
                if ((it?.length ?: 0) >= 10) {
                    binding.btnSaveMessage.visibility = View.VISIBLE
                } else {
                    binding.btnSaveMessage.visibility = View.GONE
                }
            }

            binding.btnSaveMessage.setOnClickListener {
                saveCustomMessageClickListener.invoke(
                    data.node,
                    binding.etCustomMessage.text.toString()
                )
                binding.btnSaveMessage.visibility = View.GONE
                binding.etCustomMessage.text?.clear()
                binding.etCustomMessage.hideKeyboard()
                Toast.makeText(
                    itemView.context,
                    R.string.saved_successfully,
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }
}