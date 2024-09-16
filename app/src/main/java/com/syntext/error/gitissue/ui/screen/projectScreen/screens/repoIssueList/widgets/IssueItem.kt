package com.syntext.error.gitissue.ui.screen.projectScreen.screens.repoIssueList.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.syntext.error.gitissue.common.EmptySpace
import com.syntext.error.gitissue.data.IssueResp
import com.syntext.error.gitissue.ui.theme.TextColorGray
import com.syntext.error.gitissue.ui.theme.TextColorLightGray
import com.syntext.error.gitissue.utils.formatTimestamp

@Composable
fun IssueItem(
    issue: IssueResp.IssueItem?,
    onTap: (issue: IssueResp.IssueItem) -> Unit = {}
) {

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = MaterialTheme.colorScheme.onBackground
            )
            .clickable {
                if (issue != null) {
                    onTap(issue)
                }
            }
            .padding(vertical = 8.dp, horizontal = 4.dp),
    ) {

        Column(modifier = Modifier.padding(horizontal = 8.dp)) {

            Row(
                verticalAlignment = Alignment.CenterVertically,

                ) {
                Text(
                    issue?.title ?: "N/A",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontWeight = FontWeight.Normal,
                        color = Color.White,
                        fontSize = 16.sp,
                    ),
                    modifier = Modifier.weight(1f),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )


                Text(
                    formatTimestamp(issue?.created_at),
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = TextColorGray,
                        fontSize = 12.sp
                    ),
                    modifier = Modifier
                )


            }

            EmptySpace(4)

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(vertical = 4.dp)
            ) {


                AsyncImage(
                    model = issue?.user?.avatar_url,
                    contentDescription = "",
                    modifier = Modifier
                        .size(22.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )

                EmptySpace(4)

                Text(
                    issue?.user?.login ?: "N/A",
                    style = MaterialTheme.typography.bodySmall.copy(fontSize = 12.sp),
                    modifier = Modifier.padding(horizontal = 4.dp),
                    color = TextColorLightGray
                )


            }


        }

    }

}