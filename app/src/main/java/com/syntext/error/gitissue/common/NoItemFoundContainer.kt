package com.syntext.error.gitissue.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun NoItemFoundContainer(
    modifier: Modifier = Modifier,
    message: String = "No items found.",
    imageVector: ImageVector = Icons.Default.Search,
    contentDescription: String = "No items found icon",
    onSearchAgain: () -> Unit = {},
    showActionBtn  : Boolean = true
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Icon(
            imageVector = imageVector,
            contentDescription = contentDescription,
            modifier = Modifier
                .size(80.dp)
                .padding(bottom = 16.dp),
            tint = Color.White
        )

        // Display the message
        Text(
            text = message,
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center,
            color = Color.White
        )

        EmptySpace(16)

        if(showActionBtn){
            Button(
                onClick = { onSearchAgain() },
                modifier = Modifier.padding(top = 8.dp)
            ) {
                Text("Search Again")
            }
        }
    }
}
