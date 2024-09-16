package com.syntext.error.gitissue.ui.screen.projectScreen.screens.repoIssueList.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Preview
@Composable
fun TopPReview() {
    TopBarWithSearch()
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarWithSearch(
    modifier: Modifier = Modifier,
    title: String = "Demo App Bar with Search",
    onSearch: (query: String) -> Unit = {},
    onNavigateBack: () -> Unit = {},
    onSearchCancel: () -> Unit = {}
) {

    var isSearchActive by remember { mutableStateOf(false) }
    var searchText by rememberSaveable { mutableStateOf("") }
    val focusManager = LocalFocusManager.current
    val focusRequester = remember { FocusRequester() }

    TopAppBar(
        title = {
            if (isSearchActive) {
                TextField(
                    value = searchText,
                    onValueChange = {
                        searchText = it
                    },
                    modifier = Modifier
                        .focusRequester(focusRequester)
                        .padding(horizontal = 4.dp, vertical = 0.dp)
                        .fillMaxWidth(),
                    singleLine = true,
                    placeholder = {
                        Text(
                            "Search For Issues",
                            style = MaterialTheme.typography.bodyLarge,
                            color = Color.White.copy(alpha = 0.7f),
                            fontSize = 14.sp
                        )
                    },
                    textStyle = LocalTextStyle.current.copy(fontSize = 14.sp),
                    colors = TextFieldDefaults.colors(
                        unfocusedContainerColor = Color.Transparent,
                        unfocusedPlaceholderColor = Color.Gray,
                        focusedContainerColor = Color.Transparent,
                        focusedTextColor = Color.White,
                        unfocusedLabelColor = Color.White.copy(alpha = 0.8f),
                        unfocusedTextColor = Color.White.copy(alpha = 0.8f),
                        cursorColor = Color.White,
                        focusedIndicatorColor = Color.White.copy(alpha = 0.5f),
                        unfocusedIndicatorColor = Color.Transparent,
                    ),
                    keyboardActions = KeyboardActions(
                        onSearch = {
                            if (searchText.isNotEmpty()) {
                                focusManager.clearFocus()
                                onSearch(searchText)
                            }

                        }
                    ),
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Search, // Show search action on keyboard
                        keyboardType = KeyboardType.Text
                    ),
                )
                LaunchedEffect(isSearchActive) {
                    if (isSearchActive) {
                        focusRequester.requestFocus()
                    }
                }
            } else {
                Text(
                    title, color = Color.White,
                    fontSize = 14.sp,
                )
            }

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
        modifier = modifier
            .background(Color.White),
        actions = {
            IconButton(
                onClick = {
                    isSearchActive = !isSearchActive

                    if (!isSearchActive) {
                        focusManager.clearFocus()
                        searchText = "" // Clear search text when closing
                        onSearchCancel()
                    }
                }
            ) {
                Icon(
                    imageVector = if (isSearchActive) Icons.Default.Close else Icons.Default.Search,
                    contentDescription = "Search Icon",
                    tint = Color.White
                )
            }
        }
    )
}