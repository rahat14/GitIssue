package com.syntext.error.gitissue.ui.screen.projectScreen.screens.repoIssueList

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.syntext.error.gitissue.common.CommonDialog
import com.syntext.error.gitissue.common.EmptySpace
import com.syntext.error.gitissue.common.NoItemFoundContainer
import com.syntext.error.gitissue.data.IssueResp
import com.syntext.error.gitissue.data.Repo
import com.syntext.error.gitissue.ui.screen.projectScreen.screens.projectSummaryScreen.ProjectSummaryEvent
import com.syntext.error.gitissue.ui.screen.projectScreen.screens.repoIssueList.widgets.IssueItem
import com.syntext.error.gitissue.ui.screen.projectScreen.screens.repoIssueList.widgets.TopBarWithSearch
import com.syntext.error.gitissue.ui.screen.searchListScreen.SearchListAction
import com.syntext.error.gitissue.ui.screen.searchListScreen.SearchListEvent
import com.syntext.error.gitissue.utils.observeAsActions
import org.koin.androidx.compose.koinViewModel


@Composable
fun ProjectIssueListScreen(
    currentRepo: Repo?,
    onNavigateBack: () -> Boolean,
    onNavigateToIssueDetails: (title: String, body: String, userName: String, userImage: String) -> Unit
) {

    val viewModel: RepoIssueViewmodel = koinViewModel()
    val state by viewModel.state.collectAsState()
    var search = ""

    currentRepo?.let {

        LaunchedEffect(currentRepo) {
            viewModel.postActions(
                RepoIssuesAction.GetInitialIssues(
                    currentRepo = currentRepo
                )
            )
        }
    }

    viewModel.actions.observeAsActions { repoIssueEvent ->
        when (repoIssueEvent) {

            ProjectSummaryEvent.DoNothing -> {

            }
        }
    }

    if(state.errorMessage != null){
        CommonDialog(
            message = state.errorMessage ?: "" ,
            onConfirm = {
                viewModel.postActions(RepoIssuesAction.InitDoNothing)
            },
        )
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.statusBars),
        contentAlignment = Alignment.Center,
    ) {

        ProjectIssueListScreenContent(
            modifier = Modifier.padding(top = 64.dp),
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
                    viewModel.postActions(RepoIssuesAction.LoadMoreSearchIssues(query = search))
                }
            },
            onRepoTap = { issue ->

                onNavigateToIssueDetails(
                    issue.title ?: "",
                    issue.body ?: "",
                    issue.user?.login ?: "",
                    issue.user?.avatar_url ?: ""
                )

            },

            )

        AnimatedVisibility(state.isLoading) {

            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center),
                    color = Color.White,
                )
            }


        }


        AnimatedVisibility(
            (state.isEmpty)
        ) {
            NoItemFoundContainer(
                showActionBtn = false
            )
        }


        TopBarWithSearch(
            title = "'${currentRepo?.name}' Issues",
            modifier = Modifier.align(alignment = Alignment.TopCenter),
            onSearch = { query ->
                search = query
                viewModel.postActions(RepoIssuesAction.SearchIssues(query = query))
            },
            onNavigateBack = { onNavigateBack() },
            onSearchCancel = {
                viewModel.postActions(RepoIssuesAction.ToggleSearchOff)
            },


            )


    }


}

@Composable
fun ProjectIssueListScreenContent(
    modifier: Modifier,
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
        if (reachedBottom && issueList.size >= 16) {
            onLoadMore()
        }
    }

    LazyColumn(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        state = listState
    ) {

        items(
            count = issueList.size,
//            key = { (issueList[it].id) ?: (System.currentTimeMillis() / 100) }
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


