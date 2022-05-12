package com.acosta.challenge.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.acosta.challenge.R
import com.acosta.challenge.adapters.index.IndexRow
import com.acosta.challenge.adapters.index.RowType
import com.acosta.challenge.databinding.LayoutIndexHeaderBinding
import com.acosta.challenge.databinding.LayoutIndexItemBinding
import com.acosta.challenge.models.IndexItem
import com.acosta.challenge.models.RaiseType
import com.acosta.challenge.utils.getFormattedPercentage
import com.acosta.challenge.utils.getFormattedPrice

class IndicesAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val rows = mutableListOf<IndexRow>()

    inner class HeaderViewHolder(
        private val binding: LayoutIndexHeaderBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(type: RaiseType) {
            binding.txtLabel.setText(
                when (type) {
                    RaiseType.RAISE -> R.string.top_raises
                    RaiseType.LOW -> R.string.top_losers
                    RaiseType.VOLUME -> R.string.top_volume
                    RaiseType.DEFAULT -> R.string.item_default
                }
            )
        }

    }

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

            val percentageColor = if (item.percentageChange > 0) {
                R.color.color_text_green
            } else if (item.percentageChange < 0) {
                R.color.color_text_red
            } else R.color.color_text_neutral
            binding.txtIssuePercentage.setTextColor(binding.root.context.getColor(percentageColor))
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (RowType.values()[viewType]) {
            RowType.HEADER -> HeaderViewHolder(
                LayoutIndexHeaderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            )
            RowType.INDEX -> ViewHolder(
                LayoutIndexItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (val itemRow = rows[position]) {
            is IndexRow.Index -> (holder as ViewHolder).bind(itemRow.item)
            is IndexRow.Header -> (holder as HeaderViewHolder).bind(itemRow.type)
        }
    }

    override fun getItemCount() = rows.count()

    override fun getItemViewType(position: Int): Int {
        return rows[position].rowType.ordinal
    }

    /**
     * Updates content from RV.
     *
     * @param _items new content to be added.
     */
    fun setItems(_items: List<IndexItem>) {
        val newGroup = _items
            .groupBy { it.riseLowTypeId }
            .toSortedMap(compareBy { RaiseType.getRaiseType(it) })
            .flatMap {
                listOf(
                    IndexRow.Header(RaiseType.getRaiseType(it.key)), *(it.value.map { index ->
                        (IndexRow.Index(index))
                    }).toTypedArray()
                )
            }
        val diff = IndexItemDiffCallback(rows, newGroup)
        val diffResult = DiffUtil.calculateDiff(diff)

        rows.clear()
        rows.addAll(newGroup)
        diffResult.dispatchUpdatesTo(this)
    }

    class IndexItemDiffCallback(
        val old: List<IndexRow>,
        val new: List<IndexRow>
    ) : DiffUtil.Callback() {

        override fun getOldListSize() = old.count()

        override fun getNewListSize() = new.count()

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            val oldItem = old[oldItemPosition]
            val newItem = new[newItemPosition]
            if (oldItem is IndexRow.Index && newItem is IndexRow.Header) {
                return false
            } else if (oldItem is IndexRow.Index && newItem is IndexRow.Index) {
                return oldItem.item.issueId == newItem.item.issueId
            } else if (oldItem is IndexRow.Header && newItem is IndexRow.Header) {
                return oldItem.type == newItem.type
            }
            return false
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            val oldItem = old[oldItemPosition]
            val newItem = new[newItemPosition]
            if (oldItem is IndexRow.Index && newItem is IndexRow.Header) {
                return false
            } else if (oldItem is IndexRow.Index && newItem is IndexRow.Index) {
                return oldItem.item.lastPrice == newItem.item.lastPrice
            } else if (oldItem is IndexRow.Header && newItem is IndexRow.Header) {
                return oldItem.type == newItem.type
            }
            return false
        }

    }
}