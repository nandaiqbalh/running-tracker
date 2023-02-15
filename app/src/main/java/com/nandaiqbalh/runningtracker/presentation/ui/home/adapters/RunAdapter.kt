package com.nandaiqbalh.runningtracker.presentation.ui.home.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.nandaiqbalh.runningtracker.R
import com.nandaiqbalh.runningtracker.data.local.database.entity.RunEntity
import com.nandaiqbalh.runningtracker.databinding.ItemRunBinding
import com.nandaiqbalh.runningtracker.other.TrackingUtility
import java.text.SimpleDateFormat
import java.util.*

class RunAdapter : RecyclerView.Adapter<RunAdapter.RunViewHolder>() {

	private lateinit var binding: ItemRunBinding

	inner class RunViewHolder(binding: ItemRunBinding) : RecyclerView.ViewHolder(binding.root)

	val diffCallback = object : DiffUtil.ItemCallback<RunEntity>() {
		override fun areItemsTheSame(oldItem: RunEntity, newItem: RunEntity): Boolean {
			return oldItem.id == newItem.id
		}

		override fun areContentsTheSame(oldItem: RunEntity, newItem: RunEntity): Boolean {
			return oldItem.hashCode() == newItem.hashCode()
		}
	}

	val differ = AsyncListDiffer(this, diffCallback)

	fun submitList(list: List<RunEntity>) = differ.submitList(list)

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RunViewHolder {
		binding = ItemRunBinding.inflate(LayoutInflater.from(parent.context), parent, false)

		return RunViewHolder(binding)
	}

	override fun getItemCount(): Int {
		return differ.currentList.size
	}

	override fun onBindViewHolder(holder: RunViewHolder, position: Int) {
		val run = differ.currentList[position]

		holder.itemView.apply {
			Glide.with(this).load(run.img).into(binding.ivRunImage)

			val calendar = Calendar.getInstance().apply {
				timeInMillis = run.timestamp
			}
			val dateFormat = SimpleDateFormat("dd.MM.yy", Locale.getDefault())
			binding.tvDate.text = dateFormat.format(calendar.time)

			val avgSpeed = "${run.avgSpeedInKMH}km/h"
			binding.tvAvgSpeed.text = avgSpeed

			val distanceInKm = "${run.distanceInMeters / 1000f}km"
			binding.tvDistance.text = distanceInKm

			binding.tvTime.text = TrackingUtility.getFormattedStopWatchTime(run.timeInMillis)

			val caloriesBurned = "${run.caloriesBurned}kcal"
			binding.tvCalories.text = caloriesBurned
		}
	}
}