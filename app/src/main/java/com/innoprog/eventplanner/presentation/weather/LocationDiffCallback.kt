package com.innoprog.eventplanner.presentation.weather

import androidx.recyclerview.widget.DiffUtil
import com.innoprog.eventplanner.domain.model.ForecastLocation

class LocationDiffCallback(
    private val oldList: List<ForecastLocation>,
    private val newList: List<ForecastLocation>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(
        oldItemPosition: Int,
        newItemPosition: Int
    ): Boolean {
        val oldLocation = oldList[oldItemPosition]
        val newLocation = newList[newItemPosition]
        return oldLocation.id == newLocation.id
    }

    override fun areContentsTheSame(
        oldItemPosition: Int,
        newItemPosition: Int
    ): Boolean {
        val oldLocation = oldList[oldItemPosition]
        val newLocation = newList[newItemPosition]
        return oldLocation == newLocation
    }
}