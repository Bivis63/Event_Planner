package com.innoprog.eventplanner.presentation.main

import androidx.recyclerview.widget.DiffUtil
import com.innoprog.eventplanner.domain.model.Event

class EventItemDiffCallback : DiffUtil.ItemCallback<Event>() {

    override fun areItemsTheSame(oldItem: Event, newItem: Event): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Event, newItem: Event): Boolean {
        return oldItem == newItem
    }
}