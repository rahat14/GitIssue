package com.syntext.error.gitissue.ui.screen.searchListScreen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.syntext.error.gitissue.common.EmptySpace
import com.syntext.error.gitissue.common.NoItemFoundContainer
import com.syntext.error.gitissue.data.Repo
import com.syntext.error.gitissue.ui.theme.Orange
import com.syntext.error.gitissue.ui.theme.TextColorGray
import com.syntext.error.gitissue.utils.observeAsActions
import org.koin.androidx.compose.koinViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchListContainer(
    query: String = "",
    onNavigateBack: () -> Boolean,
    onNavigateToProjectScreen: (Repo) -> Unit
) {

    val viewModel: SearchListViewmodel = koinViewModel()
    val state by viewModel.state.collectAsState()


    LaunchedEffect(query) {
        viewModel.postActions(SearchListAction.SearchRepo(query))
    }




    viewModel.actions.observeAsActions { searchListEvent ->
        when (searchListEvent) {
            is SearchListEvent.NavigateBack -> {
                onNavigateBack()
            }

            is SearchListEvent.NavigateToProjectRepo -> onNavigateToProjectScreen(searchListEvent.repo)
        }
    }


    Box(
        modifier = Modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.statusBars),
        contentAlignment = Alignment.TopCenter,

        ) {

        SearchListScreen(
            modifier = Modifier.padding(top = 64.dp),
            repoList = state.searchList,
            isBottomLoading = state.isLoadMore,
            onLoadMore = {
                if (!state.isLoadMore) {
                    viewModel.postActions(
                        action = SearchListAction.LoadMore(
                            query
                        )
                    )
                } else {
                    // show message

                }
            },
            onRepoTap = {
                viewModel.postActions(SearchListAction.NavigateToProjectRepo(it))

            }
        )

        TopAppBar(
            title = {
                Text(
                    "Result For '$query'", color = Color.White,
                    fontSize = 14.sp
                )
            },
            navigationIcon = {
                IconButton(onClick = { onNavigateBack() }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = Color.White
                    )
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color(0xff161616)
            ),
            modifier = Modifier
                .align(Alignment.TopCenter)
                .background(Color.White)
        )

        AnimatedVisibility(
            state.isLoading
        ) {

            Box(
                modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
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
                onSearchAgain = {
                    viewModel.postActions(SearchListAction.NavigateToSearchScreen)
                }
            )
        }


    }


}

@Composable
fun SearchListScreen(
    modifier: Modifier = Modifier,
    repoList: List<Repo> = emptyList(),
    isBottomLoading: Boolean = false,
    onLoadMore: () -> Unit,
    onRepoTap: (repo: Repo) -> Unit
) {
    val listState = rememberLazyListState()

    // observe list scrolling
    val reachedBottom: Boolean by remember {
        derivedStateOf {
            val lastVisibleItem = listState.layoutInfo.visibleItemsInfo.lastOrNull()
            lastVisibleItem?.index != 0 && lastVisibleItem?.index == listState.layoutInfo.totalItemsCount - 2 // buffer
        }
    }

    // load more if scrolled to bottom
    LaunchedEffect(reachedBottom) {
        if (reachedBottom && repoList.size >= 16) {
            onLoadMore()
        }
    }


    LazyColumn(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        state = listState
    ) {

        items(
            count = repoList.size,
            key = { repoList[it].id ?: 0 }
        ) { index ->
            RepoItem(
                repo = repoList[index],
            ) {
                onRepoTap(it)
            }
            if (index < repoList.lastIndex) {
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

    }

}


@Composable
fun RepoItem(repo: Repo, onTap: (repo: Repo) -> Unit = {}) {

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = MaterialTheme.colorScheme.onBackground
            )
            .clickable {
                onTap(repo)
            }
            .padding(vertical = 8.dp),
    ) {

        Column(modifier = Modifier.padding(horizontal = 8.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(vertical = 4.dp)
            ) {

                AsyncImage(
                    model = repo.owner?.avatar_url,
                    contentDescription = "",
                    modifier = Modifier
                        .size(20.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )

                Text(
                    repo.owner?.login ?: "N/A",
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(horizontal = 4.dp),
                    color = TextColorGray
                )

            }
            Text(
                repo.name ?: "N/A",
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight.Medium,
                    color = Color.White,
                    fontSize = 16.sp
                ),
                modifier = Modifier.padding(vertical = 4.dp)
            )

            Text(
                repo.description ?: "N/A",
                style = MaterialTheme.typography.bodySmall.copy(
                    color = Color.White,
                    fontSize = 14.sp,
                    letterSpacing = 1.5.sp
                ),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

            EmptySpace(2)

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(vertical = 4.dp)
            ) {

                Icon(
                    Icons.Rounded.Star,
                    contentDescription = "Star",
                    modifier = Modifier.size(16.dp),
                    tint = Orange
                )

                Text(
                    repo.stargazers_count.toString(),
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(horizontal = 6.dp),
                    color = TextColorGray
                )

                Box(
                    modifier = Modifier
                        .size(10.dp)
                        .clip(CircleShape)
                        .background(Color.Cyan)
                ) {}




                Text(
                    repo.language.toString(),
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(horizontal = 6.dp),
                    color = TextColorGray
                )

            }


        }

    }

}


