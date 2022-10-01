package com.app.general.balloonstore.data.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.app.general.balloonstore.BalloonListQuery
import com.app.general.balloonstore.type.Color
import com.app.general.balloonstore.type.Variant

@Entity(tableName = "balloons")
data class BalloonEntity(
    @PrimaryKey
    val id: String,
    val name: String,

    @ColumnInfo(name = "image_url")
    val imageUrl: String,
    val description: String,
    val color: Color,
    val variant: Variant,
    val price: Int,

    @ColumnInfo(name = "available_since")
    val availableSince: String?,

    @ColumnInfo(name = "custom_message")
    var customMessage: String? = null
) {

    companion object {
        fun nodeToEntity(node: BalloonListQuery.Node) : BalloonEntity {
            return BalloonEntity(
                id = node.id,
                name = node.name,
                imageUrl = node.imageUrl,
                description = node.description,
                color = node.color,
                variant = node.variant,
                price = node.price,
                availableSince = node.availableSince.toString()
            )
        }
    }
}