package com.syntext.error.gitissue.common

import androidx.compose.foundation.MutatePriority
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.Coil
import com.syntext.error.gitissue.R
import com.syntext.error.gitissue.ui.theme.InconsolataFamily
import dev.jeziellago.compose.markdowntext.MarkdownText

@Composable
fun MarkDownViewer(modifier: Modifier = Modifier, markdownText: String) {
    val ctx = LocalContext.current
    MarkdownText(
        modifier = modifier
            .fillMaxWidth()
            .padding(12.dp),
        markdown = markdownText,
        //   fontResource = R.font.montserrat_medium,
        style = TextStyle(
            color = Color.White,
            fontSize = 14.sp,
        ),
        fontResource = R.font.inconsolata_regular,
        imageLoader = Coil.imageLoader(ctx)

    )
}