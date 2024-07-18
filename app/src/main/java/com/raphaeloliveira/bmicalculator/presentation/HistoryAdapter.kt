package com.raphaeloliveira.bmicalculator.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.raphaeloliveira.bmicalculator.databinding.ItemHistoryBinding
import com.raphaeloliveira.bmicalculator.domain.ui.HistoryUiData

class HistoryAdapter :
    ListAdapter<HistoryUiData, HistoryAdapter.HistoryViewHolder>(diffCallback()) {

    private var clickListener: (HistoryUiData) -> Unit = {}
    private var deleteListener: (HistoryUiData) -> Unit = {}

    fun setOnClickListener(onClick: (HistoryUiData) -> Unit) {
        clickListener = onClick
    }

    fun setOnDeleteListener(onDelete: (HistoryUiData) -> Unit) {
        deleteListener = onDelete
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val binding = ItemHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HistoryViewHolder(binding, clickListener, deleteListener)
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        val history = getItem(position)
        holder.bind(history)
    }

    class HistoryViewHolder(
        private val binding: ItemHistoryBinding,
        private val clickListener: (HistoryUiData) -> Unit,
        private val deleteListener: (HistoryUiData) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(history: HistoryUiData) {
            binding.apply {
                tvClassificationHistory.text = history.classification
                tvDate.text = history.date
                tvWeightHistory.text = history.weight
                tvHeightHistory.text = history.height
                tvBmiHistory.text = history.result

                ivDelete.setOnClickListener { deleteListener(history) }
                root.setOnClickListener { clickListener(history) }
            }
        }
    }

    class diffCallback  : DiffUtil.ItemCallback<HistoryUiData>() {
        override fun areItemsTheSame(oldItem: HistoryUiData, newItem: HistoryUiData): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: HistoryUiData, newItem: HistoryUiData): Boolean {
            return oldItem.classification == newItem.classification
                    && oldItem.date == newItem.date
                    && oldItem.weight == newItem.weight
                    && oldItem.height == newItem.height
                    && oldItem.result == newItem.result
        }
    }
}