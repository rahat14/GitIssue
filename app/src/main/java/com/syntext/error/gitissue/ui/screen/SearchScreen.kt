package com.syntext.error.gitissue.ui.screen


import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.syntext.error.gitissue.R
import com.syntext.error.gitissue.common.EmptySpace
import com.syntext.error.gitissue.ui.theme.GitIssueTheme
import com.syntext.error.gitissue.ui.theme.Orange
import com.syntext.error.gitissue.ui.theme.TextColorGray


@Composable
fun RepoSearchScreen() {
    var searchText by rememberSaveable { mutableStateOf("") }

    Box(modifier = Modifier.fillMaxSize()) {

        Column(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                "GitIssue",
                style = MaterialTheme.typography.headlineLarge
            )

            EmptySpace(35)

            TextField(
                value = searchText,
                onValueChange = {
                    searchText = it
                },
                singleLine = true,
                placeholder = {
                    Text(
                        "Search For Repositories",
                        style = MaterialTheme.typography.bodyLarge
                    )
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search Icon",
                        tint = Color.Gray
                    )
                },
                trailingIcon = {
                    if (searchText.isNotEmpty()) {
                        IconButton(onClick = { searchText = "" }) {
                            Icon(
                                imageVector = Icons.Default.Clear,
                                contentDescription = "Clear Text",
                                tint = Color.Gray
                            )
                        }
                    }
                },
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
                    .background(Color(0xFFF0F0F0), RoundedCornerShape(25.dp))
                    .clip(RoundedCornerShape(25.dp)),
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    unfocusedPlaceholderColor = Color.Gray,
                    focusedContainerColor = Color.Transparent,

                    ),
                keyboardActions = KeyboardActions(

                    onDone = {
                        ///focusManager.clearFocus()
                    }
                ),
            )

            EmptySpace()

            SearchButton {
                Log.d("TAG", "RepoSearchScreen: ")
            }

        }


    }

}





@Composable
fun SearchButton(onSearchClick: () -> Unit) {
    Button(
        onClick = onSearchClick,
        shape = RoundedCornerShape(50),
        elevation = ButtonDefaults.buttonElevation(
            defaultElevation = 6.dp,
            pressedElevation = 6.dp,
            hoveredElevation = 6.dp,
            focusedElevation = 6.dp
        ),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFF6200EE),
            contentColor = Color.White
        ),
        modifier = Modifier
            .height(46.dp)


    ) {

        Text("Search", modifier = Modifier.padding(horizontal = 48.dp))
    }
}


@Preview(showBackground = true, device = "id:pixel_8", showSystemUi = true)
@Composable
fun RepoSearchScreenPreview() {

    GitIssueTheme {
        RepoSearchScreen()
    }

}
