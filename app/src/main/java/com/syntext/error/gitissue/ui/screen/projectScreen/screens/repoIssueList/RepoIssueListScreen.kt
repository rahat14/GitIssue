package com.syntext.error.gitissue.ui.screen.projectScreen.screens.repoIssueList

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.syntext.error.gitissue.common.EmptySpace
import com.syntext.error.gitissue.data.IssueResp
import com.syntext.error.gitissue.data.Repo
import com.syntext.error.gitissue.ui.theme.TextColorGray
import com.syntext.error.gitissue.ui.theme.TextColorLightGray
import com.syntext.error.gitissue.utils.formatTimestamp
import org.koin.androidx.compose.koinViewModel


@Composable
fun ProjectIssueListScreen(currentRepo: Repo?) {

    val viewModel: RepoIssueViewmodel = koinViewModel()
    val state by viewModel.state.collectAsState()

    currentRepo?.let {

        LaunchedEffect(currentRepo) {
            viewModel.postActions(
                RepoIssuesAction.GetInitialIssues(
                    currentRepo = currentRepo
                )
            )
        }
    }

    Box {

        ProjectIssueListScreenContent(
            issueList = if (state.isSearchOn) {
                state.currentSearchList
            } else {
                state.issueList
            },
            isBottomLoading = state.isLoadMore,
            onLoadMore = {
                if (!state.isSearchOn && !state.isLoadMore) {
                    viewModel.postActions(RepoIssuesAction.LoadMoreIssues)
                } else if (state.isSearchOn && !state.isLoadMore) {
                    viewModel.postActions(RepoIssuesAction.LoadMoreSearchIssues(query = ""))
                }
            },
            onRepoTap = {

            }
        )

        AnimatedVisibility(state.isLoading) {

            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.TopEnd),
                color = Color.White,
            )

        }

    }


}

@Composable
fun ProjectIssueListScreenContent(
    issueList: List<IssueResp.IssueItem> = emptyList(),
    isBottomLoading: Boolean = false,
    onLoadMore: () -> Unit,
    onRepoTap: (issue: IssueResp.IssueItem) -> Unit
) {

    val listState = rememberLazyListState()

    // observe list scrolling
    val reachedBottom: Boolean by remember {
        derivedStateOf {
            val lastVisibleItem = listState.layoutInfo.visibleItemsInfo.lastOrNull()
            lastVisibleItem?.index != 0 && lastVisibleItem?.index == listState.layoutInfo.totalItemsCount - 1 // buffer
        }
    }

    // load more if scrolled to bottom
    LaunchedEffect(reachedBottom) {
        if (reachedBottom) {
            onLoadMore()
        }
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        state = listState
    ) {

        items(
            count = issueList.size,
            key = { issueList[it].id ?: 0 }
        ) { index ->
            IssueItem(
                issue = issueList[index],
            ) {
                onRepoTap(it)
            }
            if (index < issueList.lastIndex) {
                HorizontalDivider()
            }


        }

        if (isBottomLoading) {
            item {
                CircularProgressIndicator(
                    color = Color.White
                )
            }
        }

        item {
            EmptySpace(20)
        }

    }


}

@Preview
@Composable
fun ProjectIssueListScreenPreview() {

    IssueItem(issue = null) {

    }
}


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
                // onTap(issue)
            }
            .padding(vertical = 8.dp),
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