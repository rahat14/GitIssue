package com.syntext.error.gitissue.utils

import android.util.Log
import java.util.Base64
import kotlin.io.encoding.ExperimentalEncodingApi

@OptIn(ExperimentalEncodingApi::class)
fun decodeBase64ToString(base64String: String): String {
    val decodedBytes = Base64.getDecoder().decode(base64String.replace("\n", ""))
    Log.d("TAG", "decodeBase64ToString:  before -> $base64String")
    Log.d("TAG", "decodeBase64ToString:  before -> ${String(decodedBytes)}")
    return String(decodedBytes)
}