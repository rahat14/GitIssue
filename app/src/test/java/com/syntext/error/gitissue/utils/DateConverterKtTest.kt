package com.syntext.error.gitissue.utils

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.*

import org.junit.jupiter.api.Test

class DateConverterKtTest {

    @Test
    fun checkDateFormatWithNull() {
        val convertedDate = formatTimestamp(timestamp = null)
        assertEquals("" , convertedDate)
    }

    @Test
    fun checkDateFormatWithEmptyValue() {
        val convertedDate = formatTimestamp(timestamp = "")
        assertEquals("" , convertedDate)
    }


    @Test
    fun checkDateFormatWithNonValue() {
        val convertedDate = formatTimestamp(timestamp = "sdfg sdf g")
        assertEquals("" , convertedDate)
    }

}