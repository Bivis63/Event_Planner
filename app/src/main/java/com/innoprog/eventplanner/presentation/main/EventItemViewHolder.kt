package com.innoprog.eventplanner.presentation.main

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.innoprog.eventplanner.databinding.EventItemBinding
import com.innoprog.eventplanner.domain.model.Event

class EventItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val binding = EventItemBinding.bind(view)

    fun bind(event: Event, clickListener: ClickListener) = with(binding) {
        title.text = event.eventName
        descriptions.text = event.description
        location.text = event.location
        city.text = event.city
        dataForEvent.text = event.data
        status.text = event.status
        weather.text = event.weather
        val imageResourceId = itemView.context.resources.getIdentifier(
            event.imageWeather, "drawable", itemView.context.packageName
        )
        weatherIcon.setImageResource(imageResourceId)



        itemView.setOnClickListener {
            clickListener.onClick(event)
        }
        itemView.setOnLongClickListener {
            clickListener.onLongClick(event)
            true
        }
    }

    interface ClickListener {
        fun onClick(eventItem: Event)
        fun onLongClick(eventItem: Event)
    }
}