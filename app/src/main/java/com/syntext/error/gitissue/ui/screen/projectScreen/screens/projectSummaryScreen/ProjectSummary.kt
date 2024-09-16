package com.syntext.error.gitissue.ui.screen.projectScreen.screens.projectSummaryScreen

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Share
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.syntext.error.gitissue.R
import com.syntext.error.gitissue.common.AppBar
import com.syntext.error.gitissue.common.CommonDialog
import com.syntext.error.gitissue.common.EmptySpace
import com.syntext.error.gitissue.common.MarkDownViewer
import com.syntext.error.gitissue.data.Repo
import com.syntext.error.gitissue.ui.screen.searchListScreen.SearchListAction
import com.syntext.error.gitissue.ui.screen.searchListScreen.SearchListEvent
import com.syntext.error.gitissue.ui.theme.Orange
import com.syntext.error.gitissue.ui.theme.TextColorGray
import com.syntext.error.gitissue.utils.observeAsActions
import org.koin.androidx.compose.koinViewModel

@Composable
fun ProjectSummaryScreen(currentRepo: Repo?, onNavigateBack: () -> Boolean) {

    val viewModel: ProjectSummaryViewmodel = koinViewModel()
    val state by viewModel.state.collectAsState()

    viewModel.actions.observeAsActions { projectSummeryEvent ->
        when (projectSummeryEvent) {
            ProjectSummaryEvent.DoNothing ->{}
        }
    }

    if(state.errorMessage != null){
        CommonDialog(
            message = state.errorMessage ?: "" ,
            onConfirm = {
                viewModel.postActions(ProjectSummaryAction.InitDoNothing)
            },
        )
    }


    LaunchedEffect(currentRepo) {
        viewModel.postActions(
            ProjectSummaryAction.GetReadme(
                owner = currentRepo?.owner?.login ?: "",
                repoName = currentRepo?.name ?: ""
            )
        )
    }


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                color = MaterialTheme.colorScheme.onBackground
            )
            .windowInsetsPadding(WindowInsets.statusBars),

        ) {
        val modifier = Modifier.padding(horizontal = 8.dp)

        AppBar(
            modifier = Modifier.align(Alignment.TopCenter),
            title = currentRepo?.name ?: "n?a"
        ) { onNavigateBack() }


        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 64.dp)
                .verticalScroll(rememberScrollState())
        ) {

            EmptySpace(4)

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = modifier.padding(vertical = 4.dp)
            ) {

                AsyncImage(
                    model = currentRepo?.owner?.avatar_url ?: "",
                    contentDescription = "",
                    modifier = Modifier.size(20.dp),
                    contentScale = ContentScale.Crop
                )

                Text(
                    currentRepo?.owner?.login ?: "",
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(horizontal = 4.dp),
                    color = TextColorGray
                )

            }

            Text(
                currentRepo?.name ?: "N?A",
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight.Medium,
                    color = Color.White
                ),
                modifier = modifier.padding(vertical = 8.dp)
            )



            Text(
                currentRepo?.description ?: "N?A",
                style = MaterialTheme.typography.bodySmall.copy(
                    color = Color.White.copy(alpha = 0.7f),
                    fontSize = 13.sp,
                    letterSpacing = 1.5.sp
                ),
                modifier = modifier
            )

            EmptySpace(12)

            Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    Icons.Rounded.Share,
                    contentDescription = "share icon",
                    modifier = Modifier.size(16.dp),
                    tint = Color.White
                )


                EmptySpace(2)


                Text(
                    currentRepo?.html_url ?: "N?A",
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = Color.White,
                        fontSize = 14.sp,
                        letterSpacing = 1.5.sp
                    ),
                )


            }

            EmptySpace(4)

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = modifier.padding(vertical = 4.dp)
            ) {

                Icon(
                    Icons.Rounded.Star,
                    contentDescription = "Star",
                    modifier = Modifier.size(16.dp),
                    tint = Color.White
                )

                EmptySpace(6)

                Text(
                    currentRepo?.stargazers_count.toString() ?: "N?A",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.White
                )

                Text(
                    "Stars",
                    style = MaterialTheme.typography.bodySmall.copy(fontSize = 12.sp),
                    modifier = Modifier.padding(horizontal = 6.dp),
                    color = Color.White.copy(alpha = 0.7f)
                )

                Image(
                    painter = painterResource(R.drawable.fork_icon),
                    modifier = Modifier.size(22.dp),
                    contentDescription = "",
                    colorFilter = ColorFilter.tint(Color.White)
                )

                EmptySpace(6)

                Text(
                    currentRepo?.forks_count.toString() ?: "N?A",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.White
                )

                Text(
                    "Forks",
                    style = MaterialTheme.typography.bodySmall.copy(fontSize = 12.sp),
                    modifier = Modifier.padding(horizontal = 6.dp),
                    color = Color.White.copy(alpha = 0.7f)
                )


            }

            HorizontalDivider(
                thickness = 1.dp,
                color = Color.White.copy(alpha = 0.5f),
                modifier = Modifier.padding(vertical = 8.dp)
            )

            Text(
                "README.md",
                style = MaterialTheme.typography.bodySmall.copy(
                    color = Color.White.copy(alpha = 0.7f),
                    fontSize = 13.sp,
                    letterSpacing = 1.5.sp
                ),
                modifier = modifier
            )

            AnimatedVisibility(
                state.isLoading
            ) {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(
                        color = Color.White,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }


            if (state.readme.isNotEmpty()) {
                MarkDownViewer(markdownText = state.readme)
            }
        }


    }


}