package com.syntext.error.gitissue.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.syntext.error.gitissue.R

val InconsolataFamily = FontFamily(
    Font(R.font.inconsolata_regular, FontWeight.Normal),

)


val SpaceMonoSansFamily = FontFamily(
    Font(R.font.space_mono_regular, FontWeight.Normal),
    Font(R.font.space_mono_bold, FontWeight.Bold)
)
// Set of Material typography styles to start with
val Typography = Typography(

    bodySmall = TextStyle(
        fontFamily = SpaceMonoSansFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        letterSpacing = 0.5.sp
    ),

    bodyMedium = TextStyle(
        fontFamily = SpaceMonoSansFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp,
    ),


    bodyLarge = TextStyle(
        fontFamily = SpaceMonoSansFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 18.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    ),

    titleLarge = TextStyle(
        fontFamily = SpaceMonoSansFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 24.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp,

    ),
    labelSmall = TextStyle(
        fontFamily = SpaceMonoSansFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    ),

    headlineLarge = TextStyle(
        fontFamily = SpaceMonoSansFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 36.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp,

        )

)