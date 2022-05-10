package com.acosta.challenge.models

import android.content.Context
import android.util.Log
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

typealias IPCHistoryResponse = ArrayList<IPCHistory>

data class IPCHistory(
    val date: String,
    val price: Double,
    val percentageChange: Double,
    val volume: Long,
    val change: Double
) {
    /**
     * Returns date in readable format.
     * This will parse default format.
     */
    fun getFormattedDate(): String {
        return try {
            formatter.parse(date)?.let {
                DateFormat.getDateTimeInstance().format(it)
            } ?: ""
        } catch (e: Exception) {
            Log.e(TAG, "getFormattedDate: Unable to parse current date '$date'", e)
            ""
        }
    }

    companion object {
        private const val DATE_PATTERN = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX"
        private val TAG = "IPCHistory"
        private val formatter = SimpleDateFormat(DATE_PATTERN, Locale.US)
    }
}
