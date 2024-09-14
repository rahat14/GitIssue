package com.syntext.error.gitissue.ui.screen.searchListScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.syntext.error.gitissue.R
import com.syntext.error.gitissue.common.EmptySpace
import com.syntext.error.gitissue.ui.theme.Orange
import com.syntext.error.gitissue.ui.theme.TextColorGray
import com.syntext.error.gitissue.utils.observeAsActions
import org.koin.androidx.compose.koinViewModel


@Composable
fun SearchListContainer() {

    val viewModel: SearchListViewmodel = koinViewModel()
    val state by viewModel.state.collectAsState()




    viewModel.actions.observeAsActions { searchListEvent ->
        when (searchListEvent) {
            SearchListEvent.NavigateBack -> {

            }

            SearchListEvent.NavigateToProjectRepo -> {

            }
        }
    }


    Scaffold {

        SearchListScreen()
    }

}

@Composable
fun SearchListScreen() {

    Column(modifier = Modifier.fillMaxSize()) {

        SearchItem()
        SearchItem()
        SearchItem()

    }

}


@Composable
fun SearchItem() {

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = MaterialTheme.colorScheme.onBackground
            )
            .padding(vertical = 8.dp),
    ) {

        Column(modifier = Modifier.padding(horizontal = 8.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(vertical = 4.dp)
            ) {

                Image(
                    painter = painterResource(R.drawable.ic_launcher_foreground),
                    contentDescription = "",
                    modifier = Modifier.size(20.dp),
                    contentScale = ContentScale.Crop
                )

                Text(
                    "User Name",
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(horizontal = 4.dp),
                    color = TextColorGray
                )

            }
            Text(
                "User Name",
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight.Medium,
                    color = Color.White
                ),
                modifier = Modifier.padding(vertical = 4.dp)
            )

            Text(
                "Lorem ipadk aajsdfn jasndjfnas asdjfaj nsjdfn kjasdnf sadjfnakjsdnfjnajksdnfjasdnfjk" +
                        " ansdfasdnjfknasdjfnjasn djfknasjkd fnjkasdnf kjansdkjf nassaj njkfna skjdnfjkas asdjfn a",
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
                    "2130",
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
                    "2130",
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(horizontal = 6.dp),
                    color = TextColorGray
                )

            }


        }

    }

}