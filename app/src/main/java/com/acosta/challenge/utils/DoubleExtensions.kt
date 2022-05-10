package com.acosta.challenge.utils

import java.text.DecimalFormat

/**
 * Formats given value as currency
 *
 * @param value double value
 * @return currency format.
 */
fun Double.getFormattedPrice() = DecimalFormat.getCurrencyInstance().format(this)

/**
 * Formats given value as percentage
 *
 * @param value double value
 * @return percentage format.
 */
fun Double.getFormattedPercentage() = DecimalFormat.getPercentInstance().format(this)