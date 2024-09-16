package com.syntext.error.gitissue.ui.screen.projectScreen.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.syntext.error.gitissue.common.EmptySpace
import com.syntext.error.gitissue.common.MarkDownViewer
import com.syntext.error.gitissue.ui.screen.projectScreen.AppBar
import com.syntext.error.gitissue.ui.theme.TextColorGray

@Composable
fun IssueDetailsScreen(
    title: String,
    body: String,
    userName: String,
    userImage: String,
    onNavigateBack: () -> Boolean
) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                color = MaterialTheme.colorScheme.onBackground
            )
    ) {
        Column(
            modifier = Modifier.verticalScroll(rememberScrollState()).padding(horizontal = 8.dp)
        ) {

            AppBar(
                title = "Issue Details",
                onNavigateBack = { onNavigateBack() },

                )

            EmptySpace(8)

            Text(
                title,
                style = MaterialTheme.typography.titleLarge,
                color = Color.White
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(vertical = 4.dp)
            ) {

                AsyncImage(
                    model = userImage,
                    contentDescription = "",
                    modifier = Modifier
                        .size(32.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )

                Text(
                    userName,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(horizontal = 4.dp),
                    color = Color.White
                )

            }

            EmptySpace(8)

            MarkDownViewer(
                markdownText = body,
                modifier = Modifier.fillMaxWidth()
            )
        }

    }

}