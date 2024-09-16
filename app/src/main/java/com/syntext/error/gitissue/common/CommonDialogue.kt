package com.syntext.error.gitissue.common

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.window.DialogProperties

@Composable
fun CommonDialog(
    title: String  = "Message",
    message: String,
    onDismiss: () -> Unit = {},
    onConfirm: () -> Unit = {},
    confirmButtonText: String = "OK",
    dismissButtonText: String = "Cancel",
    showDismiss: Boolean = false,
    properties: DialogProperties = DialogProperties(
        dismissOnBackPress = false,
        dismissOnClickOutside = false
    )
    ) {
    AlertDialog(
        properties = properties,
        onDismissRequest = onDismiss,
        title = {
            Text(text = title)
        },
        text = {
            Text(text = message)
        },
        confirmButton = {
            TextButton(onClick = onConfirm) {
                Text(text = confirmButtonText)
            }
        },
        dismissButton = {
            if (showDismiss) {
                TextButton(onClick = onDismiss) {
                    Text(text = dismissButtonText)
                }
            }
        }
    )
}