package com.acosta.challenge.adapters.index

import com.acosta.challenge.models.IndexItem
import com.acosta.challenge.models.RaiseType

/**
 * Used for adapter. Will separate whenever there is
 * an index from a header using sealed class [IndexRow]
 *
 * @author @seacosta
 */
enum class RowType {
    INDEX,
    HEADER
}

/**
 * Wrappers for content from sealed class.
 */
sealed class IndexRow(val rowType: RowType) {
    data class Index(val item: IndexItem) : IndexRow(RowType.INDEX)

    data class Header(val type: RaiseType) : IndexRow(RowType.HEADER)
}