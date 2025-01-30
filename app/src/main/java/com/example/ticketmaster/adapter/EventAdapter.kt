package com.example.ticketmaster.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.ticketmaster.api.Event
import com.example.ticketmaster.R
import com.google.android.material.chip.Chip
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class EventAdapter(
    private val events: List<Event>,
    private val onEventClicked: (Event) -> Unit
) : RecyclerView.Adapter<EventAdapter.EventViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_event, parent, false)
        return EventViewHolder(view)
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        val event = events[position]
        holder.bind(event)
    }

    override fun getItemCount(): Int = events.size

    inner class EventViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    onEventClicked(events[position])
                }
            }
        }

        private val eventImage: ImageView = itemView.findViewById(R.id.eventImage)
        private val eventName: TextView = itemView.findViewById(R.id.eventName)
        private val eventDateTime: TextView = itemView.findViewById(R.id.eventDateTime)
        private val priceRange: TextView = itemView.findViewById(R.id.priceRange)
        private val statusChip: Chip = itemView.findViewById(R.id.statusChip)
        private val categoryChip: Chip = itemView.findViewById(R.id.categoryChip)

        @SuppressLint("SetTextI18n")
        fun bind(event: Event) {
            // Set event name
            eventName.text = event.name

            // Format and set date time
            val dateStr = event.dates.start.localDate
            val timeStr = event.dates.start.localTime
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
            val date = LocalDate.parse(dateStr, formatter)
            val formattedDate = date.format(DateTimeFormatter.ofPattern("MMM dd, yyyy"))
            eventDateTime.text = "$formattedDate • $timeStr"

            // Set price range
            priceRange.text = event.priceRanges?.let {
                "$${it[0].min} - $${it[0].max}"
            } ?: "Price TBA"

            // Set status chip
            statusChip.apply {
                text = event.dates.status.code.capitalize()
                setChipBackgroundColorResource(when(event.dates.status.code.toLowerCase()) {
                    "onsale" -> R.color.status_onsale
                    "cancelled" -> R.color.status_cancelled
                    "postponed" -> R.color.status_postponed
                    else -> R.color.text_secondary
                })
            }

            // Set category chip
            categoryChip.text = event.type.capitalize()

            // Load image with specific aspect ratio
            Glide.with(itemView.context)
                .load(event.images.firstOrNull { it.ratio == "16_9" }?.url ?: event.images[0].url)
                .transform(CenterCrop())
                .placeholder(R.drawable.placeholder_image)
                .error(R.drawable.error_image)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(eventImage)
        }
    }
}
