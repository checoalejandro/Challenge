package com.acosta.challenge.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.acosta.challenge.databinding.LayoutIndexItemBinding
import com.acosta.challenge.models.IndexItem
import com.acosta.challenge.utils.getFormattedPercentage
import com.acosta.challenge.utils.getFormattedPrice

class IndicesAdapter : RecyclerView.Adapter<IndicesAdapter.ViewHolder>() {

    private val items = mutableListOf<IndexItem>()

    inner class ViewHolder(
        private val binding: LayoutIndexItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        /**
         * Binds view with item.
         */
        fun bind(item: IndexItem) {
            binding.txtIssueId.text = item.issueId
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

    /**
     * Updates content from RV.
     *
     * @param _items new content to be added.
     */
    fun setItems(_items: List<IndexItem>) {
        val diff = IndexItemDiffCallback(items, _items)
        val diffResult = DiffUtil.calculateDiff(diff)

        items.clear()
        items.addAll(_items)
        diffResult.dispatchUpdatesTo(this)
    }

    class IndexItemDiffCallback(
        val old: List<IndexItem>,
        val new: List<IndexItem>
    ) : DiffUtil.Callback() {

        override fun getOldListSize() = old.count()

        override fun getNewListSize() = new.count()

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return old[oldItemPosition].issueId == new[newItemPosition].issueId
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return old[oldItemPosition].lastPrice == new[newItemPosition].lastPrice
        }

    }
}