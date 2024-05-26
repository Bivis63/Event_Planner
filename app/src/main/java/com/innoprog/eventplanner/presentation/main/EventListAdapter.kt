package com.innoprog.eventplanner.presentation.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.innoprog.eventplanner.R
import com.innoprog.eventplanner.domain.model.Event

class EventListAdapter(private val clickListener: EventItemViewHolder.ClickListener) :
    ListAdapter<Event, EventItemViewHolder>(EventItemDiffCallback()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.event_item, parent, false)
        return EventItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: EventItemViewHolder, position: Int) {
        val eventItem = getItem(position)

        holder.bind(eventItem, clickListener)
    }
}