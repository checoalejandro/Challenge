package com.acosta.challenge.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.acosta.challenge.databinding.LayoutIndexItemBinding
import com.acosta.challenge.models.IndexItem
import com.acosta.challenge.utils.getFormattedPercentage
import com.acosta.challenge.utils.getFormattedPrice

class IndicesAdapter(
    private val items: List<IndexItem>
) : RecyclerView.Adapter<IndicesAdapter.ViewHolder>() {

    inner class ViewHolder(
        private val binding: LayoutIndexItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        /**
         * Binds view with item.
         */
        fun bind(item: IndexItem) {
            binding.txtIssueId.text = item.issueID
            binding.txtIssuePrice.text = item.lastPrice.getFormattedPrice()
            binding.txtIssuePercentage.text = item.percentageChange.getFormattedPercentage()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutIndexItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount() = items.count()

}